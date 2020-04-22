package Entites.Ressources;

public class Viande extends Ressource {

    double tauxDePourriture; // définit si une viande est pourrie, 0 = clean, 1 = pourrie

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
     * Savoir si une viande est pourrie ou non
     * @return true si elle est pourrie, false sinon
     */
    public boolean estPourrie() {
        return this.tauxDePourriture <= 0.5;        //valeur à discuter
    }
}
