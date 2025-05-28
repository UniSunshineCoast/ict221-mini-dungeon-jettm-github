package dungeon.engine;

public class Gold implements Item {
    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addScore(2);
        cell.setItem(null);
        System.out.println("You picked up a gold.");
    }
}