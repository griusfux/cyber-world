/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zDemoniac
 */
public class Player {
    private float energy = 0;
    private float energyGenerationSpeed = 0.2f;
    
    private ColorRGBA color;

    private String baseNamePrefix;

    private Node selectedObject = null;
    private Base selectedBase = null;
    private Game game = null;

    private Map<String, Base> bases = new HashMap<String, Base>();
    private List<Unit> units = new ArrayList<Unit>();
    
    public Player(ColorRGBA color, float startEnergy, String baseName, Game game) {
        energy = startEnergy;
        this.baseNamePrefix = baseName;
        this.game = game;
        this.color = color;
    }
    
    public void addUnit(String[] parts) {
        int price = 0;
    
        // check price, TODO: calc to GUI and get from there
        for (String part :parts) {
            //System.out.println(part);
            Base.PartInfo partInfo = selectedBase.getPartInfo(part);
            price += partInfo.getPrice();
        }

	if (energy >= price) {
            units.add(new Unit(parts, this));
	    energy -= price;
        }
    }
    
    public String getBaseNamePrefix() {
        return baseNamePrefix;
    }
    
    public Base getBase() {
        return selectedBase;
    }
    
    public Game getGame() {
        return game;
    }
    
    public ColorRGBA getColor() {
        return color;
    } 

    public void addBase(String name, Vector3f pos, int color) {
        Base base = new Base(pos);
        bases.put(name, base);
        
        // TODO
        selectedBase = base;
    }

}
