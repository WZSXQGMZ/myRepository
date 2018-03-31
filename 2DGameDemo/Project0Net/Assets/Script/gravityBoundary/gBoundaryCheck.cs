using UnityEngine;
using System.Collections;

public class gBoundaryCheck : MonoBehaviour {

	float lastY0;
	float lastX0;
	float lastY1;
	float lastX1;
	bool player0Entered = false;
	bool player1Entered = false;
	enum gBoundaryType {UP,DOWN,LEFT,RIGHT};
	gBoundaryType type;
	// Use this for initialization
	void Start () {
		if (tag == "gBoundaryUp") {
			type = gBoundaryType.UP;
		} else if (tag == "gBoundaryDown") {
			type = gBoundaryType.DOWN;
		} else if (tag == "gBoundaryLeft") {
			type = gBoundaryType.LEFT;
		} else if (tag == "gBoundaryRight") {
			type = gBoundaryType.RIGHT;
		}
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	void OnTriggerEnter2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer ("Player0") && !player0Entered) {
			Debug.Log ("palyer0Enter");
			player0Entered = true;
			lastX0 = col.transform.position.x;
			lastY0 = col.transform.position.y;
		} else if (col.gameObject.layer == LayerMask.NameToLayer ("Player1") && !player1Entered) {
			Debug.Log ("palyer1Enter");
			player1Entered = true;
			lastX1 = col.transform.position.x;
			lastY1 = col.transform.position.y;
		}
	}

	void OnTriggerStay2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer("Player0")) {
			if (player0Entered) {
				switch (type) {
				case gBoundaryType.UP:
					if (lastY0 >= transform.position.y && col.transform.position.y < transform.position.y) {
						changeColGravity (col, -1);
					} else if (lastY0 <= transform.position.y && col.transform.position.y > transform.position.y) {
						changeColGravity (col, 1);
					}
					break;
				case gBoundaryType.DOWN:
					if (lastY0 >= transform.position.y && col.transform.position.y < transform.position.y) {
						changeColGravity (col, 1);
					} else if (lastY0 <= transform.position.y && col.transform.position.y > transform.position.y) {
						changeColGravity (col, -1);
					}
					break;
				case gBoundaryType.LEFT:
					if (lastX0 >= transform.position.x && col.transform.position.x < transform.position.x) {
						changeColGravity (col, 1);
					} else if (lastX0 <= transform.position.x && col.transform.position.x > transform.position.x) {
						changeColGravity (col, -1);
					}
					break;
				case gBoundaryType.RIGHT:
					if (lastX0 >= transform.position.x && col.transform.position.x < transform.position.x) {
						changeColGravity (col, -1);
					} else if (lastX0 <= transform.position.x && col.transform.position.x > transform.position.x) {
						changeColGravity (col, 1);
					}
					break;
				default:
					break;
				}
			} else {
				player0Entered = true;
			}

			lastX0 = col.transform.position.x;
			lastY0 = col.transform.position.y;
		} else if (col.gameObject.layer == LayerMask.NameToLayer("Player1")) {
			if (player1Entered) {
				switch (type) {
				case gBoundaryType.UP:
					if (lastY1 >= transform.position.y && col.transform.position.y < transform.position.y) {
						changeColGravity (col, -1);
					} else if (lastY1 <= transform.position.y && col.transform.position.y > transform.position.y) {
						changeColGravity (col, 1);
					}
					break;
				case gBoundaryType.DOWN:
					if (lastY1 >= transform.position.y && col.transform.position.y < transform.position.y) {
						changeColGravity (col, 1);
					} else if (lastY1 <= transform.position.y && col.transform.position.y > transform.position.y) {
						changeColGravity (col, -1);
					}
					break;
				case gBoundaryType.LEFT:
					if (lastX1 >= transform.position.x && col.transform.position.x < transform.position.x) {
						changeColGravity (col, 1);
					} else if (lastX1 <= transform.position.x && col.transform.position.x > transform.position.x) {
						changeColGravity (col, -1);
					}
					break;
				case gBoundaryType.RIGHT:
					if (lastX1 >= transform.position.x && col.transform.position.x < transform.position.x) {
						changeColGravity (col, -1);
					} else if (lastX1 <= transform.position.x && col.transform.position.x > transform.position.x) {
						changeColGravity (col, 1);
					}
					break;
				default:
					break;
				}
			} else {
				player1Entered = true;
			}
			lastX1 = col.transform.position.x;
			lastY1 = col.transform.position.y;
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if (col.gameObject.layer == LayerMask.NameToLayer ("Player0")) {
			player0Entered = false;
		} else if (col.gameObject.layer == LayerMask.NameToLayer ("Player1")) {
			player1Entered = false;
		}
	}

	void changeColGravity(Collider2D col, int gravity){
		if (SceneInfo.getPlaymode () == 0) {
			col.GetComponent<moveCtrl_OFFLINE> ().changeGravity (gravity);
		} else {
			col.GetComponent<moveCtrl> ().changeGravity (gravity);
		}
	}
}
