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
public class Base {
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
    
    public Base(Vector3f unitSpawnPosition) {
        this.unitSpawnPosition = unitSpawnPosition;
        
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
}
