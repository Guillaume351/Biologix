package Environnement.Terrain;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Entites.Ressources.Viande;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Utils.Perlin.PerlinParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Génére un terrain et une population de créatures et ressources
 */
public class TerrainGenerator {

    /**
     * Params utilisés pour la génération du terrain
     */
    private PerlinParams perlinParams;


    /**
     * Le terrain généré
     */
    private Terrain generatedTerrain;

    public TerrainGenerator(PerlinParams perlinParams, int nombreCreaturesInitial) {
        this.perlinParams = perlinParams;
        this.generatedTerrain = this.generateTerrain();
        this.generatedTerrain.getEntites().addAll(creaturesPopulate(nombreCreaturesInitial));
    }

    public Terrain getGeneratedTerrain() {
        return generatedTerrain;
    }

    /**
     * Génère le terrain, lui donne une météo, des populations...
     *
     * @return Le terrain généré
     */
    private Terrain generateTerrain() {
        //TODO : Séparer dans des méthodes avec des paramètres changeable pour rendre la génération modifiable
        Meteo meteo = this.initMeteo();

        AltitudeMap altitudeMap = new AltitudeMap(perlinParams, 0, 1);

        // On génère et ajoute les populations au terra
        //ArrayList<Creature> creatures = creaturesPopulate();

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


    ArrayList[] tableauRessources = new ArrayList[3];  //3 types de resources différents

    /**
     * Génère une population de ressources initiale pour le terrain
     *
     * @return : Une liste de ressources initiale
     */
    private ArrayList[] resourcePopulate(int nbViande) {
        ArrayList<Viande> listeViande = new ArrayList<Viande>();
        for (int i = 0; i<nbViande; i++){
            listeViande.add(new Viande(new Random(), this.generatedTerrain));
        }
        tableauRessources[0].add(listeViande);
        return tableauRessources;
    }
    /**
     * Génère une population de créatures initiale pour le terrain
     *
     * @return : Une liste de créatures initiale
     */
    private ArrayList<Creature> creaturesPopulate(int nombreCreatures) {
        ArrayList<Creature> testCreatures = new ArrayList<Creature>();
        for (int i = 0; i < nombreCreatures; i++) {
            testCreatures.add(new Creature(new Random(), this.generatedTerrain));
        }
        return testCreatures;
    }
}
