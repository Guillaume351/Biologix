package Entites.Creatures;

import Entites.Entite;
import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;

public class Creature extends Entite {
    public Vector2 orientation;
    double vitesse;
    Cerveau cerveau;

    public double getMasse() {
        return 0;
    }

    public double deplacer(double dt, Terrain terrain) {
        //deplace la creature et renvoie l'energie dépensee
        double z0 = terrain.getAltitudes().getValeur(getPosition());
        getPosition().add(orientation.scl((float) (dt * vitesse)));
        double z1 = terrain.getAltitudes().getValeur(getPosition());
        return (z1 - z0) * terrain.getGravite() * getMasse();
    }
}
