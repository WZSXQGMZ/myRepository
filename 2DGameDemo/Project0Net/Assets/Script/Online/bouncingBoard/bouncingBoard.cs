using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class bouncingBoard : NetworkBehaviour {

	bool player0Entered = false;
	bool player1Entered = false;
	float bouncingSpeed = 9.0f;
	GameObject player0;
	GameObject player1;
	bool isBoardUP;

	// Use this for initialization
	void Start () {
		if (transform.name == "board_up") {
			isBoardUP = true;
		} else if (transform.name == "board_down") {
			isBoardUP = false;
		}
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OnTriggerEnter2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer ("Player0")) {
			Debug.Log ("Player0 entered bouncing board");
			player0Entered = true;
			player0 = col.gameObject;
		} else if (col.gameObject.layer == LayerMask.NameToLayer ("Player1")) {
			Debug.Log ("Player1 entered bouncing board");
			player1Entered = true;
			player1 = col.gameObject;
		}

		if (col.gameObject.GetComponentInParent<Rigidbody2D> ().velocity.y >= 3.5f) {
			if (!isBoardUP) {
				Debug.Log ("player hit bouncing board");
				col.gameObject.GetComponentInParent<moveCtrl> ().setTriggeBouncingFlag (true);
			}
		} else if (col.gameObject.GetComponentInParent<Rigidbody2D> ().velocity.y <= -3.5f) {
			if (isBoardUP) {
				Debug.Log ("player hit bouncing board");
				col.gameObject.GetComponentInParent<moveCtrl> ().setTriggeBouncingFlag (true);
			}
		} else {
			return;
		}

		if (col.gameObject.GetComponentInParent<moveCtrl> ().isLocalPlayer) {
			Debug.Log ("local player hit bouncing board");
			if (isBoardUP) {
				if (player0Entered) {
					transform.parent.transform.Find ("board_down").GetComponent<bouncingBoard> ().bouncePlayer (1);
				} else if (player1Entered) {
					transform.parent.transform.Find ("board_down").GetComponent<bouncingBoard> ().bouncePlayer (0);
				}
						
			} else if (!isBoardUP) {
				if (player0Entered) {
					transform.parent.transform.Find ("board_up").GetComponent<bouncingBoard> ().bouncePlayer (1);
				} else if (player1Entered) {
					transform.parent.transform.Find ("board_up").GetComponent<bouncingBoard> ().bouncePlayer (0);
				}
			}
		}
		
					
	}

	void OnTriggerStay2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer ("Player0") || col.gameObject.layer == LayerMask.NameToLayer ("Player1")) {
			if (col.gameObject.GetComponentInParent<moveCtrl> ().isLocalPlayer == false && col.gameObject.GetComponentInParent<moveCtrl> ().getTriggeBouncingFlag ()) {
				if (isBoardUP) {
					if (player0Entered) {
						transform.parent.transform.Find ("board_down").GetComponent<bouncingBoard> ().bouncePlayer (1);
					} else if (player1Entered) {
						transform.parent.transform.Find ("board_down").GetComponent<bouncingBoard> ().bouncePlayer (0);
					}

				} else if (!isBoardUP) {
					if (player0Entered) {
						transform.parent.transform.Find ("board_up").GetComponent<bouncingBoard> ().bouncePlayer (1);
					} else if (player1Entered) {
						transform.parent.transform.Find ("board_up").GetComponent<bouncingBoard> ().bouncePlayer (0);
					}
				}

				col.gameObject.GetComponentInParent<moveCtrl> ().setTriggeBouncingFlag (false);
			}
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer ("Player0")) {
			player0Entered = false;
			player0 = null;
		} else if (col.gameObject.layer == LayerMask.NameToLayer ("Player1")) {
			player1Entered = false;
			player1 = null;
		}
	}

	public void bouncePlayer(int playerToBounce){
		if (playerToBounce == 0 && player0Entered) {
			player0.GetComponent<moveCtrl> ().jumpWithVelocity (bouncingSpeed);
		} else if (playerToBounce == 1 && player1Entered) {
			player1.GetComponent<moveCtrl> ().jumpWithVelocity (bouncingSpeed);
		}
	}
		
}
