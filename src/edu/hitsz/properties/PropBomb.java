package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 炸弹道具
 */


public class PropBomb extends AbstractProp{
    public PropBomb(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        for (BaseBullet bullet : enemybullet){
            bullet.vanish();
        }
        for (AbstractAircraft aircraft : enemyaircraft){
            if (!(aircraft instanceof BossEnemy))
                aircraft.vanish();
        }
        System.out.println("BombSupply active!");
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        return false;
    }

    @Override
    public void setInvalid(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        //炸弹道具只在一瞬间有效
    }

}
