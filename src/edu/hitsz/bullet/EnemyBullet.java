package edu.hitsz.bullet;

import edu.hitsz.observerPattern.Subscriber;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Subscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    /**炸弹道具生效后敌机子弹直接消失*/
    @Override
    public void update() {
        this.vanish();
    }
}
