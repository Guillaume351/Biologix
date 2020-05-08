package Entites.Ressources;
import java.util.*;
import Environnement.Meteo.*;

public class Vegetal extends Ressource {

    private double energieMaxStockable; // l'énergie maximale stockable par la plante
    private double energieVegetal; //l'énergie propre à la plante, dont elle a besoin pour grandir etc. energieVegetal = 0 <=> plante = dead. energieVegetal = 1 <=> plante en pleine forme
    MeteoMap meteo;

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
        if (quantiteEnergie < this.energieMaxStockable) {
            this.quantiteEnergie = quantiteEnergie;
        } else { this.quantiteEnergie = this.energieMaxStockable; }
    }

    /**
     * Constructeur du Végétal, par défaut non empoisonné
     * @param energieMaxStockable
     * @param energieVegetal
     */
    public Vegetal(double energieMaxStockable, double energieVegetal) {
        this.energieVegetal = energieVegetal;
        this.energieMaxStockable = energieMaxStockable;
    }

    /**
     * définir l'énergie maximale stockable par la plante
     * @param energieMax
     */
    public void setEnergieMaxStockable(double energieMax) {
        this.energieMaxStockable = energieMax;
    }

    public double getEnergeVegetal() {return this.energieVegetal;}

    public void setEnergieVegetal(double energieVegetal) {
        this.energieVegetal = energieVegetal;
    }

    /** Ce qu'il se passe lorsque la pauvre plante se fait bouffer
     *
     * @param quantiteIngeree, la quantité d'énergie ingérée par une créature
     */
    public void estMange(double quantiteIngeree) {
        if (getQuantiteEnergie() >= quantiteIngeree) {
            setQuantiteEnergie(getQuantiteEnergie() - quantiteIngeree);
        } else {
            setQuantiteEnergie(0);
        }
    }

    /**
     * Petite fonction pour faire grandir une plante
     */
    public void grandir() {
        double energieGagnee = 0.1;
        double energieDepensee = 0.1;
        double coefficientGagne;             // qui va limiter la croissance d'une plante quand il fait froid par exemple
        double coefficientDepense;
        double temperature = meteo.getTemp(getPosition().x, getPosition().y);

        //on set le coefficient en fonction de la température extérieure
        if (temperature < 10 && temperature > 0) {
            coefficientGagne = 0.01; // la plante grandit lentement
            coefficientDepense = 1; // la plante dépense bcp d'énergie pour grandir
        } else if (temperature > 10) {
            coefficientGagne = 1;
            coefficientDepense = 0.1; // easy peasy
        } else { coefficientGagne = 0;//trop froid
            coefficientDepense = 2; // aie aie aie la plante va pourrir
        }

        // Energie gagnée
        if ((getQuantiteEnergie() + coefficientGagne*energieGagnee) <= this.energieMaxStockable) {
            this.setQuantiteEnergie((getQuantiteEnergie() + coefficientGagne*energieGagnee));    // la plante gagne en ressource
        } else {
            this.setQuantiteEnergie(energieMaxStockable);
        }

        // Energie dépensée
        if ((this.energieVegetal - coefficientDepense*energieDepensee) >= 0) {
            setEnergieVegetal(this.energieVegetal - coefficientDepense*energieDepensee);       // la plante dépense de l'énergie en grandissant
        } else {
            setEnergieVegetal(0);       // si elle en dépense trop, elle meurt
        }
    }

    /**
     * est-ce que la plante est morte ??
     * @return
     */
    public boolean estMorte() {
        return (this.energieVegetal == 0);
    }
}
