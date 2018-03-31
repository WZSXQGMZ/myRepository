using UnityEngine;
using System.Collections;

public class buttonIteract : MonoBehaviour {

	bool buttonDown = false;
	// Use this for initialization
	void Start () {
	
	}

	// Update is called once per frame
	void Update () {
	
	}

	public void setButtonDown(bool value){
		Debug.Log ("iterButtonDown");
		buttonDown = value;
	}

	public bool getButtonDown(){
		return buttonDown;
	}
}
