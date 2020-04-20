package Entites.Ressources;

public class Viande extends Ressource {
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
     * Savoir si une viande est pourrie ou non
     * @return true si elle est pourrie, false sinon
     */
    public boolean estPourrie() {
        return this.quantiteEnergie <= 0;
    }
}
