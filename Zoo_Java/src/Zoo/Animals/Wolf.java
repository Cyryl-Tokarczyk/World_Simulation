package Zoo.Animals;

import java.awt.Color;

import Zoo.Animal;
import Zoo.Organism;
import Zoo.World;

public class Wolf extends Animal {

    public Wolf(World world) {
        super(world);
        strength = 9;
        initiative = 5;
    }

    public Wolf(World world, int x, int y) {
        super(world, x, y);
        strength = 9;
        initiative = 5;
    }

    public Wolf(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 128, 128, 128 );
    }

    @Override
    protected Organism getChild(int x, int y) {
        return new Wolf(world, x, y);
    }

}
