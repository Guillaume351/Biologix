package com.mygdx.game;

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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Terrain gameWorld;
    TiledMap map;
    MapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {

        // Nos parametres de génération de map pour le test
        PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(1000), 1);

        // On créer notre générateur de terrain
        TerrainGenerator generator = new TerrainGenerator(perlinParams);

        // On génère le terrain
        this.gameWorld = generator.generateTerrain();

        // On créer notre outil de rendu de terrain
        TerrainRenderer renderTerrain = new TerrainRenderer(this.gameWorld);
        this.camera = new OrthographicCamera();

        // On récupère le terrain convertit en TileSet
        this.map = renderTerrain.getMap();

        MapProperties properties = map.getProperties();

        // Les infos concernant notre tile. TODO : utiliser les infos de TerrainRenderer
        int tileWidth = 300;
        int tileHeight = 300;
        int mapWidthInTiles = 32;
        int mapHeightInTiles = 32;
        int mapWidthInPixels = mapWidthInTiles * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;


        // Les paramètres de notre caméras. TODO : faire davantage de tests pour obtenir une vue plus éloignée
        camera = new OrthographicCamera(300.f, 300.f);

        camera.position.x = mapWidthInPixels * .5f;
        camera.position.y = mapHeightInPixels * .35f;
        camera.zoom = 16f;
        mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1f);
        mapRenderer.setView(camera);


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int sensitivity = 10;
        // Gestion du déplacement de la caméra. TODO: a déplacer
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            camera.position.x -= Gdx.input.getDeltaX() * sensitivity;
            camera.position.y += Gdx.input.getDeltaY() * sensitivity;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            camera.zoom += (Gdx.input.getDeltaX() + Gdx.input.getDeltaY()) / 3;
        }

        camera.update();
        this.mapRenderer.setView(camera);
        this.mapRenderer.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
