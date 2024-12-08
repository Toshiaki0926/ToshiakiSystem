package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Component;
import dao.Dao;

@WebServlet("/CodeComponentAddPageServlet")
public class CodeComponentAddPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//現在のソースコードidを取得
		String source_id = request.getParameter("source_id");
		int sourceId = Integer.parseInt(source_id);

		// チェックされた行番号を取得
		String[] selectedCodes = request.getParameterValues("selectedCodes");

		// チェックボックスが何も選択されていない場合、リダイレクト
		if (selectedCodes == null || selectedCodes.length == 0) {
			response.sendRedirect("ComponentEditorPageServlet?source_id=" + sourceId);
			return; // 処理を終了
		}

		int[] selectedLineNumbers = new int[selectedCodes.length];

		//行番号を整数型にキャスト
		for (int i = 0; i < selectedCodes.length; i++) {
			selectedLineNumbers[i] = Integer.parseInt(selectedCodes[i]);
		}

		// 変換後の確認（デバッグ用）
		for (int num : selectedLineNumbers) {
			System.out.println("Selected Line Number: " + num);
		}


		// サンプル: データベースから `CodeLine` を取得するDAO
		Dao dao = new Dao();
		List<String> Codes = dao.getCodeLines(selectedLineNumbers, sourceId);

		StringBuilder builder = new StringBuilder();
		for (String code : Codes) {
			builder.append(code).append("\n"); // 各行に改行を追加
		}
		String strCode = builder.toString().trim();

		List<Component> components = dao.getComponents(); // DAOメソッドで取得
		// リストをリクエスト属性にセット
		request.setAttribute("Codes", strCode);
		request.setAttribute("Components", components);
		request.setAttribute("source_id", sourceId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/codeComponentAdd.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGetと同じ動作をしたいので，丸投げ
		doGet(request, response);
	}

}
