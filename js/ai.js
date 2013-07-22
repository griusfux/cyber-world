function  AI(computer, human)
{
    this.computer = computer;
	this.human = human;
    this.addUnit = function() {
        this.computer.selectedBase = this.computer.bases[0];
        this.computer.addUnit(100, 0xff0000, scene, sceneMap);
    };

    this.update = function(deltaTime) {
        if (this.computer.energy > 10) this.addUnit();

		for(var i=0; i<this.computer.units.length; i++) {
			if(!this.computer.units[i].isMoving() && this.human.units[0])
				this.computer.units[i].goTo(this.human.units[0].mesh.position);
		}
    };
}
