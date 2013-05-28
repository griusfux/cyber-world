{

"metadata" :
{
	"formatVersion" : 3.2,
	"type"          : "scene",
	"sourceFile"    : "game.blend",
	"generatedBy"   : "Blender 2.66 Exporter",
	"objects"       : 7,
	"geometries"    : 2,
	"materials"     : 2,
	"textures"      : 4
},

"urlBaseType" : "relativeToScene",


"objects" :
{
	"Light1" : {
		"groups"    : [  ],
		"position"  : [ 4.07625, 5.90386, -1.00545 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ]
	},

	"wall1.001" : {
		"geometry"  : "geo_wall1",
		"groups"    : [  ],
		"material"  : "wall1",
		"position"  : [ 1.19607, 0.910513, 4.31056 ],
		"rotation"  : [ -1.5708, 0, 1.5708 ],
		"quaternion": [ -0.5, 0.5, 0.5, 0.5 ],
		"scale"     : [ 1, 1, 1 ],
		"visible"       : true,
		"castShadow"    : false,
		"receiveShadow" : false,
		"doubleSided"   : false
	},

	"wall1" : {
		"geometry"  : "geo_wall1",
		"groups"    : [  ],
		"material"  : "wall1",
		"position"  : [ -4.19505, 0.910513, -2.16084 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ],
		"visible"       : true,
		"castShadow"    : false,
		"receiveShadow" : false,
		"doubleSided"   : false
	},

	"CameraTarget" : {
		"groups"    : [  ],
		"position"  : [ 0, -6.84571e-08, 2 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ]
	},

	"Ground" : {
		"geometry"  : "geo_ground",
		"groups"    : [  ],
		"material"  : "Ground",
		"position"  : [ 0, 0, 0 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ],
		"visible"       : true,
		"castShadow"    : false,
		"receiveShadow" : false,
		"doubleSided"   : false
	},

	"default_light" : {
		"type"       : "DirectionalLight",
		"direction"  : [ 0, 1, 1 ],
		"color"      : 16777215,
		"intensity"  : 0.80
	},

	"CameraMain" : {
		"type"  : "PerspectiveCamera",
		"fov"   : 75.038067,
		"aspect": 1.333000,
		"near"  : 5.000000,
		"far"   : 100.000000,
		"position": [ 0, 8.40799, 8.11417 ],
		"target"  : [ 0, 0, 0 ]
	}
},


"geometries" :
{
	"geo_wall1" : {
		"type" : "ascii",
		"url"  : "game.wall1.js"
	},

	"geo_ground" : {
		"type" : "ascii",
		"url"  : "game.ground.js"
	}
},


"textures" :
{
	"Gravel0107_5_S.jpg" : {
		"url": "Gravel0107_5_S.jpg",
        "repeat": [3, 3],
        "wrap": ["repeat", "repeat"]
	},

	"Gravel0107_5_S_normal.jpg" : {
		"url": "Gravel0107_5_S_normal.jpg",
        "wrap": ["repeat", "repeat"]
	},

	"wall1.jpg" : {
		"url": "wall1.jpg",
        "wrap": ["repeat", "repeat"]
	},

	"wall1_norm.jpg" : {
		"url": "wall1_norm.jpg",
        "wrap": ["repeat", "repeat"]
	}
},


"materials" :
{
	"Ground" : {
		"type": "MeshPhongMaterial",
		"parameters": { "color": 10724259, "opacity": 1, "ambient": 10724259, "specular": 0, "shininess": 5e+01, "map": "Gravel0107_5_S.jpg", "normalMap": "Gravel0107_5_S_normal.jpg", "normalMapFactor": 0.5, "blending": "NormalBlending" }
	},

	"wall1" : {
		"type": "MeshPhongMaterial",
		"parameters": { "color": 13421772, "opacity": 1, "ambient": 13421772, "specular": 3355443, "shininess": 5e+01, "map": "wall1.jpg", "normalMap": "wall1_norm.jpg", "normalMapFactor": 0.7, "blending": "NormalBlending" }
	}
},


"transform" :
{
	"position"  : [ 0, 0, 0 ],
	"rotation"  : [ -1.5708, 0, 0 ],
	"scale"     : [ 1, 1, 1 ]
},

"defaults" :
{
	"bgcolor" : [ 0, 0, 0 ],
	"bgalpha" : 1.000000,
	"camera"  : "CameraMain"
}

}
