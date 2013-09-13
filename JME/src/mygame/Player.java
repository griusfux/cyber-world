/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

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

    private String baseNamePrefix;

    private Node selectedObject = null;
    private Base selectedBase = null;
    private Node rootNode = null;

    private Map<String, Base> bases = new HashMap<String, Base>();
    private List<Unit> units = new ArrayList<Unit>();
    
    public Player(float startEnergy, String baseName, Node rootNode) {
        energy = startEnergy;
        this.baseNamePrefix = baseName;
        this.rootNode = rootNode;
    }
    
    void addUnit(String[] parts, int color) {
        int price = 0;
        int health = 0;
        
        for (String part :parts) {
            Base.PartInfo partInfo = selectedBase.getPartInfo(part);
            price += partInfo.getPrice();
            health += partInfo.getHealth();
        }

	if (energy > price) {
            units.add(new Unit(health, color));
	    energy -= price;
        }
    }
    
    String getBaseNamePrefix() {
        return baseNamePrefix;
    }

    void addBase(String name, Vector3f pos, int color) {
        bases.put(name, new Base(pos));
    }

}
