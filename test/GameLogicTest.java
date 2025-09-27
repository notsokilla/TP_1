
import Misc.GameLogic;
import Misc.MainMap;
import Misc.BattleMap;
import Misc.QuestHandler;
import PlayersEnemies.Player;
import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.*;

public class GameLogicTest {
    @Test
    public void testPlayerMovementOnMainMap() {
        Player player = new TestPlayer();
        MainMap mainMap = new MainMap();
        GameLogic gameLogic = new GameLogic(player, mainMap, new BattleMap(), new Scanner(System.in));

        boolean moved = gameLogic.movePlayerOnMainMap('D');

        assertTrue("Игрок должен переместиться", moved);
        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void testEnterSettlement() {
        Player player = new TestPlayer();
        MainMap mainMap = new MainMap();
        mainMap.setCell(0, 1, 'Д');
        GameLogic gameLogic = new GameLogic(player, mainMap, new BattleMap(), new Scanner(System.in));


        gameLogic.movePlayerOnMainMap('D');
        int newX = player.getX();
        int newY = player.getY();
        if (mainMap.getCell(newX, newY) == 'Д' && !gameLogic.isInSettlement()) {

            gameLogic.enterSettlement();
        }

        assertTrue(gameLogic.isInSettlement());
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