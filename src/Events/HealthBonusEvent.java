
package Events;

import PlayersEnemies.Player;
import PlayersEnemies.Item;
import PlayersEnemies.ItemType;

import java.io.Serializable;

public class HealthBonusEvent implements EventBehavior, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void trigger(Player player) {
        System.out.println("Вы нашли перевернутую телегу, умудрившись поставить её на колеса, вы увидели под ней зелье исцеления!");


        // Пример добавления предмета в инвентарь
        Item hppotion = new Item("Зелье исцеления", ItemType.CONSUMABLE, 50) {
            @Override
            public void applyEffect(Player player) {
                player.setHealth(Math.min(player.getHealth() + 25, player.getMaxHealth()));
            }

            @Override
            public void removeEffect(Player player) {}
        };

        boolean success = player.addItemToInventory(hppotion);
        if (!success) {
            System.out.println("Сумка переполнена!");
        }
    }
}