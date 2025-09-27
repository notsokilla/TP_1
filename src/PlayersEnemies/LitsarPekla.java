package PlayersEnemies;
import Misc.*;
import Events.*;

import java.io.Serializable;

public class LitsarPekla extends Enemy implements Serializable {
    private static final long serialVersionUID = 1L;
    public LitsarPekla(int x, int y, int speed, int health, int armor, int damage, int sneak, int kritchance) {
        super(x, y, speed, health, armor, damage, sneak, kritchance, "Странствующий Рыцарь. Топфхельм");
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