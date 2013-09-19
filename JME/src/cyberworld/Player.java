/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberworld;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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

    private Map<String, Base> bases = new HashMap<>();
    private List<Unit> units = new ArrayList<>();

    
    public Player(ColorRGBA color, float startEnergy, String baseName, Game game) {
        energy = startEnergy;
        this.baseNamePrefix = baseName;
        this.game = game;
        this.color = color;
    }
    
    public void addBase(String name, Vector3f pos, int color) {
        Node baseNode = (Node)game.getRootNode().getChild(baseNamePrefix+".ogremesh");
        Base base = new Base(pos, baseNode);
        bases.put(name, base);
        
        // TODO
        selectedBase = base;
    }
    
    public void addUnit(String[] parts) {
        int price = 0;
        // check price, TODO: calc to GUI and get from there
        for (String part :parts) {
            //System.out.println(part);
            Base.PartInfo partInfo = selectedBase.getPartInfo(part);
            if(partInfo == null) {
                System.out.println("no info for '" + part + "'");                
            } else {
                price += partInfo.getPrice();
            }
        }

	if (energy >= price) {
            units.add(new Unit(parts, this));
	    energy -= price;
        }
        else {
            System.err.println("insufficient energy");
        }
    }
    
    public float getEnergy() {
        return energy;
    }
    
    public List<Unit> getUnits() {
        return units;
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
    
    public Node getSelectedObject() {
        return selectedObject;
    }
    
    public Unit getSelectedUnit() {
        return selectedObject.getUserData("parent");
    }
    
    public void setSelectedObject(Node target) {
        selectedObject = target;
    }

    void update(float tpf) {
       energy += energyGenerationSpeed * tpf; 
       
       for(Unit unit: units) {
           unit.update(tpf);
       }
    }
}
