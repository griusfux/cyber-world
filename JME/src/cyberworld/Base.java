/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberworld;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zDemoniac
 */
public class Base implements Savable {
    public class PartInfo {
        private int health = 0;
        private int price = 0;

        public PartInfo(int price, int health) {
            this.health = health;
            this.price = price;
        }
        
        public int getHealth() {
            return health;
        }
        
        public int getPrice() {
            return price;
        }
    }
    
    private Vector3f unitSpawnPosition;
    private Map<String, PartInfo> parts = new HashMap<String, PartInfo>();
    
    public Base(Vector3f unitSpawnPosition, Node node) {
        this.unitSpawnPosition = unitSpawnPosition;
        node.setUserData("parent", this);
        
        // TODO
        parts.put("chassis1", new PartInfo(2, 50));
        parts.put("chassis2", new PartInfo(3, 75));
        parts.put("torso1", new PartInfo(3, 100));
        parts.put("torso2", new PartInfo(5, 150));
        parts.put("gun1", new PartInfo(2, 10));
        parts.put("gun2", new PartInfo(4, 20));
    }
    
    public PartInfo getPartInfo(String partName) {
        return parts.get(partName);
    }
    
    public Vector3f getSpawnPosition() {
        return unitSpawnPosition;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        //capsule.write(health,   "health",   0f);
        //capsule.write(healthMax, "healthMax", 0f);
        //capsule.write(someJmeObject,  "someJmeObject",  new Material());
    }
 
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        //health   = capsule.readFloat("health",   0f);
        //healthMax = capsule.readFloat("healthMax", 0f);
        //someJmeObject  = capsule.readSavable("someJmeObject",  new Material());
    }
}
