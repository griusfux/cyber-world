/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.List;

/**
 *
 * @author zDemoniac
 */
public class Player {
    private float energy = 0;
    private float energyGenerationSpeed = 0.2f;

    private String baseName;

    //Node selectedObject = null;
    Base selectedBase = null;

    List<Base> bases;
    List<Unit> units;
    
    public Player(float startEnergy, String baseName) {
        energy = startEnergy;
        this.baseName = baseName;
    }
    
    public String getBaseName() {
        return baseName;
    }

    void addBase(Vector3f pos, int color) {
        bases.add(new Base(pos));
    }

}
