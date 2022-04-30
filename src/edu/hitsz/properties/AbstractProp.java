package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 抽象道具类
 */

public abstract class AbstractProp extends AbstractFlyingObject {
    /**获得道具时间*/
    private int startTime = 0;

    public AbstractProp(int locationX, int locationY) {
        super(locationX, locationY, 0, 2);
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getStartTime(){
        return this.startTime;
    }

    /**道具生效函数
     * */
    public abstract void operate();

    /**是否超过使用限时
     * @param currentTime 当前时间
     * @return 超时返回true，未超时返回false
     * */
    public abstract boolean timeLimitExceeded(int currentTime);

//    /**道具失效函数
//     * */
//    public abstract void setInvalid();

    @Override
    public void forward(){
        super.forward();
        // 判定 y 轴向下飞行出界
        if (this.locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
