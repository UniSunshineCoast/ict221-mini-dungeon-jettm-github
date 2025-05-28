package dungeon.engine;

import java.util.Random;

public class MapGenerator {
    public static Cell[][] generateFixedLevel(int difficulty) {
        String[] rows = {
                ". . . # . . . . . .",
                ". # . # . # # . # #",
                ". # . . . . . . . .",
                ". # # # # # # # # .",
                ". . . . . . . . # .",
                ". # . . # # # . # .",
                ". # . . # . . . # .",
                ". # . . # . . # # .",
                "# # . . # # . # . .",
                "E . . . . . . # L ."
        };

        int size = rows.length;
        Cell[][] map = new Cell[size][size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            String[] symbols = rows[i].split(" ");
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell();
                switch (symbols[j]) {
                    case "#":
                        cell.setWall(true);
                        break;
                    case "E":
                        cell.setEntry(true);
                        break;
                    case "L":
                        cell.setItem(new Ladder());
                        break;
                }
                map[i][j] = cell;
            }
        }

        placeItems(map, new Gold(), 5, rand);
        placeItems(map, new Trap(), 5, rand);
        placeItems(map, new MeleeMutant(), 3, rand);
        placeItems(map, new HealthPotion(), 2, rand);
        for (int i = 0; i < difficulty; i++) {
            placeItems(map, new RangedMutant(), 1, rand);
        }

        return map;
    }

    private static void placeItems(Cell[][] map, Item item, int count, Random rand) {
        int size = map.length;
        for (int i = 0; i < count;) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            Cell cell = map[x][y];
            if (!cell.isWall() && !cell.isEntry() && cell.getItem() == null) {
                cell.setItem(item);
                i++;
            }
        }
    }
}