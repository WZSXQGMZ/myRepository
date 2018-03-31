using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class loadScene : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void loadSceneByName(string name){
		SceneManager.LoadScene (name);
	}

	public void loadSceneByNameFromOnline(string name){
		GameObject.Find ("NetworkManager").GetComponent<networkManagerCus> ().destroy ();
		SceneManager.LoadScene (name);
	}
}
