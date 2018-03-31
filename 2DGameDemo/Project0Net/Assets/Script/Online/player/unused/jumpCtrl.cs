using UnityEngine;
using System.Collections;

public class jumpCtrl : MonoBehaviour {

	GameObject player;
	GameObject floor;
	bool jumpAvaliable = true;
	// Use this for initialization
	void Start () {

		player = GameObject.Find ("role");
		floor = GameObject.Find ("background");
	}
	
	// Update is called once per frame
	void Update () {
	
		if(Input.GetKey(KeyCode.Space)){
			Debug.Log("Hit space");

			if(jumpAvaliable){
				Debug.Log("Hit space AND JUMPAVALIABLE");
				player.GetComponent<Rigidbody2D> ().velocity = new Vector2 (player.GetComponent<Rigidbody2D> ().velocity.x, 7f);
				jumpAvaliable=false;
				floor.GetComponent<BoxCollider2D>().isTrigger=true;
			}
		}

		if(player.GetComponent<Rigidbody2D>().velocity.y<0){
			Debug.Log("start falling");
			floor.GetComponent<BoxCollider2D>().isTrigger=false;
		}
	}

	void OnCollisionEnter2D(Collision2D col){
		if(col.gameObject.name=="background"){
			Debug.Log("Hit SpriteTestmap");
			jumpAvaliable=true;
		}
	}
}
