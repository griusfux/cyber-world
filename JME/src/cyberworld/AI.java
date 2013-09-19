/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberworld;

import com.jme3.math.Vector3f;

/**
 *
 * @author demoniac
 */
public class AI {
    private Player computer;
    private Player human;
    private float targetCloseEnough = 4f;
    
    public AI(Player computer, Player human) {
        this.computer = computer;
        this.human = human;
    }
    
    private void addUnit() {
        computer.addUnit(new String[]{"torso1", "chassis1", "gun1"});
    };

    public void update(float tpf) {
        if(computer.getEnergy() > 7) addUnit(); // TODO

        if(human.getUnits().isEmpty()) return;

	for(Unit compUnit: computer.getUnits()) {
            Unit humUnitTarget = human.getUnits().get(0);
            float dist = Float.POSITIVE_INFINITY;
            for(Unit humUnit: human.getUnits()) {
                Vector3f humPos = humUnit.getPos();
                Vector3f compPos = compUnit.getPos();
                float distNew = compPos.distanceSquared(humPos);
                if(distNew < dist) {
                    dist = distNew;
                    humUnitTarget = humUnit;
                }
            }
            
            if(!compUnit.isMoving() && dist > targetCloseEnough*targetCloseEnough) {
                compUnit.setCloseEnough(targetCloseEnough);
                compUnit.goTo(humUnitTarget.getPos());
            }
	}
    };

    
}
