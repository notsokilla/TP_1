
import Events.BattleEvent;
import Misc.BattleManager;
import Misc.BattleMap;
import Misc.GameLogic;
import Misc.MainMap;
import PlayersEnemies.Enemy;
import PlayersEnemies.Player;
import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.*;

public class BattleEventTest {
    @Test
    public void testBattleEventCreation() {
        BattleManager battleManager = new BattleManager();
        Scanner scanner = new Scanner(System.in);
        BattleMap battleMap = new BattleMap();
        BattleEvent event = new BattleEvent(battleManager, scanner, battleMap);

        assertNotNull("BattleEvent должен создаваться", event);
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