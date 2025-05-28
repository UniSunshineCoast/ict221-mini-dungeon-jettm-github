package dungeon.engine;

public class HealthPotion implements Item {
    @Override
    public String getSymbol() {
        return "H";
    }

    @Override
    public void onEnter(Player player, Cell cell) {
        player.addHP(4);
        cell.setItem(null);
        System.out.println("You drank a health potion.");
    }
}
