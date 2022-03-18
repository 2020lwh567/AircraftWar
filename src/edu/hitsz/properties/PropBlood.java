package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

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
    public void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet) {
        heroaircraft.increaseHp(returnBlood);
    }

}
