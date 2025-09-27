
package PlayersEnemies;

import java.io.Serializable;

public class Armor extends Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private int defense;

    public Armor(String name, ItemType type, int defense) {
        super(name, type, 50);
        this.defense = defense;
    }

    @Override
    public void applyEffect(Player player) {
        player.setArmor(player.getArmor() + defense);
    }

    @Override
    public void removeEffect(Player player) {
        player.setArmor(player.getArmor() - defense);
    }

    public int getDefense() {
        return defense;
    }
}