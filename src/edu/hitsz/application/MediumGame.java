package edu.hitsz.application;

import java.awt.*;

public class MediumGame extends Game{
    public MediumGame(boolean musicOn) {
        System.out.println("进入普通模式");
        setMusicFlag(musicOn);
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop, null);
    }
}
