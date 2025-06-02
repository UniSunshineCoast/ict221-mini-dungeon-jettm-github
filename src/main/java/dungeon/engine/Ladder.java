package dungeon.engine;

public class Ladder implements Item {
    @Override
    public String getSymbol() {
        return "L";
    }

    @Override
    public String onEnter(Player player, Cell cell) {
        return ("You found the ladder and advanced to the next level or exited the dungeon.");
    }
}