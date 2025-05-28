
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testEnterWithItem() {
        Player player = new Player();
        Cell cell = new Cell();
        cell.setItem(new Gold());
        int initialScore = player.getScore();
        cell.enter(player);
        assertTrue(player.getScore() > initialScore);
    }

    @Test
    void testEnterWithoutItem() {
        Player player = new Player();
        Cell cell = new Cell();
        assertDoesNotThrow(() -> cell.enter(player));
    }
}
