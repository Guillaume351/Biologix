package Entites;

import Utils.Position.Localisable;
import Utils.Updatable;
import com.badlogic.gdx.math.Vector2;

public abstract class Entite implements Localisable, Updatable {
    Vector2 position;

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public double distance(Localisable autreLocalisable){
        double diffX = this.position.x - autreLocalisable.getPosition().x;
        double diffY = this.position.y - autreLocalisable.getPosition().y;
        return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }
}
