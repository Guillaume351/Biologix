package Utils.Position;

import com.badlogic.gdx.math.Vector2;

public interface Localisable {
    //Un objet dont on peut connaitre la position
    Vector2 getPosition();

    double distance(Localisable autreLocalisable);
}
