package dungeon.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    @Test
    public void testGetSymbolIsNotNull() {
        Enemy enemy = new MeleeMutant();
        assertNotNull(enemy.getSymbol());
    }
}