package Utils;

import Environnement.Terrain.Terrain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TerrainRenderer {

    // Contient le terrain sous forme de TiledMap
    private TiledMap map;

    /**
     * La taille de la map qui est render
     */
    private int height;
    private int width;


    public TerrainRenderer(Terrain terrain, int height, int width) {
        this.height = height;
        this.width = width;
        this.map = convertToTiledMap(terrain);

    }

    /**
     * Récupérer la hauteur du terrain render
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Récupérer la largeur du terrain render
     *
     * @return
     */
    public int getWidth() {
        return width;
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

        TiledMapTileLayer layer1 = new TiledMapTileLayer(width, height, 32, 32);

        TextureRegion eau = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("eau_v2.jpg"))));
        TextureRegion terre = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("terre_v2.jpg"))));
        TextureRegion angle = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("angle_v2.jpg"))));



        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                StaticTiledMapTile staticTiledMapTile;
                if (terrain.getAltitudes().getValeur(i, k) > terrain.getPourcentageEau()) {
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
