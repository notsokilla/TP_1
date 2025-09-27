import Misc.*;
import PlayersEnemies.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeCasesTest {
    @Test
    public void testPlayerDeath() {
        Player player = new TestPlayer();
        player.takeDamage(player.getHealth() + 10);

        assertFalse("Игрок должен быть мертв", player.isAlive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPosition() {
        MainMap mainMap = new MainMap();
        mainMap.getCell(-1, -1); // Должно бросить исключение
    }

    private static class TestPlayer extends Player {
        public TestPlayer() {
            super( "Тестовый игрок");
        }

        @Override
        public String StartAvatar() {
            return "";
        }

        @Override
        public void someAbstractMethod() {

        }
    }
}