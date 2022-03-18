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
    public AbstractProp(int locationX, int locationY) {
        super(locationX, locationY, 0, 2);
    }

    public abstract void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet);
}
