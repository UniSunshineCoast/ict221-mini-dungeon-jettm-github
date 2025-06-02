package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoldTest {

    @Test
    public void testOnEnterIncreasesScore() {
        Gold gold = new Gold();
        Player player = new Player();
        Cell cell = new Cell();
        int initialScore = player.getScore();
        gold.onEnter(player, cell);
        assertTrue(player.getScore() > initialScore);
    }
}
