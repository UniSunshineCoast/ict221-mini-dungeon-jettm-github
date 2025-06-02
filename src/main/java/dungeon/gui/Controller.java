package dungeon.gui;

import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import dungeon.engine.Player;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Controller {
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField difficultyInput;
    @FXML
    private TextArea logArea;
    @FXML
    private ListView<String> scoreList;

    @FXML
    private void saveGame() {
        if (engine != null) {
            engine.saveGame("savegame.dat");
            updateLog();
        }
    }

    @FXML
    private void loadGame() {
        GameEngine loaded = GameEngine.loadGame("savegame.dat");
        if (loaded != null) {
            this.engine = loaded;
            renderMap();
            updateLog();
        }
    }



    private GameEngine engine;

    public Controller() {
    }

    public void initialize() {
        this.log("Welcome to Mini Dungeon! Enter difficulty and press Start Game.");
    }

    public void startGame() {
        int difficulty = 3;

        try {
            difficulty = Math.max(0, Math.min(10, Integer.parseInt(this.difficultyInput.getText())));
        } catch (NumberFormatException var3) {
            this.log("Invalid difficulty, using default (3).\n");
        }

        this.engine = new GameEngine(difficulty);
        this.renderMap();
        this.updateStatus();
        this.updateScores();
        this.log("Game started at difficulty " + difficulty);
    }

    public void moveUp() {
        this.movePlayer("u");
    }

    public void moveDown() {
        this.movePlayer("d");
    }

    public void moveLeft() {
        this.movePlayer("l");
    }

    public void moveRight() {
        this.movePlayer("r");
    }

    private void movePlayer(String direction) {
        String isWon = engine.checkGameStatus();
        if (isWon.equals("CONTINUE")) {
            this.engine.movePlayer(direction);
            this.renderMap();
            this.updateStatus();
            this.updateLog();

        } else {
            return;
        }
        boolean isOverAfterMove = !engine.checkGameStatus().equals("CONTINUE");
        if (isOverAfterMove) {
            this.engine.endGame(this.engine.isGameWon());
            this.updateLog();
            this.updateScores();

        }
//        String isWon = engine.checkGameStatus();
//        if (!isWon.equals("CONTINUE")) {
//            this.engine.endGame(isWon.equals("WIN"));
//            return;
//        }




    }

    private Image getImage(String name) {
        return new Image((InputStream)Objects.requireNonNull(this.getClass().getResourceAsStream("/images/" + name)));
    }

    private void renderMap() {
        this.mapGrid.getChildren().clear();
        Cell[][] map = this.engine.getMap();
        Player player = this.engine.getPlayer();

        for(int i = 0; i < map.length; ++i) {
            for(int j = 0; j < map[i].length; ++j) {
                StackPane cellStack = new StackPane();
                cellStack.setPrefSize((double)50.0F, (double)50.0F);
                String baseImage = "Plain Floor.png";
                if (map[i][j].isWall()) {
                    baseImage = "WALL.png";
                } else if (map[i][j].isEntry()) {
                    baseImage = "Entry.jpg";
                }

                ImageView baseView = new ImageView(this.getImage(baseImage));
                baseView.setFitWidth((double)50.0F);
                baseView.setFitHeight((double)50.0F);
                cellStack.getChildren().add(baseView);
                if (map[i][j].getItem() != null) {
                    String itemImage = null;
                    switch (map[i][j].getItem().getSymbol()) {
                        case "G" -> itemImage = "Gold.png";
                        case "H" -> itemImage = "Health.png";
                        case "L" -> itemImage = "Ladder.png";
                        case "M" -> itemImage = "Melee.png";
                        case "R" -> itemImage = "Ranged.png";
                        case "T" -> itemImage = "Trap.jpg";
                    }

                    if (itemImage != null) {
                        ImageView itemView = new ImageView(this.getImage(itemImage));
                        itemView.setFitWidth((double)50.0F);
                        itemView.setFitHeight((double)50.0F);
                        cellStack.getChildren().add(itemView);
                    }
                }

                if (i == player.getX() && j == player.getY()) {
                    ImageView playerView = new ImageView(this.getImage("Player.png"));
                    playerView.setFitWidth((double)50.0F);
                    playerView.setFitHeight((double)50.0F);
                    cellStack.getChildren().add(playerView);
                }

                this.mapGrid.add(cellStack, j, i);
            }
        }

        this.mapGrid.autosize();
        this.mapGrid.requestLayout();
    }

    private void updateStatus() {
        Player p = this.engine.getPlayer();
        Label var10000 = this.statusLabel;
        int var10001 = p.getHP();
        var10000.setText("HP: " + var10001 + " | Score: " + p.getScore() + " | Steps: " + p.getSteps());
    }

    private void updateLog() {
        List<String> messages = engine.getLog();
        for (String msg : messages) {
            log(msg);
        }
        engine.clearLog();
    }


    private void updateScores() {
        this.scoreList.getItems().setAll(this.engine.getTopScores());
    }

    private void log(String message) {
        this.logArea.appendText(message + "\n");


    }
}
