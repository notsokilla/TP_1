// SaveLoadManager.java
package Misc;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SaveLoadManager {
    private static final String SAVE_DIR = "saves";

    static {
        new File(SAVE_DIR).mkdirs(); // Создаем директорию при первом использовании
    }

    public static void saveGame(GameState state, String playerName) throws IOException {
        Path path = Paths.get(SAVE_DIR, playerName + ".sav");
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(state);
        }
    }

    public static GameState loadGame(String playerName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(SAVE_DIR, playerName + ".sav");
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return (GameState) ois.readObject();
        }
    }

    public static List<String> getSaveList() {
        List<String> saves = new ArrayList<>();
        File dir = new File(SAVE_DIR);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles((d, name) -> name.endsWith(".sav"))) {
                saves.add(file.getName().replace(".sav", ""));
            }
        }
        return saves;
    }

    public static void deleteSave(String playerName) {
        Path path = Paths.get(SAVE_DIR, playerName + ".sav");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Ошибка удаления сохранения: " + e.getMessage());
        }
    }
}