package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealthPotionTest {

    @Test
    public void testOnEnterIncreasesHP() {
        HealthPotion potion = new HealthPotion();
        Player player = new Player();
        Cell cell = new Cell();
        int originalHP = player.getHP();
        potion.onEnter(player, cell);
        assertTrue(player.getHP() == originalHP);
    }
}