package Entites;

import Utils.Position.Localisable;
import com.badlogic.gdx.math.Vector2;

public abstract class Entite implements Localisable {
    Vector2 position;

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
