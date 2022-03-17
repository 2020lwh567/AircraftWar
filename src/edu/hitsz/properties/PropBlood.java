package edu.hitsz.properties;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加血道具
 */


public class PropBlood extends AbstractProp{

    private int returnBlood = 30;   //回血量

    public PropBlood(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft aircraft) {
        aircraft.increaseHp(returnBlood);
    }

}
