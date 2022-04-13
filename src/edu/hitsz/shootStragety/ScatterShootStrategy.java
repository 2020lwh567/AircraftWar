package edu.hitsz.shootStragety;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements ShootStrategyInterface {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        // int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection()*5;
        BaseBullet abstractBullet;
        for(int i=0; i<aircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (aircraft instanceof HeroAircraft){
                abstractBullet = new HeroBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, i-aircraft.getShootNum()/2, speedY, aircraft.getBulletPower());
            }
            else{
                abstractBullet = new EnemyBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, i-aircraft.getShootNum()/2, speedY, aircraft.getBulletPower());
            }
            res.add(abstractBullet);
        }
        return res;
    }
}
