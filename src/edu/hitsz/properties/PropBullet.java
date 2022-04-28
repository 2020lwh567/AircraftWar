package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootStragety.DirectShootStrategy;
import edu.hitsz.shootStragety.ScatterShootStrategy;

import java.util.List;

/**
 * 火力道具
 */

public class PropBullet extends AbstractProp{

    /**一次道具能增加1颗子弹*/
    private int increaseShootNum = 1;

    /**一次道具能增加20火力*/
    private int increasePower = 20;

    /**火力道具使用时长限制*/
    private int limitTime = 20000;

    public PropBullet(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate(HeroAircraft heroaircraft, List<AbstractEnemyAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        Runnable r = () -> {
            try{
                heroaircraft.increaseFire(increaseShootNum, increasePower);
                heroaircraft.setStrategy(new ScatterShootStrategy());
                System.out.println("FireSupply active!");
                Thread.sleep(limitTime);
            } catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //System.out.println("inactive --------");
                setInvalid(heroaircraft, enemyaircraft, enemybullet);
            }
        };
        new Thread(r).start();
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        if (currentTime - this.getStartTime() >= limitTime) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    /**道具超时失效*/
    public void setInvalid(HeroAircraft heroaircraft, List<AbstractEnemyAircraft> enemyaircraft, List<BaseBullet> enemybullet) {
        heroaircraft.increaseFire(-increaseShootNum, -increasePower);
        System.out.println("FireSupply exceeds time limit!");

        if (heroaircraft.getShootNum()==1){
            heroaircraft.setStrategy(new DirectShootStrategy());
        }
    }
}
