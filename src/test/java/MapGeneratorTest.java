package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapGeneratorTest {

    @Test
    public void testGenerateMapNotNull() {
        for (int i=1; i<3;i++) { //Testing Both Maps
            Cell[][] map = MapGenerator.generateSpecificLevel(3,i);
            assertNotNull(map);
        }
    }

    @Test
    public void testGenerateMapCorrectSize() {
        for (int i=1; i<3;i++) { //Testing Both Maps
        Cell[][] map = MapGenerator.generateSpecificLevel(3, i);
        assertEquals(10, map.length);
        for (Cell[] row : map) {
            assertEquals(10, row.length);
        }
        }
    }

    @Test
    public void testCorrectNumberOfItems() {
        for (int level = 1; level <= 3; level++) {
            int difficulty = 4;
            Cell[][] map = MapGenerator.generateSpecificLevel(difficulty, level);

            int traps = 0, gold = 0, melee = 0, ranged = 0, potions = 0, ladders = 0, entries = 0;

            for (Cell[] cells : map) {
                for (Cell cell : cells) {
                    if (cell.isEntry()){entries++;}
                    if (cell.getItem() != null) {
                        Item item = cell.getItem();
                        switch (item.getSymbol()) {
                            case "T" -> traps++;
                            case "G" -> gold++;
                            case "M" -> melee++;
                            case "R" -> ranged++;
                            case "H" -> potions++;
                            case "L" -> ladders++;
                        }
                    }
                }
            }

        assertEquals(5, traps);
        assertEquals(5, gold);
        assertEquals(3, melee);
        assertEquals(difficulty, ranged);
        assertEquals(2, potions);
        assertEquals(1, ladders);
        assertEquals(1, entries);

        }
    }

}
