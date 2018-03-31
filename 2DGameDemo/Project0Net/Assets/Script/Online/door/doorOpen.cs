using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class doorOpen : NetworkBehaviour {

	[SyncVar] bool doorOpened = false;
	Animator doorAnimator = null;
	// Use this for initialization
	void Start () {
	
		doorAnimator = this.GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update () {
	
		statuCheck ();
	}



	void statuCheck(){
		if (doorOpened) {
			if (doorAnimator.GetInteger ("statu") == 1) {
				doorAnimator.SetInteger ("statu", 2);
			} else if (doorAnimator.GetInteger ("statu") == 0) {
				doorAnimator.SetInteger ("statu", 1);
			}
		}
	}

	public void setDoorOpened(){
		doorOpened = true;
	}

	[Command]
	void CmdsetDoorOpened(){
		doorOpened = true;
	}

}
