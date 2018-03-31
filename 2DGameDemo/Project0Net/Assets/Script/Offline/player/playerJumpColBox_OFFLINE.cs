﻿using UnityEngine;
using System.Collections;

public class playerJumpColBox_OFFLINE : MonoBehaviour {


	// Use this for initialization
	void Start () {

	}

	// Update is called once per frame
	void Update () {

	}

	void OnTriggerStay2D(Collider2D col){
		if (col.gameObject.tag == "ground") {
			Debug.Log("Hit floor");
			this.GetComponentInParent<moveCtrl_OFFLINE> ().setJumpAvaliable (true);
		} else if (col.gameObject.tag == "Player") {
			Debug.Log("Hit player");
			this.GetComponentInParent<moveCtrl_OFFLINE> ().setJumpAvaliable (true);
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.tag == "ground") {
			this.GetComponentInParent<moveCtrl_OFFLINE> ().setJumpAvaliable (false);
			//Debug.Log("Hit floor");
		} else if (col.gameObject.tag == "Player") {
			this.GetComponentInParent<moveCtrl_OFFLINE> ().setJumpAvaliable (false);
		}
	}
}