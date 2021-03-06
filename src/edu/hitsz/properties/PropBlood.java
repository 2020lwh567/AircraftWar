package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 加血道具
 */


public class PropBlood extends AbstractProp{
    /**回血量*/
    private int returnBlood = 30;

    public PropBlood(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate() {
        HeroAircraft.getHeroAircraft().increaseHp(returnBlood);
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        return false;
    }

}
