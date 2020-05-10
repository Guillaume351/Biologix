package Entites.Ressources;

import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Vegetal extends Ressource {

    private double croissanceMax;
    private double ratioDecroissance; //Entre 0 et 1
    private double toleranceTemperature;
    private double temperatureIdeale;
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
     *
     */
    public Vegetal(Vector2 position, Random r, Terrain terrain) {
        super(position);
        this.energieMaxStockable = ConstantesBiologiques.energieMaxStockable * r.nextDouble();
        this.energieVegetal = this.energieMaxStockable * r.nextDouble();
        this.toleranceTemperature = ConstantesBiologiques.toleranceTemperatureVegetal * 2*r.nextDouble();
        this.temperatureIdeale = ConstantesBiologiques.temperatureIdealeVegetal * 2*r.nextDouble();
        this.croissanceMax = ConstantesBiologiques.croissanceMaxVegetal;
        this.ratioDecroissance = r.nextDouble();

        this.terrain = terrain;
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

    public double getCroissance(double temperature) {
        return (Math.exp(-Math.pow(temperature - temperatureIdeale, 2) / toleranceTemperature) - ratioDecroissance) * croissanceMax;
    }

    /**
     * Petite fonction pour faire grandir une plante
     * @param dt
     */
    public void update(double dt) {
        double temperature = meteo.getTemp(getPosition().x, getPosition().y, terrain);
        setQuantiteEnergie(Math.max(0, getQuantiteEnergie() + getCroissance(temperature) * dt));
    }

    /**
     * est-ce que la plante est morte ??
     *
     * @return
     */
    public boolean estMorte() {
        return (this.energieVegetal == 0);
    }

}
