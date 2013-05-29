var sceneName = argv.scene ? argv.scene : "game";

var camera, scene, projector, renderer;
var objects = [];
var particleMaterial;
var mesh;

var bgColor = 0x3A3938;

init();
animate();
function echo(text)
{
    document.write(text);
}

function init() {
    renderer = new THREE.WebGLRenderer();
    projector = new THREE.Projector();
	renderer.setSize( window.innerWidth, window.innerHeight );
	document.body.appendChild( renderer.domElement );

	//camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 100 );
	//camera.position.z = 10;

    var sceneLoader = new THREE.SceneLoader();
	//sceneLoader.callbackProgress = callbackProgress;
    sceneLoader.load( "scenes/"+sceneName+".js", onSceneLoaded);

	//
    document.addEventListener( 'mousedown', onDocumentMouseDown, false );
	window.addEventListener( 'resize', onWindowResize, false );
}

function onSceneLoaded(result)
{
    console.log(result);

    //result.lights["default_light"].position = result.objects["Light1"].position

    camera = result.cameras["CameraMain"];
    var tmpY = camera.position.y;
    camera.position.y = camera.position.z;
    camera.position.z = tmpY;
    camera.lookAt(result.objects["CameraTarget"].position);

    camera.fov = 65;
    camera.updateProjectionMatrix();

    //camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 100 );
	//camera.position.z = 10;

   	scene = result.scene;

    //scene.fog = new THREE.Fog( bgColor, 0.00025, 15 );
                
    scene.add( new THREE.AmbientLight( 0xF3F3F3 ) );

  	//var light = new THREE.DirectionalLight( 0xFFEEBB );
   	//light.position.set( -5, 15, -5 );
   	//scene.add( light );

    //var meshLoader = new THREE.JSONLoader();
    //meshLoader.load( "models/base02.js", function( geometry, materials ) {
    //    mesh = new THREE.Mesh( geometry, new THREE.MeshFaceMaterial( materials ) );
    //    scene.add( mesh );
	//	mesh.rotation.x += .2;
    //});

    for (object in result.objects) {
        var obj = result.objects[object];
        if (obj instanceof THREE.Mesh)
            objects.push(obj);
    }


    var PI2 = Math.PI * 2;
    particleMaterial = new THREE.ParticleCanvasMaterial( {
        color: 0x000000,
        program: function ( context ) {
            context.beginPath();
            context.arc( 0, 0, 1, 0, PI2, true );
            context.closePath();
            context.fill();

        }
    } );

   console.log(scene);
}

function onDocumentMouseDown( event ) {
    event.preventDefault();

    var vector = new THREE.Vector3((event.clientX/window.innerWidth)*2-1, -(event.clientY/window.innerHeight)*2+1, 0.5 );
    projector.unprojectVector( vector, camera );

    var raycaster = new THREE.Raycaster( camera.position, vector.sub(camera.position).normalize() );
    var intersects = raycaster.intersectObjects( objects );

    console.log(intersects);

    if ( intersects.length > 0 ) {
        console.log(intersects[0].object.name);
//        intersects[ 0 ].object.material.color.setHex( Math.random() * 0xffffff );
//        var particle = new THREE.Particle( particleMaterial );
//        particle.position = intersects[ 0 ].point;
//        particle.scale.x = particle.scale.y = 8;
//        scene.add( particle );
    }

    /*
     // Parse all the faces
     for ( var i in intersects ) {

     intersects[ i ].face.material[ 0 ].color.setHex( Math.random() * 0xffffff | 0x80000000 );

     }
     */
}

function onWindowResize() {
    if (camera == null) return;
    camera.aspect = window.innerWidth / window.innerHeight;
	camera.updateProjectionMatrix();

	renderer.setSize( window.innerWidth, window.innerHeight );
}

function animate() {
    requestAnimationFrame( animate );

//	if (mesh != null) {
//        //mesh.rotation.x += 0.005;
//    	mesh.rotation.y += 0.01;
//    }

	if (scene != null) renderer.render( scene, camera );
}