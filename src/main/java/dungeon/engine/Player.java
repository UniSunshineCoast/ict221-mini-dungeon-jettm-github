package dungeon.engine;

import java.io.Serializable;

public class Player implements Serializable {
    private int hp = 10;
    private int score = 0;
    private int steps = 0;
    private int x = 0, y = 0;

    public void moveTo(int newX, int newY) {
        x = newX;
        y = newY;
        steps++;
    }

    public void addScore(int value) {
        score += value;
    }

    public void addHP(int value) {
        hp = Math.min(10, hp + value);
    }

    public int getHP() { return hp; }
    public int getScore() { return score; }
    public int getSteps() { return steps; }
    public int getX() { return x; }
    public int getY() { return y; }
}
