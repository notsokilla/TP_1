import Misc.*;

import PlayersEnemies.Npc;
import PlayersEnemies.Player;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class QuestBoardTest {
    private QuestBoard board;
    private Player player;
    private final int MAX_ACTIVE = 1; // Значение по умолчанию

    // Вспомогательный класс для тестирования
    static class TestPlayer extends Player {
        public TestPlayer(String name) {
            super(name);
        }

        @Override
        public String StartAvatar() {
            return "";
        }

        @Override
        public void someAbstractMethod() {

        }
    }

    @Before
    public void setUp() {
        board = new QuestBoard();
        player = new TestPlayer("Test Player");
    }

    @Test
    public void testInitialState() {
        assertTrue(board.tryStartQuest(player, new TablesQuest("Test", "Desc", 10, 1000L, new String[]{})));
        String status = board.getStatusInfo();
        assertTrue(status.contains("Очередь: 1 заданий в ожидании"));
    }

    @Test
    public void testQuestCompletion() throws InterruptedException {
        TablesQuest quest = new TablesQuest("Short Quest", "", 10, 100L, new String[]{});
        board.tryStartQuest(player, quest);

        TimeUnit.MILLISECONDS.sleep(500);

        String status = board.getStatusInfo();
    }

    @Test
    public void testActiveQuestLimit() {
        for (int i = 0; i < MAX_ACTIVE + 2; i++) {
            Player p = new TestPlayer("Player " + i);
            board.tryStartQuest(p, new TablesQuest("Q" + i, "", 10, 10000L, new String[]{}));
        }

        String status = board.getStatusInfo();
        assertTrue(status.contains("Активные квесты (" + MAX_ACTIVE + "/"));
        assertTrue(status.contains("Очередь: " + (MAX_ACTIVE + 2) + " заданий"));
    }
}