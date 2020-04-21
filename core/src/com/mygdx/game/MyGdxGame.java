package com.mygdx.game;

import Environnement.Meteo.MeteoMap;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	MeteoMap testmap;
	Pixmap map;
	private Viewport viewport;
	private Camera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		testmap = new MeteoMap(new PerlinParams(2, 0.01, 0.5, 1, 1), 0, 1);

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.viewport.setCamera(this.camera);

		int res = 800;

		this.map = new Pixmap(res, res, Pixmap.Format.RGBA8888);

		float niveauMer = (float) 0.4;
		for (int i = 0; i < res; i++) {
			for (int k = 0; k < res; k++) {
				float valeur = (float) testmap.getValeur(i, k);
				if (valeur > niveauMer) {
					this.map.drawPixel(i, k, Color.rgba8888(valeur * 2, valeur, 0.0F, 1));
				} else {
					this.map.drawPixel(i, k, Color.rgba8888(0.0F, 0.0F, valeur, 1));
				}

			}
		}
	}

	@Override
	public void render () {
		camera.update();


		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(new Texture(map), 0, 0, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
