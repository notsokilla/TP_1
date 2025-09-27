package Events;

import PlayersEnemies.*;

import java.io.Serializable;
import java.util.Scanner;

public class EventTile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String eventDescription; // Описание события
    private EventBehavior eventBehavior;// Поведение события
    private char symbol;

    // Конструктор с тремя параметрами
    public EventTile(String eventDescription, EventBehavior eventBehavior, char symbol) {
        this.eventDescription = eventDescription;
        this.eventBehavior = eventBehavior;
        this.symbol = symbol;
    }

    // Метод для получения поведения события
    public EventBehavior getEventBehavior() {
        return eventBehavior;
    }

    // Метод для активации события
    public void triggerEvent(Player player) {
        System.out.println(eventDescription); // Выводим описание события
        eventBehavior.trigger(player); // Выполняем поведение события
    }

    // Метод для получения символа события
    public char getSymbol() {
        return '?'; // Символ события
    }
}