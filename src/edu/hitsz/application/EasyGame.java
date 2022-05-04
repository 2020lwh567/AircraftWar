package edu.hitsz.application;

import edu.hitsz.aircraftFactory.EliteEnemyFactory;
import edu.hitsz.aircraftFactory.MobEnemyFactory;

import java.awt.*;

public class EasyGame extends Game{
    public EasyGame(boolean musicOn) {
        super(musicOn);
        System.out.println("进入简单模式");
        setDifficulty(1);

        // 产生精英敌机概率
        propOfGeneratingEliteAircraft = 0.2;
    }

    /**简单模式不随时间提高难度*/
    @Override
    public boolean increaseDifficultyFlag(){
        return false;
    }

    @Override
    public void increaseDifficulty() {}

    /**产生敌机*/
    @Override
    protected void generateEnemyAircraft() {
        generateMobAndEliteAircraft();
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop, null);
    }
}
