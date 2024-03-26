package Zoo.Plants;

import java.awt.Color;

import Zoo.Plant;
import Zoo.World;

public class Dandelion extends Plant {

    public Dandelion(World world) {
        super(world);
    }

    public Dandelion(World world, int x, int y) {
        super(world, x, y);
    }

    public Dandelion(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 240, 225, 48 );
    }

    @Override
    public void takeAction() {
        for (int i = 0; i < 3; i++) {
            spread(new Dandelion(world, x, y));
        }
    }

}
