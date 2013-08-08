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

		for(var i = 0; i < that.computer.units.length; i++) {
			var compUnit = that.computer.units[i];
			var humUnit = that.human.units[0];

			if(!compUnit.isMoving() && humUnit) {
				var pos = humUnit.body.position.clone();
				pos.x += 1.5;
				compUnit.goTo(pos);
			}
		}
    };

    this.infoTimer = window.setInterval(this.update, 2000);
}
