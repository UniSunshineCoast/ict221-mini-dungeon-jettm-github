package dungeon.engine;

import java.util.Random;

public class RangedMutant implements Enemy {
    private static final Random rand = new Random();

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addScore(2);
        cell.setItem(null);
        System.out.println("You defeated a ranged mutant.");
    }

    public void tryAttack(Player player, int mutantX, int mutantY) {
        int dx = Math.abs(player.getX() - mutantX);
        int dy = Math.abs(player.getY() - mutantY);
        if ((dx == 2 && dy == 0) || (dy == 2 && dx == 0)) {
            if (rand.nextBoolean()) {
                player.addHP(-getDamage());
                System.out.println("A ranged mutant attacked you for 2 HP!");
            } else {
                System.out.println("A ranged mutant missed.");
            }
        }
    }
}