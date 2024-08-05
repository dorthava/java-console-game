package game;

import com.diogonunes.jcdp.color.api.Ansi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Options {
    private final String profile;

    private char enemyChar;
    private char playerChar;
    private char wallChar;
    private char goalChar;
    private char emptyChar;

    private Ansi.BColor enemyColor;
    private Ansi.BColor playerColor;
    private Ansi.BColor wallColor;
    private Ansi.BColor goalColor;
    private Ansi.BColor emptyColor;

    public Options(String profile) {
        this.profile = profile;
    }

    public void parseOptionsInFile() throws IOException {
        if (profile == null || !profile.equals("production") && !profile.equals("dev")) {
            System.err.println("Неверно передан параметр. Должен быть или --profile=production или --profile=dev");
            throw new IOException();
        }

        InputStream inputStream;
        if (profile.equals("production")) {
            inputStream = Main.class.getResourceAsStream("/application-production.properties");
        } else {
            inputStream = Main.class.getResourceAsStream("/application-dev.properties");
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            enemyChar = getChar(properties.getProperty("enemy.char"));
            playerChar = getChar(properties.getProperty("player.char"));
            wallChar = getChar(properties.getProperty("wall.char"));
            goalChar = getChar(properties.getProperty("goal.char"));
            emptyChar = getChar(properties.getProperty("empty.char"));

            enemyColor = getBackColor(properties.getProperty("enemy.color"));
            playerColor = getBackColor(properties.getProperty("player.color"));
            wallColor = getBackColor(properties.getProperty("wall.color"));
            goalColor = getBackColor(properties.getProperty("goal.color"));
            emptyColor = getBackColor(properties.getProperty("empty.color"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IOException();
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    private char getChar(String charName) {
        if (charName == null) return '0';
        if (charName.isEmpty()) return ' ';
        return charName.charAt(0);
    }

    private Ansi.BColor getBackColor(String colorName) {
        Ansi.BColor backgroundColor = Ansi.BColor.WHITE;
        if (colorName.equals("BLACK")) {
            backgroundColor = Ansi.BColor.BLACK;
        } else if (colorName.equals("RED")) {
            backgroundColor = Ansi.BColor.RED;
        } else if (colorName.equals("GREEN")) {
            backgroundColor = Ansi.BColor.GREEN;
        } else if (colorName.equals("YELLOW")) {
            backgroundColor = Ansi.BColor.YELLOW;
        } else if (colorName.equals("BLUE")) {
            backgroundColor = Ansi.BColor.BLUE;
        } else if (colorName.equals("MAGENTA")) {
            backgroundColor = Ansi.BColor.MAGENTA;
        } else if (colorName.equals("CYAN")) {
            backgroundColor = Ansi.BColor.CYAN;
        }
        return backgroundColor;
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public char getPlayerChar() {
        return playerChar;
    }

    public char getWallChar() {
        return wallChar;
    }

    public char getGoalChar() {
        return goalChar;
    }

    public char getEmptyChar() {
        return emptyChar;
    }

    public Ansi.BColor getEnemyColor() {
        return enemyColor;
    }

    public Ansi.BColor getPlayerColor() {
        return playerColor;
    }

    public Ansi.BColor getWallColor() {
        return wallColor;
    }

    public Ansi.BColor getGoalColor() {
        return goalColor;
    }

    public Ansi.BColor getEmptyColor() {
        return emptyColor;
    }

    public String getProfile() {
        return profile;
    }
}
