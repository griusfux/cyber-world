/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberworld;

import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;

/**
 *
 * @author zDemoniac
 */
public class Missile {
    private float damageMin;
    private float damageMax;
    private float rangeSq;
    private Node node;
    private float speed = 10;
    private float reloadSec;
    private Unit target;
    private final Quaternion lookRotation = new Quaternion(); 
    
    public Missile(float damageMin, float damageMax, 
            float rangeSq, float reloadSec, Player player) {
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.rangeSq = rangeSq;
        this.reloadSec = reloadSec;
        //this.player = player;
        
        // TODO load model
        Geometry geom = new Geometry("Missile", new Box(0.1f, 0.1f, 0.4f));
        Material mat = new Material(player.getGame().getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");        
        mat.setColor("Color", player.getColor());
        geom.setMaterial(mat);
        node = new Node("Missile");
        node.attachChild(geom);
        player.getGame().getRootNode().attachChild(node);
        Hide();
    }
    
    public boolean fire(Vector3f pos, Unit target) {
        if (!isHiden()) return true;   // already firing
        //if()
        if (pos.distanceSquared(target.getPos()) > rangeSq) return false; // too far

	unHide();
	node.setLocalTranslation(pos);
	this.target = target;

        return true;
    }
    
    public void update(float tpf) {
        if (isHiden()) return; // idle
        
        Vector3f dist = target.getPos().subtract(node.getWorldTranslation());        

	if (dist.length() > 0.1f) {
            dist.normalizeLocal();
            lookRotation.lookAt(dist, Vector3f.UNIT_Y);
            node.setLocalRotation(lookRotation);
            node.move(dist.multLocal(speed * tpf));
        }
        else {
            Hide();
            // do damage
            float dmg = damageMin + (int)(Math.random() * ((damageMax - damageMin) + 1));
            target.healthDec(dmg);
            System.out.println("damaged by: " + Math.round(dmg));
        }
    }
    
    private void Hide() {
        node.setCullHint(CullHint.Always);
    }
    
    private void unHide() {
        node.setCullHint(CullHint.Inherit);
    }
    
    private boolean isHiden() {
        return node.getCullHint() == CullHint.Always;
    }

    void remove() {
        node.detachAllChildren();
        node.removeFromParent();
        node = null;
    }
}
