package edu.hitsz.application;

import java.awt.*;

public class MediumGame extends Game{
    public MediumGame(boolean musicOn) {
        super(musicOn);
        System.out.println("进入普通模式");
        setDifficulty(2);

        // 产生精英敌机概率
        propOfGeneratingEliteAircraft = 0.2;

        // 难度提升周期
        increaseDifficultyCycle.setCycleDuration(3000);
    }

    /**提升游戏难度*/
    @Override
    public void increaseDifficulty() {
        //增加同时出现的敌机数
        enemyMaxNumber++;

        //增加普通敌机血量
        mobHp += 5;

        //增大敌机速度
        if (Math.random()>propOfIncreasingEnemySpeedy){
            speedyOfMob++;
            speedyOfElite++;
        }

        //减小英雄机发射周期
        heroShootCycle.decreaseCycleDuration(10);

        //减小敌机发射周期
        enemyShootCycle.decreaseCycleDuration(5);

        System.out.printf("提升难度!最大敌机数%d,普通敌机速度%d,精英敌机速度%d,普通敌机血量%d,敌机发射周期%d\n",enemyMaxNumber, speedyOfMob, speedyOfElite, mobHp,enemyShootCycle.CycleDuration);
    }

    @Override
    protected void generateEnemyAircraft() {
        generateMobAndEliteAircraft();
        generateBossAircraft();
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop, null);
    }
}
