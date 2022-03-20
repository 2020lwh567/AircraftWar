package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 加血道具
 */


public class PropBlood extends AbstractProp{

    private int returnBlood = 30;   //回血量

    public PropBlood(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        heroaircraft.increaseHp(returnBlood);
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        return false;
    }

    @Override
    public void setInvalid(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        //加血道具始终有效
    }

}
