using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class startButton : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void loadScene(){
		Debug.Log ("click startButton");
		SceneInfo.setPlaymode (0);
		SceneManager.LoadScene ("LevelSelectScene");
	}
	public void loadNetStartScene(){
		SceneInfo.setPlaymode (1);
		SceneManager.LoadScene ("NetStartScene");
	}
}
