import PlayersEnemies.*;
import Misc.TablesQuest;
import org.junit.Test;
import static org.junit.Assert.*;

public class NpcTest {

    @Test
    public void testNpcCreation() {
        Npc npc = new Npc();
        assertNotNull(npc.getName());
        assertTrue(npc.getName().split(" ").length >= 3); // Имя + Фамилия + Титул
        assertNull(npc.getCurrentQuest());
    }

    @Test
    public void testQuestAssignment() {
        Npc npc = new Npc();
        TablesQuest quest = new TablesQuest("Test", "Desc", 10, 1000L, new String[]{});

        npc.setCurrentQuest(quest);
        assertEquals(quest, npc.getCurrentQuest());

        npc.setCurrentQuest(null);
        assertNull(npc.getCurrentQuest());
    }

    @Test
    public void testToString() {
        Npc npc = new Npc();
        String baseString = npc.toString();

        TablesQuest quest = new TablesQuest("Test Quest", "", 0, 0L, new String[]{});
        npc.setCurrentQuest(quest);

        assertTrue(npc.toString().contains("Test Quest"));
        assertTrue(npc.toString().length() > baseString.length());
    }
}