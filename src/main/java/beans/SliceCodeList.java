package beans;

public class SliceCodeList {
	private int line_id; 
	private int list_id;
	private int line_number;
	private String code;
	private String description;

	// 空のコンストラクタ
	public SliceCodeList() {
	}

	public SliceCodeList(int line_id, int list_id, int line_number, String code, String description) {
		this.line_id = line_id;
		this.list_id = list_id;
		this.line_number = line_number;
		this.code = code;
		this.description = description;
	}
	
	public int getLine_id() {
		return line_id;
	}

	public void setLine_id(int line_id) {
		this.line_id = line_id;
	}

	public int getList_id() {
		return list_id;
	}

	public void setList_id(int list_id) {
		this.list_id = list_id;
	}

	public int getLine_number() {
		return line_number;
	}

	public void setLine_number(int line_number) {
		this.line_number = line_number;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
