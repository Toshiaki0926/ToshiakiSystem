package divide;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.CodeLine;
import dao.Dao;

public class JsonToDatabase {
	public static void main(int source_id) {
		String jsonFilePath = "C:\\pleiades\\2023-12\\workspace\\Hint1\\json\\output.json"; // JSONファイルのパス
		try (FileReader reader = new FileReader(jsonFilePath)) {
			// JSONをリストにパース
			Gson gson = new Gson();
			Type listType = new TypeToken<List<CodeLine>>() {}.getType();
			List<CodeLine> codeLines = gson.fromJson(reader, listType);
			
			Dao dao = new Dao();
			dao.insertCodeLines(codeLines, source_id);
			
			System.out.println("データの挿入が完了しました！");
		} catch (IOException e) {
			System.err.println("JSONファイルの読み取りエラー: " + e.getMessage());
		} 
	}

}
