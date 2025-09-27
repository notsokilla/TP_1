package Misc;

import PlayersEnemies.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BattleMap implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SIZE = 15;
    private char[][] board;
    private Enemy enemy;
    private List<Recruit> activeRecruits;
    private Player player;
    public static final char TREE = '|';
    public static final char LUZHA = 'o';
    public static final char BUSH = '#';
    boolean useCustomMap = false;

    public BattleMap() {
        this.board = new char[SIZE][SIZE];
        this.activeRecruits = new ArrayList<>();
        initializeDefaultBoard();
    }

    public void setUseCustomMap(boolean useCustomMap) {
        this.useCustomMap = useCustomMap;
        if (!useCustomMap) {
            initializeDefaultBoard();
        }
    }

    public void loadBoard(char[][] newBoard) {
        if (newBoard == null || newBoard.length != SIZE || newBoard[0].length != SIZE) {
            throw new IllegalArgumentException("Неверный размер карты!");
        }
        this.board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(newBoard[i], 0, this.board[i], 0, SIZE);
        }
        this.useCustomMap = true;
    }

    private void initializeDefaultBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 'O';
            }
        }

        // Стандартные препятствия
        board[3][3] = TREE;
        board[7][10] = TREE;
        board[2][5] = BUSH;
        board[5][8] = BUSH;
        board[10][12] = LUZHA;
        board[9][4] = LUZHA;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }



    public void initializeBoard() {
        System.out.println("DEBUG: Используется " + (useCustomMap ? "кастомная" : "стандартная") + " карта");
        if (!useCustomMap) {
            initializeDefaultBoard();
        }
        placeCharacters();
    }

    private void placeCharacters() {
        // Очищаем предыдущие позиции персонажей
        clearCharacterPositions();

        // Размещаем персонажей
        if (player != null) {
            board[player.getX()][player.getY()] = 'P';
            if (activeRecruits != null) {
                for (int i = 0; i < activeRecruits.size(); i++) {
                    Recruit r = activeRecruits.get(i);
                    if (isValidPosition(r.getX(), r.getY())) {
                        board[r.getX()][r.getY()] = (char)('1' + i);
                    }
                }
            }
        }

        if (enemy != null) {
            board[enemy.getX()][enemy.getY()] = 'E';
        }
    }

    private void clearCharacterPositions() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char c = board[i][j];
                if (c == 'P' || c == 'E' || (c >= '1' && c <= '9')) {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public char getCell(int x, int y) {
        return getCellSymbol(x, y);
    }

    public void placePlayerAndParty(Player player) {
        this.player = player;
        player.setPosition(0, 0);

        if (player.getPartyRecruits() != null) {
            activeRecruits = new ArrayList<>(player.getPartyRecruits());
            for (int i = 0; i < activeRecruits.size(); i++) {
                activeRecruits.get(i).setPosition(0, i+1);
            }
        }
    }

    public void placeEnemy(Enemy enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException("Enemy cannot be null");
        }
        this.enemy = enemy;
        enemy.setPosition(SIZE-1, SIZE-1);
    }

    public void placeParty(Player player) {
        this.activeRecruits = new ArrayList<>(player.getPartyRecruits());
        for (int i = 0; i < activeRecruits.size(); i++) {
            activeRecruits.get(i).setPosition(0, i+1);
        }
    }

    private void printBattleInfo() {
        System.out.println("\n=== ИНФОРМАЦИЯ О БОЕ ===");
        if (player != null) {
            System.out.println("Игрок (P): " + player.getPlayerName() +
                    " | Здоровье: " + player.getHealth() + "/" + player.getMaxHealth());
        }

        if (activeRecruits != null) {
            for (int i = 0; i < activeRecruits.size(); i++) {
                Recruit r = activeRecruits.get(i);
                System.out.println("Союзник (" + (char)('1' + i) + "): " + r.getName() +
                        " | Здоровье: " + r.getHealth());
            }
        }

        if (enemy != null) {
            System.out.println("Враг (E): " + enemy.getName() +
                    " | Здоровье: " + enemy.getHealth());
        }
    }

    public void printBoard() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        initializeBoard();

        printBattleInfo();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char cell = getCellSymbol(i, j);
                String colorCode = getColorForCell(cell);
                System.out.print(colorCode + cell + " " + "\u001B[0m");
            }
            System.out.println();
        }
    }

    private String getColorForCell(char cell) {
        switch (cell) {
            case 'P': case '#': case '|': return "\u001B[32m"; // Зеленый
            case 'E': return "\u001B[31m"; // Красный
            case '1': case '2': case '3': case 'o': return "\u001B[34m"; // Синий
            default: return "\u001B[37m"; // Белый
        }
    }

    private char getCellSymbol(int x, int y) {
        if (player != null && x == player.getX() && y == player.getY()) return 'P';
        if (enemy != null && x == enemy.getX() && y == enemy.getY()) return 'E';

        if (activeRecruits != null) {
            for (int i = 0; i < activeRecruits.size(); i++) {
                Recruit r = activeRecruits.get(i);
                if (x == r.getX() && y == r.getY()) return (char)('1' + i);
            }
        }

        return board[x][y];
    }

    public boolean movePlayer(char direction) {
        if (player == null) return false;

        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case 'W': newX--; break;
            case 'S': newX++; break;
            case 'A': newY--; break;
            case 'D': newY++; break;
            case 'C': newX++; newY++; break;
            case 'Z': newX++; newY--; break;
            case 'Q': newX--; newY--; break;
            case 'E': newX--; newY++; break;
            default: return false;
        }

        if (!isValidPosition(newX, newY)) {
            System.out.println("Нельзя переместиться: препятствие или занятая клетка!");
            return false;
        }

        if (board[newX][newY] == BUSH || board[newX][newY] == LUZHA) {
            int odPenalty = 1;
            if (player.getCurrentActionPoints() >= odPenalty) {
                player.spendActionPoints(odPenalty);
                System.out.println("Вы на что-то наткнулись и подустали.");
            } else {
                System.out.println("Недостаточно ОД для преодоления препятствия!");
                return false;
            }
        }

        player.setPosition(newX, newY);
        return true;
    }

    public boolean isEnemyNoticedPlayer(Player player) {
        if (enemy == null || player == null) return false;
        return Math.abs(enemy.getX() - player.getX()) <= 3 &&
                Math.abs(enemy.getY() - player.getY()) <= 3;
    }

    public boolean isValidPosition(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }

        if (player != null && player.getX() == x && player.getY() == y) {
            return false;
        }

        if (enemy != null && enemy.getX() == x && enemy.getY() == y) {
            return false;
        }

        if (board[x][y] == TREE) {
            return false;
        }

        if (activeRecruits != null) {
            for (Recruit recruit : activeRecruits) {
                if (recruit.getX() == x && recruit.getY() == y) {
                    return false;
                }
            }
        }

        return true;
    }

    public char[][] getBoard() {
        char[][] copy = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    public void clearEnemy() {
        this.enemy = null;
    }

    public void resetToDefault() {
        this.useCustomMap = false;
        initializeDefaultBoard();
    }
}