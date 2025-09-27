import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderboardTest {
    private static final String TEST_LEADERBOARD_FILE = "test_leaderboard.dat";
    private PolyanaMain.LeaderboardEntry entry1, entry2, entry3;

    @Before
    public void setUp() {
        // Создаем тестовые записи
        entry1 = new PolyanaMain.LeaderboardEntry("Player1", 65000); // 1:05
        entry2 = new PolyanaMain.LeaderboardEntry("Player2", 120000); // 2:00
        entry3 = new PolyanaMain.LeaderboardEntry("Player3", 45000); // 0:45

        // Удаляем тестовый файл, если он существует
        try {
            Files.deleteIfExists(Paths.get(TEST_LEADERBOARD_FILE));
        } catch (IOException e) {
            fail("Не удалось удалить тестовый файл лидерборда");
        }
    }

    @Test
    public void testAddToLeaderboard() {
        // Добавляем записи в лидерборд
        PolyanaMain.addToLeaderboard(TEST_LEADERBOARD_FILE, entry1.getName(), entry1.getTime());
        PolyanaMain.addToLeaderboard(TEST_LEADERBOARD_FILE, entry2.getName(), entry2.getTime());
        PolyanaMain.addToLeaderboard(TEST_LEADERBOARD_FILE, entry3.getName(), entry3.getTime());

        // Проверяем, что файл создан
        assertTrue(Files.exists(Paths.get(TEST_LEADERBOARD_FILE)));

        // Проверяем содержимое файла
        try {
            List<PolyanaMain.LeaderboardEntry> entries = PolyanaMain.readLeaderboard(TEST_LEADERBOARD_FILE);
            assertEquals(3, entries.size());

            // Проверяем сортировку (должна быть по возрастанию времени)
            assertEquals("Player3", entries.get(0).getName()); // 0:45 - первое место
            assertEquals("Player1", entries.get(1).getName()); // 1:05 - второе место
            assertEquals("Player2", entries.get(2).getName()); // 2:00 - третье место
        } catch (IOException e) {
            fail("Ошибка чтения тестового лидерборда: " + e.getMessage());
        }
    }

    @Test
    public void testFormatTime() {
        assertEquals("00:45", PolyanaMain.formatTime(45000));
        assertEquals("01:05", PolyanaMain.formatTime(65000));
        assertEquals("02:00", PolyanaMain.formatTime(120000));
        assertEquals("10:30", PolyanaMain.formatTime(630000));
    }

    @Test
    public void testEmptyLeaderboard() {
        try {
            List<PolyanaMain.LeaderboardEntry> entries = PolyanaMain.readLeaderboard(TEST_LEADERBOARD_FILE);
            assertTrue(entries.isEmpty());
        } catch (IOException e) {
            fail("Ошибка чтения пустого лидерборда: " + e.getMessage());
        }
    }

    @Test
    public void testLeaderboardWithEmptyName() {
        PolyanaMain.addToLeaderboard(TEST_LEADERBOARD_FILE, "", 30000);

        try {
            List<PolyanaMain.LeaderboardEntry> entries = PolyanaMain.readLeaderboard(TEST_LEADERBOARD_FILE);
            assertEquals(1, entries.size());
            assertFalse(entries.get(0).getName().isEmpty()); // Должно быть заменено на "Неизвестный"
        } catch (IOException e) {
            fail("Ошибка чтения лидерборда с пустым именем: " + e.getMessage());
        }
    }

    @Test
    public void testLeaderboardEntrySerialization() {
        PolyanaMain.LeaderboardEntry original = new PolyanaMain.LeaderboardEntry("TestPlayer", 50000);

        // Проверяем геттеры
        assertEquals("TestPlayer", original.getName());
        assertEquals(50000, original.getTime());
    }
}