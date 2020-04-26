package Environnement.Terrain;

import Entites.Entite;
import Environnement.ConstantesBiologiques;
import Environnement.Meteo.Meteo;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class Terrain {
    private Meteo meteo;
    private List<Entite> entites;
    private AltitudeMap altitudes;
    private double gravite;
    private double pourcentageEau;
    private ConstantesBiologiques constantesBio;

    public Terrain(Meteo meteo, List<Entite> entites, AltitudeMap altitudes, double gravite, double pourcentageEau, ConstantesBiologiques constantesBio) {
        this.meteo = meteo;
        this.entites = entites;
        this.altitudes = altitudes;
        this.gravite = gravite;
        this.pourcentageEau = pourcentageEau;
        this.constantesBio = constantesBio;
    }

    /**
     * Renvoie la meteo de la carte
     */
    public Meteo getMeteo() {
        return meteo;
    }

    /**
     * Permet de modifier la meteo du terrain
     * @param meteo la nouvelle meteo
     */
    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    /**
     * Obtenir la liste des entitées presente sur le terrain.
     * @return la liste des entités
     */
    public List<Entite> getEntites() {
        return entites;
    }

    /**
     * Modifier les entités presentes sur le terrain.
     * @param entites nouvelles entitées
     */
    public void setEntites(List<Entite> entites) {
        this.entites = entites;
    }

    /**
     * Obtenir la carte d'altitude du terrain.
     * @return la carte d'altitude
     */
    public AltitudeMap getAltitudes() {
        return altitudes;
    }

    /**
     * Redefinir la carte d'altitude.
     * @param altitudes la nouvelle carte d'altitude
     */
    public void setAltitudes(AltitudeMap altitudes) {
        this.altitudes = altitudes;
    }

    /**
     * Obtenir la gravité associée au terrain
     * @return g du terrain
     */
    public double getGravite() {
        return gravite;
    }

    /**
     * Modifier la constante de gravité d'un terrain.
     * @param gravite nouvelle gravité du terrain
     */
    public void setGravite(double gravite) {
        this.gravite = gravite;
    }

    /**
     * renvoie si une entite est dans l'eau ou non.
     * @param entite l'entite dont on veut savoir si elle est immergé
     * @param altitudes la carte d'altitude sur laquelle on joue
     * @return vrai si on se situe sous l'eau, faux sinon
     */
    public boolean estDansEau(Entite entite, AltitudeMap altitudes) {

        boolean estDansEau;
        Vector2 position = entite.getPosition();
        double altitude = altitudes.getValeur(position.x,position.y);
        double niveauMer = altitudes.hauteurMer(pourcentageEau);

        estDansEau = !(altitude >= niveauMer);
        return estDansEau;
    }
}
