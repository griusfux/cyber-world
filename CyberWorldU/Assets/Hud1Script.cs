using UnityEngine;
using System.Collections;

public class Hud1Script : MonoBehaviour {

	// Use this for GUI
	void OnGUI () {
		GUI.Box (new Rect (10, 10, 100, 90), "Info");
	
		if (GUI.Button (new Rect (20, 40, 80, 20), "Build")) {
			print ("You clicked the button!");
			PlayerScript player = gameObject.GetComponent<PlayerScript>();
			player.addUnit();
			//Player.addUnit ();
		}
	}

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
