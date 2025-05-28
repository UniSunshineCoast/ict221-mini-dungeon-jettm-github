package dungeon.engine;

import java.util.Random;

public class RangedMutant implements Enemy {
    private String symbol;
    private int damage;

    public RangedMutant() {
        this.symbol = "R";
        this.damage = 2; // Fixed ranged attack damage
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        System.out.println("You stepped on a ranged mutant. You destroyed it before it could react.");
        cell.setItem(null); // remove the mutant
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public void act(Cell[][] map, Player player, int myX, int myY) {
        int px = player.getX();
        int py = player.getY();
        Random rand = new Random();

        // Check vertical or horizontal alignment within 2 cells
        if ((myX == px && Math.abs(myY - py) <= 2) || (myY == py && Math.abs(myX - px) <= 2)) {
            if (!hasWallBetween(map, myX, myY, px, py)) {
                if (rand.nextBoolean()) {
                    player.addHP(-damage);
                    System.out.println("Ranged mutant shot you from afar! You lost " + damage + " HP.");
                } else {
                    System.out.println("A ranged mutant missed their shot.");
                }
            }
        }
    }

    private boolean hasWallBetween(Cell[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++) {
                if (map[x1][y].isWall()) return true;
            }
        } else if (y1 == y2) {
            for (int x = Math.min(x1, x2) + 1; x < Math.max(x1, x2); x++) {
                if (map[x][y1].isWall()) return true;
            }
        }
        return false;
    }
}