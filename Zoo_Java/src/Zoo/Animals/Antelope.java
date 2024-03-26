package Zoo.Animals;

import java.awt.Color;
import java.util.Random;

import Zoo.Animal;
import Zoo.Organism;
import Zoo.World;

public class Antelope extends Animal {
    private static final int AntelopeDodgeChance = 2;

    public Antelope(World world) {
        super(world);
        strength = 4;
        initiative = 4;
    }

    public Antelope(World world, int x, int y) {
        super(world, x, y);
        strength = 4;
        initiative = 4;
    }

    public Antelope(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color(160, 82, 45);
    }

    @Override
    protected Organism getChild(int x, int y) {
        return new Antelope(world, x, y);
    }

    @Override
    public void takeAction() {
        int[] newPos = getRandomMovePosition(x, y, 2);

        Organism organism = world.getOrganism(newPos[0], newPos[1]);

        if (organism != null) {
            organism.collision(this);
        } else {
            changePosition(newPos[0], newPos[1]);
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass()) {
            reproduce();
            return;
        }

        if (new Random().nextInt(AntelopeDodgeChance) == 0) {
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
            } while (organism != null);

            if (organism == null) {
                changePosition(newPos[0], newPos[1]);
                return;
            }
        }

        super.collision(attacker);
    }

}
