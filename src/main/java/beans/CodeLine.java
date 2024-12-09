package beans;

public class CodeLine {
	private int line_id; //主キー
    private int line_number;  //何行目なのか
    private String code;  //該当コード
    private String description;  //該当コードの説明

 // 空のコンストラクタ
 	public CodeLine() {
 	}
 	
    // コンストラクタ
    public CodeLine(int line_id, int line_number, String code, String description) {
    	this.line_id = line_id;
        this.line_number = line_number;
        this.code = code;
        this.description = description;
    }

    // ゲッターとセッター
    public int getLineId() {
    	return line_id;
    	}
    
    public void setLineId(int line_id) {
    	this.line_id = line_id;
    	}

    public int getLineNumber() {
    	return line_number;
    	}
    
    public void setLineNumber(int line_number) {
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
