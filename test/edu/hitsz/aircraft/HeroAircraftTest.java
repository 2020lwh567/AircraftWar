package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.lang.Enum.*;

import static edu.hitsz.aircraft.HeroAircraft.getHeroAircraft;
import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private static HeroAircraft heroaircraft;

//    public final int ordinal();
//    @org.junit.jupiter.api.Test
//    void shoot() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void increaseHp() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void increaseFire() {
//    }

    @BeforeAll
    public static void testBeginner(){
        System.out.println("**--- HeroAircraft test begins ---**");
        heroaircraft = getHeroAircraft();
    }

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
    }

    /*测试发射1颗子弹 */
    void testShoot1() {
        List<BaseBullet> basebullets =  heroaircraft.shoot();
        assertEquals(basebullets.size(), 1);
        BaseBullet bullet = basebullets.get(0);
        assertEquals(bullet.getLocationX(), heroaircraft.getLocationX());
        assertAll(
                () -> assertEquals(bullet.getLocationY(), 2*heroaircraft.getDirection()+ heroaircraft.getLocationY()),
                () -> assertEquals(bullet.getSpeedX(), 0),
                () -> assertEquals(bullet.getSpeedY(), 5*heroaircraft.getDirection()),
                () -> assertEquals(bullet.getPower(), heroaircraft.getBulletPower())
        );
    }

    /*测试发射3颗子弹 */
    void testShoot3() {
        List<BaseBullet> basebullets =  heroaircraft.shoot();
        assertEquals(basebullets.size(), 3);

        //先测试三个子弹的横向位置
        BaseBullet bullet1 = basebullets.get(0);
        assertEquals(bullet1.getLocationX(), heroaircraft.getLocationX()-20);
        BaseBullet bullet2 = basebullets.get(1);
        assertEquals(bullet2.getLocationX(), heroaircraft.getLocationX());
        BaseBullet bullet3 = basebullets.get(2);
        assertEquals(bullet3.getLocationX(), heroaircraft.getLocationX()+20);

        for (BaseBullet bullet:basebullets){
            assertAll(
                    () -> assertEquals(bullet.getLocationY(), 2*heroaircraft.getDirection()+ heroaircraft.getLocationY()),
                    () -> assertEquals(bullet.getSpeedX(), 0),
                    () -> assertEquals(bullet.getSpeedY(), 5*heroaircraft.getDirection()),
                    () -> assertEquals(bullet.getPower(), heroaircraft.getBulletPower())
            );
        }
    }

    @Test
    void testShootMain(){
        heroaircraft.setShootNum(1);
        testShoot1();
        heroaircraft.setShootNum(3);
        testShoot3();
    }

    @Test
    void testIncreaseHp() {
        //最大血量是100
        heroaircraft.setHp(80);
        heroaircraft.increaseHp(10);
        assertEquals(heroaircraft.getHp(), 90);
        heroaircraft.increaseHp(20);
        assertEquals(heroaircraft.getHp(), 100);
    }

    @Test
    void testIncreaseFire() {
        int shootNumOri = heroaircraft.getShootNum();
        int powerOri = heroaircraft.getBulletPower();
        heroaircraft.increaseFire(2,10);
        assertAll(
                () -> assertEquals(heroaircraft.getShootNum(), shootNumOri+2),
                () -> assertEquals(heroaircraft.getBulletPower(), powerOri+10)
        );
    }

    @Test
    void testDecreaseHp(){
        heroaircraft.setHp(20);
        heroaircraft.decreaseHp(10);
        assertEquals(heroaircraft.getHp(), 10);
        heroaircraft.decreaseHp(15);
        assertEquals(heroaircraft.getHp(), 0);
    }

    @Test
    void testcrash(){
        int locationX = heroaircraft.getLocationX();//256
        int locatoionY = heroaircraft.getLocationY();//685
        int width = heroaircraft.getWidth();//100
        int height = heroaircraft.getHeight();//83
//        System.out.printf("%d %d %d %d ", locationX, locatoionY, width, height);
        AbstractAircraft eliteAircraft1 = new EliteEnemy(357, 720, 1, 0, 20);//精英敌机,碰撞成功
//        int width2 = eliteAircraft1.getWidth();//105
//        int height2 = eliteAircraft1.getHeight();//68
//        System.out.printf("%d %d", width2, height2);
        assertTrue(heroaircraft.crash(eliteAircraft1));
        AbstractAircraft eliteAircraft2 = new EliteEnemy(154, 700, 1, 0, 20);//精英敌机,碰撞失败
        assertFalse(heroaircraft.crash(eliteAircraft2));
        EnemyBullet bullet1 = new EnemyBullet(202,693,1,1,20);//敌机子弹,碰撞成功
//        int width3 = bullet1.getWidth();//10
//        int height3 = bullet1.getHeight();//18
//        System.out.printf("%d %d",width3, height3);
        assertTrue(heroaircraft.crash(bullet1));
        EnemyBullet bullet2 = new EnemyBullet(212,593,1,1,20);//敌机子弹,碰撞失败
        assertFalse(heroaircraft.crash(bullet2));
    }
}