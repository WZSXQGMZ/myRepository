using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class terminal : NetworkBehaviour {

	Animator terminalAnimator = null;
	// Use this for initialization
	void Start () {
	
		terminalAnimator = this.GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	IEnumerator waitForAnim(float animTime){

		yield return new WaitForSeconds (animTime);
		terminalAnimator.SetInteger ("statu", 2);
		yield return new WaitForSeconds (animTime);
		NetworkManager.singleton.StopHost ();
	}

	public void getTerminal(){
		terminalAnimator.SetInteger ("statu", 1);
		StartCoroutine (waitForAnim (0.5f));
	}
}
