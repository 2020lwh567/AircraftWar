package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.propFactory.AbstractPropFactory;
import edu.hitsz.propFactory.PropBloodFactory;
import edu.hitsz.propFactory.PropBombFactory;
import edu.hitsz.propFactory.PropBulletFactory;
import edu.hitsz.properties.AbstractProp;

public abstract class AbstractEnemyAircraft extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    protected int direction = 1;

    private AbstractPropFactory abstractPropFactory;//抽象的道具产生工厂

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    //默认向下飞
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    //产生道具
    public AbstractProp generateProp(){
        AbstractProp prop = null;

        //以下三种道具获得概率相同
        if (Math.random()<0.33){//获得加血道具
            abstractPropFactory = new PropBloodFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }
        else if(Math.random()>0.66){//获得炸弹道具
            abstractPropFactory = new PropBombFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }
        else{//获得火力道具
            abstractPropFactory = new PropBulletFactory();
            prop = abstractPropFactory.createProp(this.getLocationX(), this.getLocationY());
        }

        return prop;
    }
}
