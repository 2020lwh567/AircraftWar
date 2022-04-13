package edu.hitsz.shootStragety;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategyInterface {
    public List<BaseBullet> shoot(AbstractAircraft aircraft);
}
