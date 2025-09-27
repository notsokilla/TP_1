package Misc;

import PlayersEnemies.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settlement implements Serializable {
    private static final long serialVersionUID = 2L;
    private ItemCatalog itemCatalog;
    private SettlementMap settlementMap;
    private final QuestBoard questBoard = new QuestBoard();
    //private final QuestHandler questHandler;
    //int elderquest = 0;
    private MainMap mainMap;
    private transient final Logger logger = Logger.getLogger(Settlement.class.getName());
    private int x = 7;
    private int y = 7;
    private final Set<String> hiredRecruits = new HashSet<>();
    private int elderQuestState = 0;

    public Settlement() {
        //this.questHandler = questHandler;
        this.settlementMap = new SettlementMap();
        itemCatalog = new ItemCatalog(); // Инициализируем каталог предметов
    }

    public void reset() {
        this.elderQuestState = 0;
        this.hiredRecruits.clear();
    }

    public Set<String> getHiredRecruits() {
        return this.hiredRecruits;
    }

    public void enterSettlement(Player player) {
        System.out.println("Вы вошли в поселение.");
        // Здесь будет логика взаимодействия с поселением (пока пусто)
    }

    // Событие в доме старосты (С)
    // Settlement.java
    public void handleElderHouse(Player player, QuestHandler questHandler) {
        System.out.println("Вы зашли в дом старосты.");
        System.out.println("Староста: Добро пожаловать в нашу деревню, путник!");
        System.out.println("1. Спросить о деревне.");
        System.out.println("2. Я могу вам чем-нибудь помочь?.");
        System.out.println("3. Поблагодарить и уйти.");

        if (Quest.isCompleted()){
            System.out.println("4. Я убил его.");
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Староста: Наша деревня небольшая, но здесь есть всё, что нужно для жизни.");
        } else if ((choice == 2) && (elderQuestState == 0)) {
            elderQuestState = 1;
            Quest quest = new Quest("Мерзкий Поганец",
                    "Убей мародера, который живет неподалеку от деревни, на юге. \n" +
                            "Он почти каждую ночь крадет все что под руку попадется из нашей деревни. \n" +
                            "Однажды дочь кузнеца попыталась ему помешать - он её заколол. \n" +
                            "Убей его, узнаешь его по крюку вместо руки.");
            player.addQuest(quest);
            questHandler.setQuestMarker(12, 7); // Используем переданный обработчик
            logger.log(Level.INFO, "Игрок получил новый квест: " + quest.getTitle());
            System.out.println("Староста: Да, есть тут одно дельце - " + quest.getDescription());
        } else if ((choice == 2) && (elderQuestState == 1)) {
            System.out.println("Староста: Мне больше не о чем тебя просить, путник.");
        } else if (choice == 3) {
            System.out.println("Староста: Возвращайтесь, если понадобится помощь.");
        } else if (choice == 4) {
            logger.log(Level.INFO, "Игрок выполнил квест и получил награду");
            System.out.println("Староста: Отлично, путник, ты очень помог нашей деревне. Вот наша благодарность тебе.");
            player.setCashReward(200);
        }
    }

    // Событие в мастерской кузнеца (К)
    public void handleBlacksmith(Player player) {
        System.out.println("Вы зашли в мастерскую кузнеца.");
        System.out.println("Кузнец: Здравствуй, путник! Хочешь посмотреть на мои товары?");
        System.out.println("1. Посмотреть товары.");
        System.out.println("2. Уйти.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Кузнец предлагает 2 случайных предмета типа WEAPON
            Item item1 = itemCatalog.getRandomItem(ItemType.WEAPON);
            Item item2 = itemCatalog.getRandomItem(ItemType.HELMET);
            Item item3 = itemCatalog.getRandomItem(ItemType.CHESTPLATE);

            System.out.println("Кузнец: У меня есть для тебя два отличных оружия:");
            System.out.println("1. " + item1.getName() + " Стоимость: " + item1.getPrice());
            System.out.println("2. " + item2.getName() + " Стоимость: " + item2.getPrice());
            System.out.println("3. " + item3.getName() + " Стоимость: " + item3.getPrice());
            System.out.println("4. Уйти.");

            int itemChoice = scanner.nextInt();
            if (itemChoice == 1 && player.getCash() >= item1.getPrice()) {
                boolean success = player.addItemToInventory(item1);
                player.setCash(item1.getPrice());
                if (success) {
                    System.out.println("Вы купили " + item1.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            } else if (itemChoice == 2 && player.getCash() >= item2.getPrice()) {
                boolean success = player.addItemToInventory(item2);
                player.setCash(item2.getPrice());
                if (success) {
                    System.out.println("Вы купили " + item2.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            }else if (itemChoice == 3 && player.getCash() >= item3.getPrice()) {
                    boolean success = player.addItemToInventory(item3);
                    player.setCash(item3.getPrice());
                    if (success) {
                        System.out.println("Вы купили " + item3.getName() + "!");
                    } else {
                        System.out.println("Ваш инвентарь полон.");
                    }
            } else {
                System.out.println("Кузнец: Возвращайся, если передумаешь!");
            }
        } else {
            System.out.println("Кузнец: Возвращайся, если понадобится оружие!");
        }
    }

    // Событие в корчме (Т)
    public void handleTavern(Player player) {
        System.out.println("Вы зашли в корчму.");

        // Проверяем, нанят ли уже Мрачный Нил
        boolean nilHired = isRecruitHired("Мрачный Нил") ||
                player.getPartyRecruits().stream()
                        .anyMatch(r -> r.getName().equals("Мрачный Нил"));

        // Меню в зависимости от состояния найма
        if (nilHired) {
            System.out.println("1. Подойти к корчмарю.");
            System.out.println("2. Уйти.");
        } else {
            System.out.println("1. Подойти к одинокому путнику.");
            System.out.println("2. Подойти к корчмарю.");
            System.out.println("3. Уйти.");
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1 && !nilHired) {
            // Диалог с одиноким путником
            Recruit loneTraveler = new Recruit("Мрачный Нил", 60, 25, 35);
            System.out.println("Одинокий путник: ...");
            System.out.println("1. Пойдешь со мной спасать мир от неведомых чудовищ?");
            System.out.println("2. Уйти.");

            int hireChoice = scanner.nextInt();
            if (hireChoice == 1) {
                if (player.addRecruitToParty(loneTraveler)) {
                    hiredRecruits.add("Мрачный Нил");
                    System.out.println("Я пойду с тобой. Но только потому, что я слаб и легко поддаюсь давлению со стороны окружающих... *Вздох*");
                    System.out.println(loneTraveler.getName() + " присоединился к вашему отряду!");
                } else {
                    System.out.println("Ваш отряд полон.");
                }
            }
        } else if ((choice == 2 && !nilHired) || (choice == 1 && nilHired)) {
            // Меню корчмаря
            Item item1 = itemCatalog.getRandomItem(ItemType.CONSUMABLE);
            Item item2 = itemCatalog.getRandomItem(ItemType.CONSUMABLE);

            System.out.println("Корчмарь: У меня есть для тебя два полезных зелья, за счет заведения:");
            System.out.println("1. " + item1.getName());
            System.out.println("2. " + item2.getName());
            System.out.println("3. Уйти.");

            int itemChoice = scanner.nextInt();
            if (itemChoice == 1 || itemChoice == 2) {
                Item selectedItem = itemChoice == 1 ? item1 : item2;
                if (player.addItemToInventory(selectedItem)) {
                    System.out.println("Вы купили " + selectedItem.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            }
        }

        System.out.println("Вы покинули корчму.");
    }

    public void handleQuestBoard(Player player) {
        questBoard.startPlayerInteraction(); // Начинаем взаимодействие

        Scanner scanner = new Scanner(System.in);
        boolean stayAtBoard = true;
        boolean questTaken = false;

        while (stayAtBoard) {
            System.out.println(questBoard.getStatusInfo());
            System.out.println("1. Взять случайное задание");
            System.out.println("2. Проверить статус");
            System.out.println("3. Отойти от доски");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    if (!questTaken) {
                        TablesQuest quest = BoardQuest.generateRandomQuest();
                        if (questBoard.tryStartQuest(player, quest)) {
                            System.out.println("Вы взяли задание: " + quest.getTitle());
                            questTaken = true;
                        } else {
                            System.out.println("Не удалось взять задание!");
                        }
                    } else {
                        System.out.println("Вы уже взяли задание!");
                    }
                    break;

                case 2:
                    // Показываем прогресс
                    if (questTaken) {
                        System.out.println("Ваш квест выполняется...");
                        System.out.println(generateProgressMessage());
                    } else {
                        System.out.println("У вас нет активного задания");
                    }
                    break;

                case 3:
                    stayAtBoard = false;
                    break;
            }

            // Пауза для "выполнения" задания
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        questBoard.endPlayerInteraction(); // Заканчиваем взаимодействие
    }

    private String generateProgressMessage() {
        String[] messages = {
                "Вы изучаете карту местности...",
                "Вы собираете информацию у местных жителей...",
                "Вы проверяете снаряжение...",
                "Вы планируете следующий шаг...",
                "Вы отдыхаете перед следующим этапом задания..."
        };
        return messages[new Random().nextInt(messages.length)];
    }

    private void takeRandomQuest(Player player) {
        TablesQuest tquest = BoardQuest.generateRandomQuest();

        if (questBoard.tryStartQuest(player, tquest)) {
            System.out.println("Задание началось: " + tquest.getTitle());
        } else {
            System.out.println("Все слоты заняты! Ждать в очереди? (1-Да, 2-Нет)");
            int answer = new Scanner(System.in).nextInt();
            if (answer == 1) {
                questBoard.tryStartQuest(player, tquest);
                System.out.println("Вы в очереди. Ожидайте...");
            }
        }
    }

    public void setElderQuestState(int state) {
        this.elderQuestState = state;
    }

    public void clearHiredRecruits() {
        hiredRecruits.clear();
    }

    public void markRecruitAsHired(String name) {
        hiredRecruits.add(name);
    }

    public boolean isRecruitHired(String name) {
        return hiredRecruits.contains(name);
    }

    public int getX() {
        return this.x; // Возвращает X-координату поселения
    }

    public int getY() {
        return this.y; // Возвращает Y-координату поселения
    }

    public int getQuestState() {
        return this.elderQuestState; // Возвращает состояние квеста
    }
}