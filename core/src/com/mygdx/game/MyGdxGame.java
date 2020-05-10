package com.mygdx.game;

import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.RessourceRenderer;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static Environnement.Terrain.TerrainRenderer.TILE_SIZE;
import static com.badlogic.gdx.Gdx.input;

public class MyGdxGame extends ApplicationAdapter {
    Viewport viewport;
    SpriteBatch batch;
    Terrain gameWorld;
    TiledMap map;
    MapRenderer mapRenderer;
    private OrthographicCamera camera;

    CreatureRenderer creatureRenderer;
    RessourceRenderer ressourceRenderer;

    @Override
    public void create() {

        // Nos parametres de génération de map pour le test
        PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(10000), 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams, 100, 0, 0, 0);

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

        input.setInputProcessor(new CustomInputProcessor(camera));

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Gestion du déplacement de la caméra. TODO: a déplacer
        if (input.isButtonPressed(Input.Buttons.LEFT)) {

            camera.position.x -= input.getDeltaX() * camera.zoom;
            camera.position.y += input.getDeltaY() * camera.zoom;
        }


        camera.update();
        this.mapRenderer.setView(camera);
        this.mapRenderer.render();

        // Affichage créature
        batch.setProjectionMatrix(camera.combined);
        creatureRenderer.renduCreature();
        this.ressourceRenderer.renduRessource();
        for (Entite c : gameWorld.getEntites()) {

            c.update(2);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
