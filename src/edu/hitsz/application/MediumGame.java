package edu.hitsz.application;

public class MediumGame extends Game{
    public MediumGame(boolean musicOn) {
        System.out.println("进入普通模式");
        setMusicFlag(musicOn);
    }
}
