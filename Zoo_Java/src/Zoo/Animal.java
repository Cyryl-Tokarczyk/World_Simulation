package Zoo;
public abstract class Animal extends Organism {

    public static final int MaxChildPositionRandomisingTries = 15;

    protected abstract Organism getChild(int x, int y);

    public Animal(World world) {
        super(world);
    }

    public Animal(World world, int x, int y) {
        super(world, x, y);
    }

    public Animal(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public void takeAction() {
        move();
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass()) {
            reproduce();
            return;
        }

        if (attacker.getStrength() > strength) {
            attacker.changePosition(x, y);
            world.logAttack(attacker, this);
            world.deleteOrganism(this);
        }
    }

    public void reproduce() {
        int timer = 0;
        int[] childPos = new int[2];

        do {
            if (timer > MaxChildPositionRandomisingTries) {
                return;
            }
            childPos[0] = x;
            childPos[1] = y;
            childPos = getRandomMovePosition(childPos[0], childPos[1]);
            timer++;
        } while (world.getOrganism(childPos[0], childPos[1]) != null);

        world.addOrganism(getChild(childPos[0], childPos[1]));
    }

    public void move() {
        int[] newPos;

	    newPos = getRandomMovePosition( x, y );

	    Organism organism = world.getOrganism( newPos[0], newPos[1] );

        if (organism != null)
        {
            organism.collision( this );
        }
        else
        {
            changePosition( newPos[0], newPos[1] );
        }
    }
}
