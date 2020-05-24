package Utils;

import Entites.Creatures.Creature;
import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.*;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.Perlin.PerlinParams;
import Utils.Stats.StatRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CustomInputProcessor;

import java.text.DecimalFormat;
import java.util.Random;

import static Environnement.Terrain.TerrainRenderer.TILE_SIZE;
import static com.badlogic.gdx.Gdx.input;

public class EcranSimulation implements Screen {

    Viewport viewport;
    SpriteBatch batch;

    /**
     * Batch d'affichage des options de simulation
     */
    SpriteBatch optionsUI;

    /**
     * Batch d'affichage des statistiques
     */
    SpriteBatch creatureStatsUI;

    /**
     * Batch pour l'affichage des caractéristiques (Temp, humidité) de la carte
     */
    SpriteBatch carteStatsUI;

    /**
     * Entitée pour laquelle on affiche les statistiques
     */
    Entite entiteSelectionne;

    /**
     * Le terrain de la game
     */
    Terrain gameWorld;
    TiledMap map;
    MapRenderer mapRenderer;
    private OrthographicCamera camera;

    CreatureRenderer creatureRenderer;
    RessourceRenderer ressourceRenderer;
    StatRenderer statRenderer;

    private int nbRendus;

    /**
     * Police d'écriture
     */
    BitmapFont font = new BitmapFont();

    /**
     * Mode pause
     */
    private  boolean estPause;

    /**
     * Fin de la simulation
     */
    private boolean finSimulation;

    public EcranSimulation(){
        this(100, 25);
    }

    public EcranSimulation(int nombreCreatures, int nombreRessources) {

        // Nos parametres de génération de map pour le test
        int seed = new Random().nextInt(10000000);
        Random random = new Random(seed);
        PerlinParams perlinParams = new PerlinParams(3, 0.01, 0.5, seed, 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams, nombreCreatures, nombreRessources, nombreRessources, nombreRessources, random);

        // On génère le terrain
        this.gameWorld = generator.getGeneratedTerrain();

        // On créer notre outil de rendu de terrain
        TerrainRenderer renderTerrain = new TerrainRenderer(this.gameWorld);
        this.camera = new OrthographicCamera();

        // On récupère le terrain convertit en TileSet
        this.map = renderTerrain.getMap();

        // Les infos concernant notre tile.
        int mapWidthInTiles = TILE_SIZE;
        int mapHeightInTiles = TILE_SIZE;
        int mapWidthInPixels = mapWidthInTiles * renderTerrain.getTaille();
        int mapHeightInPixels = mapHeightInTiles * renderTerrain.getTaille();


        // Les paramètres de notre caméras. TODO : faire davantage de tests pour obtenir une vue plus éloignée
        camera = new OrthographicCamera(800.f, 480.f);


        viewport = new FillViewport(800, 480, camera);


        camera.position.x = mapWidthInPixels * .5f;
        camera.position.y = mapHeightInPixels * .35f;
        camera.zoom = 16f;
        mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1f);
        mapRenderer.setView(camera);


        // Initilialisation de l'afficheur des stats de créature
        this.creatureStatsUI = new SpriteBatch();

        // Initialisation du booleen qui indique si la simulation est en pause
        this.estPause = false;

        // Initialisation du booleen qui indique si la simulation est terminée
        this.finSimulation = false;

        // Initialisation de l'afficheur des stats de la carte
        this.carteStatsUI = new SpriteBatch();

        // Initialisation de l'afficheur des options
        this.optionsUI = new SpriteBatch();

        // Test créature & ressource
        batch = new SpriteBatch();
        // On projette sur la caméra
        batch.setProjectionMatrix(camera.combined);
        creatureRenderer = new CreatureRenderer(this.gameWorld.getCreatures(), batch);
        ressourceRenderer = new RessourceRenderer(this.gameWorld.getRessources(), batch);
        statRenderer = new StatRenderer(this.gameWorld.getStatisticien(), batch);

        nbRendus = 0;
    }

    @Override
    public void show() {
        input.setInputProcessor(new CustomInputProcessor(camera));

    }

    public boolean getFinSimulation(){
        return this.finSimulation;
    }


    private void gestionCamera() {
        // Gestion du déplacement de la caméra. TODO: a déplacer
        if (input.isButtonPressed(Input.Buttons.LEFT)) {
            if (input.getDeltaX() != 0 || input.getDeltaY() != 0) {
                camera.position.x -= input.getDeltaX() * camera.zoom;
                camera.position.y += input.getDeltaY() * camera.zoom;
            }
        }

        if (input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (!(input.getDeltaX() != 0 || input.getDeltaY() != 0)) {

                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);

                for (Entite entite : this.gameWorld.getEntites()) {
                    double facteur = (ConstantesBiologiques.PixelsParCoord / 2);
                    if (entite instanceof Creature) {
                        facteur *= ((Creature) entite).getTaille();
                    }
                    //if(entite instanceof Ressource){
                    //   facteur
                    //}

                    if (entite.getPosition().dst2(new Vector2((float) (touchPos.x - facteur) / (float) ConstantesBiologiques.PixelsParCoord, (float) (touchPos.y - facteur) / (float) ConstantesBiologiques.PixelsParCoord)) < 4) {
                        this.entiteSelectionne = entite;
                    }
                }
            }

        }
        if (input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.estPause = !this.estPause;
        }

        if (input.isKeyJustPressed(Input.Keys.TAB)){
            this.finSimulation = true;
        }

        camera.update();
        this.mapRenderer.setView(camera);
    }

    public void rendus() {
        this.mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        creatureRenderer.renduCreature(nbRendus);
        boolean vivant;
        this.ressourceRenderer.renduRessource();
        this.statRenderer.rendu();
        this.creatureRenderer.setCreatures(this.gameWorld.getCreatures());
        this.ressourceRenderer.setRessources(this.gameWorld.getRessources());
        TerrainRenderer.majLuminosite(this.map, this.gameWorld);
    }

    public void affichageSelection() {
        if (this.entiteSelectionne != null) { // Si on a selectionné une créature
            DecimalFormat df = new DecimalFormat("0.00##");
            this.creatureStatsUI.begin();
            String textDeStats = "Position : " + this.entiteSelectionne.getPosition().toString();

            if (this.entiteSelectionne instanceof Creature) {
                textDeStats += "\nNom : " + ((Creature) this.entiteSelectionne).getNom() + " " + ((Creature) this.entiteSelectionne).getPrenom();
                textDeStats += "\nAge : " + (int) (((Creature) this.entiteSelectionne).getAge());
                textDeStats += "\nVie : " + df.format(((Creature) this.entiteSelectionne).getFoie().getPointsDeVie());
                textDeStats += "\nMasse : " + df.format(((Creature) this.entiteSelectionne).getMasse());
                textDeStats += "\nEnergie : " + df.format(((Creature) this.entiteSelectionne).getGraisse().getEnergie());
                textDeStats += "\nTempérature : " + df.format(((Creature) this.entiteSelectionne).getTemperatureInterne());
                textDeStats += "\nTaille : " + df.format(((Creature) this.entiteSelectionne).getTaille());
                textDeStats += "\nOxygene : " + df.format(((Creature) this.entiteSelectionne).getAppareilRespiratoire().getQuantiteOxygene());
                textDeStats += ((Creature) this.entiteSelectionne).getEnVie() ? "" : "\nDécédée";
                textDeStats += ((Creature) this.entiteSelectionne).getSexe().getEnceinte() ? "\nEnceinte" : "";
            }

            if (this.entiteSelectionne instanceof Viande) {
                textDeStats += "\nViande ";
                textDeStats += ((Viande) this.entiteSelectionne).estPourrie() ? "Pourrie" : "Consommable";

            }

            if (this.entiteSelectionne instanceof Arbre) {
                textDeStats += "\nArbre ";
            }

            if (this.entiteSelectionne instanceof Fruit) {
                textDeStats += "\nFruit ";
                textDeStats += "\nAge : " + df.format(((Fruit) this.entiteSelectionne).getTempsDepuisChute());
            }

            if (this.entiteSelectionne instanceof Ressource) {
                textDeStats += "\nEnergie : " + df.format(((Ressource) this.entiteSelectionne).getQuantiteEnergie());
            }


            font.draw(this.creatureStatsUI, textDeStats, this.viewport.getWorldWidth() / 2, (this.viewport.getWorldHeight() / 2));

            this.creatureStatsUI.end();
        }
    }

    private void affichageOptionsSimulation() {
        this.optionsUI.begin();
        String textDesOptions = "Mettre en pause la simulation : Space" + "\nQuitter la simulation : Tab";
        font.draw(this.optionsUI, textDesOptions, 300, 35);
        this.optionsUI.end();
    }

    private void framePhysique() {
        gameWorld.update(ConstantesBiologiques.deltaT);
    }

    private void affichageUI() {
        DecimalFormat df2 = new DecimalFormat("0.##");
        this.carteStatsUI.begin();
        String textStatsCarte = "Température : " + df2.format(this.gameWorld.getMeteo().getTemp().getMoyenne());
        textStatsCarte += "\nPourcentage d'humidité : " + df2.format(this.gameWorld.getMeteo().getHumidite().getMoyenneHumidite());
        textStatsCarte += "\nDensité de nuage : " + df2.format(this.gameWorld.getMeteo().getDensiteNuages());
        textStatsCarte += "\nMeteo : " + this.gameWorld.getMeteo().getMeteo();
        textStatsCarte += "\nLuminosite : " + df2.format(this.gameWorld.getMeteo().getLuminosite(gameWorld.getTemps()));
        textStatsCarte += "\nNombre de créatures : " + this.gameWorld.getCreatures().size();
        font.draw(this.carteStatsUI, textStatsCarte, 70, this.viewport.getWorldHeight() - 35);
        this.carteStatsUI.end();
    }

    @Override
    public void render(float delta) {
        if (!this.estPause) {
            Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gestionCamera();

            nbRendus++;

            if (nbRendus == ConstantesBiologiques.ratioAffichageSimulation) {
                nbRendus = 0;
                framePhysique();
            }
            rendus();
            affichageSelection();
            affichageOptionsSimulation();
            affichageUI();
        }
        else if (this.estPause) {
            Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gestionCamera();
            rendus();
            affichageSelection();
            affichageOptionsSimulation();
            affichageUI();
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        this.creatureStatsUI.dispose();
        this.carteStatsUI.dispose();
        this.optionsUI.dispose();
    }

}
