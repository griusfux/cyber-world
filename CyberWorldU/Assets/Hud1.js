#pragma strict

function OnGUI () {
	// Make a background box
	GUI.Box(new Rect(10,10,100,90), "Info");
		
	if (GUI.Button (Rect (20,40,80,20), "Build")) {
		print ("You clicked the button!");
	}
}

function Start () {

}

function Update () {

}