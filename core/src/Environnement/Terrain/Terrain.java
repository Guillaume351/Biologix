package Environnement.Terrain;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Environnement.Meteo.Meteo;
import Utils.ConstantesBiologiques;
import Utils.Stats.Statisticien;
import Utils.Updatable;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;


public class Terrain implements Updatable {
    double temps = 0;
    static int DIV_ANG = 20;
    static float RMAX = 300f;
    static int DIV_R = 40;

    public double getTemps() {
        return temps;
    }
    private Meteo meteo;
    private List<Entite> entites;
    int subdivX = (int) (3 * ConstantesBiologiques.XMAX);

    private AltitudeMap altitudes;
    private double gravite;
    private double pourcentageEau;


    Statisticien statisticien;

    /**
     * Taille du terrain. Correspond au nombre de tile (tileset carré)
     */
    private int taille;
    int subdivY = (int) (3 * ConstantesBiologiques.YMAX);

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

    TerrainInfo[][] quadrillage;

    public Terrain(Meteo meteo, List<Entite> entites, AltitudeMap altitudes, double gravite, double pourcentageEau, int taille) {
        this.meteo = meteo;
        this.entites = entites;
        this.altitudes = altitudes;
        this.gravite = gravite;
        this.pourcentageEau = pourcentageEau;
        this.taille = taille;
        this.statisticien = new Statisticien();
        genererQuadrillage();
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
        statisticien.collecter(entites);
    }

    public TerrainInfo getTerrainLocal(double x, double y) {
        int X = (int) (Math.min(1, Math.max(0, x / ConstantesBiologiques.XMAX)) * (subdivX - 1));
        int Y = (int) (Math.min(1, Math.max(0, y / ConstantesBiologiques.YMAX)) * (subdivY - 1));
        return quadrillage[X][Y];
    }

    public void genererQuadrillage() {
        this.quadrillage = new TerrainInfo[subdivX][subdivY];
        for (int i = 0; i < subdivX; i++) {
            for (int j = 0; j < subdivY; j++) {
                double x = (i / (double) (subdivX - 1)) * ConstantesBiologiques.XMAX;
                double y = (j / (double) (subdivY - 1)) * ConstantesBiologiques.YMAX;
                this.quadrillage[i][j] = new TerrainInfo(this.getAltitudes().getValeur(new Vector2((float) x, (float) y)));
            }
        }
    }

    /**
     * Obtenir la carte d'altitude du terrain.
     *
     * @return la carte d'altitude
     */
    public double getAltitude(Vector2 position) {
        return getTerrainLocal(position.x, position.y).getAltitude();
    }

    public Vector2 vectPointeurChgtMilieu(Creature creature) {
        TerrainInfo ti = getTerrainLocal(creature.getPosition().x, creature.getPosition().y);
        Vector2 vect = ti.getChgtMilieu();
        if (vect == null) {
            vect = calcVectPointeurChgtMilieu(creature);
            ti.setChgtMilieu(vect);
        }

        return new Vector2(vect);
    }

    public Vector2 calcVectPointeurChgtMilieu(Creature creature) {
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

    public boolean LigneDeVue(Vector2 A, Vector2 B, double delta, double h0) {
        //delta : taille d'un pas
        //h0 : elevation de la ligne de vue
        int nbPas = (int) (A.dst(B) / delta);
        //Altitude en a
        double h1 = getAltitude(A);
        //Altitude en b
        double h2 = getAltitude(B);
        //resultat
        boolean ok = true;
        //parcourir le trajet
        for (int i = 0; i < nbPas; i++) {
            float k = (float) i / (float) nbPas;
            //Altitude du terrain au point du parcours
            double hloc = getAltitude(new Vector2(A).lerp(B, k));
            //Altitude de la ligne de vue en ce point du parcours
            double hview = (1.0 - k) * h1 + k * h2;
            if (hview + h0 < hloc) {
                //Si la ligne de vue passe sous le terrain
                ok = false;
                break;
            }
        }
        return ok;
    }


}
