package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.propFactory.AbstractPropFactory;
import edu.hitsz.propFactory.PropBloodFactory;
import edu.hitsz.propFactory.PropBombFactory;
import edu.hitsz.propFactory.PropBulletFactory;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.shootStragety.ShootStrategyInterface;

public abstract class AbstractEnemyAircraft extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    protected int direction = 1;

    /**
     * 抽象的道具产生工厂
     */
    private AbstractPropFactory abstractPropFactory;

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, ShootStrategyInterface shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, shootStrategy);
    }

    @Override
    /**默认向下飞*/
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /**产生道具*/
    public AbstractProp generateProp(){
        AbstractProp prop = null;

        // 炸弹道具获得概率0.2， 其他道具获得概率均为0.4
        double prob1 = 0.2;
        double prob2 = 0.6;
        double num = Math.random();

        //获得炸弹道具
        if (num<prob1){
            abstractPropFactory = new PropBombFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }

        //获得加血道具
        else if(num>prob2){
            abstractPropFactory = new PropBloodFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }

        //获得火力道具
        else{
            abstractPropFactory = new PropBulletFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }

        return prop;
    }
}
