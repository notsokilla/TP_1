

import PlayersEnemies.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testInventoryFull() {
        Player player = new Player("Бедни Тестировщик") {
            @Override
            public String StartAvatar() {
                return "";
            }

            @Override
            public void someAbstractMethod() {}
        };
        Item item = new Item("Potion", ItemType.CONSUMABLE, 1) {
            @Override
            public void applyEffect(Player player) {

            }

            @Override
            public void removeEffect(Player player) {

            }
        };
        for (int i = 0; i < 4; i++) {
            player.addItemToInventory(item);
        }
        assertFalse(player.addItemToInventory(item));
    }
}