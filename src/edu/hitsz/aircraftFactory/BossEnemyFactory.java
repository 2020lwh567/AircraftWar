package edu.hitsz.aircraftFactory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStragety.ScatterShootStrategy;

public class BossEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * (Main.WINDOW_HEIGHT - ImageManager.BOSS_ENEMY_IMAGE.getHeight()) * 0.5),
                ((Math.random()>0.5)?1:-1)*2,
                0,
                500,
                3,
                30,
                new ScatterShootStrategy()
        );
    }
}
