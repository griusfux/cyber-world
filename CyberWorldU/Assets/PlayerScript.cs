using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public GameObject enemy;
	public NavMeshAgent meshAgent;
	public Transform spawn;
	public float energy = 5.0f;
	public float energyRegenSpeed = 0.01f;

	public void addUnit() {
		if (energy < 5) {
			print ("not enough energy");
			return;
		}

		GameObject newUnit = (GameObject)Instantiate (enemy, spawn.position, Quaternion.identity);
		meshAgent = newUnit.AddComponent<NavMeshAgent> ();
		meshAgent.SetDestination(new Vector3(120, -45, -50));
		meshAgent.speed = 30;
		meshAgent.acceleration = 15;
		meshAgent.angularSpeed = 700;
		meshAgent.stoppingDistance = 2;

		energy -= 5;
		//print (meshAgent.speed);
	}
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void FixedUpdate () {
		energy += energyRegenSpeed;
	}
}
