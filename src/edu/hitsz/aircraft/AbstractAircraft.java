package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shootStragety.ShootStrategyInterface;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected ShootStrategyInterface shootStrategy;
    protected int shootNum = 0;
    protected int power = 0;
    protected int direction = 1;

    /**不发射子弹的飞机构造函数*/
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**发射子弹的飞机构造函数*/
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, ShootStrategyInterface shootStrategy) {
        this(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
        this.power = power;
        this.shootStrategy = shootStrategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getShootNum(){
        return this.shootNum;
    }

    public int getDirection(){
        return this.direction;
    }

    public int getBulletPower() {
        return this.power;
    }

    public void setStrategy(ShootStrategyInterface strategy){
        this.shootStrategy = strategy;
    }


    /**
     * 策略模式的do_something函数
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<BaseBullet> shoot(){
        return this.shootStrategy.shoot(this);
    }

}


