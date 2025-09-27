import Misc.BattleMap;
import PlayersEnemies.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattleMapTest {
    @Test
    public void testInitialization() {
        BattleMap battleMap = new BattleMap();
        Player player = new TestPlayer();

        battleMap.placePlayerAndParty(player);

        assertEquals('P', battleMap.getCell(0, 0));
    }

    private static class TestPlayer extends Player {
        public TestPlayer() {
            super("Тестовый игрок");
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


