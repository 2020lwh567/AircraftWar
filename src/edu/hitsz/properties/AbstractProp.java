package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.List;

/**
 * 抽象道具类
 */

public abstract class AbstractProp extends AbstractFlyingObject {
    private int startTime = 0;//获得道具时间
    private int linitTime = (int)2e9;//道具使用时长限制

    public AbstractProp(int locationX, int locationY) {
        super(locationX, locationY, 0, 2);
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getStartTime(){
        return this.startTime;
    }

    //道具生效函数
    public abstract void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet);

    //是否超过使用限时
    public abstract boolean timeLimitExceeded(int currentTime);

    //道具失效函数
    public abstract void setInvalid(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet);
}
