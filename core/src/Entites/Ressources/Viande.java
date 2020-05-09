package Entites.Ressources;

import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;


public class Viande extends Ressource {

    double tauxDePourriture; // définit si une viande est pourrie. tauxdePourriture + getquantiteEnergie = EnergieMaxStockable
    private double energieMaxStockable; // l'énergie pax stockable de la plante,
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
     * Constructeur de l'objet viade
     *
     * @param quantiteEnergie
     */
    public Viande(Vector2 position, double quantiteEnergie, Terrain terrain) {
        super(position);
        this.quantiteEnergie = quantiteEnergie;
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
        return this.tauxDePourriture >= this.energieMaxStockable/2;        //valeur à discuter
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
    public void update(int delta_t) {
        //TODO
    }
}
