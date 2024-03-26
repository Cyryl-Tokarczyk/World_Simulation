package Zoo.Plants;

import java.awt.Color;

import Zoo.Organism;
import Zoo.Plant;
import Zoo.World;

public class Sosnowsky extends Plant {

    public Sosnowsky(World world) {
        super(world);
    }

    public Sosnowsky(World world, int x, int y) {
        super(world, x, y);
    }

    public Sosnowsky(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 200, 255, 200 );
    }

    @Override
    public void takeAction() {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                Organism org = world.getOrganism(i, j);
                if (org != null && !(org instanceof Plant)) {
                    world.deleteOrganism(org);
                }
            }
        }

        spread(new Sosnowsky(world, x, y));
    }

    @Override
    public void collision(Organism attacker) {
        world.logAttack( attacker, this );
        world.deleteOrganism( attacker );
        world.deleteOrganism( this );
    }

}
