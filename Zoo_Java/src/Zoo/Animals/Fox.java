package Zoo.Animals;

import java.awt.Color;

import Zoo.Animal;
import Zoo.Organism;
import Zoo.World;

public class Fox extends Animal {

    public Fox(World world) {
        super(world);
        strength = 3;
        initiative = 7;
    }

    public Fox(World world, int x, int y) {
        super(world, x, y);
        strength = 3;
        initiative = 7;
    }

    public Fox(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 255, 132, 19 );
    }

    @Override
    protected Organism getChild(int x, int y) {
        return new Fox(world, x, y);
    }

    @Override
    public void takeAction() {
        int timer = 0;
        int[] newPos = new int[2];

        Organism organism;

        do {
            if (timer > MaxChildPositionRandomisingTries) {
                return;
            }
            newPos[0] = x;
            newPos[1] = y;
            getRandomMovePosition(newPos[0], newPos[1]);
            organism = world.getOrganism(newPos[0], newPos[1]);
            timer++;
        } while (organism != null && organism.getStrength() > strength);

        if (organism != null) {
            organism.collision(this);
        } else {
            changePosition(newPos[0], newPos[1]);
        }
    }

}
