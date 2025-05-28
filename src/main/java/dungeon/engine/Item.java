package dungeon.engine;

public interface Item {
    String getSymbol();
    void onEnter(Player player, Cell cell);
}