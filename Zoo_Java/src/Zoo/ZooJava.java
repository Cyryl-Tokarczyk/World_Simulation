package Zoo;

import java.util.Scanner;

import javax.swing.SwingUtilities;

public class ZooJava {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        World world = new World(x, y, false);

        Frame frame = new Frame(world);
        SwingUtilities.updateComponentTreeUI(frame);

        scanner.close();
    }
}