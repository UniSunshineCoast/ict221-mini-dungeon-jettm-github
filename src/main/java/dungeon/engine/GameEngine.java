package dungeon.engine;

import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class GameEngine {
    private Cell[][] map;
    private Player player;
    private int size;
    private final int maxSteps = 100;
    private final String SCORE_FILE = "top_scores.txt";
    private final int difficulty;
    private int currentLevel = 1;

    public GameEngine(int difficulty) {
        this.difficulty = difficulty;
        this.map = MapGenerator.generateSpecificLevel(difficulty,currentLevel);
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
            case "u":
                newX--;
                break;
            case "d":
                newX++;
                break;
            case "l":
                newY--;
                break;
            case "r":
                newY++;
                break;
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
        if (!(map[newX][newY].getItem() == null) && (map[newX][newY].getItem().getSymbol().equals("L"))) {
            currentLevel++;
            if (currentLevel < 3) {
                map = MapGenerator.generateSpecificLevel(difficulty, currentLevel);
            }
        }
    }

    private void triggerRangedMutantActions() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Item item = map[i][j].getItem();
                if (!(item==null) && item.getSymbol().equals("R")) {
                    ((RangedMutant) item).act(map, player, i, j);
                }
            }
        }
    }

    public void saveScore() {
        List<String> scores = new ArrayList<>();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String entry = player.getScore() + ", " + now;

        try {
            File file = new File(SCORE_FILE);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        scores.add(line);
                    }
                }
            }
            scores.add(entry);
            scores.sort((a, b) -> Integer.parseInt(b.split(",")[0].trim()) - Integer.parseInt(a.split(",")[0].trim()));
            if (scores.size() > 5) {
                scores = scores.subList(0, 5);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String s : scores) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save score: " + e.getMessage());
        }
    }

    public List<String> getTopScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            System.out.println("No scores found.");
        }
        return scores;
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

        while (engine.player.getHP() > 0 && engine.player.getSteps() < engine.maxSteps && engine.currentLevel < 3) {
            System.out.print("Move (u/d/l/r): ");
            String cmd = sc.nextLine();
            engine.movePlayer(cmd);
            engine.triggerRangedMutantActions();
            engine.printMap();
        }

        if (engine.player.getHP() <= 0) {
            System.out.println("You died. Game over.");
            engine.player.addScore(-1);
        } else if (engine.player.getSteps() >= engine.maxSteps) {
            engine.player.addScore(-1);
            System.out.println("You ran out of steps. Game over.");
        } else {
            System.out.println("WINNER WINNER CHICKEN DINNER!");
            System.out.println("Thanks for playing!");

        }
        engine.saveScore();
        for (int i = 0; i < engine.getTopScores().size(); i++) {
            System.out.println("#" + Integer.toString(i+1) + " " + engine.getTopScores().get(i));
        }
    }
}

