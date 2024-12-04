package beans;

public class Source_file {
	private int source_id; // プログラムのid(主キー)
	private String source_code; // プログラムのソースコード
	private String source_name; // プログラムの名前

	// 空のコンストラクタ
	public Source_file() {
	}

	public Source_file(int source_id, String source_name, String source_code) {
		this.source_id = source_id;
		this.source_name = source_name;
		this.source_code = source_code;
	}

	public int getSource_Id() {
		return source_id;
	}

	public void setSource_Id(int source_id) {
		this.source_id = source_id;
	}
	
	public String getSource_Code() {
		return source_code;
	}

	public void setSource_Code(String source_code) {
		this.source_code = source_code;
	}

	public String getSource_Name() {
		return source_name;
	}

	public void setSource_Name(String source_name) {
		this.source_name = source_name;
	}

}