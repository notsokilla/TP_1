
import Misc.SettlementMap;
import PlayersEnemies.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SettlementMapTest {
    private SettlementMap settlementMap;
    private Player testPlayer;

    @Before
    public void setUp() {
        settlementMap = new SettlementMap();
        testPlayer = new TestPlayer();
    }

    @Test
    public void testInitialization() {
        assertEquals('С', settlementMap.getCell(2, 2));
        assertEquals('К', settlementMap.getCell(4, 4));
        assertEquals('Т', settlementMap.getCell(6, 6));
    }

    @Test
    public void testSetAndGetCell() {
        settlementMap.setCell(3, 3, 'X');
        assertEquals('X', settlementMap.getCell(3, 3));
    }

    @Test
    public void testInvalidCoordinates() {
        assertEquals(' ', settlementMap.getCell(-1, -1));
        assertEquals(' ', settlementMap.getCell(100, 100));
    }

    @Test
    public void testOriginalCellPreservation() {
        settlementMap.setCell(2, 2, 'X');
        assertEquals('С', settlementMap.getOriginalCell(2, 2));
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