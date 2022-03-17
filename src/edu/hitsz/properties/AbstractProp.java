package edu.hitsz.properties;

import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 道具类。
 */

public abstract class AbstractProp extends AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY) {
        super(locationX, locationY, 0, 0);
    }
}
