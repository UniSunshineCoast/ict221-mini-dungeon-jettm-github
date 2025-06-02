package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeleeMutantTest {

    @Test
    public void testOnEnterDealsDamage() {
        MeleeMutant mutant = new MeleeMutant();
        Player player = new Player();
        Cell cell = new Cell();
        int initialHP = player.getHP();
        mutant.onEnter(player, cell);
        assertTrue(player.getHP() < initialHP);
    }
    @Test
    void testOnEnterDestroysMutant() {
        Player player = new Player();
        Cell cell = new Cell();
        MeleeMutant rm = new MeleeMutant();
        cell.setItem(rm);
        rm.onEnter(player, cell);
        assertNull(cell.getItem());
    }
}

