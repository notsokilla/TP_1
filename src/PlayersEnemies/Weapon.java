package PlayersEnemies;

import java.io.Serializable;

public class Weapon extends Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private int damage;

    public Weapon(String name, int damage) {
        super(name, ItemType.WEAPON, 50);
        this.damage = damage;
    }

    @Override
    public void applyEffect(Player player) {
        player.setDamage(player.getDamage() + damage);
    }

    @Override
    public void removeEffect(Player player) {
        player.setDamage(player.getDamage() - damage);
    }

    public int getAdditionalDamage() {
        return damage;
    }
}