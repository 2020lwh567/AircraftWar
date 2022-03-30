package edu.hitsz.aircraftFactory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

/**产生敌机的工厂抽象类*/
public abstract class AbstractEnemyAircraftFactory {

    /**产生敌机方法
     * @return 返回产生的敌机
     * */
    public abstract AbstractEnemyAircraft createEnemyAircraft();

}
