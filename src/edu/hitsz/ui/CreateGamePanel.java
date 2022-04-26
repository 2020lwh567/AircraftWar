package edu.hitsz.ui;

import edu.hitsz.application.EasyGame;
import edu.hitsz.application.Game;
import edu.hitsz.application.HardGame;
import edu.hitsz.application.MediumGame;

public class CreateGamePanel {
    private static int difficulty;
    private static boolean musicOn;

    public static void setDifficulty(int x){
        difficulty = x;
    }

    public static void setMusicOn(int x){
        musicOn = (x==0);

    }

    public static Game createGamePanel(){
        if (difficulty==1){
            return new EasyGame(musicOn);
        }else if(difficulty==2){
            return new MediumGame(musicOn);
        }else{
            return new HardGame(musicOn);
        }
    }
}
