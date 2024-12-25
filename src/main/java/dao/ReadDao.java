package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import beans.CodeLine;
import beans.Component;
import beans.ComponentList;
import beans.ListLine;
import beans.Source_file;
import beans.User;

public class ReadDao extends DriverAccessor{

	//DBとのコネクションを入れる変数
	private Connection connection;


	public static final String UTF_8 = "UTF-8";
	public static final String MS932 = "MS932";

	public ReadDao(){
	}

	//ユーザidを入力すると，そのidを持つUserを返す
	public User getUserById(String user_id) {
		//返り値用Userを作成
		User user = new User();

		//コネクションを生成
		this.connection = this.createConnection();

		//Exceptionが発生するので，try-catch
		try{
			//SQL文の定義．?には，後で値を埋める．
			String sql = "select * from users where user_id = ?";
			//SQL文から，PreparedStatementを生成
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			//SQL文の1個目の?に，文字列型のidを埋め込む．
			//SQLインジェクション攻撃への対策を自動で行ってくれる.
			stmt.setString(1, user_id);

			//SQL文を実行し，結果（ResultSet型）を受け取る．
			//結果があるタイプのSQL文（selectなど）はexecuteQuery()で実行し，そうでないもの(insertなど)はexecuteUpdate()で実行する．
			//executeUpdate()は，戻り値を受け取る必要はない．
			ResultSet rs = stmt.executeQuery();

			//rs,first() : 取得した結果の最初のレコードが存在するとtrue
			if(rs.first()){
				//結果rsから，"id"というカラムの値を取得し，userのIdにセット(カラム名は，mysqlからテーブル構造を参照)
				user.setId( rs.getString("user_id") );
				//rsから，"password"というカラムの値を取得し，userのpasswordにセット（以下，同様）
				user.setPassword( rs.getString("password") );
				user.setName( rs.getString("name") );
				user.setRole( rs.getInt("role") );
			}
			else{
				this.closeConnection(this.connection);
				return null;
			}

		}catch(SQLException e){
			this.closeConnection(this.connection);
			e.printStackTrace();
			return null;

		} finally {
			this.closeConnection(this.connection);
		}

		return user;
	}

	//component_idに一致する部品を取得
	public Component getComponentById(int component_id) {
		Component component = new Component();

		this.connection = this.createConnection();

		try{
			String sql = "select * from components where component_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setInt(1, component_id);

			ResultSet rs = stmt.executeQuery();

			if(rs.first()){
				component.setComponent_id( rs.getInt("component_id") );
				component.setComponent_description( rs.getString("component_description") );
			}
			else{
				this.closeConnection(this.connection);
				return null;
			}

		}catch(SQLException e){
			this.closeConnection(this.connection);
			e.printStackTrace();
			return null;

		} finally {
			this.closeConnection(this.connection);
		}

		return component;
	}

	public Source_file getSource_Code(int source_id) {
		Source_file source_code = new Source_file();
		this.connection = this.createConnection();

		try{
			String sql = "select * from source_files where source_id = ?";

			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();

			if(rs.first()){
				//結果rsから，カラムの値を取得し，セット
				source_code.setSource_Name( rs.getString("source_name") );
				source_code.setSource_Code( rs.getString("source_code") );
			}
			else{
				this.closeConnection(this.connection);
				return null;
			}

		}catch(SQLException e){
			this.closeConnection(this.connection);
			e.printStackTrace();
			return null;

		} finally {
			this.closeConnection(this.connection);
		}

		return source_code;
	}

	public String getSource_name(int source_id) {
		String sourceName = null;
		this.connection = this.createConnection();

		try{
			String sql = "select source_name from source_files where source_id = ?";

			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();

			if(rs.first()){
				sourceName = rs.getString("source_name");		
			} else{
				this.closeConnection(this.connection);
				return null;
			}

		}catch(SQLException e){
			this.closeConnection(this.connection);
			e.printStackTrace();
			return null;

		} finally {
			this.closeConnection(this.connection);
		}

		return sourceName;
	}

	//ソースコードのリストを取得
	public List<Source_file> getSourceList() {
		List<Source_file> sourceList = new ArrayList<>();
		this.connection = this.createConnection();

		try {
			String sql = "SELECT * FROM source_files";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			// ResultSetの処理
			while (rs.next()) {
				// DBから取得した値を引数としてCodeを作成
				Source_file s = new Source_file(rs.getInt("source_id"), rs.getString("source_name"), rs.getString("source_code"));
				// 返り値用リストに追加
				sourceList.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return sourceList;
	}

	//部品のリストを取得
	public List<Component> getComponents() {
		List<Component> componentList = new ArrayList<>();
		this.connection = this.createConnection();

		try {
			String sql = "SELECT * FROM components";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			// ResultSetの処理
			while (rs.next()) {
				// DBから取得した値を引数としてCodeを作成
				Component c = new Component(rs.getInt("component_id"), rs.getString("component_description"));
				// 返り値用リストに追加
				componentList.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return componentList;
	}

	//source_idを受け取ると、一致するcodeを全て取得
	public List<CodeLine> getCodeList(int source_id) {
		List<CodeLine> codeList = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT * FROM code_lines WHERE source_id = ?";;
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();

			// ResultSetの処理
			while (rs.next()) {
				// DBから取得した値を引数としてCodeを作成
				CodeLine c = new CodeLine(rs.getInt("line_id"), rs.getInt("line_number"), rs.getString("code"), rs.getString("description"));
				// 返り値用リストに追加
				codeList.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return codeList;
	}


	//lineIdsの配列を受け取ると、該当する行のcode全てをString型のリストで取得
	public List<String> getCodeLines(List<Integer> lineIds) {
		List<String> codeLines = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			// プレースホルダーを生成 (例: "?, ?, ?")
			StringBuilder placeholders = new StringBuilder();
			for (int i = 0; i < lineIds.size(); i++) {
				placeholders.append("?");
				if (i < lineIds.size() - 1) {
					placeholders.append(", ");
				}
			}

			// 動的に SQL 文を構築
			String sql = "SELECT code FROM code_lines WHERE line_id IN (" + placeholders + ")";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			// パラメータを設定
			int index = 1; // sourceId に続くパラメータのインデックス
			for (int lineId : lineIds) {
				stmt.setInt(index++, lineId);
			}

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				codeLines.add(rs.getString("code")); // 結果をリストに追加
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return codeLines; // 結果を返す
	}

	//lineIdsの配列を受け取ると、該当する行のcode全てをCodeLine型のリストで取得
	public List<CodeLine> getComponentLines(List<Integer> lineIds) {
		List<CodeLine> componentLines = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			// プレースホルダーを生成 (例: "?, ?, ?")
			StringBuilder placeholders = new StringBuilder();
			for (int i = 0; i < lineIds.size(); i++) {
				placeholders.append("?");
				if (i < lineIds.size() - 1) {
					placeholders.append(", ");
				}
			}

			// 動的に SQL 文を構築
			String sql = "SELECT * FROM code_lines WHERE line_id IN (" + placeholders + ")";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			// パラメータを設定
			int index = 1;
			for (int lineId : lineIds) {
				stmt.setInt(index++, lineId);
			}

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int lineId = rs.getInt("line_id");
				int lineNum = rs.getInt("line_number");
				String code = rs.getString("code");
				String description = rs.getString("description");

				CodeLine codeLine = new CodeLine(lineId, lineNum, code, description);
				componentLines.add(codeLine); // 結果をリストに追加
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return componentLines; // 結果を返す
	}

	//部品の説明を取得
	public List<Component> getComponentDescriptions() {
		List<Component> components = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT * FROM components";;
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// DBから取得した値を引数としてCodeを作成
				Component c = new Component(rs.getInt("component_id"), rs.getString("component_description"));
				// 返り値用リストに追加
				components.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return components;
	}

	//ソースコードに含まれる親がない部品（parent_idがnullの部品）のリストを取得
	public List<Integer> getPNComponentList(int source_id) {
		List<Integer> componentIds = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT component_id FROM component_lists WHERE source_id = ? AND parent_id IS NULL";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// component_idをint型で取得
				componentIds.add(rs.getInt("component_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection);
		}
		return componentIds;
	}

	//部品に含まれる部品のリストを取得（部品idを受け取り、それを親とする部品を取得）
	public List<Integer> getChildComponentList(int source_id, int component_id) {
		List<Integer> componentIds = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT component_id FROM component_lists WHERE source_id = ? AND parent_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, source_id);
			stmt.setInt(2, component_id);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// component_idをint型で取得
				componentIds.add(rs.getInt("component_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection);
		}
		return componentIds;
	}

	//ソースコードに含まれる部品のリストを取得
	public List<ComponentList> getComponentList(int source_id) {
		List<ComponentList> components = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT * FROM component_lists WHERE source_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int listId = rs.getInt("list_id");
				int componentId = rs.getInt("component_id");
				int sourceId = rs.getInt("source_id");
				Integer parentId = (Integer) rs.getObject("parent_id");
				String componentCode = rs.getString("component_code");

				ComponentList component = new ComponentList(listId, componentId, sourceId, parentId, componentCode);;
				components.add(component);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection);
		}
		return components;
	}

	//部品idを受け取り、一致する部品のリストを返す
	public List<Component> getComponentsByIds(Set<Integer> componentIds) {
		List<Component> components = new ArrayList<>();
		// 引数が空の場合、早期リターン
	    if (componentIds == null || componentIds.isEmpty()) {
	        return components; // 空のリストを返す
	    }
	    
		this.connection = this.createConnection();

		// プレースホルダーを生成（例: "?, ?, ?")
		StringBuilder placeholders = new StringBuilder();
		for (int i = 0; i < componentIds.size(); i++) {
			placeholders.append("?");
			if (i < componentIds.size() - 1) {
				placeholders.append(",");
			}
		}

		try {
			String sql = "SELECT * FROM components WHERE component_id IN (" + placeholders + ")";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			// プレースホルダーに値を設定
			int index = 1;
			for (int id : componentIds) {
				stmt.setInt(index++, id);
			}

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int componentId = rs.getInt("component_id");
				String description = rs.getString("component_description");

				// Componentオブジェクトを作成し、リストに追加
				Component component = new Component(componentId, description);
				components.add(component);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return components;

	}

	//ソースコードに含まれる部品のうち最も若いidのコードを返す
	public String getComponentCode(int componentId, int sourceId) {
		String componentCode = null;
		this.connection = this.createConnection();

		try {
			String sql = "SELECT component_code FROM component_lists WHERE component_id = ? AND source_id = ? ORDER BY list_id ASC LIMIT 1";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setInt(1, componentId);
			stmt.setInt(2, sourceId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				componentCode = rs.getString("component_code");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return componentCode;
	}

	//source_idとcomponent_idを受け取ると、一致するlist_idを全て取得
	public List<Integer> getComponentListIds(int component_id, int source_id) {
		List<Integer> listIds = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT list_id FROM component_lists WHERE component_id = ? AND source_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, component_id);
			// 2個目の「?」に値をセット
			stmt.setInt(2, source_id);

			ResultSet rs = stmt.executeQuery();

			// ResultSetの処理
			while (rs.next()) {
				listIds.add(rs.getInt("list_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}
		return listIds;
	}

	//部品のlist_idを複数受け取って、list_idに一致するline_idを受け取る
	public List<ListLine> getLineIds(List<Integer> listIds) {
		List<ListLine> lineIds = new ArrayList<>();
		this.connection = this.createConnection();

		// プレースホルダーを生成（例: "?, ?, ?")
		StringBuilder placeholders = new StringBuilder();
		for (int i = 0; i < listIds.size(); i++) {
			placeholders.append("?");
			if (i < listIds.size() - 1) {
				placeholders.append(",");
			}
		}

		try {
			String sql = "SELECT * FROM component_lines WHERE list_id IN (" + placeholders + ")";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			// プレースホルダーに値を設定
			int index = 1;
			for (int id : listIds) {
				stmt.setInt(index++, id);
			}

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int listId = rs.getInt("list_id");
				int lineId = rs.getInt("line_id");

				ListLine listLines = new ListLine(listId, lineId);
				lineIds.add(listLines);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return lineIds;
	}

	//部品のlist_idに一致するline_idを全て取得
	public List<Integer> getComponentLineIds(int listId) {
		List<Integer> lineIds = new ArrayList<>();
		this.connection = this.createConnection();

		try {
			String sql = "SELECT line_id FROM component_lines WHERE list_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setInt(1, listId);

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lineIds.add(rs.getInt("line_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}
		return lineIds;
	}

	//部品idを受け取り、一致する部品のリストを返す
	public List<CodeLine> getSliceComponent(List<Integer> lineIds) {
		List<CodeLine> codeLines = new ArrayList<>();

		this.connection = this.createConnection();

		// プレースホルダーを生成（例: "?, ?, ?")
		StringBuilder placeholders = new StringBuilder();
		for (int i = 0; i < lineIds.size(); i++) {
			placeholders.append("?");
			if (i < lineIds.size() - 1) {
				placeholders.append(",");
			}
		}

		try {
			String sql = "SELECT * FROM code_lines WHERE line_id IN (" + placeholders + ")";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			// プレースホルダーに値を設定
			int index = 1;
			for (int line : lineIds) {
				stmt.setInt(index++, line);
			}

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int lineId = rs.getInt("line_id");
				int lineNumber = rs.getInt("line_number");
				String code = rs.getString("code");
				String description = rs.getString("description");

				CodeLine cl = new CodeLine(lineId, lineNumber, code, description);
				codeLines.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーメッセージを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return codeLines;
	}

	//source_idを受け取ると一番大きいline_numberを返す（プログラムが全体で何行あるか）
	public int getMaxLineNumberBySourceId(int sourceId) {
		int maxLineNumber = -1; // デフォルト値（該当行がない場合）
		this.connection = this.createConnection();

		try {
			// SQLクエリを準備
			String sql = "SELECT MAX(line_number) AS max_line_number FROM code_lines WHERE source_id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, sourceId); // パラメータを設定

			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				maxLineNumber = rs.getInt("max_line_number"); // 最大値を取得
			}
		} catch (SQLException e) {
			e.printStackTrace(); // エラーを出力
		} finally {
			this.closeConnection(this.connection); // 接続を必ず閉じる
		}

		return maxLineNumber; // 結果を返す
	}

}
