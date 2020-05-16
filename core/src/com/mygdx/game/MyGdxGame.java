package com.mygdx.game;

import Entites.Creatures.CreatureRenderer;
import Entites.Entite;
import Entites.Ressources.RessourceRenderer;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainGenerator;
import Environnement.Terrain.TerrainRenderer;
import Utils.EcranDemarrage;
import Utils.EcranOptions;
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
    public EcranOptions ecranOptions;
    int i = 0;

    @Override
    public void create() {
        //this.ecranDemarrage = new EcranDemarrage();
        this.ecranOptions = new EcranOptions();
        this.ecranDemarrage = new EcranDemarrage(this);
        //this.ecranSimulation = new EcranSimulation();
        //setScreen(ecranOptions);
        setScreen(ecranDemarrage);

    }

    @Override
    public void render () {

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());

        if (this.ecranOptions.retourEcranDemarrage && getScreen() == this.ecranOptions){
            setScreen(this.ecranDemarrage);
            this.ecranOptions.retourEcranDemarrage = false;
        }

       if (this.ecranDemarrage.lancerJeu && getScreen() == this.ecranDemarrage && this.ecranOptions.nbCreatureValide){
            int nombreCreatures = Integer.parseInt(this.ecranOptions.nombreCreatures.getText());
            this.ecranSimulation = new EcranSimulation(nombreCreatures);
            setScreen(this.ecranSimulation);
       }
       else if (this.ecranDemarrage.lancerJeu && getScreen() == this.ecranDemarrage && !this.ecranOptions.nbCreatureValide){
           this.ecranSimulation = new EcranSimulation();
           setScreen(this.ecranSimulation);
       }


        if (this.ecranDemarrage.options && getScreen() == this.ecranDemarrage){
            setScreen(this.ecranOptions);
            this.ecranDemarrage.options = false;
        }

        if (this.getScreen() == this.ecranSimulation && i == 0){
            this.ecranDemarrage.dispose();
            this.ecranOptions.dispose();
            i++;
        }
    }
}
