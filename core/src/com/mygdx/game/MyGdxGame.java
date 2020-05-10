package com.mygdx.game;

import Entites.Creatures.Creature;
import Entites.Creatures.CreatureRenderer;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Utils.Perlin.PerlinParams;
import Utils.TerrainRenderer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    Viewport viewport;
    SpriteBatch batch;
    Terrain gameWorld;
    TiledMap map;
    MapRenderer mapRenderer;
    private OrthographicCamera camera;

    CreatureRenderer testCreature;

    @Override
    public void create() {

        // Nos parametres de génération de map pour le test
        PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(10000), 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams);

        // On génère le terrain
        this.gameWorld = generator.generateTerrain();

        // On créer notre outil de rendu de terrain
        TerrainRenderer renderTerrain = new TerrainRenderer(this.gameWorld, 300, 300);
        this.camera = new OrthographicCamera();

        // On récupère le terrain convertit en TileSet
        this.map = renderTerrain.getMap();

        // Les infos concernant notre tile. TODO : utiliser les infos de TerrainRenderer
        int mapWidthInTiles = 32;
        int mapHeightInTiles = 32;
        int mapWidthInPixels = mapWidthInTiles * renderTerrain.getWidth();
        int mapHeightInPixels = mapHeightInTiles * renderTerrain.getHeight();


        // Les paramètres de notre caméras. TODO : faire davantage de tests pour obtenir une vue plus éloignée
        camera = new OrthographicCamera(800.f, 480.f);

        viewport = new FitViewport(800, 480, camera);

        
        camera.position.x = mapWidthInPixels * .5f;
        camera.position.y = mapHeightInPixels * .35f;
        camera.zoom = 16f;
        mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1f);
        mapRenderer.setView(camera);

        // Test créature
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        testCreature = new CreatureRenderer(generator.creaturesPopulate(this.gameWorld, 100), batch);

        //System.out.println(testCreature.creatureHote.getPosition().x);


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
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            camera.position.x -= Gdx.input.getDeltaX() * camera.zoom;
            camera.position.y += Gdx.input.getDeltaY() * camera.zoom;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            camera.zoom += (Gdx.input.getDeltaX() + Gdx.input.getDeltaY()) / 8;

            // On impose un max et un min au zoom
            camera.zoom = Math.min(camera.zoom, 20);
            camera.zoom = Math.max(camera.zoom, 1);
        }

        camera.update();
        this.mapRenderer.setView(camera);
        this.mapRenderer.render();

        // Affichage créature
        batch.setProjectionMatrix(camera.combined);
        testCreature.renduCreature();
        for (Creature c : testCreature.creatures) {
            c.update(0.05);
        }
    }

    @Override
    public void dispose() {
    }
}
