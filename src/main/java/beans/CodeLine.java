package beans;

public class CodeLine {
    private int line_number;  //何行目なのか
    private String code;  //該当コード
    private String description;  //該当コードの説明

 // 空のコンストラクタ
 	public CodeLine() {
 	}
 	
    // コンストラクタ
    public CodeLine(int lineNumber, String code, String description) {
        this.line_number = lineNumber;
        this.code = code;
        this.description = description;
    }

    // ゲッターとセッター
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
