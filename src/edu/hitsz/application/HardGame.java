package edu.hitsz.application;

import java.awt.*;

public class HardGame extends Game{
    public HardGame(boolean musicOn) {
        super(musicOn);
        System.out.println("进入困难模式");
        setDifficulty(3);
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop, null);
    }
}
