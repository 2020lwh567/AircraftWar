package edu.hitsz.properties;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 火力道具
 */


public class PropBullet extends AbstractProp{
    public PropBullet(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft aircraft) {
        System.out.println("FireSupply active!");
    }
}
