using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class levelSelect : MonoBehaviour {

	// Use this for initialization
	void Start () {
		SceneInfo.getSingleton ();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void OnLevelSelect(){
		if (GetComponent<Dropdown> ().value == 0) {
			SceneInfo.setSceneName ("Scenes/Online/Test Scene");
		} else if (GetComponent<Dropdown> ().value == 1) {
			SceneInfo.setSceneName ("Scenes/Online/ONLINE0");
		}
	}
}
