// GameState.java
package Misc;

import PlayersEnemies.*;
import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Player player;
    private MainMap mainMap;
    private Settlement settlement;
    private boolean isInSettlement;
    private int playerX;
    private int playerY;
    private Map<String, Boolean> questStatuses;

    public GameState(Player player, MainMap mainMap, Settlement settlement,
                     boolean isInSettlement, int playerX, int playerY,
                     Map<String, Boolean> questStatuses) {
        this.player = player;
        this.mainMap = mainMap;
        this.settlement = settlement;
        this.isInSettlement = isInSettlement;
        this.playerX = playerX;
        this.playerY = playerY;
        this.questStatuses = questStatuses;
    }

    // Геттеры
    public Player getPlayer() { return player; }
    public MainMap getMainMap() { return mainMap; }
    public Settlement getSettlement() { return settlement; }
    public boolean isInSettlement() { return isInSettlement; }
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public Map<String, Boolean> getQuestStatuses() { return questStatuses; }
}