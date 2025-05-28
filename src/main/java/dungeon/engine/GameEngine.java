package dungeon.engine;

import java.util.Scanner;

public class GameEngine {
    private Cell[][] map;
    private Player player;
    private int size;
    private final int maxSteps = 100;

    public GameEngine(int difficulty) {
        this.map = MapGenerator.generateFixedLevel(difficulty);
        this.size = map.length;
        this.player = new Player();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j].isEntry()) {
                    player.moveTo(i, j);
                    return;
                }
            }
        }
    }
    public int getSize() {
        return size;
    }
    public Cell[][] getMap() {
        return map;
    }
    public Player getPlayer() {
        return player;
    }
    public void printMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (player.getX() == i && player.getY() == j) {
                    System.out.print("P ");
                } else if (map[i][j].isWall()) {
                    System.out.print("# ");
                } else if (map[i][j].isEntry()) {
                    System.out.print("E ");
                } else {
                    Item item = map[i][j].getItem();
                    System.out.print((item != null ? item.getSymbol() : ".") + " ");
                }
            }
            System.out.println();
        }
        System.out.printf("HP: %d | Score: %d | Steps: %d\n\n",
                player.getHP(), player.getScore(), player.getSteps());
    }

    public void movePlayer(String dir) {
        int x = player.getX();
        int y = player.getY();
        int newX = x, newY = y;
        switch (dir) {
            case "u": newX--; break;
            case "d": newX++; break;
            case "l": newY--; break;
            case "r": newY++; break;
            default:
                System.out.println("Invalid command.");
                return;
        }
        if (newX < 0 || newY < 0 || newX >= size || newY >= size || map[newX][newY].isWall()) {
            System.out.println("You hit a wall.");
            return;
        }
        player.moveTo(newX, newY);
        map[newX][newY].enter(player);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter difficulty (0-10, default 3): ");
        String input = sc.nextLine();
        int difficulty = 3;
        try {
            difficulty = Math.max(0, Math.min(10, Integer.parseInt(input)));
        } catch (NumberFormatException ignored) {}

        GameEngine engine = new GameEngine(difficulty);
        System.out.println("Welcome to MiniDungeon Fixed Map Mode!");
        engine.printMap();

        while (engine.player.getHP() > 0 && engine.player.getSteps() < engine.maxSteps) {
            System.out.print("Move (u/d/l/r): ");
            String cmd = sc.nextLine();
            engine.movePlayer(cmd);
            engine.printMap();
        }

        if (engine.player.getHP() <= 0) {
            System.out.println("You died. Game over.");
        } else if (engine.player.getSteps() >= engine.maxSteps) {
            System.out.println("You ran out of steps. Game over.");
        } else {
            System.out.println("Thanks for playing!");
        }
    }
}

