package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LadderTest {

    @Test
    public void testOnEnterDoesNotCrash() {
        Ladder ladder = new Ladder();
        Player player = new Player();
        Cell cell = new Cell();
        assertDoesNotThrow(() -> ladder.onEnter(player, cell));
    }
}
