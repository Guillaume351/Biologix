package Environnement.Terrain;

import Utils.ConstantesBiologiques;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class TerrainRenderer {

    // Contient le terrain sous forme de TiledMap
    private TiledMap map;
    /**
     * Taille en pixel d'un tile
     */
    public static final int TILE_SIZE = 32;
    /**
     * La taille de la map qui est render en cases
     */
    private int taille;


    public TerrainRenderer(Terrain terrain) {
        double nbPixelsMap = ConstantesBiologiques.XMAX * ConstantesBiologiques.PixelsParCoord;
        this.taille = (int) (nbPixelsMap / TILE_SIZE);
        this.map = convertToTiledMap(terrain);

    }

    /**
     * Récupérer la taille du terrain
     *
     * @return
     */
    public int getTaille() {
        return taille;
    }

    public static void majLuminosite(TiledMap map, Terrain terrain) {
        double nuit = 1.0 - terrain.getMeteo().getLuminosite(terrain.getTemps());
        map.getLayers().get("nuit").setOpacity((float) (nuit * 0.5));
    }

    public TiledMapTileLayer calcLayerLuminosite() {
        Pixmap lum = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        lum.setColor(Color.BLACK);
        lum.fill();
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        StaticTiledMapTile st = new StaticTiledMapTile(new TextureRegion(new Texture(lum)));
        cell.setTile(st);
        TiledMapTileLayer layer1 = new TiledMapTileLayer(taille, taille, TILE_SIZE, TILE_SIZE);
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                layer1.setCell(i, j, cell);
            }
        }
        return layer1;
    }


    /**
     * Convertit un terrain en TiledMap
     *
     * @param terrain : Le terrain à convertir
     * @return La tiledmap créé
     */
    public TiledMap convertToTiledMap(Terrain terrain) {

        TiledMap map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer1 = new TiledMapTileLayer(taille, taille, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer layer2 = new TiledMapTileLayer(taille, taille, TILE_SIZE, TILE_SIZE);
        TextureRegion eau = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("eau_v2.jpg"))));
        TextureRegion angle = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("angle_v2.jpg"))));
        TextureRegion terre = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("terre_v2.jpg"))));
        TextureRegion terre_Aride = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("terre_seche.png"))));
        TextureRegion neige = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("neige.png"))));
        TextureRegion montagne = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("cobblestone.png"))));
        TextureRegion plage = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("sable.png"))));
        Pixmap alt = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        double echelle = TILE_SIZE / ConstantesBiologiques.PixelsParCoord;
        StaticTiledMapTile staticTiledMapTile;
        StaticTiledMapTile staticTiledMapTile2;
        for (int i = 0; i < taille; i++) {
            for (int k = 0; k < taille; k++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                TiledMapTileLayer.Cell cell2 = new TiledMapTileLayer.Cell();
                Vector2 position = new Vector2((float) (i * echelle), (float) (k * echelle));
                double altitude = terrain.getAltitude(position);
                double distanceMer = terrain.getDistanceMer(position);
                boolean terrestre = altitude > terrain.getPourcentageEau();
                boolean bordEau = distanceMer < ConstantesBiologiques.taillePlage;
                boolean montagneux = altitude > ConstantesBiologiques.altitudeMontagne;
                boolean enneige = altitude > ConstantesBiologiques.altitudeNeige;
                boolean aride = distanceMer > ConstantesBiologiques.distanceDesert;
                if (terrestre) {
                    if (bordEau) {
                        staticTiledMapTile = new StaticTiledMapTile(plage);
                    } else {
                        if (montagneux) {
                            if (enneige) {
                                staticTiledMapTile = new StaticTiledMapTile(neige);
                            } else {
                                staticTiledMapTile = new StaticTiledMapTile(montagne);
                            }
                        } else {
                            if (aride) {
                                staticTiledMapTile = new StaticTiledMapTile(terre_Aride);
                            } else {
                                staticTiledMapTile = new StaticTiledMapTile(terre);
                            }
                        }
                    }
                } else {
                    staticTiledMapTile = new StaticTiledMapTile(eau);
                }
                if (ConstantesBiologiques.AltLayer) {

                    float hue = (float) ((altitude - terrain.getAltitudes().getMin()) / (terrain.getAltitudes().getMax() - terrain.getAltitudes().getMin()));
                    alt.setColor(hue, hue, hue, 1f);
                    alt.fill();
                    staticTiledMapTile2 = new StaticTiledMapTile(new TextureRegion(new Texture(alt)));
                    cell2.setTile(staticTiledMapTile2);
                    layer2.setCell(i, k, cell2);
                }
                cell.setTile(staticTiledMapTile);
                layer1.setCell(i, k, cell);

            }
        }
        alt.dispose();
        layers.add(layer1);
        // On ajoute la layer d'altitudes seulement si elle est activée (cause du lag)
        if (ConstantesBiologiques.AltLayer) {
            layer2.setOpacity(0.6f);
            layers.add(layer2);
        }
        TiledMapTileLayer layerNuit = calcLayerLuminosite();
        layerNuit.setName("nuit");
        layers.add(layerNuit);
        return map;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }


}
