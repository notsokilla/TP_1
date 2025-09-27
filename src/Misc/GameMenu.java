// GameMenu.java
package Misc;

import PlayersEnemies.*;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class GameMenu {
    private Player player;
    private Settlement settlement;
    private MainMap mainMap;
    private Scanner scanner;
    private GameLogic gameLogic;
    private int playerX;
    private int playerY;
    private Map<String, Boolean> questStatuses;

    public GameMenu(Player player, Settlement settlement, MainMap mainMap,
                    GameLogic gameLogic, int x, int y,
                    Map<String, Boolean> questStatuses, Scanner scanner) {
        this.player = player;
        this.settlement = settlement;
        this.mainMap = mainMap;
        this.gameLogic = gameLogic;
        this.playerX = x;
        this.playerY = y;
        this.questStatuses = questStatuses;
        this.scanner = scanner;
    }

    public void showMenu() {
        System.out.println("\n=== ИГРОВОЕ МЕНЮ ===");
        System.out.println("1. Сохранить игру");
        System.out.println("2. Загрузить игру");
        System.out.println("3. Удалить сохранение");
        System.out.println("4. Вернуться в игру");
        System.out.println("5. Выйти в главное меню");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                saveGameMenu();
                break;
            case "2":
                loadGameMenu();
                break;
            case "3":
                deleteSaveMenu();
                break;
            case "4":
                System.out.println("Возвращаемся в игру...");
                break;
            case "5":
                throw new RuntimeException("Return to main menu");
            default:
                System.out.println("Неверный выбор!");
        }
    }

    private void saveGameMenu() {
        try {
            GameState state = new GameState(
                    player,
                    mainMap,
                    settlement,
                    gameLogic.isInSettlement(),
                    playerX,
                    playerY,
                    questStatuses
            );

            SaveLoadManager.saveGame(state, player.getPlayerName());
            System.out.println("Игра сохранена для персонажа: " + player.getPlayerName());
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    private void loadGameMenu() {
        List<String> saves = SaveLoadManager.getSaveList();
        if (saves.isEmpty()) {
            System.out.println("Нет доступных сохранений");
            return;
        }

        System.out.println("\nДоступные сохранения:");
        for (int i = 0; i < saves.size(); i++) {
            System.out.println((i + 1) + ". " + saves.get(i));
        }

        System.out.print("Выберите сохранение для загрузки: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < saves.size()) {
                String saveName = saves.get(choice);
                GameState state = SaveLoadManager.loadGame(saveName);

                // Обновляем состояние игры
                player = state.getPlayer();
                mainMap = state.getMainMap();
                settlement = state.getSettlement();
                gameLogic.setInSettlement(state.isInSettlement());
                playerX = state.getPlayerX();
                playerY = state.getPlayerY();
                questStatuses = state.getQuestStatuses();

                // Помещаем игрока на карту
                if (state.isInSettlement()) {
                    gameLogic.getSettlementMap().setCell(playerX, playerY, 'P');
                } else {
                    mainMap.placePlayer(playerX, playerY, player);
                }

                System.out.println("Игра загружена: " + saveName);
            } else {
                System.out.println("Неверный выбор!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }

    private void deleteSaveMenu() {
        List<String> saves = SaveLoadManager.getSaveList();
        if (saves.isEmpty()) {
            System.out.println("Нет доступных сохранений");
            return;
        }

        System.out.println("\nДоступные сохранения:");
        for (int i = 0; i < saves.size(); i++) {
            System.out.println((i + 1) + ". " + saves.get(i));
        }

        System.out.print("Выберите сохранение для удаления: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < saves.size()) {
                String saveName = saves.get(choice);
                SaveLoadManager.deleteSave(saveName);
                System.out.println("Сохранение удалено: " + saveName);
            } else {
                System.out.println("Неверный выбор!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }
}