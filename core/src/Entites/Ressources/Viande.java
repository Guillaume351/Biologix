package Entites.Ressources;

import Entites.Creatures.Creature;
import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Viande extends Ressource {
    double tauxDePourriture; // définit si une viande est pourrie. tauxdePourriture + getquantiteEnergie = EnergieMaxStockable
    MeteoMap meteo;

    @Override
    public double getQuantiteEnergie() {
        return super.getQuantiteEnergie();
    }

    @Override
    public String getTypeRessource() {
        return "Viande";
    }

    @Override
    public void setQuantiteEnergie(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
    }

    public Viande(Random r, Creature victime) {
        super(victime.getPosition());
        this.terrain = victime.getTerrain();
        this.tauxDePourriture = 0;
        this.quantiteEnergie = victime.getValeurViande();
    }

    /**
     * Constructeur de l'objet viande
     *
     * @param r, terrain
     */
    public Viande(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        this.quantiteEnergie = ConstantesBiologiques.energieMaxStockableViande * r.nextDouble();
        this.tauxDePourriture = 0; //pas pourrie au début
        this.terrain = terrain;
    }

    /**
     * La viande perd de l'énergie dans le temps
     * @param retrait
     */
    boolean retirerEnergie(double retrait) {
        if (retrait < quantiteEnergie) {
            this.quantiteEnergie = quantiteEnergie - retrait;
            return true;
        } else {
            this.quantiteEnergie = 0;
            return false;
        }
    }


    void setTauxDePourriture(double tauxDePourriture) {
        this.tauxDePourriture = tauxDePourriture;
    }

    /**
     * Ajouter de la pourriture
     * @param ajout
     */
    void ajouterPourriture(double ajout) {
        this.tauxDePourriture = tauxDePourriture + ajout;
    }

    /**
     * Savoir si une viande est pourrie ou non
     * @return true si elle est pourrie, false sinon
     */
    public boolean estPourrie() {
        return this.tauxDePourriture > getQuantiteEnergie();
    }

    public double getVitessePourriture(double temperature) {
        return ConstantesBiologiques.vitessePourritureMax * Math.exp(-ConstantesBiologiques.stabilitePourriture / temperature);
    }

    /**
     * La viande pourrit dans le temps... son énergie se transforme en pourriture
     */
    public void update(double dt) {

        double temperature = meteo.getTemp(getPosition().x, getPosition().y, terrain);

        double vitesse = getVitessePourriture(temperature);
        ajouterPourriture(vitesse * dt);
        retirerEnergie(vitesse * dt);
    }

}
