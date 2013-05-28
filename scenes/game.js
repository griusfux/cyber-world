{

"metadata" :
{
	"formatVersion" : 3.2,
	"type"          : "scene",
	"sourceFile"    : "game.blend",
	"generatedBy"   : "Blender 2.66 Exporter",
	"objects"       : 6,
	"geometries"    : 2,
	"materials"     : 2,
	"textures"      : 0
},

"urlBaseType" : "relativeToScene",


"objects" :
{
	"BaseGreen" : {
		"geometry"  : "geo_baseGreen",
		"groups"    : [  ],
		"material"  : "baseGreen",
		"position"  : [ -7, 1, -5 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ],
		"visible"       : true,
		"castShadow"    : false,
		"receiveShadow" : false,
		"doubleSided"   : false
	},

	"Floor" : {
		"geometry"  : "geo_Floor",
		"groups"    : [  ],
		"material"  : "floor",
		"position"  : [ 0, 0, 0 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 15, 15, 15 ],
		"visible"       : true,
		"castShadow"    : false,
		"receiveShadow" : false,
		"doubleSided"   : false
	},

	"Light1" : {
		"groups"    : [  ],
		"position"  : [ 4.07625, 5.90386, -1.00545 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ]
	},

	"CameraTarget" : {
		"groups"    : [  ],
		"position"  : [ 0, -6.84571e-08, 2 ],
		"rotation"  : [ -1.5708, 0, 0 ],
		"quaternion": [ -0.707107, 0, 0, 0.707107 ],
		"scale"     : [ 1, 1, 1 ]
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
	"geo_baseGreen" : {
		"type" : "ascii",
		"url"  : "game.baseGreen.js"
	},

	"geo_Floor" : {
		"type" : "ascii",
		"url"  : "game.Floor.js"
	}
},


"materials" :
{
	"baseGreen" : {
		"type": "MeshLambertMaterial",
		"parameters": { "color": 379904, "opacity": 1, "blending": "NormalBlending" }
	},

	"floor" : {
		"type": "MeshLambertMaterial",
		"parameters": { "color": 13421772, "opacity": 1, "blending": "NormalBlending" }
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
