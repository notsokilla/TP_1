package Misc;

import Events.EventBehavior;
import Events.EventTile;
import Events.QuestEvent;
import PlayersEnemies.Maradeur;
import PlayersEnemies.Player;
import java.util.Scanner;
import java.util.logging.Logger;

import static Misc.MainMap.*;

public class QuestHandler {
    private final MainMap mainMap;
    private static int questX = -1;
    private static int questY = -1;
    private static boolean questActive = false;
    private static final Logger logger = Logger.getLogger(QuestHandler.class.getName());

    public QuestHandler(MainMap mainMap) {
        this.mainMap = mainMap;
    }

    public void setQuestMarker(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            questX = x;
            questY = y;
            questActive = true;
            mainMap.setCell(x, y, 'Q');
            logger.info("Маркер на квест установлен: (" + x + ", " + y + ")");

        }
    }

    public void removeQuestMarker() {
        if (questX != -1 && questY != -1) {
            mainMap.setCell(questX, questY, 'O');
            questX = -1;
            questY = -1;
            questActive = false;
            logger.info("Маркер на квест удален: (" + questX + ", " + questY + ")");
        }
    }

    public boolean isQuestActive() {
        return questActive;
    }

    public static boolean isPlayerOnQuest(int playerX, int playerY) {
        return questActive && playerX == questX && playerY == questY;
    }

public void StartQuestBattle(Player player,Scanner scanner) {
    BattleManager battleManager = new BattleManager();
    logger.info("Битва начинается для игрока: " + player.getPlayerName());

    Maradeur questEnemy = new Maradeur(14, 14, 1, 1, 1, 1, 1, 1);
    QuestEvent questEvent = new QuestEvent(mainMap, questEnemy, battleManager, scanner);
    questEvent.trigger(player);
    }
}
