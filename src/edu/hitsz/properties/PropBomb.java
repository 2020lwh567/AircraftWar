package edu.hitsz.properties;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observerPattern.Subscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具
 * 观察者模式中的发布者
 */


public class PropBomb extends AbstractProp{

    /**观察者列表*/
    private List<Subscriber> subscriber = new LinkedList<>();

    public PropBomb(int locationX, int locationY) {
        super(locationX, locationY);
    }

    /**通知所有观察者*/
    @Override
    public void operate() {
        System.out.println("BombSupply active!");
        notifyAllSubscribers();
    }

    @Override
    public boolean timeLimitExceeded(int currentTime) {
        return false;
    }

    /** 添加观察者 */
    public void addSubscriber(Subscriber obj){
        subscriber.add(obj);
    }

    /** 移除观察者 */
    public void removeSubscriber(Subscriber obj){
        subscriber.remove(obj);
    }

    /** 通知所有观察者 */
    public void notifyAllSubscribers(){
        for (Subscriber obj : subscriber){
            obj.update();
        }
    }
}
