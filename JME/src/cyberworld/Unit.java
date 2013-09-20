/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberworld;

import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author zDemoniac
 */
public class Unit implements Savable {
    private float health;
    private float healthMax;
    private Player player;      
    private Node node;
    private NavMeshPathfinder navi;
    private Path path = null;
    private Iterator<Waypoint> wpIt = null;
    private Waypoint wp = null;
    private Geometry geomHealth = null;
    private float speed = 2.0f;
    private float closeEnough = 0.5f;
    private Missile missile;
    
    private final Quaternion lookRotation = new Quaternion(); 
      
    public Unit(String[] parts, Player player) {
        this.player = player;
       
        Material mat = new Material(player.getGame().getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors",true);  
        mat.setColor("Diffuse", player.getColor());
        mat.setColor("Specular",ColorRGBA.White);
        mat.setFloat("Shininess", 64f);
               
        node = new Node("Unit");
        
        for (String part :parts) {
            Base.PartInfo partInfo = player.getBase().getPartInfo(part);
            if(partInfo == null) {
                System.out.println("no info for '" + part + "'");                
            } else {
                health += partInfo.getHealth();
            }
            Box b;
            Geometry geom = null;
            // TODO load parts from assets
            switch (part) {
                case "chassis1": 
                    b = new Box(.8f, .15f, 1.0f);
                    geom = new Geometry(part, b);
                    geom.move(0f, -.2f, 0f);
                    break;
                case "torso1": 
                    b = new Box(.5f, .6f, .5f);
                    geom = new Geometry(part, b);
                    break;
                case "gun1": 
                    b = new Box(.1f, .1f, .5f);
                    geom = new Geometry(part, b);
                    geom.move(0f, .3f, .9f);
                    //
                    missile = new Missile(10,20, 16, .7f, player);
                    break;
            }
            if(geom != null) {
                geom.setMaterial(mat);
                geom.setShadowMode(ShadowMode.Cast);
                node.attachChild(geom);
            }
        }
        node.setLocalTranslation(player.getBase().getSpawnPosition());
        
        this.player.getGame().getRootNode().attachChild(node);
        healthMax = health;
        addHealthBar();
                      
        navi = new NavMeshPathfinder(player.getGame().getNavMesh());     
        
        node.setUserData("parent", this);
        //System.out.println("unit added");
    }

    private void addHealthBar() {
        Geometry geomBack = new Geometry("Quad", new Quad(1.15f, .17f));
        Material matBack = new Material(player.getGame().getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");        
        matBack.setColor("Color", ColorRGBA.Black);
        geomBack.setMaterial(matBack);
        geomBack.move(-0.52f, -0.02f, .01f);
        
        geomHealth = new Geometry("Quad", new Quad(1.1f, .1f));
        Material matFront = matBack.clone();
        matFront.setColor("Color", ColorRGBA.Green);
        geomHealth.setMaterial(matFront);
        geomHealth.move(-0.5f, 0f, .1f);
        
        Node bb = new Node("billboard");
        BillboardControl control=new BillboardControl();
        
        bb.addControl(control);
        bb.attachChild(geomBack);
        bb.attachChild(geomHealth);
        bb.move(0, 1.5f, 0);
        
        node.attachChild(bb);
    }
        
    public void goTo(Vector3f pos) {
        navi.setPosition(node.getWorldTranslation());
        if(navi.computePath(pos)) {
            path = navi.getPath();
            wpIt = path.getWaypoints().iterator();
            System.out.println("path start");
            if(wpIt.hasNext()) wp = wpIt.next();
            else {
                System.out.println("bad path");
                path = null;
            }
        }
        else {
            System.out.println("path not computed");
        }
    }
    
    public float getHealth() {
        return health;
    }
    
    public Vector3f getPos() {
        return node.getWorldTranslation();
    }
    
    public Node getNode() {
        return node;
    }
    
    public void update(float tpf) {
        // move if path
        if(path != null) {          
            Vector3f target = wp.getPosition();
            target.y = node.getWorldTranslation().y;
            //System.out.println("wp: " + target);
                
            Vector3f dist = target.subtract(node.getWorldTranslation());
            if (dist.length() < closeEnough) {
                if(wpIt.hasNext()) {
                    wp = wpIt.next();
                }
                else {
                    wpIt = null;
                    wp = null;
                    path = null;
                    System.out.println("path finish");
                }
            }
            else {
                dist.normalizeLocal();
                lookRotation.lookAt(dist, Vector3f.UNIT_Y);
                node.setLocalRotation(lookRotation);
                node.move(dist.multLocal(speed * tpf)); 
            }   
        }
        
        if(missile != null) missile.update(tpf);
        
        // update health bar
        geomHealth.setLocalScale(health/healthMax, 1f, 1f);
    }
    
    public void fire(Player enemy) {
        //fire
        if(missile == null) return; // no gun
        
	for (Unit target: enemy.getUnits()) {
            if(missile.fire(node.getWorldTranslation(), target)) {
                //that.body.lookAt(parent.enemy.units[i].body.position); // TODO
                break;
            }
        }
    }
    
    public void setCloseEnough(float value) {
        closeEnough = value;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(health,   "health",   0f);
        capsule.write(healthMax, "healthMax", 0f);
        //capsule.write(someJmeObject,  "someJmeObject",  new Material());
    }
 
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        health   = capsule.readFloat("health",   0f);
        healthMax = capsule.readFloat("healthMax", 0f);
        //someJmeObject  = capsule.readSavable("someJmeObject",  new Material());
    }

    public boolean isMoving() {
        return path != null;
    }
    
    private void remove() {
        Node rootNode = player.getGame().getRootNode();
        
        if (missile != null) {
            missile.remove(rootNode);
            missile = null;
        }
        
        node.detachAllChildren();
        rootNode.detachChild(node);
        node = null;
    }

    void healthDec(float dmg) {
        health -= dmg;
        // TODO kill node if life is over
        if(health <= 1) remove();
    }
}
