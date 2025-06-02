package dungeon.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    private GameEngine engine;

    @BeforeEach
    public void setup() {
        engine = new GameEngine(3); // default difficulty
    }

    @Test
    public void testPlayerSpawnsAtEntry() {
        Player player = engine.getPlayer();
        assertNotNull(player);
        assertEquals(9, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void testMoveIntoWallBlocked() {
        engine.getPlayer().moveTo(0, 9); // position next to wall
        engine.movePlayer("u");
        assertEquals(9, engine.getPlayer().getY()); // should not move
    }

    @Test
    public void testMoveValidDirection() {
        engine.getPlayer().moveTo(0, 0);
        engine.movePlayer("d");
        assertEquals(1, engine.getPlayer().getX());
    }

    @Test
    public void testWinGameStatus() {
        engine.getPlayer().moveTo(9, 9); // ladder at this pos
        engine.movePlayer("l");
        engine.getPlayer().moveTo(0, 7); // simulate again
        engine.movePlayer("d");
        assertTrue(engine.isGameWon());
        assertEquals("WIN", engine.checkGameStatus());
    }

    @Test
    public void testLoseByHealth() {
        engine.getPlayer().addHP(-engine.getPlayer().getHP());
        assertEquals("LOSE", engine.checkGameStatus());
    }

    @Test
    public void testLoseBySteps() {
        for (int i = 0; i < 100; i++) {
            if (i%2 == 0) {
                engine.getPlayer().moveTo(0, 1);
            } else {
                engine.getPlayer().moveTo(0, 0);
            }


        }
        assertEquals("LOSE", engine.checkGameStatus());
    }

    @Test
    public void testSaveAndLoadGame() {
        engine.getPlayer().addScore(123);
        engine.saveGame("test_save.dat");

        GameEngine loaded = GameEngine.loadGame("test_save.dat");
        assertNotNull(loaded);
        assertEquals(123, loaded.getPlayer().getScore());

        File file = new File("test_save.dat");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testTopScoresFileWriteAndRead() {
        engine.getPlayer().addScore(999);
        engine.saveScore();

        List<String> topScores = engine.getTopScores();
        assertNotNull(topScores);
        assertFalse(topScores.isEmpty());
    }

    @Test
    public void testSysLogBehavior() {
        engine.getLog().clear();
        engine.movePlayer("d");
        assertFalse(engine.getLog().isEmpty());

        engine.clearLog();
        assertTrue(engine.getLog().isEmpty());
    }

    @Test
    public void testRangedMutantsActMethod() {
        assertDoesNotThrow(() -> {
            engine.getPlayer().moveTo(1, 1);
            engine.movePlayer("d");
        });
    }
}
