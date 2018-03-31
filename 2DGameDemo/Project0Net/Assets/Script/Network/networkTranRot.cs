using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class networkTranRot : NetworkBehaviour {

	float lerpSpeed = 0.5f;
	[SyncVar] Quaternion hostRotate;

	// Update is called once per frame
	void FixedUpdate () {
		if (isLocalPlayer) {
			CmdSetHostPosition (this.transform.rotation);
		} else {
			lerpPosition ();
		}
	}

	[Command]
	void CmdSetHostPosition(Quaternion rotate){
		hostRotate = rotate;
	}

	void lerpPosition(){
		this.transform.rotation = Quaternion.Slerp (this.transform.rotation, hostRotate, lerpSpeed);
	}
}
