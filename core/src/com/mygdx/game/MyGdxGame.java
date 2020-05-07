package com.mygdx.game;

import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Utils.Perlin.PerlinParams;
import Utils.RenderTerrain;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Terrain gameWorld;

	private Viewport viewport;
	TiledMap map;
	MapRenderer mapRenderer;
	private OrthographicCamera camera;

	@Override
	public void create() {

		PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(1000), 1);
		TerrainGenerator generator = new TerrainGenerator(perlinParams);

		this.gameWorld = generator.generateTerrain();
		RenderTerrain renderTerrain = new RenderTerrain(this.gameWorld);
		this.camera = new OrthographicCamera();
		this.map = renderTerrain.getMap();

		MapProperties properties = map.getProperties();

		int tileWidth = 300;
		int tileHeight = 300;
		int mapWidthInTiles = 32;
		int mapHeightInTiles = 32;
		int mapWidthInPixels = mapWidthInTiles * tileWidth;
		int mapHeightInPixels = mapHeightInTiles * tileHeight;

		camera = new OrthographicCamera(300.f, 300.f);

		camera.position.x = mapWidthInPixels * .5f;
		camera.position.y = mapHeightInPixels * .35f;
		camera.zoom = 4f;
		mapRenderer = new OrthoCachedTiledMapRenderer(this.map, 1, 8096);
		mapRenderer.setView(camera);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		this.mapRenderer.setView(camera);
		this.mapRenderer.render();


		//batch.begin();

		//
		// batch.draw(worldTexture, 0, 0, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
		//batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
