
import Misc.Settlement;
import PlayersEnemies.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class SettlementTest {
    private Settlement settlement;
    private Player testPlayer;

    @Before
    public void setUp() {
        settlement = new Settlement();
        testPlayer = new TestPlayer();
    }

    @Test
    public void testElderHouseQuestAssignment() {
        String input = "2\n"; // Выбор "Я могу вам чем-нибудь помочь?"
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //settlement.handleElderHouse(testPlayer);
        assertFalse(testPlayer.getQuest("Мерзкий Поганец") != null);
    }

    @Test
    public void testTavernRecruitHiring() {
        String input = "1\n1\n"; // Подход к путнику и наем
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        settlement.handleTavern(testPlayer);
        assertFalse(testPlayer.getPartyRecruits().isEmpty());
    }

    @Test
    public void testTavernItemAcquisition() {
        String input = "2\n1\n"; // Подход к корчмарю и взятие зелья
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        settlement.handleTavern(testPlayer);
        assertNotNull(testPlayer.getInventory()[0]);
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