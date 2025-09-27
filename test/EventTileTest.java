
import Events.EventTile;
import Events.HealthBonusEvent;
import PlayersEnemies.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTileTest {
    @Test
    public void testEventTrigger() {
        HealthBonusEvent eventBehavior = new HealthBonusEvent();
        EventTile eventTile = new EventTile("Тест", eventBehavior, '?');
        Player player = new TestPlayer();

        eventTile.triggerEvent(player);

        assertNotNull("Должен добавить предмет", player.getInventory()[0]);
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