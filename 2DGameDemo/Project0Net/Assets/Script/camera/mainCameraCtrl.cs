using UnityEngine;
using System.Collections;

public class mainCameraCtrl : MonoBehaviour {
	
	GameObject player = null;
	bool cameraMove = false;
	bool cameraTouchMove = false;
	float delayTime = 0;
	float currTime = 0;
	int cameraRotated = 1;
	//Animator mainCamAnimator = null;
	//float camRotateCd = 0;
	//bool rotCamButton = false;
	//bool cameraRotated = false

	Vector2 lastVec,currVec,lastVec1,currVec1;
	Vector3 worldPos;
	float deltaSize;
	float currSize;

	float TOP = 2.4f;
	float BOTTOM = -7.19f;
	float LEFT = -3.2f;
	float RIGHT = 9.6f;
	//float CAMERA_Y_OFFSET = 0F;
	float CAMERA_LEFT_BOUNDARY = 0;
	float CAMERA_RIGHT_BOUNDARY = 0;
	float CAMERA_TOP_BOUNDARY = 0;
	float CAMERA_BOTTOM_BOUNDARY = 0;
	float CAMERA_TOUCH_MOVE_SPEED = 2.6F;
	float CAMERA_SCALE_SPEED = 5F;
	float CAMERA_LW_RATIO = 16 / 9;
	float CAMERA_MIN_SIZE = 2.5F;
	float CAMERA_MAX_SIZE = 3.5F;
	//float CAMERA_TOUCHMOVE_VERTICAL_SPEED = 3.5F;
	//float CAMERA_TOUCHMOVE_HORIZONTAL_SPEED = 4.5F;
	// Use this for initialization
	void Start () {
		TOP = GameObject.Find ("mapBoundaryRU").transform.position.y;
		BOTTOM = GameObject.Find ("mapBoundaryLD").transform.position.y;
		LEFT = GameObject.Find ("mapBoundaryLD").transform.position.x;
		RIGHT = GameObject.Find ("mapBoundaryRU").transform.position.x;
		Debug.Log (Screen.width);
		Debug.Log (Screen.height);
		CAMERA_LW_RATIO = (float)Screen.width / Screen.height;
		Debug.Log (CAMERA_LW_RATIO);
		CAMERA_LEFT_BOUNDARY = LEFT + Camera.main.orthographicSize * CAMERA_LW_RATIO;
		CAMERA_RIGHT_BOUNDARY = RIGHT - Camera.main.orthographicSize * CAMERA_LW_RATIO;
		CAMERA_TOP_BOUNDARY = TOP - Camera.main.orthographicSize;
		CAMERA_BOTTOM_BOUNDARY = BOTTOM + Camera.main.orthographicSize;

		//mainCamAnimator = Camera.main.GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update () {
		if (player == null) {
			player = GameObject.Find ("Player");
		}
		cameraTouchFollow ();
		//cameraFollow ();
		cameraStatuCheck ();
		//rotateCam ();
	}
	/*
	void cameraFollow(){
		if (cameraMove || cameraTouchMove)
			return;
		
		if (player.transform.position.x <= CAMERA_LEFT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_LEFT_BOUNDARY, player.transform.position.y, Camera.main.transform.position.z);
		} else if (player.transform.position.x >= CAMERA_RIGHT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_RIGHT_BOUNDARY, player.transform.position.y, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (player.transform.position.x, player.transform.position.y, Camera.main.transform.position.z);
		}

		if (player.transform.position.y <= CAMERA_BOTTOM_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_BOTTOM_BOUNDARY, Camera.main.transform.position.z);
		} else if (player.transform.position.y >= CAMERA_TOP_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_TOP_BOUNDARY, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, player.transform.position.y, Camera.main.transform.position.z);
		}

		//Camera.main.transform.Rotate (new Vector3 (0, 0, player.GetComponentInChildren<Transform> ().rotation.z * player.GetComponent<Rigidbody2D> ().gravityScale));
		//Camera.main.transform.rotation = Quaternion.Euler(0, 0, playerSprite.transform.rotation.z);
	}
	*/
		
	void cameraStatuCheck(){

		if (currTime > delayTime) {
			currTime = 0;
			delayTime = 0;
			cameraMove = false;
		}

		if (cameraMove) {
			currTime += Time.deltaTime;

		}
	}
		
	void cameraTouchFollow(){
	
		if (cameraMove)
			return;

		if (SceneInfo.getPlaymode() == 0) {
			if (player.GetComponent<moveCtrl_OFFLINE> ().getPlayerDirect() != 0 || player.GetComponent<Rigidbody2D> ().velocity.y != 0) {
				return;
			}
		} else {
			if (player.GetComponent<moveCtrl> ().getPlayerDirect() != 0 || player.GetComponent<Rigidbody2D> ().velocity.y != 0) {
				return;
			}
		}
		/*
		if (!player.GetComponent<keyBdMoveCtrl> ().getJumpAvaliable() || player.GetComponent<keyBdMoveCtrl> ().getSpeed() != 0)
			return;
		*/

		if (Input.touchCount == 1 && Input.GetTouch (0).phase == TouchPhase.Moved) {
			Debug.Log ("get touch move");
			currVec = Input.GetTouch (0).position;
			lastVec = currVec - Input.GetTouch (0).deltaPosition;
			worldPos = CAMERA_TOUCH_MOVE_SPEED * (GetComponent<Camera> ().ScreenToWorldPoint (currVec) - GetComponent<Camera> ().ScreenToWorldPoint (lastVec));
			//Vector3 viewportPos = cameraRotated * GetComponent<Camera> ().ScreenToViewportPoint (Input.GetTouch (0).deltaPosition);
			cameraTouchMove = true;
			Vector3 currPosition = Camera.main.transform.position - new Vector3 (worldPos.x, worldPos.y, Camera.main.transform.position.z);
			setCamPos (currPosition);
		} else if (Input.touchCount == 2 && (Input.GetTouch (0).phase == TouchPhase.Moved || Input.GetTouch (1).phase == TouchPhase.Moved)) {
		
			currVec = Input.GetTouch (0).position;
			lastVec = currVec - Input.GetTouch (0).deltaPosition;
			currVec1 = Input.GetTouch (1).position;
			lastVec1 = currVec1 - Input.GetTouch (1).deltaPosition;
			deltaSize = Vector3.Distance (GetComponent<Camera> ().ScreenToViewportPoint (currVec), GetComponent<Camera> ().ScreenToViewportPoint (currVec1))
				- Vector3.Distance (GetComponent<Camera> ().ScreenToViewportPoint (lastVec), GetComponent<Camera> ().ScreenToViewportPoint (lastVec1));
			currSize = Camera.main.orthographicSize - deltaSize * CAMERA_SCALE_SPEED;
			if (currSize > CAMERA_MAX_SIZE) {
				Camera.main.orthographicSize = CAMERA_MAX_SIZE;
			} else if (currSize < CAMERA_MIN_SIZE) {
				Camera.main.orthographicSize = CAMERA_MIN_SIZE;
			} else {
				Camera.main.orthographicSize = currSize;
			}

			CAMERA_LEFT_BOUNDARY = LEFT + Camera.main.orthographicSize * CAMERA_LW_RATIO;
			CAMERA_RIGHT_BOUNDARY = RIGHT - Camera.main.orthographicSize * CAMERA_LW_RATIO;
			CAMERA_TOP_BOUNDARY = TOP - Camera.main.orthographicSize;
			CAMERA_BOTTOM_BOUNDARY = BOTTOM + Camera.main.orthographicSize;
			setCamPos (Camera.main.transform.position);
		}
	}

	void setCamPos(Vector3 currPosition){
		
		if (currPosition.x <= CAMERA_LEFT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_LEFT_BOUNDARY, currPosition.y, Camera.main.transform.position.z);
		} else if (currPosition.x >= CAMERA_RIGHT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_RIGHT_BOUNDARY, currPosition.y, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (currPosition.x, currPosition.y, Camera.main.transform.position.z);
		}

		if (currPosition.y <= CAMERA_BOTTOM_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_BOTTOM_BOUNDARY, Camera.main.transform.position.z);
		} else if (currPosition.y >= CAMERA_TOP_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_TOP_BOUNDARY, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, currPosition.y, Camera.main.transform.position.z);
		}
	}

	public void setCamFollow(Vector3 currPosition){
		if (cameraMove || cameraTouchMove)
			return;
		if (currPosition.x <= CAMERA_LEFT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_LEFT_BOUNDARY, currPosition.y, Camera.main.transform.position.z);
		} else if (currPosition.x >= CAMERA_RIGHT_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (CAMERA_RIGHT_BOUNDARY, currPosition.y, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (currPosition.x, currPosition.y, Camera.main.transform.position.z);
		}

		if (currPosition.y <= CAMERA_BOTTOM_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_BOTTOM_BOUNDARY, Camera.main.transform.position.z);
		} else if (currPosition.y >= CAMERA_TOP_BOUNDARY) {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, CAMERA_TOP_BOUNDARY, Camera.main.transform.position.z);
		} else {
			Camera.main.transform.position = new Vector3 (Camera.main.transform.position.x, currPosition.y, Camera.main.transform.position.z);
		}
	}


	public void setCamera(Vector3 vector,float time){
		delayTime = time;
		setCamPos (vector);
		cameraMove = true;
	}

	public void setCameraTouchMove(bool value){
		cameraTouchMove = value;
	}

	public void changeCameraRotated(){
		cameraRotated *= -1;
	}

	public void setRotCamButton(bool value){
		//rotCamButton = value;
	}

	/*
	void OnGUI(){
	
		GUI.Label (new Rect (10, 10, 100, 200), GetComponent<Camera> ().ScreenToViewportPoint (currVec).ToString());
		GUI.Label (new Rect (10, 20, 100, 200), GetComponent<Camera> ().ScreenToViewportPoint (currVec1).ToString());
		GUI.Label (new Rect (10, 30, 100, 200), GetComponent<Camera> ().ScreenToViewportPoint (lastVec).ToString());
		GUI.Label (new Rect (10, 40, 100, 200), GetComponent<Camera> ().ScreenToViewportPoint (lastVec1).ToString());
		GUI.Label (new Rect (10, 50, 100, 200), (Vector3.Distance (GetComponent<Camera> ().ScreenToViewportPoint (currVec), GetComponent<Camera> ().ScreenToViewportPoint (currVec1))).ToString());
		GUI.Label (new Rect (10, 60, 100, 200), (Vector3.Distance (GetComponent<Camera> ().ScreenToViewportPoint (lastVec), GetComponent<Camera> ().ScreenToViewportPoint (lastVec1))).ToString());
	}
	*/


}
