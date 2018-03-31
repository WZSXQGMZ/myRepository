using UnityEngine;
using System.Collections;
using UnityEngine.Networking;

public class keyBdMoveCtrl : NetworkBehaviour {

	public bool jumpAvaliable = true;
	bool walkingRight = true;
	bool cameraRotated = false;
	bool ctrlable = true;

	Animator roleAnimator = null;
	Animator mainCamAnimator = null;

	float JUMPSPEED = 4F;
	float MAXSPEED = 1.5F;
	float ACCELERATION = 2F;
	float ACCELERATION_GROUND = 1F;
	//float ROTATIONSPEED = 360F;
	float GRAVITYSCALE = 1F;
	float speed;
	float lastY;
	float camRotateCd = 0;

	int playerDirect = 0;
	bool jumpButton = false;
	bool rotCamButton = false;

	// Use this for initialization
	void Start () {
		
		if (isLocalPlayer) {
			this.gameObject.name = "Player";
		}
		this.transform.FindChild ("PlayerSprite/PlayerColBox").gameObject.AddComponent<keyBdPlayerJumpColBox> ();
		roleAnimator = this.GetComponent<Animator> ();
		mainCamAnimator = Camera.main.GetComponent<Animator> ();

		speed = 0;
		lastY = this.transform.position.y;

	}

	// Update is called once per frame
	void Update () {

		//actionCtrl ();
		if (isLocalPlayer) {
			
			playerMoveCtrl ();
			if (mainCamAnimator != null) {
				rotateCam ();
				Camera.main.GetComponent<mainCameraCtrl> ().setCamFollow (this.transform.position);
			} else {
				mainCamAnimator = Camera.main.GetComponent<Animator> ();
			}
		}

		gravityCheck ();

	}

	//move control
	void playerMoveCtrl(){
		//move Right or Left
		if (!ctrlable)
			return;
		if ((Input.GetKey(KeyCode.A) && !cameraRotated) || (Input.GetKey(KeyCode.D) && cameraRotated)/*&& !attacking*/) {
			Debug.Log ("Hit a");
			if (walkingRight && speed <= 0) {
				flip ();
			} else if (walkingRight && speed > 0) {
				speed -= ACCELERATION * Time.deltaTime;
			} else {
				if (speed < MAXSPEED)
					speed += ACCELERATION * Time.deltaTime;
			}
			this.transform.Translate (speed * Time.deltaTime * this.GetComponent<Rigidbody2D> ().gravityScale, 0, 0);
			//set Animator
			if (roleAnimator.GetInteger ("walkStatu") != 1 && jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 1);
			else if(!jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 0);
			
		} else if (Input.GetKey(KeyCode.A) || Input.GetKey(KeyCode.D) /*&& !attacking*/) {
			Debug.Log ("Hit d");
			if (!walkingRight && speed <= 0) {
				flip ();
			} else if (!walkingRight && speed > 0) {
				speed -= ACCELERATION * Time.deltaTime;
			} else {
				if (speed < MAXSPEED)
					speed += ACCELERATION * Time.deltaTime;
			}
			this.transform.Translate (speed * Time.deltaTime * this.GetComponent<Rigidbody2D> ().gravityScale, 0, 0);
			//set Animator
			if (roleAnimator.GetInteger ("walkStatu") != 1 && jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 1);
			else if(!jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 0);
		} else {
			if (speed > 0.05f) {

				speed -= ACCELERATION_GROUND * Time.deltaTime;
				this.transform.Translate (speed * Time.deltaTime * this.GetComponent<Rigidbody2D> ().gravityScale, 0, 0);

			} else if (speed < -0.05f) {

				speed += ACCELERATION_GROUND * Time.deltaTime;
				this.transform.Translate (speed * Time.deltaTime * this.GetComponent<Rigidbody2D> ().gravityScale, 0, 0);

			} else {
				speed = 0;
			}

			if (jumpAvaliable /*&& !attacking*/) {
				//set Animator
				if (roleAnimator.GetInteger ("walkStatu") != 0)
					roleAnimator.SetInteger ("walkStatu", 0);
			} 
		}
		//Jump
		if (Input.GetKey(KeyCode.Space) /*&& !attacking*/) {
			Debug.Log ("Hit space");

			if (jumpAvaliable /*&& !attacking*/) {
				Debug.Log ("jumpAvaliable and Hit Space");
				this.GetComponent<Rigidbody2D> ().velocity = new Vector2 (this.GetComponent<Rigidbody2D> ().velocity.x, JUMPSPEED*this.GetComponent<Rigidbody2D> ().gravityScale);
				jumpAvaliable = false;
				//set Animator
				if(roleAnimator.GetInteger("statu") != 0)
					roleAnimator.SetInteger ("statu", 0);
			}
		} else if (this.GetComponent<Rigidbody2D> ().velocity.y * this.GetComponent<Rigidbody2D> ().gravityScale < 0 /*&& !attacking*/) {
			jumpAvaliable = false;
			Debug.Log ("falling");

		}

		//Rotate camera
		if (Input.GetKey (KeyCode.R)) {
			setRotCamButton (true);
		}

		if (Input.GetKeyDown (KeyCode.E)) {
			Camera.main.GetComponentInChildren<buttonIteract> ().setButtonDown (true);
		}else if (Input.GetKeyUp (KeyCode.E)) {
			Camera.main.GetComponentInChildren<buttonIteract> ().setButtonDown (false);
		}
	}

	void OnTriggerStay2D(Collider2D col){

		if (col.gameObject.tag == "switch" && Camera.main.GetComponentInChildren<buttonIteract> ().getButtonDown () && isLocalPlayer) {
			CmdUnlockSwitch (col.gameObject.name);
		}
	}
	[Command]
	void CmdUnlockSwitch(string switchName){
		GameObject.Find (switchName).GetComponent<switchUnlock> ().switchUnlockFunc ();
	}

	[ClientRpc]
	void Rpcflip(){

		this.transform.localScale += new Vector3 (-2*this.transform.localScale.x, 0, 0);
		Debug.Log ("flip");
	}
	[Command]
	void Cmdflip(){
		Rpcflip ();
	}

	void flip(){
		Cmdflip();
		walkingRight = !walkingRight;
		speed *= -1;
	}

	float terrainFunc (float x){
		return -2.395f;
	}

	void gravityCheck(){
		float y = terrainFunc (this.transform.position.x);
		if (this.transform.position.y >= y) {
			this.GetComponent<Rigidbody2D> ().gravityScale = GRAVITYSCALE;
			if (lastY < y) {
				roleAnimator.SetInteger ("statu", 1);
				this.transform.localScale += new Vector3 (-2*this.transform.localScale.x, 0, 0);
			} else {
				roleAnimator.SetInteger ("statu", 0);
			}
			lastY = this.transform.position.y;
		} else {
			this.GetComponent<Rigidbody2D> ().gravityScale = -GRAVITYSCALE;
			if (lastY >= y) {
				roleAnimator.SetInteger ("statu", -1);
				this.transform.localScale += new Vector3 (-2*this.transform.localScale.x, 0, 0);
			} else {
				roleAnimator.SetInteger ("statu", 2);
			}
			lastY = this.transform.position.y;
		}
	}

	public void rotateCam(){
		if (cameraRotated) {
			mainCamAnimator.SetInteger ("statu", 2);
		} else {
			mainCamAnimator.SetInteger ("statu", 0);
		}

		if (rotCamButton && camRotateCd == 0) {
			if (cameraRotated) {
				mainCamAnimator.SetInteger ("statu", 1);
			} else {
				mainCamAnimator.SetInteger ("statu", -1);
			}
			cameraRotated = !cameraRotated;
			//set rotate cd
			camRotateCd += Time.deltaTime;
		} else if (camRotateCd <= 0.6f && camRotateCd > 0) {
			//set rotate cd
			camRotateCd += Time.deltaTime;
		} else {
			//set rotate cd
			camRotateCd = 0;
		}
		rotCamButton = false;
	}

	public void setRotCamButton(bool value){
		rotCamButton = value;
	}

	public void changeCameraRotated(){
		cameraRotated = !cameraRotated;
	}
		
	public void setJumpAvaliable(bool avaliable){
		jumpAvaliable = avaliable;
	}

	public void setDirect(int direct){
		if (direct != -1 && direct != 0 && direct != 1) {
			playerDirect = 0;
		} else {
			playerDirect = direct;
		}
	}

	public void setJumpButton(bool value){
		jumpButton = value;
	}



	public void setCtrlable(bool value){
		ctrlable = value;
		if (!value) {
			speed = 0;
			roleAnimator.SetInteger ("walkStatu", 0);
		}
	}

	public bool getJumpAvaliable(){
		return jumpAvaliable;
	}

	public float getSpeed(){
		return speed;
	}
}
