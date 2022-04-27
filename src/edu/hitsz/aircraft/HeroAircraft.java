package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStragety.DirectShootStrategy;
import edu.hitsz.shootStragety.ShootStrategyInterface;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */

    public void setHp(int hp){
        this.hp = hp;
    }

    public void setShootNum(int num){
        this.shootNum = num;
    }

    /**
     * 唯一实例
     */
    private volatile static HeroAircraft heroAircraft;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     * 构造函数私有化
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, ShootStrategyInterface shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, shootStrategy);
        this.direction = -1;
    }

    public static HeroAircraft getHeroAircraft(){
        if (heroAircraft == null){
            synchronized (HeroAircraft.class){
                if (heroAircraft == null){
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 10000, 1, 30, new DirectShootStrategy());
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    /**加血*/
    public void increaseHp (int increase){
        hp += increase;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /**增强火力*/
    public void increaseFire(int increaseShootNum, int increasePower){
        shootNum += increaseShootNum;
        power += increasePower;
    }
}
