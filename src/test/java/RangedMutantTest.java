package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RangedMutantTest {

    @Test
    void testRangedMutantShootsPlayer() {
        Player player = new Player();
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        RangedMutant rm = new RangedMutant();
        map[2][2].setItem(rm);
        player.moveTo(2, 3); // In range

        int hpBefore = player.getHP();
        rm.act(map, player, 2, 2);
        int hpAfter = player.getHP();

        assertTrue(hpAfter <= hpBefore);
    }

    @Test
    void testBlockedByWall() {
        Player player = new Player();
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        RangedMutant rm = new RangedMutant();
        map[2][1].setItem(rm);
        map[2][2].setWall(true); // Wall blocks shot
        player.moveTo(2, 3);

        int hpBefore = player.getHP();
        rm.act(map, player, 2, 1);
        int hpAfter = player.getHP();

        assertEquals(hpBefore, hpAfter);
    }

    @Test
    void testOnEnterDestroysMutant() {
        Player player = new Player();
        Cell cell = new Cell();
        RangedMutant rm = new RangedMutant();
        cell.setItem(rm);
        rm.onEnter(player, cell);
        assertNull(cell.getItem());
    }
}
