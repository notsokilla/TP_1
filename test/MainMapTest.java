
import Misc.MainMap;
import PlayersEnemies.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainMapTest {
    @Test
    public void testPlayerPlacement() {
        MainMap mainMap = new MainMap();
        Player player = new TestPlayer();

        mainMap.placePlayer(3, 3, player);

        assertEquals('P', mainMap.getCell(3, 3));
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