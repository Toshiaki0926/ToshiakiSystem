package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Source_file;
import dao.Dao;
import hint.Main2;

@WebServlet("/JavaCodeServlet")
public class JavaCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Dao dao = new Dao();

		String idParam = request.getParameter("source_id");
		System.out.println("id: " + idParam);
	    int source_id = -1; // 初期値を設定
	    if (idParam != null) {
	        try {
	            source_id = Integer.parseInt(idParam);
	        } catch (NumberFormatException e) {
	            // パラメータが数値でない場合
	            e.printStackTrace();
	        }
	    }
	    
		Source_file source_code = dao.getSource_Code(source_id);

		if (source_code != null) {
			// 取得したコードを取得
			String originalCode = source_code.getSource_Code();
			String fileName = source_code.getSource_Name();
			// 変数名を空欄に置き換える
			String modifiedCode = Main2.replaceVariables(originalCode);

			// 変数名を置き換えたコードをリクエスト属性にセット
			request.setAttribute("modifiedCode", modifiedCode);
			request.setAttribute("fileName", fileName); // ファイル名も渡す

			RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/javaCode.jsp");
			dispatcher.forward(request, response);

		} else {
			response.getWriter().println("指定された名前のコードが見つかりませんでした。");
		}
	}
}