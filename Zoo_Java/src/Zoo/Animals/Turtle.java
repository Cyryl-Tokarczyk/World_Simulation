package Zoo.Animals;

import java.awt.Color;
import java.util.Random;

import Zoo.Animal;
import Zoo.Organism;
import Zoo.World;

public class Turtle extends Animal {

    private static final int TurtleMoveChance = 4;

    public Turtle(World world) {
        super(world);
        strength = 2;
        initiative = 1;
    }

    public Turtle(World world, int x, int y) {
        super(world, x, y);
        strength = 2;
        initiative = 1;
    }

    public Turtle(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 138, 154, 91 );
    }

    @Override
    protected Organism getChild(int x, int y) {
        return new Turtle(world, x, y);
    }

    @Override
    public void takeAction() {
        if (new Random().nextInt(TurtleMoveChance) == 0) {
            move();
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass()) {
            reproduce();
            return;
        }

        if (attacker.getStrength() < 5)
            return;

        if (attacker.getStrength() > strength) {
            attacker.changePosition(x, y);
            world.logAttack(attacker, this);
            world.deleteOrganism(this);
        }
    }

}
