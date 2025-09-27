// QuestBoard.java
package Misc;

import PlayersEnemies.Npc;
import PlayersEnemies.Player;

import java.util.*;
import java.util.concurrent.*;

public class QuestBoard {
    private static final int MAX_ACTIVE_QUESTS = 1;
    private final BlockingQueue<QuestSlot> questQueue = new LinkedBlockingQueue<>();
    private final Map<UUID, QuestSlot> activeQuests = new ConcurrentHashMap<>();
    private final List<Npc> npcs = new ArrayList<>();
    private final List<String> messageBuffer = new ArrayList<>();
    private boolean playerInteracting = false;

    private static class QuestSlot {
        Player player;
        Npc npc;
        TablesQuest quest;
        UUID id = UUID.randomUUID();
        long startTime;
        boolean completed;

        QuestSlot(Player player, TablesQuest quest) {
            this.player = player;
            this.quest = quest;
            this.startTime = System.currentTimeMillis();
        }

        QuestSlot(Npc npc, TablesQuest quest) {
            this.npc = npc;
            this.quest = quest;
            this.npc.setCurrentQuest(quest);
            this.startTime = System.currentTimeMillis();
        }

        String getExecutorName() {
            return player != null ? player.getPlayerName() : npc.getName();
        }

        void complete() {
            this.completed = true;
            if (player != null) {
                quest.complete(player);
            }
            if (npc != null) {
                npc.setCurrentQuest(null);
            }
        }
    }

    public QuestBoard() {
        initializeNpcs();
        startProcessing();
    }

    private void initializeNpcs() {
        for (int i = 0; i < 5; i++) {
            npcs.add(new Npc());
        }
    }

    private void startProcessing() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            // NPC берут квесты случайным образом
            if (new Random().nextInt(100) < 25) {
                npcs.stream()
                        .filter(npc -> npc.getCurrentQuest() == null)
                        .findAny()
                        .ifPresent(npc -> {
                            TablesQuest quest = BoardQuest.generateRandomQuest();
                            questQueue.add(new QuestSlot(npc, quest));
                            String msg = npc.getName() + " взял квест: " + quest.getTitle();
                            bufferMessage(msg);
                        });
            }

            // Обработка очереди
            while (activeQuests.size() < MAX_ACTIVE_QUESTS && !questQueue.isEmpty()) {
                QuestSlot slot = questQueue.poll();
                activeQuests.put(slot.id, slot);
                String msg = "Начат квест: " + slot.quest.getTitle() +
                        " | Исполнитель: " + slot.getExecutorName();
                bufferMessage(msg);
            }

            // Проверка завершения квестов
            Iterator<Map.Entry<UUID, QuestSlot>> it = activeQuests.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, QuestSlot> entry = it.next();
                QuestSlot slot = entry.getValue();

                if (System.currentTimeMillis() - slot.startTime > slot.quest.getDurationMs()) {
                    slot.complete();
                    it.remove();
                    String msg = "Завершён квест: " + slot.quest.getTitle() +
                            " | Исполнитель: " + slot.getExecutorName();
                    bufferMessage(msg);

                    // Контекстное сообщение для игрока
                    if (slot.player != null) {
                        msg = generateCompletionMessage(slot.quest, slot.player);
                        bufferMessage(msg);
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void bufferMessage(String message) {
        if (playerInteracting) {
            System.out.println(message);
        } else {
            messageBuffer.add(message);
        }
    }

    public void startPlayerInteraction() {
        playerInteracting = true;
        // Выводим все накопленные сообщения
        messageBuffer.forEach(System.out::println);
        messageBuffer.clear();
    }

    public void endPlayerInteraction() {
        playerInteracting = false;
    }

    private String generateCompletionMessage(TablesQuest quest, Player player) {
        String[] messages = {
                player.getPlayerName() + " завершил задание '" + quest.getTitle() + "'! " +
                        "Теперь можно отдохнуть в таверне.",
                "Молодец, " + player.getPlayerName() + "! Задание '" + quest.getTitle() +
                        "' выполнено безупречно.",
                quest.getTitle() + " - задание выполнено! " + player.getPlayerName() +
                        " заслужил свою награду.",
                "Горожане благодарят " + player.getPlayerName() + " за выполнение задания '" +
                        quest.getTitle() + "'!"
        };
        return messages[new Random().nextInt(messages.length)];
    }

    public boolean tryStartQuest(Player player, TablesQuest quest) {
        return questQueue.offer(new QuestSlot(player, quest));
    }

    // QuestBoard.java
    public String getStatusInfo() {
        StringBuilder sb = new StringBuilder("=== ДОСКА ПОРУЧЕНИЙ ===\n\n");
        sb.append("Активные квесты (").append(activeQuests.size()).append("/")
                .append(MAX_ACTIVE_QUESTS).append("):\n");

        if (activeQuests.isEmpty()) {
            sb.append("  Пока нет активных заданий\n");
        } else {
            activeQuests.values().forEach(slot ->
                    sb.append("- ").append(slot.quest.getTitle()).append(" (")
                            .append(slot.getExecutorName()).append(")\n")
            );
        }

        sb.append("\nОчередь: ").append(questQueue.size()).append(" заданий в ожидании\n");

        // Игрок в очереди
        Optional<QuestSlot> playerSlot = questQueue.stream()
                .filter(slot -> slot.player != null)
                .findFirst();

        if (playerSlot.isPresent()) {
            int position = getQueuePosition(playerSlot.get());
            sb.append("\nВаше задание: ").append(playerSlot.get().quest.getTitle())
                    .append(" [").append(position).append(" в очереди]\n");
        }

        sb.append("\nНедавние события:\n");
        if (messageBuffer.isEmpty()) {
            sb.append("  Пока ничего не происходило\n");
        } else {
            messageBuffer.forEach(msg -> sb.append("- ").append(msg).append("\n"));
        }

        sb.append("\nДругие искатели приключений:\n");
        npcs.forEach(npc -> sb.append("- ").append(npc).append("\n"));

        return sb.toString();
    }

    private int getQueuePosition(QuestSlot playerSlot) {
        int position = 1;
        for (QuestSlot slot : questQueue) {
            if (slot == playerSlot) return position;
            position++;
        }
        return -1;
    }

    public void generateProgressMessages() {
        activeQuests.values().forEach(slot -> {
            if (slot.player != null) {
                String[] messages = {
                        "Вы исследуете местность...",
                        "Вы собираете информацию...",
                        "Вы готовитесь к выполнению задачи...",
                        "Вы приближаетесь к цели...",
                        "Вы пишите лабу по спрингу..."
                };
                System.out.println(messages[new Random().nextInt(messages.length)]);
            }
        });
    }
}