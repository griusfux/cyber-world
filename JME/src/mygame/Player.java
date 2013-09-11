/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author zDemoniac
 */
public class Player {
    private float energy = 0;
    private float energyGenerationSpeed = 0.2f;

    private String baseName;

    //Node selectedObject = null;
    //Node selectedBase = null;

    Base bases[];
    //units = [];
    
    public Player(float startEnergy, String baseName) {
        energy = startEnergy;
        this.baseName = baseName;
    }

}
