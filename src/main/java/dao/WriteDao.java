package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import beans.CodeLine;
import beans.Component;
import beans.ComponentList;
import beans.Source_file;

public class WriteDao extends DriverAccessor{

	//DBとのコネクションを入れる変数
	private Connection connection;


	public static final String UTF_8 = "UTF-8";
	public static final String MS932 = "MS932";

	public WriteDao(){
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
	
	//ソースコードに含まれる部品を設定し、生成されたlist_idを取得
	public int insertComponentList(ComponentList component) {
		this.connection= this.createConnection();

		try {
			this.connection.setAutoCommit(false); // 自動コミットを無効にする
			String sql = "INSERT INTO component_lists (component_id, source_id, parent_id, component_code) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // 生成されたIDを取得する設定
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

	// 部品に対応する行番号を保存
	public void insertListLines(int listId, List<Integer> lineIds) {
		this.connection = this.createConnection();

		try {
			String sql = "INSERT INTO component_lines (list_id, line_id) VALUES (?, ?)";
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			for (int lineId : lineIds) { // List<Integer>を直接扱う
				stmt.setInt(1, listId);
				stmt.setInt(2, lineId);
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

	//部品を受け取り，データをアップデートする
	public void updateComponent(Component component) {
		this.connection= this.createConnection();

		try{
			//SQL文を定義
			//?には後で値を入れる．
			String sql = "update components set component_description=? where component_id=?";
			//SQL文からPreparedStatementを生成
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			//1個目の「?」に値をセット
			stmt.setString(1, component.getComponent_description() );
			//2個目の「?」に値をセット
			stmt.setInt(2, component.getComponent_id());

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

	//部品を受け取り，データを削除する
	public void deleteComponent(int componentId) {
		this.connection= this.createConnection();

		try{
			String sql = "DELETE FROM components WHERE component_id = ?";
			//SQL文からPreparedStatementを生成
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			//1個目の「?」に値をセット
			stmt.setInt(1, componentId);

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

	//ソースコードを受け取り，データを削除する
	public void deleteSource(int sourceId) {
		this.connection= this.createConnection();

		try{
			String sql = "DELETE FROM source_files WHERE source_id = ?";
			//SQL文からPreparedStatementを生成
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			//1個目の「?」に値をセット
			stmt.setInt(1, sourceId);

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
	
	//部品の説明を受け取り、DBに格納する
		public void insertEvent(String userId, int componentId, int sourceId) {
			this.connection= this.createConnection();

			try{
				String sql = "insert into events (user_id, component_id, source_id) values(?, ?, ?)";

				PreparedStatement stmt = this.connection.prepareStatement(sql);

				//1個目の「?」に値をセット
				stmt.setString(1, userId);
				stmt.setInt(2, componentId);
				stmt.setInt(3, sourceId);

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
	

}
