package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootStragety.ShootStrategyInterface;

public class BossEnemy extends AbstractEnemyAircraft {
    /**攻击方式 */

    /**
     * @param locationX boss机位置x坐标
     * @param locationY boss机位置y坐标
     * @param speedX boss机射出的子弹的基准速度（boss机无特定速度）
     * @param speedY boss机射出的子弹的基准速度（boss机无特定速度）
     * @param hp    初始生命值，默认无穷大
     */
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, ShootStrategyInterface shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, shootStrategy);
    }

    /**包含向下和向上飞两种方向*/
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向上飞行出界
        if (locationY <= 0 ) {
            speedY = -speedY;
        }
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

}
