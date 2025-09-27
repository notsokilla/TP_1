import Misc.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TablesQuestTest {
    private TablesQuest quest;
    private final String[] messages = {"Message 1", "Message 2"};

    @Before
    public void setUp() {
        quest = new TablesQuest("Test Quest", "Test Description", 100, 5000L, messages);
        quest.setCompleted(false); // Исправлено: нестатический вызов
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Test Quest", quest.getTitle());
        assertEquals("Test Description", quest.getDescription());
        assertEquals(100, quest.getReward().intValue()); // Исправлено: добавлен геттер
        assertEquals(5000L, quest.getDurationMs());
        assertFalse(quest.isCompleted());
    }

    @Test
    public void testSetCompleted() {
        quest.setCompleted(true);
        assertTrue(quest.isCompleted());
    }

    @Test
    public void testGetRandomProgressMessage() {
        String message = quest.getRandomProgressMessage();
        assertTrue(message.equals("Message 1") || message.equals("Message 2"));
    }
}