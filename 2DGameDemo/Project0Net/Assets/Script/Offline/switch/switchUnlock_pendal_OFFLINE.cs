using UnityEngine;
using System.Collections;

public class switchUnlock_pendal_OFFLINE : MonoBehaviour {

	Animator switchAnimator = null;
	bool unlocked = false;
	float cd = 0;
	// Use this for initialization
	void Start () {

		switchAnimator = GetComponent<Animator> ();
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
		} else {
			if (switchAnimator.GetInteger ("statu") == 2) {
				switchAnimator.SetInteger ("statu", 0);
			}
		}
		if (cd != 0) {
			cd += Time.deltaTime;
			if (cd >= 0.6) {
				cd = 0;
			}
		}
	}

	public void switchUnlockFunc(){
		if(cd!=0){
			return;
		}
		if (!unlocked) {
			unlocked = true;
			GameObject.Find ("pendal_" + this.transform.name).GetComponent<pendalRotate_OFFLINE> ().setPendalRotate ();
		} else {
			unlocked = false;
			GameObject.Find ("pendal_" + this.transform.name).GetComponent<pendalRotate_OFFLINE> ().setPendalRotateBack ();
		}
		cd += Time.deltaTime;
	}

	void OnTriggerStay2D(Collider2D col){
		if(col.gameObject.tag == "Player"){
			col.gameObject.GetComponent<moveCtrl_OFFLINE> ().setInteractable (true, "switch_pendal", this.transform.name);
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.tag == "Player") {
			col.gameObject.GetComponent<moveCtrl_OFFLINE> ().setInteractable (false, "", "");
		}
	}
}
