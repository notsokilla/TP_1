import Misc.*;
import PlayersEnemies.*;
import Events.*;
import java.io.*;
import java.util.Scanner;

public class BattleMapEditor {
    private char[][] board;
    private Scanner scanner;
    private BattleMap battleMap;
    private static final int SIZE = 15;
    private static final char TREE = '|';
    private static final char LUZHA = 'o';
    private static final char BUSH = '#';

    public BattleMapEditor(BattleMap battleMap) {
        this.battleMap = battleMap;
        this.board = new char[SIZE][SIZE];
        this.scanner = new Scanner(System.in);

        if (battleMap.getBoard() != null) {
            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(battleMap.getBoard()[i], 0, this.board[i], 0, SIZE);
            }
        } else {
            initializeEmptyBoard();
        }
    }

    private void initializeEmptyBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 'O';
            }
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            printBoard();
            printMenu();
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            switch (input.charAt(0)) {
                case '1' -> addObject();
                case '2' -> removeObject();
                case '3' -> saveMap();
                case '4' -> loadMap();
                case '5' -> selectMapForGame();
                case '6' -> {
                    applyChangesToBattleMap();
                    running = false;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
        System.out.println("Редактор закрыт. Изменения применены к BattleMap.");
    }

    private void selectMapForGame() {
        applyChangesToBattleMap();
        battleMap.setUseCustomMap(true);
        System.out.println("DEBUG: Карта установлена как кастомная");
        System.out.println("Проверочный символ: " + battleMap.getBoard()[3][3]);
        System.out.println("Текущая карта выбрана для использования в игре!");
    }

    private void applyChangesToBattleMap() {
        battleMap.loadBoard(this.board);
    }

    private void printMenu() {
        System.out.println("\n=== РЕДАКТОР КАРТ ===");
        System.out.println("1. Добавить объект");
        System.out.println("2. Удалить объект");
        System.out.println("3. Сохранить карту");
        System.out.println("4. Загрузить карту");
        System.out.println("5. Выбрать эту карту для игры");
        System.out.println("6. Выход");
        System.out.print("Выберите действие: ");
    }

    private void printBoard() {
        System.out.println("\nТекущая карта (15x15):");
        System.out.print("   ");
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d", i);
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
    }

    private void addObject() {
        int x = getCoordinate("X (0-14): ");
        int y = getCoordinate("Y (0-14): ");

        if (!isValidCoordinate(x, y)) {
            System.out.println("Неверные координаты!");
            return;
        }

        System.out.println("\nТипы объектов:");
        System.out.println("1. Дерево (" + TREE + ")");
        System.out.println("2. Куст (" + BUSH + ")");
        System.out.println("3. Лужа (" + LUZHA + ")");
        System.out.println("4. Бесконечная пустота (O)");
        System.out.print("Выберите тип: ");

        String choice = scanner.nextLine();
        char symbol = switch (choice) {
            case "1" -> TREE;
            case "2" -> BUSH;
            case "3" -> LUZHA;
            default -> 'O';
        };

        board[y][x] = symbol;
        System.out.println("Объект '" + symbol + "' добавлен в (" + x + "," + y + ")");
    }

    private void removeObject() {
        int x = getCoordinate("X (0-14): ");
        int y = getCoordinate("Y (0-14): ");

        if (isValidCoordinate(x, y)) {
            board[y][x] = 'O';
            System.out.println("Объект в (" + x + "," + y + ") удалён");
        } else {
            System.out.println("Неверные координаты!");
        }
    }

    private int getCoordinate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int coord = Integer.parseInt(input);
                if (coord >= 0 && coord < SIZE) {
                    return coord;
                }
            } catch (NumberFormatException e) {
                // Продолжаем цикл
            }
            System.out.println("Введите число от 0 до " + (SIZE-1));
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    private void saveMap() {
        System.out.print("Введите имя файла для сохранения (без .txt): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым!");
            return;
        }

        try (PrintWriter writer = new PrintWriter(filename + ".txt")) {
            for (char[] row : board) {
                writer.println(new String(row));
            }
            System.out.println("Карта успешно сохранена в " + filename + ".txt");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    private void loadMap() {
        System.out.print("Введите имя файла для загрузки (без .txt): ");
        String filename = scanner.nextLine().trim();
        File file = new File(filename + ".txt");

        if (!file.exists()) {
            System.out.println("Файл " + filename + ".txt не найден!");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            char[][] tempBoard = new char[SIZE][SIZE];
            int lineCount = 0;

            while (fileScanner.hasNextLine() && lineCount < SIZE) {
                String line = fileScanner.nextLine();
                if (line.length() != SIZE) {
                    System.out.println("Ошибка: длина строки " + (lineCount + 1) + " не равна " + SIZE);
                    return;
                }

                for (int x = 0; x < SIZE; x++) {
                    char c = line.charAt(x);
                    if (c != 'O' && c != TREE && c != LUZHA && c != BUSH) {
                        System.out.println("Ошибка: недопустимый символ '" + c + "' в строке " + (lineCount + 1));
                        return;
                    }
                    tempBoard[lineCount][x] = c;
                }
                lineCount++;
            }

            if (lineCount != SIZE) {
                System.out.println("Ошибка: количество строк в файле не равно " + SIZE);
                return;
            }

            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(tempBoard[i], 0, this.board[i], 0, SIZE);
            }
            System.out.println("Карта успешно загружена из " + filename + ".txt");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }
}