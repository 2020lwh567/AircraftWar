package edu.hitsz.application;

public class HardGame extends Game{
    public HardGame(boolean musicOn) {
        System.out.println("进入困难模式");
        setMusicFlag(musicOn);
    }
}
