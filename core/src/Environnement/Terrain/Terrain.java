package Environnement.Terrain;

import Entites.Entite;
import Environnement.Meteo.Meteo;

import java.util.List;

public class Terrain {
    Meteo meteo;
    List<Entite> entites;
    AltitudeMap altitudes;
    double gravite;

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    public List<Entite> getEntites() {
        return entites;
    }

    public void setEntites(List<Entite> entites) {
        this.entites = entites;
    }

    public AltitudeMap getAltitudes() {
        return altitudes;
    }

    public void setAltitudes(AltitudeMap altitudes) {
        this.altitudes = altitudes;
    }

    public double getGravite() {
        return gravite;
    }

    public void setGravite(double gravite) {
        this.gravite = gravite;
    }
}
