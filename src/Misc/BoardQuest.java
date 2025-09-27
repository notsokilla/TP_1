// BoardQuest.java
package Misc;

import PlayersEnemies.Player;
import java.util.Random;

public class BoardQuest {
    private static final Random rand = new Random();
    private static final String[] TITLES = {
            "Охотник за головами",
            "Зачистка территории",
            "Доставка груза",
            "Поиск артефакта"
    };

    private static final String[] DESCRIPTIONS = {
            "Уничтожить бандитов в старых шахтах",
            "Очистить лес от вурдалаков",
            "Доставить посылку в соседний город",
            "Найти древний артефакт в руинах"
    };

    public static TablesQuest generateRandomQuest() {
        String title = TITLES[rand.nextInt(TITLES.length)];
        String desc = DESCRIPTIONS[rand.nextInt(DESCRIPTIONS.length)];
        int reward = 10 + rand.nextInt(16); // 10-25 золота
        long duration = 30000 + rand.nextInt(60000); // 30-60 секунд

        // Контекстные сообщения для разных типов квестов
        String[] messages;
        if (title.contains("Охотник")) {
            messages = new String[]{
                    "Вы выслеживаете цель через густой лес...",
                    "Вы проверяете следы на тропе...",
                    "Вы готовите ловушку для цели..."
            };
        } else if (title.contains("Доставка")) {
            messages = new String[]{
                    "Вы упаковываете ценный груз...",
                    "Вы прокладываете безопасный маршрут...",
                    "Вы проверяете содержимое посылки..."
            };
        } else if (title.contains("Зачистка")) {
            messages = new String[]{
                    "Вы осматриваете территорию на наличие угроз...",
                    "Вы готовите оружие к предстоящему бою...",
                    "Вы изучаете слабые места врагов..."
            };
        } else {
            messages = new String[]{
                    "Вы изучаете старые карты...",
                    "Вы расспрашиваете местных мудрецов...",
                    "Вы исследуете древние руины...",
                    "Вы жоска вайбкодите..."
            };
        }

        return new TablesQuest(title, desc, reward, duration, messages);
    }
}