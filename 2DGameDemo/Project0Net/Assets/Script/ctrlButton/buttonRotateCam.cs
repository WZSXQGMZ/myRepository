using UnityEngine;
using System.Collections;

public class buttonRotateCam : MonoBehaviour {

	bool buttonDown = false;
	// Use this for initialization
	void Start () {

	}

	// Update is called once per frame
	void Update () {

	}

	public void setButtonDown(bool value){
		buttonDown = value;
	}

	public bool getButtonDown(){
		return buttonDown;
	}
}
