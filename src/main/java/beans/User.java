package beans;

import java.io.Serializable;

//ユーザを表すクラス
public class User implements Serializable{ //implements Serializableが必要
	private String user_id; //一意なユーザId
	private String password; //ログイン用パスワード
	private String name; //氏名

	//カラのコンストラクタが必要
	public User() {
	}

	public User(String user_id, String password, String name) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.name = name;
	}


	public String getId() {
		return user_id;
	}

	public void setId(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
