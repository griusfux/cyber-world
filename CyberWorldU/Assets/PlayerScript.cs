﻿using UnityEngine;
using System.Collections;

public class PlayerScript : MonoBehaviour {

	public GameObject enemy;

	public void addUnit() {
		Instantiate (enemy);
	}
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
