package servlet.t;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WriteDao;

@WebServlet("/AddComponentServlet")
@MultipartConfig
public class AddComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doGetと同じ動作をしたいので，丸投げ
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		
		// 部品名の取得
		String componentDescription = request.getParameter("componentDescription");
		
		System.out.println("Component Description: " + componentDescription);

		// Daoを使ってデータベースに保存
		WriteDao dao = new WriteDao();
		dao.insertComponent(componentDescription);

		// リダイレクトして同じページに戻る
		response.sendRedirect("AddComponentPageServlet");
	}
}
