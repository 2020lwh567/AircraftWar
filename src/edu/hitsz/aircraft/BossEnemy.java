package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public class BossEnemy extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private int direction = 1;

    /**
     * @param locationX boss机位置x坐标
     * @param locationY boss机位置y坐标
     * @param speedX boss机射出的子弹的基准速度（boss机无特定速度）
     * @param speedY boss机射出的子弹的基准速度（boss机无特定速度）
     * @param hp    初始生命值，默认无穷大
     */
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // boss机不移动
    }

    @Override
    public List<AbstractBullet> shoot() {
        return null;
    }
}
