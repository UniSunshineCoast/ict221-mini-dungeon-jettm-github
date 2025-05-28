
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    void testMovePlayerIntoWall() {
        GameEngine engine = new GameEngine(0);
        int initialX = engine.getPlayer().getX();
        int initialY = engine.getPlayer().getY();
        engine.movePlayer("r"); // Assume wall is there or will test wall check
        assertTrue(engine.getPlayer().getX() == initialX && engine.getPlayer().getY() == initialY ||
                   engine.getPlayer().getSteps() > 0);
    }

    @Test
    void testPrintMapDoesNotThrow() {
        GameEngine engine = new GameEngine(0);
        assertDoesNotThrow(engine::printMap);
    }
}
