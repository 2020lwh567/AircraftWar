package edu.hitsz.propFactory;

import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBomb;

public class PropBombFactory extends AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new PropBomb(locationX, locationY);
    }
}
