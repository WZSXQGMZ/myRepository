using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class switchUnlock : NetworkBehaviour {

	Animator switchAnimator = null;
	[SyncVar] bool unlocked = false;
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
		unlocked = true;
		GameObject.Find ("door_"+this.transform.name).GetComponent<doorOpen> ().setDoorOpened ();
	}

	[Command]
	void CmdSwitchUnlockFunc(){
		Debug.Log ("cmd unlock");
		unlocked = true;
	}
	[ClientRpc]
	void RpcSwitchUnlockFunc(){
		Debug.Log ("rpc unlock");
		unlocked = true;
	}
		
}
