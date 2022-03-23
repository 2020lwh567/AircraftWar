package edu.hitsz.propFactory;

import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBlood;

public class PropBloodFactory extends AbstractPropFactory{

    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new PropBlood(locationX, locationY);
    }
}
