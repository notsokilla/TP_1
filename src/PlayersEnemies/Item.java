
package PlayersEnemies;
import java.io.Serializable;
import java.util.Objects;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ItemType type;
    private int price;

    public Item(String name, ItemType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public ItemType getType() {
        Objects.requireNonNull(type, "Тип предмета не может быть null");
        return type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    //public ItemType getType() {
    //    return type;
    //}

    public Item deepCopy() {
        // Для конкретных классов-наследников нужно переопределить этот метод
        // Например, для ArmorItem или WeaponItem
        return new Item(this.name, this.type, this.price) {
            @Override
            public void applyEffect(Player player) {
                // Реализация эффекта
            }

            @Override
            public void removeEffect(Player player) {
                // Удаление эффекта
            }
        };
    }

    public abstract void applyEffect(Player player);
    public abstract void removeEffect(Player player);
}