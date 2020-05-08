package Entites.Ressources;

import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;


public class Viande extends Ressource {

    double tauxDePourriture; // définit si une viande est pourrie, 0 = clean, 1 = pourrie
    private double tauxDePourritureMax;
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
        return this.tauxDePourriture >= 0.5;        //valeur à discuter
    }

    /**
     * La viande pourrit dans le temps... et perd donc de l'énergie
     */
    void evoluer(double dt) {
        double pourriture = 0.1;
        double coefficient;
        double temperature = meteo.getTemp(getPosition().x, getPosition().y);

        //on set le coefficient en fonction de la température extérieure
        if (temperature < 10 && temperature > 0) {
            coefficient = 1;
        } else if (temperature > 10) {
            coefficient = 2;
        } else {
            coefficient = 0.1;//trop froid
        }

        ajouterPourriture(coefficient * pourriture * dt);
        retirerEnergie(coefficient * pourriture * dt);
    }

    @Override
    public void update(int delta_t) {
        //TODO
    }
}
