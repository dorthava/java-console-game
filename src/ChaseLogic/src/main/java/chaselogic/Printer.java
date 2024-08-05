package chaselogic;

import java.io.IOException;

public abstract class Printer {
    public char array[][];
    public int userX;
    public int userY;
    public int targetX;
    public int targetY;
    public int enemyCount;
    public int size;
    public boolean devMode;

    public int[] enemyFieldX;
    public int[] enemyFieldY;

    public abstract void printField();

    public final static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
