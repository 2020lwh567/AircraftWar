package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.properties.AbstractProp;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemyAircraft {
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 20;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet abstractBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(abstractBullet);
        }
        return res;
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

    public int getDirection() {
        return this.direction;
    }

    public int getBulletPower() {
        return this.power;
    }
}
