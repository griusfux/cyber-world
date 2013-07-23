function Player(startEnergy, baseName, parent)
{
	this.scene = parent.scene;
	this.sceneMap = parent.sceneMap;

	this.camera = parent.camera;

    this.energy = startEnergy;
    this.energyGenerationSpeed = 0.2;

    this.baseName = baseName;

    this.selectedObject = null;
    this.selectedBase = null;

    this.bases = [];
    this.units = [];

	this.enemy = null;

    this.loader = new THREE.JSONLoader();

    this.addBase = function(unitSpawnPosition, mesh, color) {
        var base = new Base(unitSpawnPosition, mesh, this.scene, color);
        this.bases.push(base);
		this.selectedBase = base;
        return base;
    };

    this.addUnit = function(health, color) {
        this.units.push(new Unit0(health, color, this));
        this.energy -= this.selectedBase.unitCost;
    };

	this.removeUnit = function(unit) {
		var i = this.units.indexOf(unit);
		if (i === -1) {
			log("removeUnit index -1");
			return;
		}

		this.scene.remove(this.units[i].mesh);
		this.scene.remove(this.units[i].meshOutline);
		this.scene.remove(this.units[i].bullet.mesh);
		delete this.units[i].bullet;
		delete this.units[i];
		this.units.splice(i,1);
		this.selectedObject = null;
	};

    this.goUnit = function(point) {
        for ( var i = 0; i < this.units.length; i++) {
            if (this.units[i].mesh.position == this.selectedObject.position)
                this.units[i].goTo(new THREE.Vector3(point.x, this.units[i].mesh.position.y, point.z));
        }
    };

    this.deselectAll = function()
    {
        var i;
        for (i = 0; i < this.units.length; i++) this.units[i].select(false);
        for (i = 0; i < this.bases.length; i++) this.bases[i].select(false);
    };
    
    this.update = function(dt) {
        // generate energy
        this.energy += this.energyGenerationSpeed * dt;
        
        var i;
        for (i = 0; i < this.units.length; i++) {
			var unit = this.units[i];
			if (unit.health <= 1) this.removeUnit(unit); // 1 because I saw 0.25 health unit
			unit.update(dt);
		}
    };
}