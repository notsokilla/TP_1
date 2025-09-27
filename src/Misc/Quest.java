package Misc;
import java.util.logging.Logger;
import java.io.Serializable;

public class Quest implements Serializable {
    private static final long serialVersionUID = 2L;
    private String title;
    private String description;
    private static boolean completed;
    private static final Logger logger = Logger.getLogger(Quest.class.getName());


    public Quest(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        logger.info("Новый квест создан: " + title);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        Quest.completed = completed;
        logger.warning("Квест выполнен: " + title);
    }

    public Quest deepCopy() {
        Quest copy = new Quest(this.title, this.description);
        copy.setCompleted(this.completed);
        return copy;
    }

    @Override
    public String toString() {
        return title + ": " + description + " [" + (completed ? "Выполнено" : "Не выполнено") + "]";
    }
}