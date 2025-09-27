
import PlayersEnemies.Enemy;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnemyTest {
    @Test
    public void testEnemyTakeDamage() {
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

        int initialHealth = enemy.getHealth();

        enemy.takeDamage(10);

        assertEquals(initialHealth - 10, enemy.getHealth());
    }
}