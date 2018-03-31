using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ctrlButton : MonoBehaviour {

	GameObject player;
	int playmode = 0;
	// Use this for initialization
	void Start () {
		player = GameObject.Find ("Player");
		playmode = SceneInfo.getPlaymode ();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void setDirectLeft(){
		if (playmode == 0) {
			GameObject.Find ("Player").GetComponent<moveCtrl_OFFLINE> ().setDirect (-1);
		} else {
			GameObject.Find ("Player").GetComponent<moveCtrl> ().setDirect (-1);
		}
	}

	public void setDirectRight(){
		if (playmode == 0) {
			GameObject.Find ("Player").GetComponent<moveCtrl_OFFLINE> ().setDirect (1);
		} else {
			GameObject.Find ("Player").GetComponent<moveCtrl> ().setDirect (1);
		}
	}

	public void setDirectOff(){
		if (playmode == 0) {
			GameObject.Find ("Player").GetComponent<moveCtrl_OFFLINE> ().setDirect (0);
		} else {
			GameObject.Find ("Player").GetComponent<moveCtrl> ().setDirect (0);
		}
	}

	public void setJumpOn(bool value){
		Debug.Log ("click jump button");
		if (value) {
			if (playmode == 0) {
				GameObject.Find ("Player").GetComponent<moveCtrl_OFFLINE> ().jump ();
			} else {
				GameObject.Find ("Player").GetComponent<moveCtrl> ().jump ();
			}
		}
	}

	public void rotateCam(){
		if (playmode == 0) {
			GameObject.Find ("Player").GetComponent<moveCtrl_OFFLINE> ().setRotCamButton (true);
		} else {
			GameObject.Find ("Player").GetComponent<moveCtrl> ().setRotCamButton (true);
		}
	}
}
