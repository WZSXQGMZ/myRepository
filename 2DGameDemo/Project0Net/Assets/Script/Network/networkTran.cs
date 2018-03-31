using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.Networking;

public class networkTran : NetworkBehaviour {
	private float lerpSpeed = 0.5f;
	private float normalLerpSpeed = 0.5f;
	private float fastLerpSpeed = 0.7f;
	[SyncVar] Vector3 hostPosition;
	[SyncVar] float localScare = 1f;
	[SyncVar] bool triBounceFlag = false;
	private float closeEnough = 0.01f;
	private List<Vector3> syncPosList = new List<Vector3>();

	// Use this for initialization
	void Start () {
	
	}

	void Update(){
		if(!isLocalPlayer){
			lerpPosition ();
			syncScale ();
			syncTriggerBouncingFlag ();
		}
	}
	void FixedUpdate(){
		if (isLocalPlayer && isServer) {
			RpcSetHostPosition (transform.position);
			RpcSetLocalScale (transform.localScale.x);
			RpcSetTriggerBouncingFlag (GetComponent<moveCtrl> ().getTriggeBouncingFlag ());
		} else if (isLocalPlayer && !isServer) {
			CmdSetHostPosition (transform.position);
			CmdSetLocalScale (transform.localScale.x);
			CmdSetTriggerBouncingFlag (GetComponent<moveCtrl> ().getTriggeBouncingFlag ());
		}
	}
	/*
	 * synchronize position
	 * */
	[Command]
	void CmdSetHostPosition(Vector3 position){
		hostPosition = position;
		//RpcSetHostPosition(position);
	}
	[ClientRpc]
	void RpcSetHostPosition(Vector3 position){
		hostPosition = position;
	}
	void lerpPosition(){
		this.transform.position = Vector3.Lerp (this.transform.position, hostPosition, lerpSpeed);
	}

	/*
	 * synchronize scale
	 * */
	[Command]
	void CmdSetLocalScale(float scale){
		localScare = scale;
	}
	[ClientRpc]
	void RpcSetLocalScale(float scale){
		localScare = scale;
	}
	void syncScale(){
		transform.localScale = new Vector3 (localScare, transform.localScale.y, transform.localScale.z);;
	}

	/*
	 * synchronize triggeBounceFlag
	 * */
	[Command]
	void CmdSetTriggerBouncingFlag(bool flag){
		triBounceFlag = flag;
	}
	[ClientRpc]
	void RpcSetTriggerBouncingFlag(bool flag){
		triBounceFlag = flag;
	}
	void syncTriggerBouncingFlag(){
		GetComponent <moveCtrl> ().setTriggeBouncingFlag (triBounceFlag);
	}
		
}
