package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.shootStragety.directShootStrategy;
import edu.hitsz.shootStragety.shootStrategyInterface;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemyAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, shootStrategyInterface shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, shootStrategy);
    }

    @Override
    public AbstractProp generateProp(){
        AbstractProp prop = null;
        double prob = 1.0/2;

        //有一半的概率获得道具
        if (Math.random()>prob){
            prop = super.generateProp();
        }
        return prop;
    }

    public void setShootNum(int num){
        this.shootNum = num;
    }

}
