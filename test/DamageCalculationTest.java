
import Misc.BattleManager;
import PlayersEnemies.Enemy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DamageCalculationTest {
    private int damage;
    private int armor;
    private int expected;

    public DamageCalculationTest(int damage, int armor, int expected) {
        this.damage = damage;
        this.armor = armor;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {10, 0, 10},
                {100, 100, 50}
        });
    }

    @Test
    public void testDamageCalculation() {
        assertEquals(expected,
                BattleManager.calculateDamage(damage, armor));
    }
}