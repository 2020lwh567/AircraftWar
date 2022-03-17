package edu.hitsz.properties;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 抽象道具类
 */

public abstract class AbstractProp extends AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY) {
        super(locationX, locationY, 0, 0);
    }

    public abstract void operate(HeroAircraft aircraft);
}
