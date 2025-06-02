package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testMoveToUpdatesCoordinates() {
        Player player = new Player();
        player.moveTo(3, 4);
        assertEquals(3, player.getX());
        assertEquals(4, player.getY());
    }

    @Test
    public void testAddScoreIncreasesCorrectly() {
        Player player = new Player();
        player.addScore(10);
        assertEquals(10, player.getScore());
        player.addScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    public void testAddHPIncreasesCorrectly() {
        Player player = new Player();
        int originalHP = player.getHP();
        player.addHP(-5);
        player.addHP(5);
        assertEquals(originalHP, player.getHP());
    }

    @Test
    public void testStepIncrementsSteps() {
        Player player = new Player();
        int steps = player.getSteps();
        player.moveTo(3, 4);
        assertEquals(steps + 1, player.getSteps());
    }

    @Test
    public void testAddNegativeScore() {
        Player player = new Player();
        player.addScore(-5);
        assertEquals(-5, player.getScore());
    }

    @Test
    public void testAddNegativeHP() {
        Player player = new Player();
        int originalHP = player.getHP();
        player.addHP(-10);
        assertEquals(originalHP - 10, player.getHP());
    }
}