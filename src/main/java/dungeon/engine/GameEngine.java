package dungeon.engine;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class GameEngine implements Serializable {
    private Cell[][] map;
    private Player player;
    private int size;
    private final int maxSteps = 100;
    private final String SCORE_FILE = "top_scores.txt";
    private final int difficulty;
    private int currentLevel = 1;
    private List<String> log = new ArrayList();
    private final List<String> sysLog = new ArrayList<>();
    private boolean reachedSecondLadder = false;



    public List<String> getLog() {
        return new ArrayList(this.log);
    }

    public void clearLog() {
        this.log.clear();
    }

    public void log(String s) {
        if (!s.isEmpty()) {
            this.log.add(s);
        }
    }

    public GameEngine(int difficulty) {
        this.difficulty = difficulty;
        this.map = MapGenerator.generateSpecificLevel(difficulty, this.currentLevel);
        this.size = this.map.length;
        this.player = new Player();

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                if (this.map[i][j].isEntry()) {
                    this.player.moveTo(i, j);
                    return;
                }
            }
        }

    }

    public int getSize() {
        return this.size;
    }

    public Cell[][] getMap() {
        return this.map;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void printMap() {
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                if (this.player.getX() == i && this.player.getY() == j) {
                    System.out.print("P ");
                } else if (this.map[i][j].isWall()) {
                    System.out.print("# ");
                } else if (this.map[i][j].isEntry()) {
                    System.out.print("E ");
                } else {
                    Item item = this.map[i][j].getItem();
                    String var10001 = item != null ? item.getSymbol() : ".";
                    System.out.print(var10001 + " ");
                }
            }

            System.out.println();
        }

        System.out.printf("HP: %d | Score: %d | Steps: %d\n\n", this.player.getHP(), this.player.getScore(), this.player.getSteps());
    }

    public void movePlayer(String dir) {
        int x = this.player.getX();
        int y = this.player.getY();
        int newX = x;
        int newY = y;
        switch (dir) {
            case "u":
                newX = x - 1;
                break;
            case "d":
                newX = x + 1;
                break;
            case "l":
                newY = y - 1;
                break;
            case "r":
                newY = y + 1;
                break;
            default:
                log("Invalid command.");
                return;
        }

        if (newX >= 0 && newY >= 0 && newX < this.size && newY < this.size && !this.map[newX][newY].isWall()) {
            this.player.moveTo(newX, newY);

            log(this.map[newX][newY].enter(this.player));


            if (this.map[newX][newY].getItem() != null && this.map[newX][newY].getItem().getSymbol().equals("L")) {
                ++this.currentLevel;
                if (this.currentLevel < 3) {
                    this.map = MapGenerator.generateSpecificLevel(this.difficulty, this.currentLevel);
                } else {
                    reachedSecondLadder = true;
                }

            }
            triggerRangedMutantActions();

        } else {
            log("You hit a wall.");
        }
    }

    public void endGame(boolean win) {
        String result = win ? "Game Won!" : "Game Over! Please try again!";
        log(result);
        saveScore();


    }

    private void triggerRangedMutantActions() {
        for (int i = 0; i < this.map.length; ++i) {
            for (int j = 0; j < this.map[i].length; ++j) {
                Item item = this.map[i][j].getItem();
                if (item != null && item.getSymbol().equals("R")) {
                    log(((RangedMutant) item).act(this.map, this.player, i, j));
                }
            }
        }

    }

    public void saveScore() {
        List<String> scores = new ArrayList();
        String now = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        int var10000 = this.player.getScore();
        String entry = var10000 + ", " + now;

        try {
            File file = new File("top_scores.txt");
            if (file.exists()) {
                String line;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
        List<String> scores = new ArrayList();
        int i = 1;
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("top_scores.txt"))) {
            while ((line = reader.readLine()) != null) {
                scores.add("#" + i + "  " + line);
                i++;
            }
        } catch (IOException var7) {
            System.out.println("No scores found.");
        }

        return scores;
    }
    public boolean isGameWon() {
        return reachedSecondLadder;
    }

    public String checkGameStatus() {
        if (isGameWon()) {
            log("You reached the ladder! You win!");
            return "WIN";
        }
        if (player.getHP() <= 0) {
            log("You died! Game over.");
            return "LOSE";
        }
        if (player.getSteps() >= 100) {
            log("You ran out of steps! Game over.");
            return "LOSE";
        }
        return "CONTINUE";
    }

    // Save the current game state
    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            log("Game saved successfully.");
        } catch (IOException e) {
            log("Failed to save game: " + e.getMessage());
        }
    }

    // Load the game state
    public static GameEngine loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            GameEngine engine = (GameEngine) in.readObject();
            engine.log("Game loaded successfully.");
            return engine;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter difficulty (0-10, default 3): ");
        String input = sc.nextLine();
        int difficulty = 3;

        try {
            difficulty = Math.max(0, Math.min(10, Integer.parseInt(input)));
        } catch (NumberFormatException var6) {
        }

        GameEngine engine = new GameEngine(difficulty);
        System.out.println("Welcome to MiniDungeon Fixed Map Mode!");
        engine.printMap();

        while (engine.player.getHP() > 0) {
            int var10000 = engine.player.getSteps();
            Objects.requireNonNull(engine);
            if (var10000 >= 100 || engine.currentLevel >= 3) {
                break;
            }

            System.out.print("Move (u/d/l/r): ");
            String cmd = sc.nextLine();
            engine.movePlayer(cmd);
            for (String message : engine.getLog()) {
                System.out.println(message);
            }
            engine.clearLog();
            engine.printMap();
        }
        if (engine.player.getHP() <= 0) {
            engine.log("You died. Game over.");
            engine.player.addScore(-1);
        } else {
            int var8 = engine.player.getSteps();
            Objects.requireNonNull(engine);
            if (var8 >= 100) {
                engine.player.addScore(-1);
                engine.log("You ran out of steps. Game over.");
            } else {
                engine.log("WINNER WINNER CHICKEN DINNER!");
                engine.log("Thanks for playing!");
            }
        }

        engine.saveScore();

        for (int i = 0; i < engine.getTopScores().size(); ++i) {
            PrintStream var9 = System.out;
            String var10001 = Integer.toString(i + 1);
            var9.println("#" + var10001 + " " + (String) engine.getTopScores().get(i));
        }

    }
}
