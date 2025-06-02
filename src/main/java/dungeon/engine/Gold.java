package dungeon.engine;

public class Gold implements Item {
    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public String onEnter(Player player, Cell cell) {
        player.addScore(2);
        cell.setItem(null);
        return ("You picked up a gold.");
    }
}