package Entites.Ressources;

public class Vegetal extends Ressource {
    @Override
    public double getQuantiteEnergie() {
        return super.getQuantiteEnergie();
    }

    @Override
    public String getTypeRessource() {
        return "Végetal";
    }

    @Override
    public void setQuantiteEnergie(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
    }

    /**
     * créer un végétal
     */
    public void creerVégétal() {
        setQuantiteEnergie(0);
    }
}
