package com.mygdx.game;

import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.AltitudeMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Terrain gameWorld;

	private Viewport viewport;
	private Camera camera;

	Texture worldTexture;
	Pixmap eau;
	Pixmap terre;

	public Terrain genererMap() {
		//TODO : pour le test, j'utilise les mêmes perlins params. A changer dans le futur
		PerlinParams perlinParams = new PerlinParams(2, 0.01, 0.5, new Random().nextInt(1000), 1);


		Meteo meteo = new Meteo(new MeteoMap(perlinParams, -10, 30), new MeteoMap(perlinParams, 0, 100), Meteo.TypeMeteo.SOLEIL, 20, 10, 0.4);
		AltitudeMap altitudeMap = new AltitudeMap(perlinParams, 0, 1);
		this.eau = new Pixmap(Gdx.files.internal("eau_v1.png"));
		this.terre = new Pixmap(Gdx.files.internal("terre_v1.png"));
		//TODO : replacer les entites par une liste d'entites
		return new Terrain(meteo, null, altitudeMap, 9.81, 0.4, new ConstantesBiologiques());
	}

	public Pixmap getMap() {
		int res = 300;

		Pixmap pixmap = new Pixmap(res * 32, res * 32, Pixmap.Format.RGBA8888);

		float niveauMer = (float) this.gameWorld.getPourcentageEau();
		for (int i = 0; i < res; i++) {
			for (int k = 0; k < res; k++) {
				float valeur = (float) this.gameWorld.getAltitudes().getValeur(i, k);
				if (valeur > niveauMer) {
					// 32 est la largeur de la texture
					pixmap.drawPixmap(terre, i * 32, k * 32);
					//pixmap.drawPixel(i, k, Color.rgba8888(valeur * 2, valeur, 0.0F, 1));
				} else {
					pixmap.drawPixmap(eau, i * 32, k * 32);
					//pixmap.drawPixel(i, k, Color.rgba8888(0.0F, 0.0F, valeur, 1));
				}

			}
		}
		return pixmap;
	}

	@Override
	public void create() {
		this.batch = new SpriteBatch();
		this.gameWorld = genererMap();

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.viewport.setCamera(this.camera);

		this.worldTexture = new Texture(getMap());
	}

	@Override
	public void render () {
		camera.update();

		batch.begin();
		batch.draw(worldTexture, 0, 0, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
