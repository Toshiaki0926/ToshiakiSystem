package servlet.s;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import hint.Main2;

@WebServlet("/ViewHintPageServlet")
public class ViewHintPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String cIdParam = request.getParameter("component_id");
		int componentId = Integer.parseInt(cIdParam);

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		Dao dao = new Dao();

		String componentCode = dao.getComponentCode(componentId, sourceId);

		String code = "public class Sample1 {\n public static void main(String[] args){\n" + componentCode + "\n} \n}";

		// 変数名を空欄に置き換える
		String hintCode = Main2.replaceVariables(code);

		// 改行で文字列を分割
		String[] lines = hintCode.split("\n");

		// 最初の1行目、最初の2行目、最後の2行目、最後の1行目を除外
		StringBuilder codeHintBuilder = new StringBuilder();

		// 2行目から最終行の2行前まで追加
		for (int i = 3; i < lines.length - 2; i++) {
			codeHintBuilder.append(lines[i]).append("\n");
		}

		// 最終的な文字列
		String codeHint = codeHintBuilder.toString().trim();

		// リストをリクエスト属性にセット
		request.setAttribute("CodeHint", codeHint);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/s/viewHint.jsp");
		dispatcher.forward(request, response);
	}

}
