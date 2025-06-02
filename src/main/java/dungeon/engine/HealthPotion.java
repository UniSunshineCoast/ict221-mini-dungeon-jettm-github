package dungeon.engine;

public class HealthPotion implements Item {
    @Override
    public String getSymbol() {
        return "H";
    }

    @Override
    public String onEnter(Player player, Cell cell) {
        player.addHP(4);
        cell.setItem(null);
        return ("You drank a health potion. You Gained 4HP");
    }
}
