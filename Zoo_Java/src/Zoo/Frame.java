package Zoo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import Zoo.Human.Actions;
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

public class Frame extends JFrame implements KeyListener, ActionListener {
    private World world;
    private JButton saveButton;
    private JButton loadButton;
    private JPanel boardPanel;
    private JScrollPane bottomPanel;
    private Tile[][] tiles;

    Frame(World world) {
        this.world = world;
        int x = world.getX();
        int y = world.getY();

        setFocusable(true);
        setTitle("Zoo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840, 840);
        setResizable(false);

        JLabel title = new JLabel("Cyryl Tokarczyk s188624 Informatyka Gr.4", JLabel.CENTER);
        title.setVerticalAlignment(JLabel.TOP);
        title.setFocusable(false);
        add(title, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(y, x, 0, 0));
        tiles = new Tile[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                tiles[i][j] = new Tile(j,i);
                boardPanel.add(tiles[i][j]);
            }
        }
        updateTiles();

        boardPanel.setFocusable(false);
        add(boardPanel, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel();
        BoxLayout layout = new BoxLayout(sidePanel, BoxLayout.Y_AXIS);
        sidePanel.setLayout(layout);

        saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);
        loadButton = new JButton("Load");
        loadButton.setFocusable(false);
        loadButton.addActionListener(this);

        sidePanel.add(saveButton);
        sidePanel.add(loadButton);

        sidePanel.setFocusable(false);
        add(sidePanel, BorderLayout.EAST);

        bottomPanel = new JScrollPane();
        bottomPanel.setFocusable(false);
        bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 100));
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    private class Tile extends JButton implements ActionListener {
        public AddOrganismMenu menu;
        public int x, y;

        public Tile(int x, int y) {
            super();
            this.x = x;
            this.y = y;
            setBackground(Color.WHITE);
            setFocusable(false);
            menu = new AddOrganismMenu(x, y);
            add(menu);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menu.show(this, getX(), getY());
            menu.setLocation(getX(), getY());
        }
    }

    private class AddOrganismMenu extends JPopupMenu {
        public int x, y;

        public AddOrganismMenu(int x, int y) {
            super();
            this.x = x;
            this.y = y;
            setFocusable(false);
            JMenuItem[] items = new JMenuItem[10];
            items[0] = new JMenuItem("Wolf");
            items[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Wolf(world, x, y));
                    updateTiles();                 
                }
            });
            items[1] = new JMenuItem("Sheep");
            items[1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Sheep(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[2] = new JMenuItem("Fox");
            items[2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Fox(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[3] = new JMenuItem("Turtle");
            items[3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Turtle(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[4] = new JMenuItem("Antelope");
            items[4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Antelope(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[5] = new JMenuItem("Grass");
            items[5].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Grass(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[6] = new JMenuItem("Dandelion");
            items[6].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Dandelion(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[7] = new JMenuItem("Guarana");
            items[7].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Guarana(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[8] = new JMenuItem("Belladonna");
            items[8].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Belladonna(world, x, y));                    
                    updateTiles();                 
                }
            });
            items[9] = new JMenuItem("Sosnowsky");
            items[9].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.addOrganism(new Sosnowsky(world, x, y));                    
                    updateTiles();                 
                }
            });

            for (JMenuItem item : items) {
                add(item);
            }

            // menuItem = new AddOrganismMenuItem(x, y);
            // add(menuItem);
        }
    }

    // private class AddOrganismMenuItem extends JMenuItem {
    //     public AddOrganismComboBox comboBox;
    //     public int x, y;

    //     public AddOrganismMenuItem(int x, int y) {
    //         super();
    //         this.x = x;
    //         this.y = y;
    //         setFocusable(false);
    //         comboBox = new AddOrganismComboBox(x, y);
    //         add(comboBox);
    //     }
    // }

    // private class AddOrganismComboBox extends JComboBox {
    //     public static final String[] options = new String[]{
    //         "Wolf",
    //         "Sheep",
    //         "Fox",
    //         "Turtle",
    //         "Antelope",
    //         "Grass",
    //         "Dandelion",
    //         "Guarana",
    //         "Belladonna",
    //         "Sosnowsky",
    //     };

    //     public int x, y;

    //     public AddOrganismComboBox(int x, int y) {
    //         super(options);
    //         this.x = x;
    //         this.y = y;
    //         setFocusable(false);
    //         addActionListener(this);
    //     }

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         String chosen = (String) ((JComboBox) e.getSource()).getSelectedItem();

    //         if (chosen.equals("Wolf")) {
    //             world.addOrganism(new Wolf(world, x, y));
    //         } else if (chosen.equals("Sheep")) {
    //             world.addOrganism(new Sheep(world, x, y));
    //         } else if (chosen.equals("Fox")) {
    //             world.addOrganism(new Fox(world, x, y));
    //         } else if (chosen.equals("Turtle")) {
    //             world.addOrganism(new Turtle(world, x, y));
    //         } else if (chosen.equals("Antelope")) {
    //             world.addOrganism(new Antelope(world, x, y));
    //         } else if (chosen.equals("Grass")) {
    //             world.addOrganism(new Grass(world, x, y));
    //         } else if (chosen.equals("Dandelion")) {
    //             world.addOrganism(new Dandelion(world, x, y));
    //         } else if (chosen.equals("Guarana")) {
    //             world.addOrganism(new Guarana(world, x, y));
    //         } else if (chosen.equals("Belladonna")) {
    //             world.addOrganism(new Belladonna(world, x, y));
    //         } else if (chosen.equals("Sosnowsky")) {
    //             world.addOrganism(new Sosnowsky(world, x, y));
    //         }

    //         world.addChildren();
    //     }
    // }

    private void updateTiles() {
        for (int j = 0; j < world.getY(); j++) {
            for (int i = 0; i < world.getX(); i++) {
                Organism organism = world.getOrganism(i, j);
                if (organism != null) {
                    tiles[j][i].setBackground(organism.getColor());
                } else {
                    tiles[j][i].setBackground(Color.WHITE);
                }
            }
        }

        SwingUtilities.updateComponentTreeUI(boardPanel);
    }

    private void updateLog() {
        Vector<String> log = world.getLog();
        JTextArea textArea = new JTextArea(log.size(), 1);
        textArea.setEditable(false);

        for (String line : log) {
            textArea.append(line);
        }

        bottomPanel.setViewportView(textArea);
        SwingUtilities.updateComponentTreeUI(bottomPanel);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());

        Human player = world.getPlayer();
        if (player != null && !player.markedForRemoval) {
            int keyCode = e.getKeyCode();
            if ((KeyEvent.VK_LEFT <= keyCode && keyCode <= KeyEvent.VK_DOWN) || keyCode == KeyEvent.VK_SPACE) {
                switch (keyCode) {
                    case KeyEvent.VK_LEFT: // Left arrow
                        if (!player.isLegalMove(player.getX() - 1, player.getY())) {
                            return;
                        }

                        player.setAction(Actions.Left);
                        break;
                    case KeyEvent.VK_UP: // Up arrow
                        if (!player.isLegalMove(player.getX(), player.getY() - 1)) {
                            return;
                        }

                        player.setAction(Actions.Up);
                        break;
                    case KeyEvent.VK_RIGHT: // Right arrow
                        if (!player.isLegalMove(player.getX() + 1, player.getY())) {
                            return;
                        }

                        player.setAction(Actions.Right);
                        break;
                    case KeyEvent.VK_DOWN: // Down arrow
                        if (!player.isLegalMove(player.getX(), player.getY() + 1)) {
                            return;
                        }

                        player.setAction(Actions.Down);
                        break;
                    case KeyEvent.VK_SPACE:
                        if (!player.isSpecialReady()) {
                            return;
                        }

                        player.setAction(Actions.Special);
                        break;
                    default:
                        return;
                }

                world.makeTurn();
                updateTiles();
                updateLog();
            }
        } else {
            world.makeTurn();
            updateTiles();
            updateLog();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == saveButton) {
            world.saveState();
        } else if (source == loadButton) {
            world = world.loadState();
            int x = world.getX(), y = world.getY();
            remove(boardPanel);
            boardPanel = new JPanel(new GridLayout(y, x, 0, 0));
            tiles = new Tile[y][x];
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    tiles[i][j] = new Tile(j, i);
                    boardPanel.add(tiles[i][j]);
                }
            }
            updateTiles();
            add(boardPanel);
        }
    }
}
