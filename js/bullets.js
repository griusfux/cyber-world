function  Bullet()
{
	this.goal = new THREE.Vector3();
	this.dx = new THREE.Vector3();
	this.color = 0x550000;
	this.speed = 20;

	var that = this;
    this.onGeometry = function(geom, mats) {
//        that.mesh = new THREE.Mesh( geom, new THREE.MeshFaceMaterial(mats));
        that.mesh = new THREE.Mesh( geom, new THREE.MeshPhongMaterial( { ambient: that.color & 0xffffff, color: that.color } ) );
        that.mesh.castShadow = true;
        that.mesh.name = "Bullet";
		that.mesh.visible = false;
        scene.add(that.mesh);
    };
//    loader.load( "models/unit0.js", onGeometry );    
    this.onGeometry(new THREE.CubeGeometry( .1, .1, .4 ), null);

	this.fire = function(position, goal) {
		this.mesh.visible = true;
		this.mesh.position = position.clone();
		this.goal = goal;
	};

	this.update = function(dt) {
        if (!this.mesh || !this.mesh.visible) return;

        this.mesh.lookAt(this.goal);
        this.dx.subVectors(this.goal, this.mesh.position);
		if (this.dx.length() > .01) {
			var moveDist = dt * this.speed;
			this.mesh.translateZ(moveDist);
        }
		else {
			this.mesh.visible = false;
        }
	};
}
