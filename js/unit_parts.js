
function UnitPart(color, unit, type) {
    this.color = color;

    var that = this;

    this.onGeometry = function(geom, mats) {
//        that.mesh = new THREE.Mesh( geom, new THREE.MeshFaceMaterial(mats));
        that.mesh = new THREE.Mesh( geom, new THREE.MeshPhongMaterial( { ambient: that.color & 0xffffff, color: that.color } ) );
        that.mesh.castShadow = true;
        //that.mesh.receiveShadow = true;
        unit.body.add(that.mesh);

        if (!type.indexOf("torso")) {
            that.mesh.name = "Unit";
            that.mesh.userData = unit;
            unit.goFromBaseToSpawn();
        }
        else
            that.mesh.name = "Unit_" + type;

        var outlineMaterial = new THREE.MeshBasicMaterial( { color: that.color, side: THREE.BackSide } );
        that.meshOutline = new THREE.Mesh( geom, outlineMaterial );
        that.meshOutline.name = that.mesh.name + ".Outline";
        that.meshOutline.scale.multiplyScalar(1.05);
        that.meshOutline.visible = false;
        unit.body.add(that.meshOutline);
    };

//    loader.load( "models/unit0_"+type+".js", onGeometry );

    var cube;
    switch (type) {
        case "chassis1":
            cube = new THREE.CubeGeometry( 1.2, .2, 1.2 );
            break;
        case "torso1":
            cube = new THREE.CubeGeometry( 1, 1, 1 );
            break;
    }

    this.onGeometry(cube, null);
}
