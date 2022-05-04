package edu.hitsz.aircraftFactory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStragety.DirectShootStrategy;

public class EliteEnemyFactory extends AbstractEnemyAircraftFactory{
    @Override
    public AbstractEnemyAircraft createEnemyAircraft(int hp, int speedY) {
        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                ((Math.random()>0.66)?1:(Math.random()>0.5?0:-1))*3,
                speedY,
                hp,
                1,
                20,
                new DirectShootStrategy()
        );
    }
}
