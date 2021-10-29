/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class TextureReader {
    
    public Map<String, Texture> readTextureDataMap(String path) {
        JSONParser parser = new JSONParser();
        Map<String, Texture> textures = new HashMap<String, Texture>();
        try {
            Object obj = parser.parse(new FileReader(path));
 
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
            
            String textureImagePath = jsonObject.get("texture_path").toString();
            
            BufferedImage textureImage = ImageIO.read(new File(textureImagePath));

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray listObj = (JSONArray) jsonObject.get("textures");


            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = listObj.iterator();
            while (iterator.hasNext()) {
                JSONObject object = iterator.next();
                Texture texture = new Texture();
                texture.setName(object.get("name").toString());
                
                int width = Integer.parseInt(object.get("width").toString());
                int height = Integer.parseInt(object.get("height").toString());
                int offsetX = Integer.parseInt(object.get("x").toString());
                int offsetY = Integer.parseInt(object.get("y").toString());
                
                //Fix bug out side raster image
                if(offsetX + width > textureImage.getWidth()) offsetX = textureImage.getWidth() - width;
                if(offsetY + height > textureImage.getHeight()) offsetY = textureImage.getHeight() - height;
                texture.setWidth(width);
                texture.setHeight(height);
                BufferedImage image = textureImage.getSubimage(offsetX, offsetY, width, height);
                texture.setSpriteImage(image);
                
                textures.put(texture.getName(), texture);
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        return textures;
    }
    
    public List<Texture> readTextureDataList(String path) {
        JSONParser parser = new JSONParser();
        List<Texture> textures = new ArrayList<Texture>();
        try {
            Object obj = parser.parse(new FileReader(path));
 
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
            
            String textureImagePath = jsonObject.get("texture_path").toString();
            
            BufferedImage textureImage = ImageIO.read(new File(textureImagePath));

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray listObj = (JSONArray) jsonObject.get("textures");


            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = listObj.iterator();
            while (iterator.hasNext()) {
                JSONObject object = iterator.next();
                Texture texture = new Texture();
                texture.setName(object.get("name").toString());
                
                int width = Integer.parseInt(object.get("width").toString());
                int height = Integer.parseInt(object.get("height").toString());
                int offsetX = Integer.parseInt(object.get("x").toString());
                int offsetY = Integer.parseInt(object.get("y").toString());
                
                //Fix bug out side raster image
                if(offsetX + width > textureImage.getWidth()) offsetX = textureImage.getWidth() - width;
                if(offsetY + height > textureImage.getHeight()) offsetY = textureImage.getHeight() - height;
                texture.setWidth(width);
                texture.setHeight(height);
                BufferedImage image = textureImage.getSubimage(offsetX, offsetY, width, height);
                texture.setSpriteImage(image);
                
                textures.add(texture);
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        return textures;
    }
}
