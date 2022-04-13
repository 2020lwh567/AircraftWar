package edu.hitsz.aircraft;

import edu.hitsz.properties.AbstractProp;
import edu.hitsz.shootStragety.ShootStrategyInterface;

public class EliteEnemy extends AbstractEnemyAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, ShootStrategyInterface shootStrategy) {
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
