package dungeon.engine;

public class MeleeMutant implements Enemy {
    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addHP(-getDamage());
        player.addScore(2);
        cell.setItem(null);
        System.out.println("You defeated a melee mutant.");
    }
}
