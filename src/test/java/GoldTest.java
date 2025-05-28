
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GoldTest {

    @Test
    void testOnEnterIncreasesScore() {
        Player player = new Player();
        Cell cell = new Cell();
        Gold gold = new Gold();
        gold.onEnter(player, cell);
        assertTrue(player.getScore() > 0);
    }
}
