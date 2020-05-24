package Environnement.Terrain;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Arbre;
import Entites.Ressources.Fruit;
import Entites.Ressources.Ressource;
import Entites.Ressources.Viande;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Utils.Perlin.PerlinParams;

import java.awt.*;
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

    public TerrainGenerator(PerlinParams perlinParams, int nombreCreaturesInitial, int nbViandeInitial, int nbFruitInitial, int nbArbreInitial, Random random) {
        this.perlinParams = perlinParams;
        this.generatedTerrain = this.generateTerrain(300); //TODO : remplacer le 300
        this.generatedTerrain.getEntites().addAll(creaturesPopulate(nombreCreaturesInitial, random));
        //TODO : remplacer 20 par un param
        this.generatedTerrain.getEntites().addAll(resourcePopulate(nbViandeInitial, nbFruitInitial, nbArbreInitial, random));
    }

    public Terrain getGeneratedTerrain() {
        return generatedTerrain;
    }

    /**
     * Génère le terrain, lui donne une météo, des populations...
     *
     * @return Le terrain généré
     */
    private Terrain generateTerrain(int taille) {
        //TODO : Séparer dans des méthodes avec des paramètres changeable pour rendre la génération modifiable
        Meteo meteo = this.initMeteo();

        AltitudeMap altitudeMap = new AltitudeMap(perlinParams, 0, 1);

        // On génère et ajoute les populations au terra
        //ArrayList<Creature> creatures = creaturesPopulate();

        List<Entite> entites = new ArrayList<>();

        return new Terrain(meteo, entites, altitudeMap, 9.81, 0.4, taille);
    }

    /**
     * Génère une météo initale pour le monde
     *
     * @return La météo initiale
     */
    public Meteo initMeteo() {
        //TODO : rendre les parametres modifiables.
        //TODO : rendre random la météo iniale
        return new Meteo(new MeteoMap(perlinParams, 1, 30), new MeteoMap(perlinParams, 1, 100), Meteo.TypeMeteo.SOLEIL, 100, 100, 0.4);
    }


    ArrayList[] tableauRessources = new ArrayList[3];  //3 types de resources différents

    /**
     * Génère une population de ressources initiale pour le terrain
     *
     * @return : Une liste de ressources initiale
     */
    private ArrayList<Ressource> resourcePopulate(int nbViande, int nbFruit, int nbArbre, Random random) {
        Color[] couleurs = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
        ArrayList<Ressource> listeRessources = new ArrayList<Ressource>();
        for (int i = 0; i < nbViande; i++) {
            listeRessources.add(new Viande(random, this.generatedTerrain));
        }
        for (int j = 0; j < nbFruit; j++) {
            int nombreAleatoire = (int)(Math.random() * couleurs.length);
            listeRessources.add(new Fruit(random, couleurs[nombreAleatoire], false, this.generatedTerrain, true));
        }
        for (int i = 0; i < nbArbre; i++) {
            listeRessources.add(new Arbre(random, this.generatedTerrain));
        }
        return listeRessources;
    }

    /**
     * Génère une population de créatures initiale pour le terrain
     *
     * @return : Une liste de créatures initiale
     */
    private ArrayList<Creature> creaturesPopulate(int nombreCreatures, Random random) {
        ArrayList<Creature> testCreatures = new ArrayList<Creature>();
        for (int i = 0; i < nombreCreatures; i++) {
            testCreatures.add(new Creature(random, this.generatedTerrain));
        }
        return testCreatures;
    }
}
