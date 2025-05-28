
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LadderTest {

    @Test
    void testOnEnterPrintsMessage() {
        Player player = new Player();
        Ladder ladder = new Ladder();
        assertDoesNotThrow(() -> ladder.onEnter(player, new Cell()));
    }
}
