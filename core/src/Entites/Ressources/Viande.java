package Entites.Ressources;
import Environnement.Meteo.*;


public class Viande extends Ressource {

    double tauxDePourriture; // définit si une viande est pourrie, 0 = clean, 1 = pourrie
    Meteo meteo;

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
     * @param quantiteEnergie
     */
    public Viande(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
        this.tauxDePourriture = 0; //pas pourrie au début
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
    void evoluer() {
        double pourriture = 0.1;
        double coefficient;
        double temperature = 3; //pareil que pour les plantes

        //on set le coefficient en fonction de la température extérieure
        if (temperature < 10 && temperature > 0) {
            coefficient = 1;
        } else if (temperature > 10) {
            coefficient= 2;
        } else {
            coefficient = 0.1;//trop froid
        }

        ajouterPourriture(coefficient*pourriture);
            retirerEnergie(coefficient*pourriture);
    }
}
