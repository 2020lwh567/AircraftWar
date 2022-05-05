package edu.hitsz.shootStragety;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

public class SweepShootStrategy implements ShootStrategyInterface{

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        int l = 0;
        int r = WINDOW_WIDTH;
        // int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection()*5;
        BaseBullet abstractBullet;
        int shootNum = 20;
        for(int i=0; i<=shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new HeroBullet(l + (r-l)/shootNum*i, y, 0, speedY, aircraft.getBulletPower());
            res.add(abstractBullet);
        }
        return res;
    }
}
