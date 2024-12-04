package beans;

public class ComponentList {
	private int component_id; // 部品のid
	private int source_id;
	private Integer parent_id;
	private String component_code;

	// 空のコンストラクタ
	public ComponentList() {
	}

	public ComponentList(int component_id, int source_id, Integer parent_id, String component_code) {
		this.component_id = component_id;
		this.source_id = source_id;
		this.parent_id = parent_id;
		this.component_code = component_code;
	}

	public int getComponent_id() {
		return component_id;
	}

	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}

	public int getSource_id() {
		return source_id;
	}

	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getComponent_code() {
		return component_code;
	}

	public void setComponent_code(String component_code) {
		this.component_code = component_code;
	}
}
