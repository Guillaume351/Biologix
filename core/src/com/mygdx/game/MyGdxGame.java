package com.mygdx.game;

import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.RessourceRenderer;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.EcranDemarrage;
import Utils.EcranSimulation;
import Utils.Perlin.PerlinParams;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static Environnement.Terrain.TerrainRenderer.TILE_SIZE;
import static com.badlogic.gdx.Gdx.input;

public class MyGdxGame extends Game {

    EcranDemarrage ecranDemarrage;
    public EcranSimulation ecranSimulation;

    @Override
    public void create() {
        InputEvent event = new InputEvent();
        this.ecranDemarrage = new EcranDemarrage(this);
        this.ecranSimulation = new EcranSimulation();
        setScreen(ecranDemarrage);
    }

    @Override
    public void render () {

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());

        if (this.ecranDemarrage.lancerJeu && getScreen() != this.ecranSimulation){
            setScreen(this.ecranSimulation);
        }
    }
}
