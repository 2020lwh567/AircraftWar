package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBlood;
import edu.hitsz.properties.PropBomb;
import edu.hitsz.properties.PropBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft{
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 20;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private int direction = 1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBullet> shoot() {
        List<AbstractBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        AbstractBullet abstractBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(abstractBullet);
        }
        return res;
    }

    public AbstractProp generateProp(){
        AbstractProp prop = null;
        if (Math.random()>0.5){ //有一半的概率获得道具
            //以下三种道具获得概率相同
            if (Math.random()<0.33){//获得加血道具
                prop = new PropBlood(this.getLocationX(), this.getLocationY());
            }
            else if(Math.random()>0.66){//获得炸弹道具
                prop = new PropBomb(this.getLocationX(), this.getLocationY());
            }
            else{//获得火力道具
                prop = new PropBullet(this.getLocationX(), this.getLocationY());
            }
        }
        return prop;
    }
}
