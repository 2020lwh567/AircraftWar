package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new EliteEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                ((Math.random()>0.66)?1:(Math.random()>0.5?0:-1))*3,
                5,
                30
        );
    }
}
