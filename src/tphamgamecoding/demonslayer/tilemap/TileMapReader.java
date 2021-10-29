/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.tilemap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TileMapReader {
    public TileMapModel loadTileMap(String path) {
        JSONParser parser = new JSONParser();
        TileMapModel tileMap = new TileMapModel();
        try {
            Object obj = parser.parse(new FileReader(path));
 
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONArray layers = (JSONArray) jsonObject.get("layers");
            LayerModel[] layerArray = new LayerModel[layers.size()];
            Iterator<JSONObject> iterator = layers.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                JSONObject objectLayer = iterator.next();
                LayerModel layer = new LayerModel();
                layer.setName(objectLayer.get("name").toString());
                layer.setTileSet(objectLayer.get("tileset").toString());
                // Data
                JSONArray dataJson = (JSONArray) objectLayer.get("data");
                long[] data = new long[dataJson.size()];
                for(int i = 0; i < data.length; i++) {
                    data[i] = (long) dataJson.get(i);
                }
                layer.setData(data);
                layerArray[index] = layer;
                index ++;
            }
            tileMap.setLayers(layerArray);
//            BufferedImage textureImage = ImageIO.read(new File(textureImagePath));

            // Tile set
            JSONArray tilesetsJson = (JSONArray) jsonObject.get("tilesets");
            TileSetModel[] tilesets = new TileSetModel[tilesetsJson.size()];
            Iterator<JSONObject> iteratorTilesets = tilesetsJson.iterator();
            int index3 = 0;
            while (iteratorTilesets.hasNext()) {
                JSONObject objectTileset = iteratorTilesets.next();
                TileSetModel tileSet = new TileSetModel();
                tileSet.setName(objectTileset.get("name").toString());
                tileSet.setImage(objectTileset.get("image").toString());
                tileSet.setImageWidth((long) objectTileset.get("imagewidth"));
                tileSet.setImageHeight((long) objectTileset.get("imageheight"));
                tileSet.setTileWidth((long) objectTileset.get("tilewidth"));
                tileSet.setTileHeight((long) objectTileset.get("tileheight"));
                tilesets[index3] = tileSet;
                
                System.out.println(tileSet.getImage().replace(" ", ""));
                BufferedImage tileSetImage = ImageIO.read(new File("data/tilemap/"+tileSet.getImage()));
                int numberOfRow = (int)(tileSet.getImageHeight()/ tileSet.getTileHeight());
                int numberOfCol = (int)(tileSet.getImageWidth()/ tileSet.getTileWidth());
                int size = numberOfRow * numberOfCol;
                BufferedImage[] tiles = new BufferedImage[size];
                int tileIndex = 0;
                for(int row = 0; row < numberOfRow; row++) {
                    for(int col = 0; col < numberOfCol; col++) {
                        tiles[tileIndex] = tileSetImage.getSubimage(col * (int) tileSet.getTileWidth(), 
                                row * (int) tileSet.getTileHeight(), (int) tileSet.getTileWidth(), 
                                (int) tileSet.getTileHeight());
                        tileIndex++;
                    }
                }
                tileSet.setTiles(tiles);
                index3 ++;
            }
            tileMap.setTilesets(tilesets);

            // Canvas
            JSONObject canvasJSONObj = (JSONObject) jsonObject.get("canvas");
            MapCanvas canvas = new MapCanvas();
            canvas.setWidth((long) canvasJSONObj.get("width"));
            canvas.setHeight((long) canvasJSONObj.get("height"));
            tileMap.setCanvas(canvas);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return tileMap;
    }
}
