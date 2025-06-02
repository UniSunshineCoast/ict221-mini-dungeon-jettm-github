package dungeon.engine;

import java.util.Random;

public class RangedMutant implements Enemy {
    private String symbol = "R";
    private int damage = 2;

    public RangedMutant() {
    }

    public String onEnter(Player player, Cell cell) {
        cell.setItem((Item)null);
        player.addScore(2);
        return ("You stepped on a ranged mutant.");
    }

    public int getDamage() {
        return this.damage;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String act(Cell[][] map, Player player, int myX, int myY) {
        int px = player.getX();
        int py = player.getY();
        Random rand = new Random();
        if ((myX == px && Math.abs(myY - py) <= 2 || myY == py && Math.abs(myX - px) <= 2) && !this.hasWallBetween(map, myX, myY, px, py)) {
            if (rand.nextInt(2) == 1) {
                player.addHP(-this.damage);
                return ("Ranged mutant shot you from afar! You lost " + this.damage + "HP.");
            } else {
                return ("A ranged mutant missed their shot.");
            }
        }
        return "";
    }

    private boolean hasWallBetween(Cell[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for(int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); ++y) {
                if (map[x1][y].isWall()) {
                    return true;
                }
            }
        } else if (y1 == y2) {
            for(int x = Math.min(x1, x2) + 1; x < Math.max(x1, x2); ++x) {
                if (map[x][y1].isWall()) {
                    return true;
                }
            }
        }

        return false;
    }
}
