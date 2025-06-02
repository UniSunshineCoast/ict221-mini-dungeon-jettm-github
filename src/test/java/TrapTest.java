package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrapTest {

    @Test
    public void testOnEnterDecreasesHP() {
        Trap trap = new Trap();
        Player player = new Player();
        Cell cell = new Cell();
        int initialHP = player.getHP();
        trap.onEnter(player, cell);
        assertTrue(player.getHP() < initialHP);
    }
}
