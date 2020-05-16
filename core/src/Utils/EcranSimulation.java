package Utils;

import Entites.Creatures.Creature;
import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.*;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.Perlin.PerlinParams;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Environnement.Terrain.TerrainRenderer.TILE_SIZE;
import static com.badlogic.gdx.Gdx.input;

public class EcranSimulation implements Screen {

    Viewport viewport;
    SpriteBatch batch;

    /**
     * Batch d'affichage des statistiques
     */
    SpriteBatch creatureStatsUI;

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

    /**
     * Police d'écriture
     */
    BitmapFont font = new BitmapFont();

    public EcranSimulation() {

        // Nos parametres de génération de map pour le test
        PerlinParams perlinParams = new PerlinParams(3, 0.01, 0.5, new Random().nextInt(10000), 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams, 100, 25, 25, 25);

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


        // Test créature & ressource
        batch = new SpriteBatch();
        // On projette sur la caméra
        batch.setProjectionMatrix(camera.combined);
        creatureRenderer = new CreatureRenderer(this.gameWorld.getCreatures(), batch);
        ressourceRenderer = new RessourceRenderer(this.gameWorld.getRessources(), batch);
    }

    @Override
    public void show() {
        input.setInputProcessor(new CustomInputProcessor(camera));

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
                System.out.println(touchPos.x / 32f + ":" + touchPos.y / 32f);

                for (Entite entite : this.gameWorld.getEntites()) {
                    if (entite.getPosition().dst2(new Vector2(touchPos.x / (float) TILE_SIZE, touchPos.y / (float) TILE_SIZE)) < 20) {
                        //System.out.println(entite);
                        this.entiteSelectionne = entite;
                    }

                }


            }

        }

        camera.update();
        this.mapRenderer.setView(camera);
        this.mapRenderer.render();

        // Affichage créature
        batch.setProjectionMatrix(camera.combined);
        creatureRenderer.renduCreature();
        boolean vivant;
        this.ressourceRenderer.renduRessource();
        List<Entite> updateEntites = new ArrayList<>(gameWorld.getEntites());
        for (Entite c : gameWorld.getEntites()) {
            c.update(0.1);
            if (c instanceof Creature) {
                if (!((Creature) c).getEnVie()) {
                    updateEntites.remove(c);
                }
            }
        }
        this.gameWorld.setEntites(updateEntites);
        this.creatureRenderer.setCreatures(this.gameWorld.getCreatures());
        // TODO : les créatures meurent toutes directement
        //System.out.println(this.gameWorld.getCreatures().size());


        if (this.entiteSelectionne != null) { // Si on a selectionné une créature
            this.creatureStatsUI.begin();
            String textDeStats = "Position : " + this.entiteSelectionne.getPosition().toString();

            if (this.entiteSelectionne instanceof Creature) {
                textDeStats += "\nNom : " + ((Creature) this.entiteSelectionne).getNom() + " " + ((Creature) this.entiteSelectionne).getPrenom();
                textDeStats += "\nAge : " + ((Creature) this.entiteSelectionne).getAge();
                textDeStats += "\nMasse : " + ((Creature) this.entiteSelectionne).getMasse();
                textDeStats += "\nEnergie : " + ((Creature) this.entiteSelectionne).getGraisse().getEnergie();
                textDeStats += "\nTempérature : " + ((Creature) this.entiteSelectionne).getTemperatureInterne();
                textDeStats += "\nTaille : " + ((Creature) this.entiteSelectionne).getTaille();
                textDeStats += ((Creature) this.entiteSelectionne).getEnVie() ? "" : "\nDécédée";
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
            }

            if (this.entiteSelectionne instanceof Ressource) {
                textDeStats += "\nEnergie : " + ((Ressource) this.entiteSelectionne).getQuantiteEnergie();
            }


            font.draw(this.creatureStatsUI, textDeStats, this.viewport.getWorldWidth() / 2, (this.viewport.getWorldHeight() / 2));

            this.creatureStatsUI.end();
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        this.creatureStatsUI.dispose();
    }
}
