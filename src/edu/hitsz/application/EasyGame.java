package edu.hitsz.application;

import java.awt.*;

public class EasyGame extends Game{
    public EasyGame(boolean musicOn) {
        super(musicOn);
        System.out.println("进入简单模式");
        setDifficulty(1);
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop, null);
    }
}
