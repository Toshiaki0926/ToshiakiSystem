package servlet.t;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.WriteDao;

@WebServlet("/DeleteJavaServlet")
@MultipartConfig
public class DeleteJavaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したcomponentIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		System.out.println("Source id: " + sourceId);

		// Daoを使ってデータベースから削除
		WriteDao dao = new WriteDao();
		dao.deleteSource(sourceId);

		// リダイレクトして同じページに戻る
		response.sendRedirect("AdminServlet");
	}

	//POSTアクセスされた場合は，doGetに丸投げ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
