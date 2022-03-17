package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBlood;
import edu.hitsz.properties.PropBomb;
import edu.hitsz.properties.PropBullet;

import java.util.List;

public class BossEnemy extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private int direction = 1;

    /**
     * @param locationX boss机位置x坐标
     * @param locationY boss机位置y坐标
     * @param speedX boss机射出的子弹的基准速度（boss机无特定速度）
     * @param speedY boss机射出的子弹的基准速度（boss机无特定速度）
     * @param hp    初始生命值，默认无穷大
     */
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // boss机不移动
    }

    @Override
    public List<AbstractBullet> shoot() {
        return null;
    }

    public AbstractProp generateProp(){
        AbstractProp prop = null;

        //炸毁boss机将100%获得道具，且以下三种道具获得概率相同
        if (Math.random()<0.33){//获得加血道具
            prop = new PropBlood(this.getLocationX(), this.getLocationY());
        }
        else if(Math.random()>0.66){//获得炸弹道具
            prop = new PropBomb(this.getLocationX(), this.getLocationY());
        }
        else{//获得火力道具
            prop = new PropBullet(this.getLocationX(), this.getLocationY());
        }

        return prop;
    }
}
