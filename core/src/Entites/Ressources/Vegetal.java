package Entites.Ressources;
import java.util.*;
import Environnement.Meteo.*;

public class Vegetal extends Ressource {

    double energieMaxStockable; // l'énergie maximale stockable par la plante
    double energieVegetal; //l'énergie propre à la plante, dont elle a besoin pour grandir etc. energieVegetal = 0 <=> plante = dead. energieVegetal = 1 <=> plante en pleine forme

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
     * @param energieDepensee
     * @param energieGagnee
     */
    public void grandir(double energieDepensee, double energieGagnee) {
        if ((getQuantiteEnergie() + energieGagnee) <= this.energieMaxStockable) {
            this.setQuantiteEnergie((getQuantiteEnergie() + energieGagnee));    // la plante gagne en ressource
        } else {
            this.setQuantiteEnergie(energieMaxStockable);
        }
        if ((this.energieVegetal - energieDepensee) >= 0) {
            setEnergieVegetal(this.energieVegetal - energieDepensee);       // la plante dépense de l'énergie en grandissant
        } else {
            setEnergieVegetal(0);       // si elle en dépense trop, elle meurt
        }
    }
}
