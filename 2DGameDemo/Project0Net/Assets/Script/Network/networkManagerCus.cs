using UnityEngine;
using System.Collections;
using UnityEngine.Networking;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class networkManagerCus : NetworkManager {
	class NetworkMessage :MessageBase{
		public int chosenCharacter = 0;
	}
	int chosenCharacter = 0;
	void Start(){
		SceneManager.sceneLoaded += OnSceneLoaded;
		setUpMenuSceneButton ();
	}

	public override void OnServerAddPlayer(NetworkConnection conn, short playerControllerId, NetworkReader extraMessageReader)
	{
		NetworkMessage message = extraMessageReader.ReadMessage<NetworkMessage> ();

		if (message.chosenCharacter == 0) {
			playerPrefab = (GameObject)Resources.Load ("Prefabs/Online/PlayerPre");
		} else {
			Debug.Log ("is not sever");
			playerPrefab = (GameObject)Resources.Load ("Prefabs/Online/Player1Pre");
		}


		GameObject player = GameObject.Instantiate(playerPrefab, playerPrefab.transform.position, Quaternion.identity) as GameObject;
		NetworkServer.AddPlayerForConnection(conn, player, playerControllerId);
	}

	public override void OnClientConnect(NetworkConnection conn){

		NetworkMessage test = new NetworkMessage ();
		test.chosenCharacter = chosenCharacter;
		ClientScene.AddPlayer (conn, 0, test);
	}

	public void setUpHost(){
		chosenCharacter = 0;
		SceneInfo.setPlaymode (1);
		//SceneInfo.setSceneName ("Scenes/Online/Test Scene");
		NetworkManager.singleton.onlineScene = SceneInfo.getSceneName();
		setPort ();
		NetworkManager.singleton.StartHost ();
	}

	public void joinGame(){
		chosenCharacter = 1;
		SceneInfo.setPlaymode (1);
		setIPAddress ();
		setPort ();
		NetworkManager.singleton.StartClient ();
	}

	void setPort(){
		NetworkManager.singleton.networkPort = 7777;
	}

	void setIPAddress(){
		NetworkManager.singleton.networkAddress = GameObject.Find ("Canvas/InputField_IP/Text").GetComponent<Text> ().text;
		//NetworkManager.singleton.networkAddress = "192.168.1.1";
		Debug.Log ("networkAddress set");
	}

	void OnSceneLoaded(Scene scene, LoadSceneMode mod){
		if (scene.name == "StartScene") {
			return;
		} else if (scene.name == "NetStartScene") {
			Debug.Log ("menu loaded");
			setUpMenuSceneButton ();
		} else {
			Debug.Log ("game loaded");
			setUpMainGameButton ();
		}
	}

	void setUpMenuSceneButton(){
		GameObject.Find ("Canvas/Button_startHost").GetComponent<Button> ().onClick.RemoveAllListeners();
		GameObject.Find ("Canvas/Button_startHost").GetComponent<Button> ().onClick.AddListener (setUpHost);

		GameObject.Find ("Canvas/Button_startClient").GetComponent<Button> ().onClick.RemoveAllListeners();
		GameObject.Find ("Canvas/Button_startClient").GetComponent<Button> ().onClick.AddListener (joinGame);

		GameObject.Find ("Canvas/Text_localhost").GetComponent<Text> ().text = Network.player.ipAddress;
		playerPrefab = (GameObject)Resources.Load ("Prefabs/Online/PlayerPre");
	}

	void setUpMainGameButton(){
		Camera.main.transform.FindChild("Canvas/Button_return").GetComponent<Button>().onClick.RemoveAllListeners();
		Camera.main.transform.FindChild ("Canvas/Button_return").GetComponent<Button> ().onClick.AddListener (NetworkManager.singleton.StopHost);
	}

	public void destroy(){
		dontDestroyOnLoad = false;
		DestroyImmediate (GameObject.Find("NetworkManager"));
	}
}
