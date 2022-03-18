package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.List;

/**
 * 火力道具
 */

public class PropBullet extends AbstractProp{

    private int increaseShootNum = 1;//一次道具能增加1颗子弹
    private int increasePower = 20;//一次道具能增加20火力
    private int linitTime = 20000;//火力道具使用时长限制

    public PropBullet(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet) {
        heroaircraft.increaseFire(increaseShootNum, increasePower);
        System.out.println("FireSupply active!");
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        if (currentTime - this.getStartTime() >= linitTime)
            return true;
        else
            return false;
    }

    @Override
    //道具超时失效
    public void setInvalid(HeroAircraft heroaircraft, List<AbstractAircraft> enemyaircraft, List<AbstractBullet> enemybullet) {
        heroaircraft.increaseFire(-increaseShootNum, -increasePower);
        System.out.println("FireSupply exceeds time limit!");
    }
}
