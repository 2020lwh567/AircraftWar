package edu.hitsz.propFactory;

import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBullet;

public class PropBulletFactory extends AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new PropBullet(locationX, locationY);
    }
}
