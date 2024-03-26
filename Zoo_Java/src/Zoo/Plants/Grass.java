package Zoo.Plants;

import java.awt.Color;

import Zoo.Plant;
import Zoo.World;

public class Grass extends Plant {

    public Grass(World world) {
        super(world);
    }

    public Grass(World world, int x, int y) {
        super(world, x, y);
    }

    public Grass(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 52, 140, 49 );
    }

    @Override
    public void takeAction() {
        spread( new Grass(world, x, y) );
    }
    
}
