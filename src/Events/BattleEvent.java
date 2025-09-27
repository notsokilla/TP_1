package Events;

import PlayersEnemies.*;
import Misc.*;

import java.io.Serializable;
import java.util.Scanner;

public class BattleEvent implements EventBehavior, Serializable {
    private static final long serialVersionUID = 1L;
    private final BattleManager battleManager;
    protected Enemy enemy;
    private Scanner scanner; // Добавляем поле
    private BattleMap battleMap;

    public BattleEvent(BattleManager battleManager, Scanner scanner, BattleMap battleMap) {
        this.battleManager = battleManager;
        this.scanner = scanner; // Инициализируем через конструктор
        this.battleMap = battleMap;
    }

    @Override
    public void trigger(Player player) {
        // Сбрасываем карту перед каждым боем
        battleMap.initializeBoard();

        if (enemy == null || !enemy.isAlive()) {
            enemy = GameInitializer.createEnemy(); // Создаем нового врага если предыдущий мертв
        }

        int originalX = player.getX();
        int originalY = player.getY();

        // Размещаем персонажей на существующей карте
        battleMap.placePlayerAndParty(player);
        battleMap.placeEnemy(enemy);

        // Запускаем бой
        battleManager.startBattle(player, enemy, battleMap, scanner);

        // Восстанавливаем позицию игрока
        player.setPosition(originalX, originalY);

        // Очищаем карту от персонажей после боя
        battleMap.clearEnemy();
        battleMap.initializeBoard();
    }
}