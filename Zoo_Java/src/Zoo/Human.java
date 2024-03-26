package Zoo;

import java.awt.Color;

public class Human extends Organism {

    public enum Actions {
        Up,
        Down,
        Left,
        Right,
        Special,
    }

    static public final int SpecialCooldown = 5;

    private int currentSpecialCooldown = 0;
    private Actions action;

    public Human(World world) {
        super(world);
        strength = 5;
        initiative = 4;
    }

    public Human(World world, int x, int y) {
        super(world, x, y);
        strength = 5;
        initiative = 4;
    }

    public Human(World world, int strength, int initiative, int x, int y, int age, int specialCooldown) {
        super(world, strength, initiative, x, y, age);
        currentSpecialCooldown = specialCooldown;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    public int getCurrentSpecialCooldown() {
        return currentSpecialCooldown;
    }

    private void move(int x, int y) {
        Organism organism = world.getOrganism(x, y);

        if (organism != null) {
            organism.collision(this);
        } else {
            changePosition(x, y);
        }
    }

    private void special() {
        if (isSpecialReady()) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    Organism org = world.getOrganism(i, j);
                    if (!(i == x && j == y) && org != null) {
                        world.deleteOrganism(org);
                    }
                }
            }

            currentSpecialCooldown = SpecialCooldown;
        }
    }

    boolean isLegalMove(int x, int y) {
        if (0 <= x && x < world.getX() &&
                0 <= y && y < world.getY()) {
            return true;
        }

        return false;
    }

    boolean isSpecialReady() {
        return currentSpecialCooldown <= 0;
    }

    @Override
    public void takeAction() {
        switch (action) {
            case Up:
                move(x, y - 1);
                break;
            case Down:
                move(x, y + 1);
                break;
            case Left:
                move(x - 1, y);
                break;
            case Right:
                move(x + 1, y);
                break;
            case Special:
                special();
                break;
            default:
                break;
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.getStrength() > strength) {
            attacker.changePosition(x, y);
            world.logAttack(attacker, this);
            world.deleteOrganism(this);
        }
    }

    @Override
    public void advanceAge() {
        age++;

        if (currentSpecialCooldown > 0) {
            currentSpecialCooldown--;
        }
    }

}
