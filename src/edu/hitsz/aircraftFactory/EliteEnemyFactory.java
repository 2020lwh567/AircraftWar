package edu.hitsz.aircraftFactory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStragety.directShootStrategy;

public class EliteEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                ((Math.random()>0.66)?1:(Math.random()>0.5?0:-1))*3,
                5,
                30,
                1,
                20,
                new directShootStrategy()
        );
    }
}
