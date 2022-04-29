package edu.hitsz.properties;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropBulletTest {
    static PropBullet propBullet;

    @BeforeAll
    public static void testBeginner(){
        System.out.println("**--- HeroAircraft test begins ---**");
        propBullet = new PropBullet(200, 600);
    }

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
    }

    @Test
    void testForward() {
        int locationX = propBullet.getLocationX();
        int locationY = propBullet.getLocationY();
        propBullet.forward();
        assertEquals(propBullet.getLocationX(), locationX+propBullet.getSpeedX());
        assertEquals(propBullet.getLocationY(), locationY+propBullet.getSpeedY());

        int times = 83;
        for (int i=0;i<times;i++){
            propBullet.forward();
        }
        assertTrue(propBullet.notValid());
    }

    @Test
    void testOperate() {
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        int shootNumOri = heroAircraft.getShootNum();
        int powerOri = heroAircraft.getBulletPower();
        propBullet.operate();
        assertEquals(heroAircraft.getShootNum(), shootNumOri+1);
        assertEquals(heroAircraft.getBulletPower(), powerOri+20);
    }

    @Test
    void testTimeLimitExceeded() {
//        System.out.printf("%d\n",propBullet.getStartTime());  起始时间为0
        assertFalse(propBullet.timeLimitExceeded(19980));
        assertTrue(propBullet.timeLimitExceeded(20010));
    }

    @Test
    void testSetInvalid() {
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        int shootNumOri = heroAircraft.getShootNum();
        int powerOri = heroAircraft.getBulletPower();
        //先生效后调用失效函数,检测英雄机是否恢复原状
        propBullet.operate();
        propBullet.setInvalid();
        assertEquals(heroAircraft.getShootNum(), shootNumOri);
        assertEquals(heroAircraft.getBulletPower(), powerOri);
    }
}