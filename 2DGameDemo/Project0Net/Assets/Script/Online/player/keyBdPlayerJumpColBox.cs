using UnityEngine;
using System.Collections;

public class keyBdPlayerJumpColBox : MonoBehaviour {

	// Use this for initialization
	void Start () {

	}

	// Update is called once per frame
	void Update () {

	}

	void OnTriggerStay2D(Collider2D col){
		if (col.gameObject.tag == "ground") {
			Debug.Log("Hit floor");
			this.GetComponentInParent<keyBdMoveCtrl> ().setJumpAvaliable (true);
		} else if (col.gameObject.tag == "Player") {
			Debug.Log("Hit player");
			this.GetComponentInParent<keyBdMoveCtrl> ().setJumpAvaliable (true);
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.tag == "ground") {
			this.GetComponentInParent<keyBdMoveCtrl> ().setJumpAvaliable (false);
			//Debug.Log("Hit floor");
		} else if (col.gameObject.tag == "Player") {
			this.GetComponentInParent<keyBdMoveCtrl> ().setJumpAvaliable (false);
		}
	}
}
