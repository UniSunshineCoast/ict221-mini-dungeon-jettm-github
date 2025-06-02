package dungeon.engine;

import java.io.Serializable;

public interface Item extends Serializable {
    String getSymbol();
    String onEnter(Player player, Cell cell);
}