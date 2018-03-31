using UnityEngine;
using System.Collections;
using UnityEngine.Networking;
using UnityEngine.UI;

public class moveCtrl : NetworkBehaviour {

	public bool jumpAvaliable = true;
	bool walkingRight = true;
	bool cameraRotated = false;
	bool ctrlable = true;
	[SyncVar] bool triggeBouncingFlag = false;
	float bouncingSpeed = 7.0f;

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
		} else {
			GetComponent<Rigidbody2D> ().gravityScale = 0;
		}
		this.transform.FindChild ("PlayerSprite/PlayerColBox").gameObject.AddComponent<playerJumpColBox> ();
		roleAnimator = this.GetComponent<Animator> ();
		mainCamAnimator = Camera.main.GetComponent<Animator> ();

		speed = MAXSPEED;
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

		//gravityCheck ();

	}

	//move control
	void playerMoveCtrl(){
		//move Right or Left
		if (!ctrlable)
			return;
		if ((playerDirect == -1 && !cameraRotated) || (playerDirect == 1 && cameraRotated)/*&& !attacking*/) {
			Debug.Log ("Hit a");
			if (walkingRight) {
				flip ();
			}
			this.transform.Translate (speed * Time.deltaTime * -1 * GRAVITYSCALE, 0, 0);
			//GetComponent<Rigidbody2D> ().velocity = new Vector2(speed * GRAVITYSCALE, GetComponent<Rigidbody2D> ().velocity.y);
			//set Animator
			if (roleAnimator.GetInteger ("walkStatu") != 1 && jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 1);
			else if (!jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 0);

		} else if (playerDirect == -1 || playerDirect == 1 /*&& !attacking*/) {
			Debug.Log ("Hit d");
			if (!walkingRight) {
				flip ();
			}
			this.transform.Translate (speed * Time.deltaTime * 1 * GRAVITYSCALE, 0, 0);
			//GetComponent<Rigidbody2D> ().velocity = new Vector2(speed * GRAVITYSCALE, GetComponent<Rigidbody2D> ().velocity.y);
			//set Animator
			if (roleAnimator.GetInteger ("walkStatu") != 1 && jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 1);
			else if (!jumpAvaliable)
				roleAnimator.SetInteger ("walkStatu", 0);
		} else if (jumpAvaliable /*&& !attacking*/) {
			//set Animator
			if (roleAnimator.GetInteger ("walkStatu") != 0) {
				roleAnimator.SetInteger ("walkStatu", 0);
			} 
		}

		if (playerDirect == 0) {
			GetComponent<Rigidbody2D> ().velocity = new Vector2 (0, GetComponent<Rigidbody2D> ().velocity.y);
		}



		if (this.GetComponent<Rigidbody2D> ().velocity.y * this.GetComponent<Rigidbody2D> ().gravityScale < 0 /*&& !attacking*/) {
			jumpAvaliable = false;
			Debug.Log ("falling");

		}

	}

	public void jump(){

		if (jumpAvaliable) {
			Debug.Log ("jumpAvaliable and Hit Space");
			this.GetComponent<Rigidbody2D> ().velocity = new Vector2 (this.GetComponent<Rigidbody2D> ().velocity.x, JUMPSPEED * this.GetComponent<Rigidbody2D> ().gravityScale);
			jumpAvaliable = false;
			//set Animator
			if (roleAnimator.GetInteger ("statu") != 0)
				roleAnimator.SetInteger ("statu", 0);
		}
	}

	public void jumpWithVelocity(float velocity){
		if (jumpAvaliable) {
			this.GetComponent<Rigidbody2D> ().velocity = new Vector2 (this.GetComponent<Rigidbody2D> ().velocity.x, velocity * this.GetComponent<Rigidbody2D> ().gravityScale);
			jumpAvaliable = false;
			//set Animator
			if (roleAnimator.GetInteger ("statu") != 0)
				roleAnimator.SetInteger ("statu", 0);
		}
	}


	[Command]
	public void CmdJumpWithVelocity(float velocity){
		Debug.Log ("CmdJumpWithVelocity called");
		if (isLocalPlayer && jumpAvaliable) {
			Debug.Log ("CmdJumpWithVelocity set y velocity");
			jumpAvaliable = false;
			this.GetComponent<Rigidbody2D> ().velocity = new Vector2 (this.GetComponent<Rigidbody2D> ().velocity.x, velocity * this.GetComponent<Rigidbody2D> ().gravityScale);
			//set Animator
			if (roleAnimator.GetInteger ("statu") != 0) {
				roleAnimator.SetInteger ("statu", 0);
			}
			return;
		}
		RpcJumpWithVelocity (velocity);
	}
	[ClientRpc]
	public void RpcJumpWithVelocity(float velocity){
		Debug.Log ("RpcJumpWithVelocity called");
		if (isLocalPlayer && jumpAvaliable) {
			Debug.Log ("CmdJumpWithVelocity set y velocity");
			jumpAvaliable = false;
			this.GetComponent<Rigidbody2D> ().velocity = new Vector2 (this.GetComponent<Rigidbody2D> ().velocity.x, velocity * this.GetComponent<Rigidbody2D> ().gravityScale);
			//set Animator
			if (roleAnimator.GetInteger ("statu") != 0)
				roleAnimator.SetInteger ("statu", 0);
		}
	}
	 
	void OnTriggerStay2D(Collider2D col){

		if (col.gameObject.tag == "switch" && Camera.main.GetComponentInChildren<buttonIteract> ().getButtonDown () && isLocalPlayer) {
			CmdUnlockSwitch (col.gameObject.name);
		}

		if (col.gameObject.tag == "terminal" && isLocalPlayer) {
			Debug.Log ("get terminal");
			CmdGetTerminal (col.gameObject.name);
		}
	}
		
	[Command]
	void CmdUnlockSwitch(string switchName){
		GameObject.Find (switchName).GetComponent<switchUnlock> ().switchUnlockFunc ();
	}
	[Command]
	void CmdGetTerminal(string name){
		GameObject.Find (name).GetComponent<terminal> ().getTerminal ();
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
		
		transform.localScale += new Vector3 (-2*this.transform.localScale.x, 0, 0);
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

	public void changeGravity(int gravity){
		if (gravity == 1) {
			GRAVITYSCALE = 1;
			//CmdChangeGravity (GRAVITYSCALE);
			this.GetComponent<Rigidbody2D> ().gravityScale = GRAVITYSCALE;
			roleAnimator.SetInteger ("statu", 1);
			this.transform.localScale += new Vector3 (-2 * this.transform.localScale.x, 0, 0);
		} else if (gravity == -1) {
			GRAVITYSCALE = -1;
			//CmdChangeGravity (GRAVITYSCALE);
			this.GetComponent<Rigidbody2D> ().gravityScale = GRAVITYSCALE;
			roleAnimator.SetInteger ("statu", -1);
			this.transform.localScale += new Vector3 (-2 * this.transform.localScale.x, 0, 0);
		}

		Debug.Log ("gravity changed");
	}
	[Command]
	void CmdChangeGravity(float gravityScale){
		RpcChangeGravity (gravityScale);
	}
	[ClientRpc]
	void RpcChangeGravity(float gravityScale){
		this.GetComponent<Rigidbody2D> ().gravityScale = gravityScale;
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

	public int getPlayerDirect(){
		return playerDirect;
	}

	public void setJumpButton(bool value){
		jumpButton = value;
	}


	public void setTriggeBouncingFlag(bool value){
		triggeBouncingFlag = value;
	}
	public bool getTriggeBouncingFlag(){
		return triggeBouncingFlag;
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
	/*
	void OnGUI(){
		GUI.Box(new Rect(10,20,100,50),GetComponent<Rigidbody2D>().velocity.y.ToString());
	}
	*/

}
