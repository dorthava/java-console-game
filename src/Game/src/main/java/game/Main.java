package game;

import com.beust.jcommander.JCommander;

import java.io.IOException;

class Main {
    public static void main(String[] args) {
        Args jArgs = new Args();
        JCommander.newBuilder().addObject(jArgs).build().parse(args);
        if (jArgs.getEnemiesCount() + jArgs.getWallsCount() >= jArgs.getSize() * jArgs.getSize() - 2) {
            System.out.println("Invalid count of enemies and walls");
            System.exit(0);
        }

        Options options = new Options(jArgs.getProfile());
        try {
            options.parseOptionsInFile();
            new BuildField(jArgs.getEnemiesCount(), jArgs.getWallsCount(), jArgs.getSize(), options);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}