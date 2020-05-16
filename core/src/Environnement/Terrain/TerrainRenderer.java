package Environnement.Terrain;

import Utils.ConstantesBiologiques;
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
        Pixmap alt = new Pixmap(Gdx.files.internal("terre_v2.jpg"));
        double echelle = TILE_SIZE / ConstantesBiologiques.PixelsParCoord;
        //TODO Facteur d echelle ???
        for (int i = 0; i < taille; i++) {
            for (int k = 0; k < taille; k++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                TiledMapTileLayer.Cell cell2 = new TiledMapTileLayer.Cell();
                StaticTiledMapTile staticTiledMapTile;
                StaticTiledMapTile staticTiledMapTile2;
                double altitude = terrain.getAltitude(new Vector2((float) (i * echelle), (float) (k * echelle)));
                if (altitude > terrain.getPourcentageEau()) {
                    staticTiledMapTile = new StaticTiledMapTile(terre);

                } else {
                    staticTiledMapTile = new StaticTiledMapTile(eau);
                }
                float hue = (float) ((altitude - terrain.getAltitudes().getMin()) / (terrain.getAltitudes().getMax() - terrain.getAltitudes().getMin()));
                alt.setColor(hue, hue, hue, 1f);
                alt.fill();
                staticTiledMapTile2 = new StaticTiledMapTile(new TextureRegion(new Texture(alt)));

                cell.setTile(staticTiledMapTile);
                cell2.setTile(staticTiledMapTile2);
                layer1.setCell(i, k, cell);
                layer2.setCell(i, k, cell2);
            }
        }
        layers.add(layer1);
        layer2.setOpacity(0.36f);
        // On ajoute la layer d'altitudes seulement si elle est activée (cause du lag)
        if (ConstantesBiologiques.AltLayer) {
            layers.add(layer2);
        }
        return map;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }


}
