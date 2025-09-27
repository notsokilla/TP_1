package PlayersEnemies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Party implements Serializable {
    private static final long serialVersionUID = 1L;
    private static List<Recruit> recruits;
    private static final int MAX_PARTY_SIZE = 3;

    public Party() {
        recruits = new ArrayList<>();
    }

    public boolean addRecruit(Recruit recruit) {
        if (recruits.size() < MAX_PARTY_SIZE) {
            recruits.add(recruit);
            recruit.setInParty(true);
            return true;
        }
        return false;
    }

    public void removeRecruit(Recruit recruit) {
        recruits.remove(recruit);
        recruit.setInParty(false);
    }

    public static List<Recruit> getRecruits() {
        return new ArrayList<>(recruits); // Возвращаем копию списка
    }

    public Party deepCopy() {
        Party copy = new Party();
        for (Recruit recruit : this.recruits) {
            copy.addRecruit(recruit.deepCopy());
        }
        return copy;
    }

    public void printParty() {
        if (recruits.isEmpty()) {
            System.out.println("Ваш отряд пуст.");
        } else {
            System.out.println("Ваш отряд:");
            for (Recruit recruit : recruits) {
                System.out.println(recruit);
            }
        }
    }
}