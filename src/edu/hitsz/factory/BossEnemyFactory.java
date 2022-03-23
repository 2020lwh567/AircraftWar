package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new BossEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * (Main.WINDOW_HEIGHT - ImageManager.BOSS_ENEMY_IMAGE.getHeight()) * 0.5)*1,
                ((Math.random()>0.5)?1:-1)*2,
                -1,
                150
        );
    }
}
