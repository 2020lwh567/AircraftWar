package edu.hitsz.application;

public class EasyGame extends Game{
    public EasyGame(boolean musicOn) {
        System.out.println("进入简单模式");
        setMusicFlag(musicOn);
    }
}
