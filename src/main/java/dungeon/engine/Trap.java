package dungeon.engine;

public class Trap implements Enemy {
    @Override
    public String getSymbol() {return "T";}

    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public String onEnter(Player player, Cell cell) {
        player.addHP(-getDamage());
        return ("You fell into a trap. You lost 2HP");
    }
}