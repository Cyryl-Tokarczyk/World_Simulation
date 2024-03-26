package Zoo.Animals;

import java.awt.Color;

import Zoo.Animal;
import Zoo.Organism;
import Zoo.World;

public class Sheep extends Animal {

    public Sheep(World world) {
        super(world);
        strength = 4;
        initiative = 4;
    }

    public Sheep(World world, int x, int y) {
        super(world, x, y);
        strength = 4;
        initiative = 4;
    }

    public Sheep(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 200, 200, 200 );
    }

    @Override
    protected Organism getChild(int x, int y) {
        return new Sheep(world, x, y);
    }
}
