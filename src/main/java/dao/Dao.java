package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CodeLine;
import beans.Component;
import beans.ComponentList;
import beans.Source_file;
import beans.User;

public class Dao extends DriverAccessor{

	//DBとのコネクションを入れる変数
	private Connection connection;


	public static final String UTF_8 = "UTF-8";
	public static final String MS932 = "MS932";

	public Dao(){
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

	//部品の説明を受け取り、DBに格納する
	public void insertComponent(String description) {
		this.connection= this.createConnection();

		try{
			String sql = "insert into components (component_description) values(?)";

			PreparedStatement stmt = this.connection.prepareStatement(sql);

			//1個目の「?」に値をセット
			stmt.setString(1, description);

			//SQL文を実行
			stmt.executeUpdate();

			stmt.close();
			this.closeConnection(connection);
		}catch(SQLException e){
			this.closeConnection(connection);
			e.printStackTrace();
		} finally {
			this.closeConnection(connection);
		}
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


	// Codeを受け取り，DBに格納し、生成されたsource_idを返す
	public int insertSource_Code2(Source_file source_code) {
		this.connection = this.createConnection();

		if (this.connection == null) {
			System.out.println("Database connection failed.");
			return -1; // 接続が失敗した場合はエラーコードを返す
		}

		try {
			this.connection.setAutoCommit(false); // 自動コミットを無効にする
			String sql = "insert into source_files (source_name, source_code) values(?, ?)";

			PreparedStatement stmt = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // 生成されたIDを取得する設定

			// 1個目の「?」に値をセット
			stmt.setString(1, source_code.getSource_Name());
			// 2個目の「?」に値をセット
			stmt.setString(2, source_code.getSource_Code());

			// SQL文を実行
			int rowsAffected = stmt.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected); // 追加

			// 生成されたIDを取得
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				int generatedId = generatedKeys.getInt(1); // 生成されたIDを取得
				this.connection.commit(); // 明示的にコミットを行う
				stmt.close();
				this.closeConnection(connection);
				return generatedId; // 生成されたIDを返す
			} else {
				this.connection.rollback(); // 生成されなかった場合、ロールバック
				this.closeConnection(connection);
				return -1; // 生成されたIDがない場合はエラーコードを返す
			}

		} catch (SQLException e) {
			try {
				if (this.connection != null) {
					this.connection.rollback(); // エラー時はロールバック
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			this.closeConnection(connection);
			e.printStackTrace();
			return -1; // エラーコードを返す
		} finally {
			this.closeConnection(connection);
		}
	}


	//ソースコードのリストを取得
	public List<Source_file> getSourceList() {
		List<Source_file> sourceList = new ArrayList<>();
		this.connection = this.createConnection();

		// 接続が成功しているか確認
		if (this.connection == null) {
			System.out.println("Database connection failed.");
			return sourceList; // 接続に失敗した場合は空のリストを返す
		}

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

	//1行ずつのコードをテーブルに保存
	public void insertCodeLines(List<CodeLine> codeLines, int sourceId) {
		this.connection = this.createConnection();

		try {
			String sql = "INSERT INTO code_lines (line_number, code, description, source_id) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			for (CodeLine line : codeLines) {
				stmt.setInt(1, line.getLineNumber());
				stmt.setString(2, line.getCode());
				stmt.setString(3, line.getDescription());
				stmt.setInt(4, sourceId);
				stmt.addBatch(); // バッチ処理
			}

			stmt.executeBatch(); // バッチ実行
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection);
		}
	}

	//source_idを受け取ると、一致するcodeを全て取得
	public List<CodeLine> getCodeList(int source_id) {
		List<CodeLine> codeList = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		// 接続が成功しているか確認
		if (this.connection == null) {
			System.out.println("Database connection failed.");
			return codeList; // 接続に失敗した場合は空のリストを返す
		}

		try {
			String sql = "SELECT * FROM code_lines WHERE source_id = ?";;
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			// 1個目の「?」に値をセット
			stmt.setInt(1, source_id);

			ResultSet rs = stmt.executeQuery();

			// ResultSetの処理
			while (rs.next()) {
				// DBから取得した値を引数としてCodeを作成
				CodeLine c = new CodeLine(rs.getInt("line_number"), rs.getString("code"), rs.getString("description"));
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


	//line_numberの配列を受け取ると、該当する行のcode全てを取得
	public List<String> getCodeLines(int[] line_numbers) {
		List<String> codeLines = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		if (this.connection == null) {
			System.out.println("Database connection failed.");
			return codeLines; // 接続に失敗した場合は空のリストを返す
		}

		try {
			// `IN` 演算子を使用してクエリを動的に生成
			StringBuilder sqlBuilder = new StringBuilder("SELECT code FROM code_lines WHERE line_number IN (");
			for (int i = 0; i < line_numbers.length; i++) {
				sqlBuilder.append("?");
				if (i < line_numbers.length - 1) {
					sqlBuilder.append(", ");
				}
			}
			sqlBuilder.append(")");
			String sql = sqlBuilder.toString();

			// プリペアドステートメントを準備
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			for (int i = 0; i < line_numbers.length; i++) {
				stmt.setInt(i + 1, line_numbers[i]); // パラメータを設定
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

	//部品の説明を取得
	public List<Component> getComponents() {
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

	//ソースコードに含まれる部品を設定する
	public void insertComponentList(ComponentList component) {
		this.connection= this.createConnection();

		try {
			String sql = "INSERT INTO component_lists (component_id, source_id, parent_id, component_code) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, component.getComponent_id());
			stmt.setInt(2, component.getSource_id());
			// parent_id が null かどうかを確認
			if (component.getParent_id() != null) {
				stmt.setInt(3, component.getParent_id());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER); // parent_id を NULL として設定
			}
			stmt.setString(4, component.getComponent_code());
			//SQL文を実行
			stmt.executeUpdate();

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(this.connection);
		}
	}

	//ソースコードに含まれる部品のリストを取得
	public List<Integer> getComponentList(int source_id) {
		List<Integer> componentIds = new ArrayList<>();
		this.connection = this.createConnection(); // 接続を生成

		try {
			String sql = "SELECT component_id FROM component_lists WHERE source_id = ?";;
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
		}
		return componentIds;
	}
	
//	public List<Component> getComponentsByIds(Set<Integer> componentIds) {
//		List<Component> components = new ArrayList<>();
//		this.connection = this.createConnection();
//		try{．
//			String sql = "select * from users where user_id = ?";
//			PreparedStatement stmt = this.connection.prepareStatement(sql);
//			
//			stmt.setString(1, user_id);
//
//			ResultSet rs = stmt.executeQuery();
//
//			if(rs.first()){
//				//結果rsから，"id"というカラムの値を取得し，userのIdにセット(カラム名は，mysqlからテーブル構造を参照)
//				user.setId( rs.getString("user_id") );
//				//rsから，"password"というカラムの値を取得し，userのpasswordにセット（以下，同様）
//				user.setPassword( rs.getString("password") );
//				user.setName( rs.getString("name") );
//			}
//			else{
//				this.closeConnection(this.connection);
//				return null;
//			}
//
//		}catch(SQLException e){
//			this.closeConnection(this.connection);
//			e.printStackTrace();
//			return null;
//
//		} finally {
//			this.closeConnection(this.connection);
//		}
//
//		return user;
//
//	}


}