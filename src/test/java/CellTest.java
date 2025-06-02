package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testSetAndGetItem() {
        Cell cell = new Cell();
        Gold gold = new Gold();
        cell.setItem(gold);
        assertEquals(gold, cell.getItem());
    }

    @Test
    public void testEnterTriggersItemInteraction() {
        Cell cell = new Cell();
        Player player = new Player();
        Trap trap = new Trap();
        cell.setItem(trap);
        int hpBefore = player.getHP();
        cell.enter(player);
        assertTrue(player.getHP() < hpBefore);
    }

    @Test
    public void testEnterWithNullItem() {
        Cell cell = new Cell();
        Player player = new Player();
        cell.setItem(null);
        assertDoesNotThrow(() -> cell.enter(player));
    }
}
