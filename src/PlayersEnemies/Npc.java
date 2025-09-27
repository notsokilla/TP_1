// Npc.java
package PlayersEnemies;
import Misc.*;
import java.io.Serializable;
import java.util.Random;

public class Npc implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String[] FIRST_NAMES = {"Алексей", "Максим", "Азазин", "Хитрый", "Большой", "Дмитрий", "Таинственный", "Веселый"};
    private static final String[] LAST_NAMES = {"Марцинкевич", "Навальный", "Крит", "Антон", "Шуршиков", "Мамонов", "незнакомец", "бард", "купец", "рыбак"};
    private static final String[] TITLES = {"из Долины Теней", "из Леса Эльфов", "из Горного Царства", "из 'ООО Вывод Крипов в Фиат'", "из Москвы"};

    private final String name;
    private TablesQuest currentQuest;

    public Npc() {
        this.name = generateName();
    }

    private String generateName() {
        Random rand = new Random();
        String firstName = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
        String title = TITLES[rand.nextInt(TITLES.length)];
        return firstName + " " + lastName + " " + title;
    }

    public String getName() {
        return name;
    }

    public void setCurrentQuest(TablesQuest quest) {
        this.currentQuest = quest;
    }

    public TablesQuest getCurrentQuest() {
        return currentQuest;
    }

    @Override
    public String toString() {
        return name + (currentQuest != null ?
                " (выполняет: " + currentQuest.getTitle() + ")" : "");
    }
}