package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

//普通敌机产生工厂
public class MobEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new MobEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                0,
                5,
                30
        );
    }
}
