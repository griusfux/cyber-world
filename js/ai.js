function  AI(computer, human)
{
    this.computer = computer;
	this.human = human;

    var that = this;

    this.addUnit = function() {
        this.computer.addUnit(["torso1"], 0xff0000);
    };

    this.update = function() {
        if (that.computer.energy > 5) that.addUnit(); // TODO

        if(!that.human.units.length) return;

		for(var i = 0; i < that.computer.units.length; i++) {
			var compUnit = that.computer.units[i];
            if (!compUnit.healthBar) continue;

            var humUnit = that.human.units[0];
            var dist = humUnit.body.position.distanceTo(compUnit.body.position);
            for(var j = 0; j < that.human.units.length; j++) {
                var newDist = that.human.units[j].body.position.distanceTo(compUnit.body.position);
                if (dist < newDist) {
                    humUnit = that.human.units[j];
                    dist = newDist;
                }
            }
            compUnit.closeEnough = 1.5;
            compUnit.goTo(humUnit.body.position.clone());
		}
    };

    this.updateTimer = window.setInterval(this.update, 1000);
}
