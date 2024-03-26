package Zoo;

import java.awt.Color;
import java.util.Comparator;

public abstract class Organism implements Comparator<Organism> {
    protected boolean markedForRemoval = false;
    protected int strength, initiative, x, y, age = 0;
    protected World world;

    @Override
    public int compare(Organism first, Organism second) {
        if (first.getInitiative() > second.getInitiative()) {
            return 1;
        }
        if (first.getInitiative() == second.getInitiative() &&
                first.getAge() > second.getAge()) {
            return 1;
        }
        return -1;
    }

    public Organism(World world) {
        this.world = world;
        strength = 0;
        initiative = 0;
        x = -1;
        y = -1;
    }

    public Organism(World world, int x, int y) {
        this.world = world;
        strength = 0;
        initiative = 0;
        this.x = x;
        this.y = y;
    }

    public Organism(World world, int strength, int initiative, int x, int y, int age) {
        this.world = world;
        this.strength = strength;
        this.initiative = initiative;
        this.x = x;
        this.y = y;
        this.age = age;
    }

    public abstract void takeAction();

    public abstract void collision(Organism attacker);

    public void advanceAge() {
        age++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public abstract Color getColor();

    public int[] getRandomMovePosition(int x, int y) {
        int[] pos = new int[2];

        switch (Directions.random()) {
            case Up:
                pos[0] = x;
                pos[1] = Math.abs(y - 1);
                break;
            case Down:
                pos[0] = x;
                pos[1] = Math.min(y + 1, world.getY() - 1);
                break;
            case Left:
                pos[0] = Math.abs(x - 1);
                pos[1] = y;
                break;
            case Right:
                pos[0] = Math.min(x + 1, world.getX() - 1);
                pos[1] = y;
                break;
            case UpLeft:
                pos[0] = Math.abs(x - 1);
                pos[1] = Math.abs(y - 1);
                break;
            case UpRight:
                pos[0] = Math.min(x + 1, world.getX() - 1);
                pos[1] = Math.abs(y - 1);
                break;
            case DownLeft:
                pos[0] = Math.abs(x - 1);
                pos[1] = Math.min(y + 1, world.getY() - 1);
                break;
            case DownRight:
                pos[0] = Math.min(x + 1, world.getX() - 1);
                pos[1] = Math.min(y + 1, world.getY() - 1);
                break;
            default:
                break;
        }

        return pos;
    }

    public int[] getRandomMovePosition(int x, int y, int move) {
        int[] pos = new int[2];

        switch (Directions.random()) {
            case Up:
                pos[0] = x;
                pos[1] = Math.abs(y - move);
                break;
            case Down:
                pos[0] = x;
                pos[1] = Math.min(y + move, world.getY() - move);
                break;
            case Left:
                pos[0] = Math.abs(x - move);
                pos[1] = y;
                break;
            case Right:
                pos[0] = Math.min(x + move, world.getX() - move);
                pos[1] = y;
                break;
            case UpLeft:
                pos[0] = Math.abs(x - move);
                pos[1] = Math.abs(y - move);
                break;
            case UpRight:
                pos[0] = Math.min(x + move, world.getX() - move);
                pos[1] = Math.abs(y - move);
                break;
            case DownLeft:
                pos[0] = Math.abs(x - move);
                pos[1] = Math.min(y + move, world.getY() - move);
                break;
            case DownRight:
                pos[0] = Math.min(x + move, world.getX() - move);
                pos[1] = Math.min(y + move, world.getY() - move);
                break;
            default:
                break;
        }

        return pos;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXAndY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public void changePosition(int x, int y) {
        world.swap(this.x, this.y, x, y);
        world.getOrganism(x, y).setXAndY(this.x, this.y);
        setXAndY(x, y);
    }

    public void buffStrength(int value) {
        strength += value;
    }
}
