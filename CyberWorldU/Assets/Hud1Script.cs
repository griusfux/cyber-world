using UnityEngine;
using System.Collections;

public class Hud1Script : MonoBehaviour {

	PlayerScript playerScript;
	//GUI.L

	// Use this for GUI
	void OnGUI () {
		GUI.Box (new Rect (10, 10, 100, 90), "Info");

		GUI.Label(new Rect(20, 25, 80, 20), "Energy: ");
		GUI.Label(new Rect(80, 25, 20, 20), playerScript.energy.ToString("0"));
	
		if (GUI.Button (new Rect (20, 50, 80, 20), "Build")) {
			print ("You clicked the button!");
			playerScript.addUnit();
			//Player.addUnit ();
		}
	}

	// Use this for initialization
	void Start () {
		playerScript = gameObject.GetComponent<PlayerScript>();
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
