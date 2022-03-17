package edu.hitsz.properties;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 炸弹道具
 */


public class PropBomb extends AbstractProp{
    public PropBomb(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft aircraft) {
        System.out.println("BombSupply active!");
    }
}
