
package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthPotionTest {

    @Test
    void testOnEnterIncreasesHP() {
        Player player = new Player();
        player.addHP(-5); // simulate damage
        int currentHP = player.getHP();
        HealthPotion potion = new HealthPotion();
        potion.onEnter(player, new Cell());
        assertTrue(player.getHP() > currentHP);
    }
}
