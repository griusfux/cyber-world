var sceneName = argv.scene ? argv.scene : "game";

var camera, scene, renderer;
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
	renderer.setSize( window.innerWidth, window.innerHeight );
	document.body.appendChild( renderer.domElement );

	//camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 1, 100 );
	//camera.position.z = 10;

    var sceneLoader = new THREE.SceneLoader();
	//sceneLoader.callbackProgress = callbackProgress;
    sceneLoader.load( "scenes/"+sceneName+".js", onSceneLoaded);

	//
	window.addEventListener( 'resize', onWindowResize, false );

}

function onSceneLoaded(result)
{
    console.log(result);

    //result.lights["default_light"].position = result.objects["Light1"].position

    camera = result.cameras["CameraMain"];
    tmpY = camera.position.y;
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

   console.log(scene);
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