import Misc.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class BoardQuestTest {

    @Test
    public void testGenerateRandomQuest() {
        TablesQuest quest = BoardQuest.generateRandomQuest();

        // Validate quest properties
        assertNotNull(quest.getTitle());
        assertNotNull(quest.getDescription());
        assertTrue(quest.getReward() >= 10 && quest.getReward() <= 25);
        assertTrue(quest.getDurationMs() >= 30000 && quest.getDurationMs() <= 90000);

        // Validate context messages
        String message = quest.getRandomProgressMessage();
        assertNotNull(message);
        assertFalse(message.isEmpty());
    }

}