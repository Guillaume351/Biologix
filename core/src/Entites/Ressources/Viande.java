package Entites.Ressources;

import Entites.Creatures.Creature;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Viande extends Ressource {
    double tauxDePourriture; // définit si une viande est pourrie. tauxdePourriture + getquantiteEnergie = EnergieMaxStockable
    boolean pourrie;
    double tempsDepuisPourriture;

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

    @Override
    public double getTaille() {
        return this.quantiteEnergie / ConstantesBiologiques.densiteEnergieRessource;
    }

    /**
     * Constructeur d'un morceau de viande à partir d'une créature morte
     * @param victime
     */
    public Viande(Creature victime) {
        super(victime.getPosition());
        this.terrain = victime.getTerrain();
        this.tauxDePourriture = 0;
        this.quantiteEnergie = victime.getValeurViande();
        this.pourrie = false;
        this.tempsDepuisPourriture = 0;
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
        this.pourrie = false;
        this.tempsDepuisPourriture = 0;
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
        return this.tauxDePourriture >= this.getQuantiteEnergie();
    }

    /**
     * Savoir si une viande est pourrie ou non
     * @return true si elle est pourrie, false sinon
     */
    public String getEtatPourriture() {
        if (this.tauxDePourriture > this.getQuantiteEnergie()) {
            return "Pourri";
        } else if (this.tauxDePourriture > this.getQuantiteEnergie()/2) {
            return "Moyen";
        } else {
            return "Sain";
        }
    }

    /**
     * Connaître la vitesse de pourriture d'un morceau de viande
     * @param temperature
     * @return
     */
    public double getVitessePourriture(double temperature) {
        return ConstantesBiologiques.vitessePourritureMax * Math.exp(-ConstantesBiologiques.stabilitePourriture / temperature);
    }

    /**
     * Fonction pour permettre l'évolution dans le temps d'un morceau de viande
     * @param dt
     */
    public void update(double dt) {

        double temperature = this.terrain.getMeteo().getTemp().getTemp(getPosition().x, getPosition().y, terrain);

        double vitesse = getVitessePourriture(temperature);
        ajouterPourriture(vitesse * dt);
        retirerEnergie(vitesse * dt);
        this.pourrie = estPourrie();
        if (this.pourrie) {
            tempsDepuisPourriture += dt;
        }
        if (tempsDepuisPourriture > ConstantesBiologiques.tempsDispawnPostPourriture) {
            this.getTerrain().retirerEntite(this);
        }
        if (quantiteEnergie <= 0) {
            this.getTerrain().retirerEntite(this);
        }
    }

}
