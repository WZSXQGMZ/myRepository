using UnityEngine;
using System.Collections;

public class switchUnlock_OFFLINE : MonoBehaviour {
	
	Animator switchAnimator = null;
	bool unlocked = false;
	// Use this for initialization
	void Start () {

		switchAnimator = this.GetComponent<Animator> ();
	}

	// Update is called once per frame
	void Update () {

		statuCheck ();
	}

	void statuCheck(){
		if (unlocked) {
			if (switchAnimator.GetInteger ("statu") == 1) {
				switchAnimator.SetInteger ("statu", 2);
			} else if (switchAnimator.GetInteger ("statu") == 0) {
				switchAnimator.SetInteger ("statu", 1);
			}
		}
	}

	public void switchUnlockFunc(){
		Debug.Log ("get switch unlock");
		unlocked = true;
		Debug.Log ("dooropenFunc call");
		GameObject.Find ("door_"+this.transform.name).GetComponent<doorOpen_OFFLINE> ().setDoorOpened ();
	}
		
	void OnTriggerStay2D(Collider2D col){
		if(col.gameObject.tag == "Player"){
			col.gameObject.GetComponent<moveCtrl_OFFLINE> ().setInteractable (true, "switch_door", this.transform.name);
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.tag == "Player") {
			col.gameObject.GetComponent<moveCtrl_OFFLINE> ().setInteractable (false, "", "");
		}
	}
}
