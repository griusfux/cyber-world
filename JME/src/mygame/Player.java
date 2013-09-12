/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zDemoniac
 */
public class Player {
    private float energy = 0;
    private float energyGenerationSpeed = 0.2f;

    private String baseNamePrefix;

    //Node selectedObject = null;
    Base selectedBase = null;

    Map bases = new HashMap<String, Base>();
    Map units = new HashMap<String, Unit>();
    
    public Player(float startEnergy, String baseName) {
        energy = startEnergy;
        this.baseNamePrefix = baseName;
    }
    
    public String getBaseNamePrefix() {
        return baseNamePrefix;
    }

    void addBase(String name, Vector3f pos, int color) {
        bases.put(name, new Base(pos));
    }

}
