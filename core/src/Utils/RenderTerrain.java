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

import java.util.Map;

public class RenderTerrain {
    private TiledMap map;
    public RenderTerrain(Terrain terrain){
        TiledMap tiledMap = convertToTiledMap(terrain);
        this.map = tiledMap;
    }

    /**
     * Convertit un terrain en TiledMap
     *
     * @param terrain : Le terrain à convertir
     * @return La tiledmap créé
     */
    public TiledMap convertToTiledMap(Terrain terrain){

        TiledMap map =  new TiledMap();
        MapLayers layers = map.getLayers();

        int height = 300;
        int width = 300;

        TiledMapTileLayer layer1 = new TiledMapTileLayer(width, height, 32, 32);

        TextureRegion eau = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("eau_v1.png"))));
        TextureRegion terre = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal("terre_v1.png"))));

        for(int i = 0; i < width; i++){
            for(int k = 0; k < width; k++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                StaticTiledMapTile staticTiledMapTile;
                if(terrain.getAltitudes().getValeur(i,k) > terrain.getPourcentageEau()){
                    staticTiledMapTile = new StaticTiledMapTile(terre);
                }else{
                    staticTiledMapTile = new StaticTiledMapTile(eau);
                }

                cell.setTile(new StaticTiledMapTile(eau));
                layer1.setCell(i, k, cell);
            }
        }
        layers.add(layer1);
        return map;
    }
}
