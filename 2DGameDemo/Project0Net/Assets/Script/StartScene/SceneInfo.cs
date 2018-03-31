using UnityEngine;
using System.Collections;

public class SceneInfo {

	private static SceneInfo singleton = null;
	private static int playMode = 0;
	private static string sceneName = "Test Scene";

	public static SceneInfo getSingleton(){
		if (singleton == null) {
			singleton = new SceneInfo ();
		}

		return singleton;
	}

	public static void setPlaymode(int mod){
		playMode = mod;
	}
	public static int getPlaymode(){
		return playMode;
	}
	public static void setSceneName(string name){
		sceneName = name;
	}
	public static string getSceneName(){
		return sceneName;
	}
}
