//package servlet;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import beans.Component;
//import dao.Dao;
//
//@WebServlet("/ComponentEditorServlet")
//public class ComponentEditorServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// チェックされた行番号を取得
//		String[] selectedCodes = request.getParameterValues("selectedCodes");
//		int[] selectedLineNumbers = new int[selectedCodes.length];
//
//
//		if (selectedCodes != null) {
//
//			for (int i = 0; i < selectedCodes.length; i++) {
//				selectedLineNumbers[i] = Integer.parseInt(selectedCodes[i]);
//			}
//
//			// 変換後の確認（デバッグ用）
//			for (int num : selectedLineNumbers) {
//				System.out.println("Selected Line Number: " + num);
//			}
//		}
//
//		// サンプル: データベースから `CodeLine` を取得するDAO
//		Dao dao = new Dao();
//		List<String> Codes = dao.getCodeLines(selectedLineNumbers);
//
//		StringBuilder builder = new StringBuilder();
//		for (String code : Codes) {
//			builder.append(code).append("\n"); // 各行に改行を追加
//		}
//		String strCode = builder.toString().trim();
//
//		List<Component> components = dao.getComponents(); // DAOメソッドで取得
//		// リストをリクエスト属性にセット
//		request.setAttribute("Codes", strCode);
//		request.setAttribute("Components", components);
//
//		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/codeComponentAdd.jsp");
//		dispatcher.forward(request, response);
//	}
//
//}