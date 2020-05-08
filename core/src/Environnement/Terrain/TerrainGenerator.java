package Environnement.Terrain;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Utils.Perlin.PerlinParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Génére un terrain et une population de créatures et ressources
 */
public class TerrainGenerator {

    /**
     * Params utilisés pour la génération du terrain
     */
    private PerlinParams perlinParams;

    public TerrainGenerator(PerlinParams perlinParams) {
        this.perlinParams = perlinParams;
    }

    /**
     * Génère le terrain, lui donne une météo, des populations...
     *
     * @return Le terrain généré
     */
    public Terrain generateTerrain() {
        //TODO : Séparer dans des méthodes avec des paramètres changeable pour rendre la génération modifiable
        Meteo meteo = this.initMeteo();

        AltitudeMap altitudeMap = new AltitudeMap(perlinParams, 0, 1);

        // On génère et ajoute les populations au terra
        ArrayList<Ressource> ressources = resourcePopulate();
        ArrayList<Creature> creatures = creaturesPopulate();

        List<Entite> entites = new ArrayList<>();
        //TODO entites.addAll(ressources);
        //TODO entites.addAll(creatures);


        //TODO entites.addAll(creaturesPopulate());

        return new Terrain(meteo, entites, altitudeMap, 9.81, 0.4);
    }

    /**
     * Génère une météo initale pour le monde
     *
     * @return La météo initiale
     */
    public Meteo initMeteo() {
        //TODO : rendre les parametres modifiables.
        //TODO : rendre random la météo iniale
        return new Meteo(new MeteoMap(perlinParams, -10, 30), new MeteoMap(perlinParams, 0, 100), Meteo.TypeMeteo.SOLEIL, 20, 10, 0.4);
    }

    /**
     * Génère une population de ressources initiale pour le terrain
     *
     * @return : Une liste de ressources initiale
     */
    public ArrayList<Ressource> resourcePopulate() {

        return null;
    }

    /**
     * Génère une population de créatures initiale pour le terrain
     *
     * @return : Une liste de créatures initiale
     */
    public ArrayList<Creature> creaturesPopulate() {
        return null;
    }
}
