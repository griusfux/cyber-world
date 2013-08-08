function Unit0(parts, color, health, parent) {
    //this.rotSpeed = 1.0;
    this.health =  health;
	this.healthMax = health;
    this.speed = 2.5;
    this.closeEnough = 0.1;
    this.goalPath = [];
    this.goalCurrent = 0;
    this.dx = new THREE.Vector3();
	this.fireRange = 10;
    this.color = color;
	this.bullet = new Bullet(10, 20, color, parent.scene);
    this.body = new THREE.Object3D();
    this.body.name = "UnitObj";
    this.body.position = parent.selectedBase.mesh.position.clone();
    parent.scene.add(this.body);
    this.parts = [];
    this.partChassis = null;    // must have
    this.partTorso = null;      // must have
    this.partGun = null;        // optional
    this.partModule1 = null;    // optional
    this.partModule2 = null;    // optional
    //this.caster = new THREE.Raycaster();
    //this.caster.far = 2;

    var that = this;

    this.init = function() {
        for (var i = 0; i < parts.length; i++)  {
            var part = new UnitPart(color, this, parts[i]);
            this.parts.push(part);

            if(!parts[i].indexOf("torso")) {
                this.mesh = part.mesh;
                this.meshOutline = part.meshOutline;
            }
        }
    };

	this.clean = function() {
		parent.scene.remove(this.body);
		this.bullet.clean();
		delete this.bullet;

        document.body.removeChild(this.healthBar);
	};

    this.goFromBaseToSpawn = function() {
        var posEnd = parent.selectedBase.unitSpawnPosition;
        var posStart = new THREE.Vector3(that.body.position.x, posEnd.y, that.body.position.z);
        that.goalPath.push(parent.sceneMap.getSceneGraphPosition(posStart));
        that.goalPath.push(parent.sceneMap.getSceneGraphPosition(posEnd));

        setTimeout(that.addHealthBar, 1500);
    };

    this.goTo = function(point) {
		this.dx.subVectors(point, this.body.position);
		//log("len: " + this.dx.length());
		if(this.dx.length() <= this.closeEnough*2) return;
		
        var posStart = parent.sceneMap.getSceneGraphPosition(this.body.position);
        var posEnd = parent.sceneMap.getSceneGraphPosition(point);
        if (!posStart || !posEnd) {
            log("can't go there :( start: " + posStart + ", end: " + posEnd);
            return;
        }
    
        var start = parent.sceneMap.pathGraph.nodes[posStart.x][posStart.y];
        var end = parent.sceneMap.pathGraph.nodes[posEnd.x][posEnd.y];
        this.goalPath = astar.search(parent.sceneMap.pathGraph.nodes, start, end, true);
        this.goalCurrent = 0;
        //log(this.goalPath);
        //for (var i in this.goalPath)
        //    drawBoundingBox(scenePathGraphBoxes[this.goalPath[i].x][this.goalPath[i].y], 0x0000aa, "debug");  // debug          
    };

    this.select = function(flag) {
        this.meshOutline.visible = flag;      
    };

	this.isMoving = function() {
		if (this.mesh && this.goalPath && this.goalPath.length) return true;
		else return false;
	};

	this.fireEnemies = function() {
		var dx = new THREE.Vector3(0);
		for (var i = 0; i < parent.enemy.units.length; i++) {
			var enemy = parent.enemy.units[i];
			dx.subVectors(this.body.position, enemy.body.position);
			if (dx.length() < this.fireRange) {
				this.bullet.fire(this.body.position, enemy);
				break;
			}
		}		
	};

	this.addHealthBar = function ()	{
        if (that.healthBar) document.body.removeChild(that.healthBar);
		that.healthBar = document.createElement('meter');	
		that.healthBar.innerHTML = "unit";
		that.healthBar.setAttribute('class','healthBar');
		that.healthBar.max = that.healthMax;
		that.healthBar.value = that.health;
		document.body.appendChild(that.healthBar);
	};

	this.updateHealth = function() {
		this.healthBar.value = this.health;
	};

    this.update = function(dt) {
        if (!this.mesh) return;
        //var dTheta = dt * this.rotSpeed;
        //lookTowards(this.mesh, this.goal, dTheta);
        if (this.goalPath && this.goalPath.length) {   
            var currentNode = this.goalPath[this.goalCurrent];  
            //log(currentNode);       
            var currentPoint = parent.sceneMap.pathGraphBoxes[currentNode.x][currentNode.y].center();
            this.body.lookAt(currentPoint);
            this.dx.subVectors(currentPoint, this.body.position);
            if (this.dx.length() > this.closeEnough) {
                var moveDist = dt * this.speed;
                this.body.translateZ(moveDist);
            }
            else {
                if (this.goalCurrent < this.goalPath.length-1) {
                    this.goalCurrent++;
                }
                else {
                    this.goalPath = null;
                    this.goalCurrent = 0;
                }
            }
        }

		if (this.bullet) {
			this.bullet.update(dt);
			this.fireEnemies();
		}
		
		// move health bar
		if(this.healthBar && this.isMoving()) {
			var pos2d = calc2Dpoint(this.body, parent.camera, parent.renderer);
			var dx = 25-(pos2d.x-300)/60;
			this.healthBar.style.left = pos2d.x - dx + "px"; // TODO
			this.healthBar.style.top = pos2d.y - 40 + "px";
		}

//        var direction = new THREE.Vector3(0,0,1).applyQuaternion(this.mesh.quaternion);
//
//        this.caster.set(this.mesh.position, direction);
//
//        var collisions = this.caster.intersectObjects(scene.__objects, false);
//        if (collisions.length) {
//            for (var i = 0; i < collisions.length; i++)
//                log(collisions[i].object.name);
//            this.goal = this.mesh.position;
//        }
    };

    this.init();
}
