using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public GameObject enemy;
	public NavMeshAgent meshAgent;
	public Transform spawn;

	public void addUnit() {
		GameObject newUnit = (GameObject)Instantiate (enemy, spawn.position, Quaternion.identity);
		meshAgent = newUnit.AddComponent<NavMeshAgent> ();
		meshAgent.SetDestination(new Vector3(120, -45, -50));
		meshAgent.speed = 30;
		meshAgent.acceleration = 15;
		meshAgent.angularSpeed = 700;
		//print (meshAgent.speed);
	}
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
