package dungeon.engine;

public class Trap implements Item {
    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addHP(-2);
        System.out.println("You fell into a trap.");
    }
}