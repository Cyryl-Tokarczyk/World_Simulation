package Zoo;
import java.util.Random;

public enum Directions {
    Up,
    Down,
    Left,
    Right,
    UpLeft,
    UpRight,
    DownLeft,
    DownRight;

    public static Directions random() {
        return Directions.values()[new Random().nextInt(Directions.values().length)];
    }
}
