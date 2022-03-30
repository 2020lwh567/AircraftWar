package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractEnemyAircraft {
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 3;

    /**
     * 子弹伤害
     */
    private int power = 30;

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

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
       // int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet abstractBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, i-shootNum/2, speedY, power);
            res.add(abstractBullet);
        }
        return res;
    }

}
