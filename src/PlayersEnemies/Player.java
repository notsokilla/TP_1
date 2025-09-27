package PlayersEnemies;

import Misc.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private int speed;
    private int health;
    private int armor;
    private int mana;
    private int damage;
    private String playerName;
    private Item[] inventory = new Item[3];
    private Item helmet;
    private Item chestplate;
    private Item leggings;
    private Item boots;
    private Item weapon;
    private int maxHealth;
    private List<Quest> quests;
    private Party party;
    private int actionPoints;
    private final int maxActionPoints = 5;
    private static int cash = 100;
    private int attackRange = 1;

    public Player(String playerName) {
        super(0, 0);
        this.playerName = playerName;
        this.health = 9000;
        this.maxHealth = 9000;
        this.armor = 1000;
        this.damage = 1500;
        this.actionPoints = maxActionPoints;
        this.party = new Party();
        this.quests = new ArrayList<>();
        this.inventory = new Item[3];
        this.cash = 100;
    }

    @Override
    public int getCurrentActionPoints() {
        return actionPoints;
    }

    public int getMaxActionPoints() {
        return maxActionPoints;
    }

    @Override
    public void startTurn() {
        this.actionPoints = maxActionPoints;
    }

    @Override
    public void spendActionPoints(int amount) {
        if (amount <= actionPoints) {
            this.actionPoints -= amount;
        }
    }

    @Override
    public boolean canAffordAction(int cost) {
        return actionPoints >= cost;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean equipItem(Item item) {
        ItemType type = item.getType();
        Item currentItem = getItemByType(type);

        if (currentItem != null) {
            if (!addItemToInventory(currentItem)) {
                return false;
            }
            currentItem.removeEffect(this);
        }

        setItemToSlot(item, type);
        item.applyEffect(this);
        return true;
    }

    public boolean unequipItem(ItemType type) {
        Item item = getItemByType(type);
        if (item == null) return false;

        if (addItemToInventory(item)) {
            setItemToSlot(null, type);
            item.removeEffect(this);
            return true;
        }
        return false;
    }

    public void clearInventory() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                inventory[i].removeEffect(this); // Снимаем эффекты
                inventory[i] = null;
            }
        }
    }

    public void restoreInventory(List<Item> items) {
        clearInventory();
        for (int i = 0; i < Math.min(items.size(), inventory.length); i++) {
            inventory[i] = items.get(i) != null ? items.get(i).deepCopy() : null;
        }
    }

    private Item getItemByType(ItemType type) {
        switch (type) {
            case HELMET: return helmet;
            case CHESTPLATE: return chestplate;
            case LEGGINGS: return leggings;
            case BOOTS: return boots;
            case WEAPON: return weapon;
            default: return null;
        }
    }

    private void setItemToSlot(Item item, ItemType type) {
        switch (type) {
            case HELMET: helmet = item; break;
            case CHESTPLATE: chestplate = item; break;
            case LEGGINGS: leggings = item; break;
            case BOOTS: boots = item; break;
            case WEAPON: weapon = item; break;
        }
    }

    public boolean addItemToInventory(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return true;
            }
        }
        return false;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public Item getHelmet() { return helmet; }
    public Item getChestplate() { return chestplate; }
    public Item getLeggings() { return leggings; }
    public Item getBoots() { return boots; }
    public Item getWeapon() { return weapon; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.printf("Получено %d урона (с учетом брони). Здоровье: %d/%d%n",
                damage, health, maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract String StartAvatar();

    public void addQuest(Quest quest) {
        quests.add(quest);
        System.out.println("Получен новый квест: " + quest.getTitle());
    }

    public void completeQuest(Quest quest) {
        quest.setCompleted(true);
    }

    public void printQuests() {
        if (quests.isEmpty()) {
            System.out.println("У вас нет активных квестов.");
        } else {
            System.out.println("Ваши квесты:");
            for (Quest quest : quests) {
                System.out.println(quest);
            }
        }
    }

    public boolean addRecruitToParty(Recruit recruit) {
        return party.addRecruit(recruit);
    }

    public void removeRecruitFromParty(Recruit recruit) {
        party.removeRecruit(recruit);
    }

    public List<Recruit> getPartyRecruits() {
        return party.getRecruits();
    }

    public void setPartyRecruits(List<Recruit> recruits) {
        this.party.getRecruits().clear();
        this.party.getRecruits().addAll(recruits);
    }

    public void printParty() {
        party.printParty();
    }

    public boolean canAfford(int cost) {
        return actionPoints >= cost;
    }

    public static int getCash() {
        return cash;
    }

    public static void goCash(int input) {
        cash = input;
    }

    public static void setCash(int price) {
        cash = cash - price;
    }

    public void setCashReward(int reward) {
        this.cash = cash + reward;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void clearAllData() {
        clearInventory();
        getPartyRecruits().clear();
        getQuests().clear();

        // Сброс экипировки
        unequipItem(ItemType.HELMET);
        unequipItem(ItemType.CHESTPLATE);
        unequipItem(ItemType.LEGGINGS);
        unequipItem(ItemType.BOOTS);
        unequipItem(ItemType.WEAPON);

    }

    public void showStatus() {
        System.out.println("\n=== ХАРАКТЕРИСТИКИ ИГРОКА ===");
        System.out.println("Имя: " + getPlayerName());
        System.out.println("Здоровье: " + getHealth() + " / " + getMaxHealth());
        System.out.println("Урон: " + getDamage());
        System.out.println("Защита: " + getArmor());
        System.out.println("AP: " + actionPoints + "/" + maxActionPoints);

        System.out.println("\n=== ИНВЕНТАРЬ ===");
        System.out.println("Шлем: " + (getHelmet() != null ? getHelmet().getName() : "Пусто"));
        System.out.println("Нагрудник: " + (getChestplate() != null ? getChestplate().getName() : "Пусто"));
        System.out.println("Поножи: " + (getLeggings() != null ? getLeggings().getName() : "Пусто"));
        System.out.println("Сапоги: " + (getBoots() != null ? getBoots().getName() : "Пусто"));
        System.out.println("Оружие: " + (getWeapon() != null ? getWeapon().getName() : "Пусто"));

        System.out.println("\nСумка (3 слота):");
        System.out.println("\nВаши богатства: " + getCash() + " медяков");
        Item[] inventory = getInventory();
        for (int i = 0; i < inventory.length; i++) {
            System.out.println(i + ": " + (inventory[i] != null ? inventory[i].getName() : "Пусто"));
        }

        System.out.println("\n=== ОТРЯД ===");
        printParty();

        System.out.println("\n=== КВЕСТЫ ===");
        printQuests();
    }

    public void setAttackRange(int range) {
        this.attackRange = range;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean hasActiveQuest(String title) {
        return quests.stream()
                .anyMatch(q -> q.getTitle().equals(title) && !q.isCompleted());
    }

    public Quest getQuest(String title) {
        return quests.stream()
                .filter(q -> q.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public boolean completeQuest(String title) {
        Quest quest = getQuest(title);
        if (quest != null && !quest.isCompleted()) {
            quest.setCompleted(true);
            return true;
        }
        return false;
    }

    public Player deepCopy() {
        Player copy = new Player(this.playerName) {
            @Override
            public String StartAvatar() {
                return Player.this.StartAvatar();
            }

            @Override
            public void someAbstractMethod() {
                Player.this.someAbstractMethod();
            }
        };

        copy.x = this.x;
        copy.y = this.y;
        copy.speed = this.speed;
        copy.health = this.health;
        copy.maxHealth = this.maxHealth;
        copy.armor = this.armor;
        copy.mana = this.mana;
        copy.damage = this.damage;
        copy.actionPoints = this.actionPoints;
        copy.attackRange = this.attackRange;
        copy.cash = this.cash;

        if (this.inventory != null) {
            copy.inventory = new Item[this.inventory.length];
            for (int i = 0; i < this.inventory.length; i++) {
                if (this.inventory[i] != null) {
                    copy.inventory[i] = this.inventory[i].deepCopy();
                }
            }
        }

        copy.helmet = this.helmet != null ? this.helmet.deepCopy() : null;
        copy.chestplate = this.chestplate != null ? this.chestplate.deepCopy() : null;
        copy.leggings = this.leggings != null ? this.leggings.deepCopy() : null;
        copy.boots = this.boots != null ? this.boots.deepCopy() : null;
        copy.weapon = this.weapon != null ? this.weapon.deepCopy() : null;

        if (this.quests != null) {
            copy.quests = this.quests.stream()
                    .map(Quest::deepCopy)
                    .collect(Collectors.toList());
        }

        if (this.party != null) {
            copy.party = this.party.deepCopy();
        }

        return copy;
    }

    public abstract void someAbstractMethod();
}