package dungeon.engine;

public class MeleeMutant implements Enemy {
    private String symbol;
    private int damage;

    public MeleeMutant() {
        this.symbol = "M";
        this.damage = 2; // Fixed ranged attack damage
    }
    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addHP(-getDamage());
        player.addScore(2);
        cell.setItem(null);
        System.out.println("You defeated a melee mutant.");
    }
}
