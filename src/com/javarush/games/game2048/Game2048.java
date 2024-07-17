package com.javarush.games.game2048;
import com.javarush.engine.cell.*;

public class Game2048 extends Game {
    private boolean isGameStopped = false;
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private int score;

    private void createGame() {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }

    private void createNewNumber() {
        int x, y, value;
        if (getMaxTileValue() == 2048) {
            win();
        }
        do {
            x = getRandomNumber(SIDE);
            y = getRandomNumber(SIDE);
            value = gameField[x][y];
        }
        while (value != 0);

        if (getRandomNumber(10) < 9) {
            gameField[x][y] = 2;
        } else {
            gameField[x][y] = 4;
        }

    }

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }


    @Override
    public void onKeyPress(Key key) {

        if(key == Key.SPACE && isGameStopped == true){
            score = 0;
            setScore(score);
            createGame();
            isGameStopped = false;
            drawScene();
        } else {


            if (canUserMove()) {
                if (key == Key.LEFT) {
                    moveLeft();
                } else if (key == Key.RIGHT) {
                    moveRight();
                } else if (key == Key.UP) {
                    moveUp();
                } else if (key == Key.DOWN) {
                    moveDown();
                } else return;

                drawScene();
            } else {
                gameOver();
            }
        }
    }

    private void moveLeft() {
        boolean boolCreateNewNum = false;
        for (int[] row : gameField) {
            boolean res1 = compressRow(row);
            boolean res2 = mergeRow(row);
            if (res2) {
                compressRow(row);
            }
            if (res1 || res2) {
                boolCreateNewNum = true;
            }
        }
        if (boolCreateNewNum) {
            createNewNumber();
        }
    }

    private void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    private void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private void setCellColoredNumber(int x, int y, int value) {
        String strValue = (value == 0) ? "" : String.valueOf(value);
        setCellValueEx(x, y, getColorByValue(value), strValue);
    }

    private Color getColorByValue(int value) {
        switch (value) {
            case 0:
                return Color.WHITE;
            case 2:
                return Color.LIGHTYELLOW;
            case 4:
                return Color.BISQUE;
            case 8:
                return Color.LAVENDER;
            case 16:
                return Color.ALICEBLUE;
            case 32:
                return Color.ANTIQUEWHITE;
            case 64:
                return Color.AZURE;
            case 128:
                return Color.PALEGOLDENROD;
            case 256:
                return Color.KHAKI;
            case 512:
                return Color.DARKKHAKI;
            case 1024:
                return Color.YELLOW;
            case 2048:
                return Color.GOLD;
            default:
                return Color.NONE;

        }
    }

    private boolean compressRow(int[] row) {
        int insertPosition = 0;
        boolean result = false;
        for (int x = 0; x < SIDE; x++) {
            if (row[x] > 0) {
                if (x != insertPosition) {
                    row[insertPosition] = row[x];
                    row[x] = 0;
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    private boolean mergeRow(int[] row) {

        boolean result = false;
        for (int i = 0; i < SIDE - 1; i++) {
            if (row[i] != 0 && row[i] == row[i + 1]) {
                row[i] = row[i] + row[i + 1];
                row[i + 1] = 0;
                result = true;
                if (result){
                    score += row[i];
                    setScore(score);
                }
            }
        }
        return result;
    }

    private void drawScene() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                setCellColoredNumber(i, j, gameField[j][i]);
            }
        }
    }

    private void rotateClockwise() {
        int[][] temp = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                temp[j][SIDE - 1 - i] = gameField[i][j];

            }
        }

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                gameField[i][j] = temp[i][j];
            }
        }
    }

    private int getMaxTileValue() {
        int result = gameField[0][0];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                result = gameField[i][j] > result ? gameField[i][j] : result;
            }

        }
        return result;
    }

    private void win() {
        showMessageDialog(Color.OLIVE, "You are the best, you win!", Color.GREY, 25);
        isGameStopped = true;
    }
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.YELLOW,"Game is Over",Color.BLUEVIOLET, 40 );
    }

    private boolean canUserMove() {
        int count = 0;
        boolean result = false;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (gameField[i][j] == 0){
                    count++;
                } else if (i < SIDE - 1 && gameField[i + 1][j] == gameField[i][j]){
                    count++;
                } else if (j < SIDE - 1 && gameField[i][j + 1] == gameField[i][j]){
                    count++;
                }

            }
        }
        result = (count > 0)? true: false;
        return result;
    }


}
