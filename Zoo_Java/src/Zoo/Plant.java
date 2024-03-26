package Zoo;

import java.util.Random;

public abstract class Plant extends Organism {

    static public final int DefaultSpreadChance = 5;

    public Plant(World world) {
        super(world);
    }

    public Plant(World world, int x, int y) {
        super(world, x, y);
    }

    public Plant(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public abstract void takeAction();

    @Override
    public void collision(Organism attacker) {
        if (attacker.getStrength() > strength) {
            attacker.changePosition(x, y);
            world.logAttack(attacker, this);
            world.deleteOrganism(this);
        }
    }

    public void spread(Organism organism) {
        if (new Random().nextInt(DefaultSpreadChance) == 0) {
            int[] newPos = getRandomMovePosition(x, y);

            organism.setXAndY(newPos[0], newPos[1]);

            world.addOrganism(organism);
        }
    }
}
