package Zoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import Zoo.Animals.Antelope;
import Zoo.Animals.Fox;
import Zoo.Animals.Sheep;
import Zoo.Animals.Turtle;
import Zoo.Animals.Wolf;
import Zoo.Plants.Belladonna;
import Zoo.Plants.Dandelion;
import Zoo.Plants.Grass;
import Zoo.Plants.Guarana;
import Zoo.Plants.Sosnowsky;

public class World {
    public static final int SpawnChance = 20;
    public static final int NumberOfOrganismsToChooseFrom = 10;

    private int x, y;
    private Human player;
    private Organism[][] board;
    private Vector<Organism> organisms;
    private Vector<Organism> children;
    private Vector<String> log;

    private void randomizeBoard() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (new Random().nextInt(SpawnChance) == 0) {
                    switch (new Random().nextInt(NumberOfOrganismsToChooseFrom)) {
                        case 0:
                            addOrganism(new Grass(this, j, i));
                            break;
                        case 1:
                            addOrganism(new Wolf(this, j, i));
                            break;
                        case 2:
                            addOrganism(new Sheep(this, j, i));
                            break;
                        case 3:
                            addOrganism(new Dandelion(this, j, i));
                            break;
                        case 4:
                            addOrganism(new Guarana(this, j, i));
                            break;
                        case 5:
                            addOrganism(new Belladonna(this, j, i));
                            break;
                        case 6:
                            addOrganism(new Sosnowsky(this, j, i));
                            break;
                        case 7:
                            addOrganism(new Fox(this, j, i));
                            break;
                        case 8:
                            addOrganism(new Turtle(this, j, i));
                            break;
                        case 9:
                            addOrganism(new Antelope(this, j, i));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void addToLog(final String line) {
        log.add(line);
    }

    public World(int x, int y, boolean empty) {
        this.x = x;
        this.y = y;
        board = new Organism[y][x];
        organisms = new Vector<Organism>();
        children = new Vector<Organism>();
        log = new Vector<String>();

        if (!empty) {
            randomizeBoard();

            player = new Human(this, x / 2, y / 2);
            addOrganism(player);
            organisms.addAll(children);
            children.clear();
        }
    }

    public void addOrganism(Organism organism) {
        if (board[organism.getY()][organism.getX()] != null) {
            return;
        }

        children.add(organism);
        board[organism.getY()][organism.getX()] = organism;
    }

    public void makeTurn() {
        organisms.sort((first, second) -> {
            if (first.getInitiative() > second.getInitiative()) {
                return 1;
            }
            if (first.getInitiative() == second.getInitiative()) {
                if (first.getAge() > second.getAge()) {
                    return 1;
                }
                if (first.getAge() == second.getAge()) {
                    return 0;
                }
            }
            return -1;
        });

        organisms.forEach((organism) -> {
            if (!organism.markedForRemoval)
                organism.takeAction();
        });
        organisms.addAll(children);
        children.clear();
        organisms.removeIf(organism -> organism.markedForRemoval);
        organisms.forEach((organism) -> organism.advanceAge());
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Human getPlayer() {
        return player;
    }

    public Organism getOrganism(int x, int y) {
        if (0 > x || x >= this.x ||
                0 > y || y >= this.y) {
            return null;
        }

        return board[y][x];
    }

    public Vector<String> getLog() {
        return log;
    }

    public void swap(int x1, int y1, int x2, int y2) {
        Organism temp = board[y1][x1];
        board[y1][x1] = board[y2][x2];
        board[y2][x2] = temp;
    }

    public void deleteOrganism(Organism organism) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (board[i][j] == organism)
                    board[i][j] = null;
            }
        }

        organism.markForRemoval();
    }

    public void logAttack(Organism attacker, Organism defender) {
        String line = "Error";
        if (defender instanceof Animal) {
            line = defender.getClass().getSimpleName() +
                    " was killed by " +
                    attacker.getClass().getSimpleName() + '!';
        } else if (defender instanceof Plant) {
            line = defender.getClass().getSimpleName() +
                    " was eaten by " +
                    attacker.getClass().getSimpleName() + '!';
        } else if (defender instanceof Human) {
            line = "You were killed! :(";
        }
        line += '\n';
        addToLog(line);
    }

    public void saveState() {
        try {
            FileWriter file = new FileWriter("save.txt");

            // Save board size
            String line = String.valueOf(x) + ',' + String.valueOf(y) + '\n';
            file.write(line);

            // Save organisms
            for (Organism org : organisms) {
                line = org.getClass().getSimpleName() + ','
                        + String.valueOf(org.getStrength()) + ','
                        + String.valueOf(org.getInitiative()) + ','
                        + String.valueOf(org.getX()) + ','
                        + String.valueOf(org.getY()) + ','
                        + String.valueOf(org.getAge());
                if (org instanceof Human) {
                    line += ',';
                    line += String.valueOf(((Human) org).getCurrentSpecialCooldown());
                }
                line += '\n';
                file.write(line);
            }

            file.close();
        } catch (IOException e) {
            System.err.println("An error occured while saving game state!");
            e.printStackTrace();
        }
    }

    public World loadState() {
        try {
            File file = new File("save.txt");
            Scanner sc = new Scanner(file);

            String[] worldPos = sc.nextLine().split(",");
            int worldX = Integer.parseInt(worldPos[0]), worldY = Integer.parseInt(worldPos[1]);

            World world = new World(worldX, worldY, true);

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");

                String name = line[0];
                int strength = Integer.parseInt(line[1]),
                        initiative = Integer.parseInt(line[2]),
                        x = Integer.parseInt(line[3]),
                        y = Integer.parseInt(line[4]),
                        age = Integer.parseInt(line[5]);

                if (name.equals("Antelope")) {
                    world.addOrganism(new Antelope(world, strength, initiative, x, y, age));
                } else if (name.equals("Belladonna")) {
                    world.addOrganism(new Belladonna(world, strength, initiative, x, y, age));
                } else if (name.equals("Dandelion")) {
                    world.addOrganism(new Dandelion(world, strength, initiative, x, y, age));
                } else if (name.equals("Fox")) {
                    world.addOrganism(new Fox(world, strength, initiative, x, y, age));
                } else if (name.equals("Grass")) {
                    world.addOrganism(new Grass(world, strength, initiative, x, y, age));
                } else if (name.equals("Guarana")) {
                    world.addOrganism(new Guarana(world, strength, initiative, x, y, age));
                } else if (name.equals("Human")) {
                    int specialCooldown = Integer.parseInt(line[6]);
                    world.player = new Human(world, strength, initiative, x, y, age, specialCooldown);
                    world.addOrganism(world.getPlayer());
                } else if (name.equals("Sheep")) {
                    world.addOrganism(new Sheep(world, strength, initiative, x, y, age));
                } else if (name.equals("Sosnowsky")) {
                    world.addOrganism(new Sosnowsky(world, strength, initiative, x, y, age));
                } else if (name.equals("Turtle")) {
                    world.addOrganism(new Turtle(world, strength, initiative, x, y, age));
                } else if (name.equals("Wolf")) {
                    world.addOrganism(new Wolf(world, strength, initiative, x, y, age));
                }
            }

            sc.close();

            world.organisms.addAll(world.children);
            world.children.clear();

            return world;
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException");
            e.printStackTrace();
        }

        return null;
    }

    public void addChildren() {
        organisms.addAll(children);
        children.clear();
    }
}
