package beans;

public class ListLine {
	private int list_id;
	private int line_id;
	
	public ListLine(){
	}
	
	public ListLine(int list_id, int line_id) {
		this.list_id = list_id;
		this.line_id = line_id;
	}

	public int getList_id() {
		return list_id;
	}

	public void setList_id(int list_id) {
		this.list_id = list_id;
	}

	public int getLine_id() {
		return line_id;
	}

	public void setLine_id(int line_id) {
		this.line_id = line_id;
	}

}
