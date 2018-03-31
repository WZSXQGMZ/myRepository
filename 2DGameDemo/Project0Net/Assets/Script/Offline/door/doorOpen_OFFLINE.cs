using UnityEngine;
using System.Collections;

public class doorOpen_OFFLINE : MonoBehaviour {

	bool doorOpened = false;
	Animator doorAnimator = null;

	// Use this for initialization
	void Start () {

		doorAnimator = this.GetComponent<Animator> ();
	}

	// Update is called once per frame
	void Update () {

		statuCheck ();
	}

	public void setDoorOpened(){
		doorOpened = true;
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

	public void setDoorOpen(){
		doorOpened = true;
	}
}
