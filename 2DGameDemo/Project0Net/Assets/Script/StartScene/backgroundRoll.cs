using UnityEngine;
using System.Collections;

public class backgroundRoll : MonoBehaviour {

	float LEFT_BOUNDARY = -12.8F;
	float RIGHT_BOUNDARY = 12.8F;
	float ROLL_SPEED = 0.6F;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
		rollBackground ();
	}

	void rollBackground(){
		this.transform.Translate (-Time.deltaTime * ROLL_SPEED, 0, 0);
		if (this.transform.position.x <= LEFT_BOUNDARY) {
			this.transform.position = new Vector3 (RIGHT_BOUNDARY, this.transform.position.y, this.transform.position.z);
		}
	}
}
