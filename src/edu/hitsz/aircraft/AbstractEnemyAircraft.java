package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.properties.AbstractProp;

public abstract class AbstractEnemyAircraft extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    protected int direction = 1;

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    //默认向下飞
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    //产生道具
    public abstract AbstractProp generateProp();
}
