package Environnement.Terrain;

import com.badlogic.gdx.Gdx;
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
     * La taille de la map qui est render
     */
    private int taille;


    public TerrainRenderer(Terrain terrain, int taille) {
        this.taille = taille;
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

        TextureRegion eau = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("eau_v2.jpg"))));
        TextureRegion terre = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("terre_v2.jpg"))));
        TextureRegion angle = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("angle_v2.jpg"))));

        //TODO Facteur d echelle ???
        for (int i = 0; i < taille; i++) {
            for (int k = 0; k < taille; k++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                StaticTiledMapTile staticTiledMapTile;
                if (terrain.getAltitude(new Vector2(i, k)) > terrain.getPourcentageEau()) {
                    staticTiledMapTile = new StaticTiledMapTile(terre);
                } else {
                    staticTiledMapTile = new StaticTiledMapTile(eau);
                }
                cell.setTile(staticTiledMapTile);
                layer1.setCell(i, k, cell);
            }
        }
        layers.add(layer1);
        return map;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
}
