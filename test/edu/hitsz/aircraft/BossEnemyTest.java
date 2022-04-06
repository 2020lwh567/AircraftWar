package edu.hitsz.aircraft;

import edu.hitsz.aircraftFactory.EliteEnemyFactory;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossEnemyTest {

    static AbstractEnemyAircraft eliteaircraft;

    @BeforeAll
    static void init() {
        eliteaircraft = new EliteEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                ((Math.random()>0.66)?1:(Math.random()>0.5?0:-1))*3,
                5,
                30
        );
    }

    @Test
    void generateProp() {
    }

    @Test
    void decreaseHp() {
    }

    @Test
    void forward() {
    }

    @Test
    void crash() {
    }

    @Test
    void shoot() {
        eliteaircraft.shoot();
    }
}