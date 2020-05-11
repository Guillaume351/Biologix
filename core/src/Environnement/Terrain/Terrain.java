package Environnement.Terrain;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Environnement.Meteo.Meteo;
import Utils.Updatable;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;


public class Terrain implements Updatable {
    double temps = 0;

    public double getTemps() {
        return temps;
    }
    private Meteo meteo;
    private List<Entite> entites;
    private AltitudeMap altitudes;
    private double gravite;
    private double pourcentageEau;


    /**
     * Taille du terrain. Correspond au nombre de tile (tileset carré)
     */
    private int taille;


    public Terrain(Meteo meteo, List<Entite> entites, AltitudeMap altitudes, double gravite, double pourcentageEau, int taille) {
        this.meteo = meteo;
        this.entites = entites;
        this.altitudes = altitudes;
        this.gravite = gravite;
        this.pourcentageEau = pourcentageEau;
        this.taille = taille;
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
     *
     * @return la liste des entités
     */
    public List<Entite> getEntites() {
        return entites;
    }


    /**
     * Obtenir la liste des créatures presente sur le terrain.
     *
     * @return la liste des créatures
     */
    public ArrayList<Creature> getCreatures() {
        ArrayList<Creature> creatures = new ArrayList<Creature>();

        for (Entite e : this.getEntites()) {
            if (e instanceof Creature) {
                creatures.add((Creature) e);
            }
        }

        return creatures;
    }


    /**
     * Obtenir la liste des ressources presente sur le terrain.
     *
     * @return la liste des ressources
     */
    public ArrayList<Ressource> getRessources() {
        ArrayList<Ressource> ressources = new ArrayList<Ressource>();

        for (Entite e : this.getEntites()) {
            if (e instanceof Ressource) {
                ressources.add((Ressource) e);
            }
        }

        return ressources;
    }

    /**
     * Récupérer la taille de terrain
     *
     * @return La taille du terrain, en nombre de tile par côté
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Modifier les entités presentes sur le terrain.
     *
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
     * Renvoie si une entite est dans l'eau ou non.
     *
     * @param entite    L'entite dont on veut savoir si elle est immergé
     * @return Vrai si on se situe sous l'eau, faux sinon
     */
    public boolean estDansEau(Entite entite) {

        boolean estDansEau;
        Vector2 position = entite.getPosition();
        double altitude = this.altitudes.getValeur(position.x, position.y);
        double niveauMer = this.altitudes.hauteurMer(pourcentageEau);

        estDansEau = !(altitude >= niveauMer);
        return estDansEau;
    }

    public double getPourcentageEau() {
        return pourcentageEau;
    }

    @Override
    public void update(double delta_t) {
        //TODO
        temps += delta_t;
    }

    static int DIV_ANG = 5;
    static float RMAX = 200;
    static int DIV_R = 5;

    public Vector2 vectPointeurChgtMilieu(Creature creature) {
        boolean local = estDansEau(creature);
        boolean recherche = local;
        int nAng = 0;
        int nRay = 1;
        double r = 0;
        double teta = 0;
        double niveauMer = this.altitudes.hauteurMer(pourcentageEau);
        while (local == recherche && nRay < DIV_R) {
            nAng++;
            if (nAng > DIV_ANG) {
                nAng = 0;
                nRay++;
            }
            r = (RMAX / DIV_R) * (double) nRay;
            teta = (2.0 * Math.PI / DIV_ANG) * (double) nAng;
            double altitude = this.altitudes.getValeur(creature.getPosition().x + r * Math.cos(teta), creature.getPosition().y + r * Math.sin(teta));
            recherche = !(altitude >= niveauMer);
        }
        if (recherche != local) {
            return new Vector2((float) Math.cos(teta), (float) Math.sin(teta));
        } else {
            return new Vector2(0, 0);
        }
    }


}
