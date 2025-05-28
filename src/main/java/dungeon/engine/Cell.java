package dungeon.engine;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane{
    private Item item;
    private boolean isWall;
    private boolean isEntry;

    public Cell() {
        this.isWall = false;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isEntry() {
        return isEntry;
    }

    public void setEntry(boolean entry) {
        isEntry = entry;
    }
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isNull() {
        return item == null;
    }
    public void enter(Player player) {
        if (item != null) {
            item.onEnter(player, this);
        }
    }
}
