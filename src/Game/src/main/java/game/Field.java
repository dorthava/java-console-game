package game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import chaselogic.Printer;

public class Field extends Printer {
    public Options options;

    public Field(Options _options) {
        options = _options;
    }

    public void printField() {
        clearConsole();

        ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (array[y][x] == options.getEmptyChar()) {
                    cp.setBackgroundColor(options.getEmptyColor());
                } else if (array[y][x] == options.getWallChar()) {
                    cp.setBackgroundColor(options.getWallColor());
                } else if (array[y][x] == options.getGoalChar()) {
                    cp.setBackgroundColor(options.getGoalColor());
                } else if (array[y][x] == options.getPlayerChar()) {
                    cp.setBackgroundColor(options.getPlayerColor());
                } else if (array[y][x] == options.getEnemyChar()) {
                    cp.setBackgroundColor(options.getEnemyColor());
                }
                cp.print(array[y][x]);
            }
            System.out.println();
        }
    }
}
