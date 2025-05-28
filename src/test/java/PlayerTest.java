
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testMoveToIncrementsSteps() {
        Player player = new Player();
        player.moveTo(1, 1);
        assertEquals(1, player.getSteps());
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void testAddScore() {
        Player player = new Player();
        player.addScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testAddHPWithCap() {
        Player player = new Player();
        player.addHP(5);
        assertTrue(player.getHP() <= 10);
    }
}
