package chaselogic;

import java.util.Scanner;

public class Core {
    public Printer field;
    private boolean gameOver;

    public Core(Printer _field) {
        field = _field;
        gameOver = false;
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            String _str = scanner.nextLine();
            if (userStep(_str.charAt(0))) {
                gameOver = gameOverCheck();
                if (field.devMode) {
                    String _str2 = scanner.nextLine();
                    while (_str2.charAt(0) != '8') {
                        _str2 = scanner.nextLine();
                    }
                }
                cpuSteps();
            }
            if (!gameOver) {
                gameOver = gameOverCheck();
            }
            if (!field.devMode && !gameOver) {
                field.printField();
            }

        }
        scanner.close();

    }

    private boolean gameOverCheck() {
        boolean _result = false;
        if (field.userX == field.targetX && field.userY == field.targetY) {
            System.out.println("User Win");
            _result = true;
        }
        for (int i = 0; i < field.enemyCount; i++) {
            if (_result != true && field.enemyFieldX[i] == field.userX && field.enemyFieldY[i] == field.userY) {
                System.out.println("CPU Win");
                _result = true;
            }
        }
        if ((field.userY == 0 || field.array[field.userY - 1][field.userX] == 'X'
                || field.array[field.userY - 1][field.userX] == '#')
                && (field.userY == field.size - 1 || field.array[field.userY + 1][field.userX] == 'X'
                || field.array[field.userY + 1][field.userX] == '#')
                && (field.userX == field.size - 1 || field.array[field.userY][field.userX + 1] == 'X'
                || field.array[field.userY][field.userX + 1] == '#')
                && (field.userX == 0 || field.array[field.userY][field.userX - 1] == 'X'
                || field.array[field.userY][field.userX - 1] == '#')) {
            System.out.println("CPU Win");
            _result = true;
        }

        return _result;
    }

    private void enemyStep(int _numberOfEnemy) {
        int enemyX = field.enemyFieldX[_numberOfEnemy];
        int enemyY = field.enemyFieldY[_numberOfEnemy];
        int[][] waveArray = new int[field.size][field.size];
        for (int y = 0; y < field.size; y++) {
            for (int x = 0; x < field.size; x++) {
                if (field.array[y][x] == '#' || field.array[y][x] == 'X') {
                    waveArray[y][x] = -2;
                } else {
                    waveArray[y][x] = -1;
                }

            }
        }
        waveArray[enemyY][enemyX] = 0;
        boolean waveStop = false;
        int stepCount = 0;
        while (!waveStop) {
            waveStop = true;
            for (int y = 0; y < field.size; y++) {
                for (int x = 0; x < field.size; x++) {
                    if (waveArray[y][x] == stepCount) {
                        if (y != 0 && waveArray[y - 1][x] == -1) {
                            waveArray[y - 1][x] = stepCount + 1;
                            waveStop = false;
                        }
                        if (y != field.size - 1 && waveArray[y + 1][x] == -1) {
                            waveArray[y + 1][x] = stepCount + 1;
                            waveStop = false;
                        }
                        if (x != 0 && waveArray[y][x - 1] == -1) {
                            waveArray[y][x - 1] = stepCount + 1;
                            waveStop = false;
                        }
                        if (x != field.size - 1 && waveArray[y][x + 1] == -1) {
                            waveArray[y][x + 1] = stepCount + 1;
                            waveStop = false;
                        }
                    }
                }
            }
            stepCount++;
        }
        changeEnemyPosition(waveArray, stepCount, _numberOfEnemy);

    }

    private void changeEnemyPosition(int[][] waveArray, int stepCount, int _numberOfEnemy) {
        stepCount = waveArray[field.userY][field.userX];
        int _tX = field.userX;
        int _tY = field.userY;
        if (waveArray[_tY][_tX] != -1) {
            while (stepCount != 1) {
                if (_tY != 0 && waveArray[_tY - 1][_tX] == stepCount - 1) {
                    _tY--;
                } else if (_tY != field.size - 1 && waveArray[_tY + 1][_tX] == stepCount - 1) {
                    _tY++;
                } else if (_tX != 0 && waveArray[_tY][_tX - 1] == stepCount - 1) {
                    _tX--;
                } else if (_tX != field.size - 1 && waveArray[_tY][_tX + 1] == stepCount - 1) {
                    _tX++;
                }
                stepCount--;
            }
            if (_tY != 0 && waveArray[_tY - 1][_tX] == 0) {

                field.enemyFieldY[_numberOfEnemy]++;
                field.array[_tY][_tX] = 'X';
                if (_tY - 1 == field.targetY && _tX == field.targetX) {
                    field.array[_tY - 1][_tX] = 'O';
                } else {
                    field.array[_tY - 1][_tX] = ' ';
                }

            } else if (_tY != field.size - 1 && waveArray[_tY + 1][_tX] == 0) {
                field.enemyFieldY[_numberOfEnemy]--;
                field.array[_tY][_tX] = 'X';
                if (_tY + 1 == field.targetY && _tX == field.targetX) {
                    field.array[_tY + 1][_tX] = 'O';
                } else {
                    field.array[_tY + 1][_tX] = ' ';
                }
            } else if (_tX != 0 && waveArray[_tY][_tX - 1] == 0) {
                field.enemyFieldX[_numberOfEnemy]++;
                field.array[_tY][_tX] = 'X';
                if (_tY == field.targetY && _tX - 1 == field.targetX) {
                    field.array[_tY][_tX - 1] = 'O';
                } else {
                    field.array[_tY][_tX - 1] = ' ';
                }
            } else if (_tX != field.size - 1 && waveArray[_tY][_tX + 1] == 0) {
                field.enemyFieldX[_numberOfEnemy]--;
                field.array[_tY][_tX] = 'X';
                if (_tY == field.targetY && _tX + 1 == field.targetX) {
                    field.array[_tY][_tX + 1] = 'O';
                } else {
                    field.array[_tY][_tX + 1] = ' ';
                }
            }
        }
    }

    private void cpuSteps() {
        for (int i = 0; i < field.enemyCount; i++) {
            boolean _theyAreNearly = false;
            if (field.enemyFieldX[i] != 0
                    && (field.enemyFieldX[i] - 1 == field.userX && field.enemyFieldY[i] == field.userY)) {
                _theyAreNearly = true;
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i]] = ' ';
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i] - 1] = 'X';
            }
            if (field.enemyFieldX[i] != field.size - 1
                    && (field.enemyFieldX[i] + 1 == field.userX && field.enemyFieldY[i] == field.userY)) {
                _theyAreNearly = true;
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i]] = ' ';
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i] + 1] = 'X';
            }
            if (field.enemyFieldX[i] == field.userX && field.enemyFieldY[i] != 0
                    && field.enemyFieldY[i] - 1 == field.userY) {
                _theyAreNearly = true;
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i]] = ' ';
                field.array[field.enemyFieldY[i] - 1][field.enemyFieldX[i]] = 'X';
            }
            if (field.enemyFieldX[i] == field.userX && field.enemyFieldX[i] != field.size - 1
                    && field.enemyFieldY[i] + 1 == field.userY) {
                _theyAreNearly = true;
                field.array[field.enemyFieldY[i]][field.enemyFieldX[i]] = ' ';
                field.array[field.enemyFieldY[i] + 1][field.enemyFieldX[i]] = 'X';
            }
            if (_theyAreNearly) {
                field.printField();
                System.out.println("CPU Win");
                System.exit(1);
            } else {
                enemyStep(i);
            }
        }
    }

    private boolean userStep(char _route) {
        boolean stepSuccesfull = false;
        switch (_route) {
            case 'w':
                if ((field.userY != 0) && (field.array[field.userY - 1][field.userX] != '#')) {
                    field.array[field.userY - 1][field.userX] = 'o';
                    field.array[field.userY][field.userX] = ' ';
                    field.userY = field.userY - 1;
                    stepSuccesfull = true;
                }
                break;
            case 's':
                if ((field.userY != field.size - 1) && (field.array[field.userY + 1][field.userX] != '#')) {
                    field.array[field.userY + 1][field.userX] = 'o';
                    field.array[field.userY][field.userX] = ' ';
                    field.userY = field.userY + 1;
                    stepSuccesfull = true;
                }
                break;
            case 'a':
                if ((field.userX != 0) && (field.array[field.userY][field.userX - 1] != '#')) {
                    field.array[field.userY][field.userX - 1] = 'o';
                    field.array[field.userY][field.userX] = ' ';
                    field.userX = field.userX - 1;
                    stepSuccesfull = true;
                }
                break;
            case 'd':
                if ((field.userX != field.size - 1) && (field.array[field.userY][field.userX + 1] != '#')) {
                    field.array[field.userY][field.userX + 1] = 'o';
                    field.array[field.userY][field.userX] = ' ';
                    field.userX = field.userX + 1;
                    stepSuccesfull = true;
                }
                break;
            case '9':
                System.out.println("CPU Win");
                System.exit(0);
                break;
            default:
                break;
        }
        return stepSuccesfull;
    }

}