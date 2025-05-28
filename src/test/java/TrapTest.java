
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrapTest {

    @Test
    void testOnEnterReducesHP() {
        Player player = new Player();
        Cell cell = new Cell();
        int originalHP = player.getHP();
        Trap trap = new Trap();
        trap.onEnter(player, cell);
        assertTrue(player.getHP() < originalHP);
    }
}
