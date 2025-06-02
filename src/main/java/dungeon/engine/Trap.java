package dungeon.engine;

public class Trap implements Item {
    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public String onEnter(Player player, Cell cell) {
        player.addHP(-2);
        return ("You fell into a trap. You lost 2HP");
    }
}