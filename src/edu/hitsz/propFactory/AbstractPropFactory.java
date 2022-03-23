package edu.hitsz.propFactory;

import edu.hitsz.properties.AbstractProp;

public abstract class AbstractPropFactory {
    public abstract AbstractProp createProp(int locationX, int locationY);
}
