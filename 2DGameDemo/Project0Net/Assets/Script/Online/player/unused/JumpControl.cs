using UnityEngine;
using System.Collections;

public class JumpControl : MonoBehaviour {

    bool grounded = true;
    public Transform groundcheck;
    float checkradius = 0.2f;
    public LayerMask whatisground;
    public float jumpforce = 5f;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        grounded = Physics2D.OverlapCircle(groundcheck.position, checkradius, whatisground);

        jumpControl ();
	}

	void jumpControl(){
        //Jump
        if (Input.GetKeyDown (KeyCode.Space) && grounded) {
            this.GetComponent<Rigidbody2D>().AddForce(Vector2.up * jumpforce);
            grounded = false;													  
		}
	}

}
