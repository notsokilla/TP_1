package Misc;


import PlayersEnemies.Player;

import java.util.Random;
import java.util.logging.Logger;

public class TablesQuest {
    private String title;
    private String description;
    private Integer reward;
    private long durationMs;
    private static boolean completed;
    private static final Logger logger = Logger.getLogger(Quest.class.getName());
    private final String[] progressMessages;
    private static final Random rand = new Random();



    public TablesQuest(String title, String description, int reward, long durationMs, String[] progressMessages) {
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.durationMs = durationMs;
        this.completed = false;
        logger.info("Новый квест создан: " + title);
        this.progressMessages = progressMessages;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void complete(Player player) {
        player.setCashReward(reward);
        // Дополнительные эффекты
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
        TablesQuest.completed = completed;
        logger.warning("Квест выполнен: " + title);
    }

    public String getRandomProgressMessage() {
        return progressMessages[new Random().nextInt(progressMessages.length)];
    }

    public Integer getReward() {
        return reward;
    }

}
