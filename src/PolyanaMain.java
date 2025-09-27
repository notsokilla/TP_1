import PlayersEnemies.*;
import Misc.*;
import Events.*;

import java.io.*;
import java.util.Objects;
import java.util.*;
import java.util.Scanner;
import java.nio.file.*;

public class PolyanaMain {
    private static final String LEADERBOARD_FILE = "leaderboard.dat";
    private static final String SAVE_DIR = "saves";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Показываем таблицу лидеров в начале
        showLeaderboard();

        System.out.println("=== ПОЛЯНА ===");
        System.out.println("1. Новая игра");
        System.out.println("2. Загрузить игру");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();
        Player player = null;
        BattleMap battleMap = new BattleMap();
        MainMap mainMap = null;
        Settlement settlement = new Settlement(); // Создаем settlement здесь
        Map<String, Boolean> questStatuses = new HashMap<>();

        if (choice.equals("1")) {
            BattleMapEditor editor = new BattleMapEditor(battleMap);
            editor.start();
            StoryManager.printIntro();

            System.out.println("\n=== СОЗДАНИЕ ПЕРСОНАЖА ===");
            player = GameInitializer.createPlayer(scanner);

            mainMap = new MainMap();
            mainMap.placePlayer(0, 0, player);
        } else if (choice.equals("2")) {
            List<String> saveList = SaveLoadManager.getSaveList();
            if (saveList.isEmpty()) {
                System.out.println("Не удалось загрузить игру");
                return;
            }

            System.out.println("\nДоступные сохранения:");
            for (int i = 0; i < saveList.size(); i++) {
                System.out.println((i+1) + ". " + saveList.get(i));
            }

            System.out.print("Выберите сохранение: ");
            try {
                int saveChoice = Integer.parseInt(scanner.nextLine()) - 1;
                if (saveChoice >= 0 && saveChoice < saveList.size()) {
                    String saveName = saveList.get(saveChoice);
                    GameState state = SaveLoadManager.loadGame(saveName);

                    player = state.getPlayer();
                    mainMap = state.getMainMap();
                    settlement = state.getSettlement();
                    questStatuses = state.getQuestStatuses();
                    boolean isInSettlement = state.isInSettlement();
                    int playerX = state.getPlayerX();
                    int playerY = state.getPlayerY();

                    if (isInSettlement) {
                        // Создаем временную карту поселения
                        SettlementMap tempSettlementMap = new SettlementMap();
                        tempSettlementMap.setCell(playerX, playerY, 'P');
                        System.out.println("Игра загружена: " + saveName + " (в поселении)");
                    } else {
                        mainMap.placePlayer(playerX, playerY, player);
                        System.out.println("Игра загружена: " + saveName);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка загрузки: " + e.getMessage());
                return;
            }
        }

        if (player != null) {
            startGame(scanner, player, mainMap, battleMap, settlement, questStatuses);
        }

        scanner.close();
    }

    private static void startGame(Scanner scanner, Player player, MainMap mainMap,
                                  BattleMap battleMap, Settlement settlement,
                                  Map<String, Boolean> questStatuses) {
        try {
            battleMap.placePlayerAndParty(player);
            battleMap.initializeBoard();

            System.out.println("\n=== ИГРА НАЧИНАЕТСЯ ===");
            long startTime = System.currentTimeMillis();

            GameManager.playGame(scanner, player, mainMap, battleMap, settlement);

            if (player.isAlive() && Quest.isCompleted() && mainMap.isExitReached(player.getX(), player.getY())) {
                StoryManager.printOutro();
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;

                // Добавляем результат в таблицу лидеров
                addToLeaderboard(player.getPlayerName(), timeElapsed);
                showLeaderboard();
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showLeaderboard() {
        try {
            Path path = Paths.get(LEADERBOARD_FILE);
            if (!Files.exists(path)) {
                System.out.println("\nТаблица лидеров пуста");
                return;
            }

            List<LeaderboardEntry> entries = readLeaderboard();
            if (entries.isEmpty()) {
                System.out.println("\nТаблица лидеров пуста");
                return;
            }

            // Сортируем по времени (от меньшего к большему)
            entries.sort(Comparator.comparingLong(LeaderboardEntry::getTime));

            System.out.println("\n=== ТАБЛИЦА ЛИДЕРОВ ===");
            System.out.println("Место  Имя               Время");
            System.out.println("------------------------------");

            int count = Math.min(5, entries.size());
            for (int i = 0; i < count; i++) {
                LeaderboardEntry entry = entries.get(i);
                System.out.printf("%2d.    %-15s %s%n",
                        i+1,
                        entry.getName(),
                        formatTime(entry.getTime()));
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки таблицы лидеров: " + e.getMessage());
        }
    }

    private static void addToLeaderboard(String name, long time) {
        try {
            List<LeaderboardEntry> entries = new ArrayList<>();
            Path path = Paths.get(LEADERBOARD_FILE);

            if (Files.exists(path)) {
                entries = readLeaderboard();
            }

            entries.add(new LeaderboardEntry(name, time));

            // Сохраняем обратно
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
                oos.writeObject(entries);
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения таблицы лидеров: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<LeaderboardEntry> readLeaderboard() throws IOException {
        Path path = Paths.get(LEADERBOARD_FILE);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<LeaderboardEntry>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Ошибка чтения таблицы лидеров", e);
        }
    }

    static String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    static class LeaderboardEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String name;
        private final long time;

        public LeaderboardEntry(String name, long time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public long getTime() {
            return time;
        }
    }

    public static List<LeaderboardEntry> readLeaderboard(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<LeaderboardEntry>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Ошибка чтения таблицы лидеров", e);
        }
    }

    public static void addToLeaderboard(String filename, String name, long time) {
        try {
            List<LeaderboardEntry> entries = new ArrayList<>();
            Path path = Paths.get(filename);

            if (Files.exists(path)) {
                entries = readLeaderboard(filename);
            }

            // Обработка пустого имени
            if (name == null || name.trim().isEmpty()) {
                name = "Неизвестный";
            }

            entries.add(new LeaderboardEntry(name, time));

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
                oos.writeObject(entries);
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения таблицы лидеров: " + e.getMessage());
        }
    }
}