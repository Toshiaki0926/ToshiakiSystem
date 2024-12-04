package beans;

public class Component {
	private int component_id; // 部品のid(主キー)
	private String component_description; // 部品の説明

	// 空のコンストラクタ
	public Component() {
	}

	public Component(int component_id, String component_description) {
		this.component_id = component_id;
		this.component_description = component_description;
	}

	public int getComponent_id() {
		return component_id;
	}

	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}

	public String getComponent_description() {
		return component_description;
	}

	public void setComponent_description(String component_description) {
		this.component_description = component_description;
	}

}
