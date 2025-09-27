
import Misc.BattleManager;
import PlayersEnemies.Player;
import PlayersEnemies.Enemy;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattleManagerTest {
    @Test
    public void testDamageCalculation() {
        Player player = new TestPlayer();
        Enemy enemy = new Enemy(0, 0, 100, 10, 5, 5, 2, 2, "Тестовый враг") {
            @Override
            public String Avatar() {
                return "";
            }

            @Override
            public String StartAvatar() {
                return "";
            }
        };

        int damage = BattleManager.calculateDamage(player.getDamage(), enemy.getArmor());

        assertTrue("Урон должен быть положительным", damage > 0);
        assertTrue("Урон должен быть меньше базового", damage < player.getDamage());
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