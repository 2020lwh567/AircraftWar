package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBlood;
import edu.hitsz.properties.PropBomb;
import edu.hitsz.properties.PropBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    static EliteEnemy eliteaircraft;
    private static List<AbstractProp> abstractProps;

    @BeforeAll
    static void testBeginner() {
        abstractProps = new LinkedList<>();
        eliteaircraft = new EliteEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                ((Math.random()>0.66)?1:(Math.random()>0.5?0:-1))*3,
                5,
                30
        );
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
    void testGenerateProp() {
        //加血道具数量
        int cnt1 = 0;

        //炸弹道具数量
        int cnt2 = 0;

        //火力道具数量
        int cnt3 = 0;

        int times = 5000000;
        for (int i=0;i<times;i++){
            AbstractProp prop = eliteaircraft.generateProp();
            if (prop==null){
                continue;
            }
            if (prop instanceof PropBlood){
                cnt1++;
            }
            else if(prop instanceof PropBomb){
                cnt2++;
            }
            else if(prop instanceof PropBullet){
                cnt3++;
            }
            abstractProps.add(prop);
            assertAll(
                    () -> assertEquals(prop.getLocationX(), eliteaircraft.getLocationX()),
                    () -> assertEquals(prop.getLocationY(), eliteaircraft.getLocationY())
            );
        }

        double eps=1e-3;
        assertTrue(Math.abs(1.0*abstractProps.size()/times-0.5)<eps);
        assertTrue(1.0*Math.abs(cnt1-cnt2)/abstractProps.size()<eps);
        assertTrue(1.0*Math.abs(cnt1-cnt3)/abstractProps.size()<eps);
        assertTrue(1.0*Math.abs(cnt2-cnt3)/abstractProps.size()<eps);
    }

    /* num为一次允许同时发射的子弹数 */
    void testShoot(int num) {
        List<BaseBullet> basebullets =  eliteaircraft.shoot();
        assertEquals(basebullets.size(), num);

        for (int i = 0;i< basebullets.size(); i++){
            BaseBullet bullet = basebullets.get(i);
            assertEquals(bullet.getLocationX(), eliteaircraft.getLocationX() + (i*2-num+1)*10);
            assertAll(
                    () -> assertEquals(bullet.getLocationY(), 2*eliteaircraft.getDirection()+ eliteaircraft.getLocationY()),
                    () -> assertEquals(bullet.getSpeedX(), 0),
                    () -> assertEquals(bullet.getSpeedY(), 5*eliteaircraft.getDirection()+eliteaircraft.getSpeedY()),
                    () -> assertEquals(bullet.getPower(), eliteaircraft.getBulletPower())
            );
        }
    }

    @Test
    void testShootMain(){
        eliteaircraft.setShootNum(1);
        testShoot(1);
        eliteaircraft.setShootNum(5);
        testShoot(5);
    }

}