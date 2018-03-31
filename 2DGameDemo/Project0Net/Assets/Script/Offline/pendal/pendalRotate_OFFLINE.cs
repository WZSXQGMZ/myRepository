using UnityEngine;
using System.Collections;

public class pendalRotate_OFFLINE : MonoBehaviour {

	bool pendalRotated = false;
	Animator pendalAnimator = null;

	// Use this for initialization
	void Start () {
	
		pendalAnimator = GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update () {
		statuCheck ();
	}

	public void setPendalRotate(){
		pendalRotated = true;
	}

	public void setPendalRotateBack(){
		pendalRotated = false;
	}

	void statuCheck(){

		if (pendalRotated) {
			this.gameObject.tag = "Untagged";
			if (pendalAnimator.GetInteger ("statu") == 0) {
				pendalAnimator.SetInteger ("statu", 1);
			}else if(pendalAnimator.GetInteger ("statu") == 1){
				pendalAnimator.SetInteger ("statu", 2);
			}
		} else {
			this.gameObject.tag = "ground";
			if (pendalAnimator.GetInteger ("statu") == 2) {
				pendalAnimator.SetInteger ("statu", 3);
			}else if(pendalAnimator.GetInteger ("statu") == 3){
				pendalAnimator.SetInteger ("statu", 0);
			}
		}
	}
}
