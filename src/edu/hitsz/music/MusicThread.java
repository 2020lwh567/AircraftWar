package edu.hitsz.music;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

import static edu.hitsz.application.Game.musicObj;

public class MusicThread extends Thread {


    //音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private int cycleFlag;
    public int stopFlag;//最后要改回来

    /**flag=0表示无需重复播放，flag=1表示需要重复播放*/
    public MusicThread(String filename, int cycleFlag) {
        //初始化filename
        this.filename = filename;
        this.cycleFlag = cycleFlag;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SoureDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            synchronized (musicObj) {
                while (numBytesRead != -1 && stopFlag == 0) {
                    System.out.println("in while");
                    synchronized (musicObj) {
                        System.out.println("play get lock");
                        //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                        numBytesRead = source.read(buffer, 0, buffer.length);
                        //通过此源数据行将数据写入混频器
                        if (numBytesRead != -1 && stopFlag == 0) {
                            dataLine.write(buffer, 0, numBytesRead);
                        }
                        if (stopFlag == 1) {
                            System.out.println("inside stopFlag===1");
                            musicObj.wait();
                            this.stopFlag=0;
                            System.out.println("inside stopFlag===0");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }

    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        this.stopFlag = 0;
        //单次播放
        if (cycleFlag == 0) {
            synchronized (musicObj) {
                play(stream);
                System.out.println("running music0");
                this.setInterrupt();
                musicObj.notify();
                System.out.println("发射完毕,notify");
            }
        }
        //循环播放
        while (cycleFlag == 1 && stopFlag==0) {
            synchronized (musicObj) {
                System.out.println("running music1");
                play(stream);
                stream = new ByteArrayInputStream(samples);
//                if(stopFlag==1){
//                    try {
//                        musicObj.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    }

    public void setInterrupt(){
        System.out.println("get interrupted");
        this.stopFlag = 1;
    }
}


