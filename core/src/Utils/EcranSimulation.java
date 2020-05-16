package Utils;

import Entites.Creatures.Creature;
import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.RessourceRenderer;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
    Terrain gameWorld;
    TiledMap map;
    MapRenderer mapRenderer;
    private OrthographicCamera camera;

    CreatureRenderer creatureRenderer;
    RessourceRenderer ressourceRenderer;


    public EcranSimulation(){

        // Nos parametres de génération de map pour le test
        PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(10000), 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams, 100, 25, 25, 25);

        // On génère le terrain
        this.gameWorld = generator.getGeneratedTerrain();

        // On créer notre outil de rendu de terrain
        TerrainRenderer renderTerrain = new TerrainRenderer(this.gameWorld, 300);
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

        // Test créature & ressource
        batch = new SpriteBatch();
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
            if (input.getDeltaX() > 0 || input.getDeltaY() > 0) {
                camera.position.x -= input.getDeltaX() * camera.zoom;
                camera.position.y += input.getDeltaY() * camera.zoom;
            } else {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                System.out.println(touchPos.x + ":" + touchPos.y);
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
            if (c instanceof Creature){
                if (!((Creature) c).getEnVie()){
                    updateEntites.remove(c);
                }
            }
        }
        this.gameWorld.setEntites(updateEntites);
        this.creatureRenderer.setCreatures(this.gameWorld.getCreatures());
        // TODO : les créatures meurent toutes directement
        //System.out.println(this.gameWorld.getCreatures().size());

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
    }
}
