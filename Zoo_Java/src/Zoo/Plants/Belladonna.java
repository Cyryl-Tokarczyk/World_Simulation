package Zoo.Plants;

import java.awt.Color;

import Zoo.Organism;
import Zoo.Plant;
import Zoo.World;

public class Belladonna extends Plant{

    public Belladonna(World world) {
        super(world);
    }

    public Belladonna(World world, int x, int y) {
        super(world, x, y);
    }

    public Belladonna(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 128, 103, 119 );
    }

    @Override
    public void takeAction() {
        spread(new Belladonna(world, x, y));
    }

    @Override
    public void collision(Organism attacker) {
        world.logAttack( attacker, this );
        world.deleteOrganism( attacker );
        world.deleteOrganism( this );
    }

}
