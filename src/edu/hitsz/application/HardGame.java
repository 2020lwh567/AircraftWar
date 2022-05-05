package edu.hitsz.application;

import edu.hitsz.aircraftFactory.BossEnemyFactory;
import edu.hitsz.aircraftFactory.EliteEnemyFactory;
import edu.hitsz.aircraftFactory.MobEnemyFactory;

import java.awt.*;

import static java.lang.Math.min;

public class HardGame extends Game{
    public HardGame(boolean musicOn) {
        super(musicOn);
        System.out.println("进入困难模式");
        setDifficulty(3);

        // 产生精英敌机概率
        propOfGeneratingEliteAircraft = 0.2;

        // 难度提升周期
        increaseDifficultyCycle.setCycleDuration(10000);
    }

    /**提升游戏难度*/
    @Override
    public void increaseDifficulty() {
        //增加同时出现的敌机数
        enemyMaxNumber++;

        //增加boss血量
        bossHp += 100;

        //增加精英机血量
        eliteHp += 50;

        //增大敌机速度
        if (Math.random()>propOfIncreasingEnemySpeedy){
            speedyOfMob++;
            speedyOfElite++;
        }

        //减小英雄机发射周期
        heroShootCycle.decreaseCycleDuration(6);

        //减小敌机发射周期
        enemyShootCycle.decreaseCycleDuration(5);

        //减小敌机产生周期
        generateEnemyCycle.decreaseCycleDuration(5);

        //提升精英机产生概率,最大为0.9
        propOfGeneratingEliteAircraft = min(propOfGeneratingEliteAircraft+0.04, 0.9);

        System.out.printf("提升难度：最大敌机数%d,普通敌机速度%d,精英敌机速度%d,精英敌机血量%d,敌机发射周期%d,敌机产生周期%d,精英机产生概率%f\n",enemyMaxNumber, speedyOfMob, speedyOfElite, eliteHp, enemyShootCycle.CycleDuration, generateEnemyCycle.CycleDuration, propOfGeneratingEliteAircraft);
    }

    @Override
    protected void generateEnemyAircraft() {
        generateMobAndEliteAircraft();
        generateBossAircraft();
    }

    @Override
    public void drawBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop, null);
    }
}
