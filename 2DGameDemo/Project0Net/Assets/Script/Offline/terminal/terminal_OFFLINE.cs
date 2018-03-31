using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class terminal_OFFLINE : MonoBehaviour {

	Animator terminalAnimator = null;
	// Use this for initialization
	void Start () {
	
		terminalAnimator = this.GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnTriggerEnter2D(Collider2D col){
		if (col.gameObject.tag == "Player"){
			Debug.Log ("get terminal");
			col.gameObject.GetComponent<moveCtrl>().setCtrlable(false);
			terminalAnimator.SetInteger ("statu", 1);
			StartCoroutine(waitForAnim(0.5f));
		}
	}

	IEnumerator waitForAnim(float animTime){

		yield return new WaitForSeconds (animTime);
		terminalAnimator.SetInteger ("statu", 2);
		yield return new WaitForSeconds (animTime);
		SceneManager.LoadScene ("StartScene");
	}


}
