package game;

import java.util.Random;

import chaselogic.Core;

class BuildField {
    Field field;
    Options options;
    private int enemiesCount;
    private int wallsCount;
    private int size;

    BuildField(int enemiesCount, int wallsCount, int size, Options options) {
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        this.size = size;

        this.options = options;
        field = new Field(options);
        field.size = size;
        field.enemyCount = enemiesCount;
        if (options.getProfile().equals("dev")) {
            field.devMode = true;
        } else {
            field.devMode = false;
        }

        draw();
    }


    private void draw() {
        int currentWalls = 0;
        field.array = new char[size][size];
        field.enemyFieldX = new int[field.enemyCount];
        field.enemyFieldY = new int[field.enemyCount];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                field.array[y][x] = ' ';
            }
        }
        Random random = new Random();

        field.userX = random.nextInt(size);
        field.userY = random.nextInt(size);
        field.array[field.userY][field.userX] = 'o';

        if (field.userY == 0)
            field.array[field.userY + 1][field.userX] = '*';
        else if (field.userY == size - 1)
            field.array[field.userY - 1][field.userX] = '*';
        else if (field.userX == 0)
            field.array[field.userY][field.userX + 1] = '*';
        else if (field.userX == field.size - 1)
            field.array[field.userY][field.userX - 1] = '*';
        else
            field.array[field.userY + 1][field.userX] = '*';

        while (currentWalls < wallsCount) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (field.array[y][x] == ' ') {
                field.array[y][x] = '#';
                currentWalls++;
            }
        }
        int currentEnemies = 0;
        while (currentEnemies < enemiesCount) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (field.array[y][x] == ' ') {
                field.array[y][x] = 'X';
                field.enemyFieldX[currentEnemies] = x;
                field.enemyFieldY[currentEnemies] = y;
                currentEnemies++;
            }
        }

        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (field.array[y][x] != ' ' && field.array[y][x] != '*');

        field.array[y][x] = 'O';
        field.targetX = x;
        field.targetY = y;

        for (int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                if (field.array[i][j] == '*')
                    field.array[i][j] = ' ';
            }
        }
        field.printField();
        new Core(field);
    }
}

