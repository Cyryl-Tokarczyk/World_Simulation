package Zoo.Plants;

import java.awt.Color;

import Zoo.Organism;
import Zoo.Plant;
import Zoo.World;

public class Guarana extends Plant {

    public Guarana(World world) {
        super(world);
    }

    public Guarana(World world, int x, int y) {
        super(world, x, y);
    }

    public Guarana(World world, int strength, int initiative, int x, int y, int age) {
        super(world, strength, initiative, x, y, age);
    }

    @Override
    public Color getColor() {
        return new Color( 215, 63, 88 );
    }

    @Override
    public void takeAction() {
        spread(new Guarana(world, x, y));
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.getStrength() > strength) {
            attacker.changePosition(x, y);
            world.logAttack(attacker, this);
            world.deleteOrganism(this);
            attacker.buffStrength(3);
        }
    }

}
