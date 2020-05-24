package Entites.Ressources;

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
        this.toleranceTemperature = ConstantesBiologiques.toleranceTemperatureVegetal * r.nextDouble();
        this.temperatureIdeale = ConstantesBiologiques.temperatureIdealeVegetal * r.nextDouble();
        this.croissanceMax = ConstantesBiologiques.croissanceMaxVegetal;
        this.ratioDecroissance = r.nextDouble();
        this.terrain = terrain;
    }

    public Vegetal(Vector2 position, Random r, Terrain terrain, double energie) {
        super(position, energie);
        this.energieMaxStockable = ConstantesBiologiques.energieMaxStockable * r.nextDouble();
        this.toleranceTemperature = ConstantesBiologiques.toleranceTemperatureVegetal * r.nextDouble();
        this.temperatureIdeale = ConstantesBiologiques.temperatureIdealeVegetal * r.nextDouble();
        this.croissanceMax = ConstantesBiologiques.croissanceMaxVegetal;
        this.ratioDecroissance = r.nextDouble();
        this.terrain = terrain;
    }

    @Override
    public double getTaille() {
        return this.quantiteEnergie / ConstantesBiologiques.densiteEnergieArbre;
    }

    /**
     * définir l'énergie maximale stockable par la plante
     * @param energieMax
     */
    public void setEnergieMaxStockable(double energieMax) {
        this.energieMaxStockable = energieMax;
    }


    public double getCroissance(double temperature) {
        return (Math.exp(-Math.pow(temperature - temperatureIdeale, 2) / toleranceTemperature) - ratioDecroissance) * croissanceMax;
    }

    /**
     * Petite fonction pour faire grandir une plante
     * @param dt
     */
    public void update(double dt) {
        double temperature = this.terrain.getMeteo().getTemp().getTemp(getPosition().x, getPosition().y, terrain);
        double croissance = getCroissance(temperature);
        setQuantiteEnergie(Math.max(0, getQuantiteEnergie() + croissance * dt));
        if (quantiteEnergie <= 0) {
            this.getTerrain().retirerEntite(this);
        }
    }


}
