
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeleeMutantTest {

    @Test
    void testOnEnterDoesDamage() {
        Player player = new Player();
        Cell cell = new Cell();
        MeleeMutant mutant = new MeleeMutant();
        int hpBefore = player.getHP();
        mutant.onEnter(player, cell);
        assertTrue(player.getHP() < hpBefore);
    }
}
