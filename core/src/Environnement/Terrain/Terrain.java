package Environnement.Terrain;

import Entites.Entite;
import Environnement.ConstantesBiologiques;
import Environnement.Meteo.Meteo;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class Terrain {
    Meteo meteo;
    List<Entite> entites;
    AltitudeMap altitudes;
    double gravite;
    double pourcentageEau;
    ConstantesBiologiques constantesBio;

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

    /**
     * renvoie si une entite est dans l'eau ou non.
     * @param entite
     * @param altitudes la carte d'altitude sur laquelle on joue
     * @return vrai si on se situe sous l'eau, faux sinon
     */
    public boolean estDansEau(Entite entite, AltitudeMap altitudes) {

        boolean estDansEau;
        Vector2 position = entite.getPosition();
        double altitude = altitudes.getValeur(position.x,position.y);
        double niveauMer = altitudes.hauteurMer(pourcentageEau);

        if (altitude>= niveauMer) {
            estDansEau = false;
        } else {
            estDansEau = true;
        }
        return estDansEau;
    }
}
