using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class loadLevel : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void loadLevelScene(int level){
		if(level == 0){
			SceneManager.LoadScene ("TestScene_OFFLINE");
		}else if(level == 1){
			SceneManager.LoadScene ("OFFLINE1");
		}
	}
}
