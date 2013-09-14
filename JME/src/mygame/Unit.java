/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author zDemoniac
 */
public class Unit {
    private float health;
    private float healthMax;
    private Player player;
    
    public Unit(String[] parts, Player player) {
        this.player = player;
                
        for (String part :parts) {
            Base.PartInfo partInfo = player.getBase().getPartInfo(part);
            health += partInfo.getHealth();
            // TODO load parts
        }
        Box b = new Box(Vector3f.ZERO, .7f, .7f, .7f);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(player.getGame().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", player.getColor());
        geom.setMaterial(mat);
        
        Node node = new Node("Unit");
        node.attachChild(geom);
        node.setLocalTranslation(player.getBase().getSpawnPosition());
        
        this.player.getGame().getRootNode().attachChild(node);
        //System.out.println("unit added");
        healthMax = health;
    }
    
    public void update(float tpf) {
        
    }
}
