
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapGeneratorTest {

    @Test
    void testGenerateFixedLevelSize() {
        Cell[][] map = MapGenerator.generateFixedLevel(3);
        assertEquals(10, map.length);
        assertEquals(10, map[0].length);
    }

    @Test
    void testItemPlacementCounts() {
        Cell[][] map = MapGenerator.generateFixedLevel(3);
        int trapCount = 0, goldCount = 0, meleeCount = 0, potionCount = 0, rangedCount = 0;

        for (Cell[] row : map) {
            for (Cell cell : row) {
                Item item = cell.getItem();
                if (item instanceof Trap) trapCount++;
                else if (item instanceof Gold) goldCount++;
                else if (item instanceof MeleeMutant) meleeCount++;
                else if (item instanceof HealthPotion) potionCount++;
                else if (item instanceof RangedMutant) rangedCount++;
            }
        }

        assertEquals(5, trapCount);
        assertEquals(5, goldCount);
        assertEquals(3, meleeCount);
        assertEquals(2, potionCount);
        assertEquals(3, rangedCount); // difficulty = 3
    }
}
