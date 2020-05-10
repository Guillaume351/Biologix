package Entites.Ressources;

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

    /**
     * La viande perd de l'énergie dans le temps
     * @param retrait
     */
    void retirerEnergie(double retrait) {
        this.quantiteEnergie = quantiteEnergie - retrait;
    }

    /**
     * Constructeur de l'objet viande
     *
     * @param r, terrain
     */
    public Viande(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        this.quantiteEnergie = ConstantesBiologiques.energieMaxStockableViande *r.nextDouble();
        this.tauxDePourriture = 0; //pas pourrie au début
        this.terrain = terrain;
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
        return this.tauxDePourriture >= ConstantesBiologiques.energieMaxStockableViande/2;        //valeur à discuter
    }

    /**
     * La viande pourrit dans le temps... son énergie se transforme en pourriture
     */
    void evoluer(double dt) {
        double pourriture = 0.1;
        double coefficient;
        double temperature = meteo.getTemp(getPosition().x, getPosition().y);

        //on set le coefficient en fonction de la température extérieure
        if (temperature < ConstantesBiologiques.tempExterieureIntermediaire && temperature > ConstantesBiologiques.tempExterieureMin) {
            coefficient = ConstantesBiologiques.coefficientMoyen;
        } else if (temperature > ConstantesBiologiques.tempExterieureIntermediaire) {
            coefficient = ConstantesBiologiques.coefficientFort;
        } else {
            coefficient = ConstantesBiologiques.coefficientFaible;//trop froid
        }

        ajouterPourriture(coefficient * pourriture * dt);
        retirerEnergie(coefficient * pourriture * dt);
    }

    @Override
    public void update(double delta_t) {
        //TODO
    }
}
