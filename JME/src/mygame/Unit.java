/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

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
        
        this.healthMax = this.health;
    }
    
    public void update(float tpf) {
        
    }
}
