package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootStragety.DirectShootStrategy;
import edu.hitsz.shootStragety.ScatterShootStrategy;
import edu.hitsz.shootStragety.SweepShootStrategy;

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
    private int limitTime = 18000;

    /**获得3个火力道具时，从散射变成扫射*/
    private int scatter2sweep = 3;

    public PropBullet(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void operate() {
        HeroAircraft heroaircraft = HeroAircraft.getHeroAircraft();
        Runnable r = () -> {
            try{
                heroaircraft.increaseFire(increaseShootNum, increasePower);
                heroaircraft.increaseNumOfBullet();
                if(heroaircraft.getNumOfBullet()<scatter2sweep){
                    // 散射
                    heroaircraft.setStrategy(new ScatterShootStrategy());
                }else{
                    // 扫射
                    heroaircraft.setStrategy(new SweepShootStrategy());
                }
                System.out.println("FireSupply active!");
                Thread.sleep(limitTime);
                setInvalid();
            } catch (InterruptedException e){
                e.printStackTrace();
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

    /** 道具超时失效 */
    public void setInvalid() {
        HeroAircraft heroaircraft = HeroAircraft.getHeroAircraft();
        heroaircraft.increaseFire(-increaseShootNum, -increasePower);
        heroaircraft.decreaseNumOfBullet();
        System.out.println("FireSupply exceeds time limit!");

        if (heroaircraft.getShootNum()==1){
            heroaircraft.setStrategy(new DirectShootStrategy());
        }else if(heroaircraft.getNumOfBullet()<scatter2sweep){
            heroaircraft.setStrategy(new ScatterShootStrategy());
        }
    }
}
