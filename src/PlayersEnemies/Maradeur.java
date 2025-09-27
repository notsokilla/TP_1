package PlayersEnemies;
import Misc.*;
import Events.*;

import java.io.Serializable;

public class Maradeur extends Enemy implements Serializable {
    private static final long serialVersionUID = 1L;
    public Maradeur(int x, int y, int speed, int health, int armor, int damage, int sneak, int kritchance) {
        super(x, y, speed, health, armor, damage, sneak, kritchance, "Мародер. Кат-Лон");
    }


    @Override
    public String Avatar() {
        return """
                             ..   .   ..   ..   ..   ..   ..   ..   .   ..   ..   . ..
                                                     ..:-=-:.                        \s
                                  ..                :-======-:.     ..               \s
                                ..   .   ..   ..  :-===========:  .    .   ..   ..   .
                             ..   .    ..   ..  .-===============:       ..          \s
                             ..   ..   ..   .:======---=====----====:.   ..   ..   . \s
                                .    .     -===========---::-::::--===-.   ..   ..   .
                                           ============---::--::---====.             \s
                             ..   ..   .. .=+==========-=----------==++. ..   .    . \s
                                          ==+==-==---=====------===++*==             \s
                                          ===*##%%%%##*====*##%%%%##*===             \s
                                .    .   .=====-============----=---===-   ..   ..   .
                             ..   ..   .. =+++============-===----==+++= ..   ..   . \s
                                          =++====+===++=======---=-===+=             \s
                                ..   .   .=++++++++++=+==============+++   ..   ..   .
                                         .+++++++++=+++===============++.            \s
                                ..   .   ...-=+++++++++++==+=====-====:.   ..   ..   .
                             ..   ..   ..   ..:-====++++++++======-:..   ..   ..   . \s
                                                 .:-=+++*+++==-:.                    \s
                                .    .    .   ..   ...::--:....   .    .    .   ..   \s
                             ..   ..   ..   ..   ..   .   ..   ..   ..   ..   ..   . \s
                """;
    }

    @Override
    public String StartAvatar() {
        return "";
    }
}