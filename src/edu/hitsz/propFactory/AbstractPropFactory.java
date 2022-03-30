package edu.hitsz.propFactory;

import edu.hitsz.properties.AbstractProp;

public abstract class AbstractPropFactory {
    /**
     * @param locationX X轴坐标
     * @param locationY Y轴坐标
     * @return 产生的具体道具
     */
    public abstract AbstractProp createProp(int locationX, int locationY);
}
