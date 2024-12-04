package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Source_file;
import dao.Dao;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    //POSTアクセスされた場合は，doGetに丸投げ
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Daoクラスから登録されたコードのリストを取得
		Dao dao = new Dao();
		List<Source_file> sourceList = dao.getSourceList();

		// リストをリクエスト属性にセット
		request.setAttribute("SourceList", sourceList);

		// javaList.jspへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/admin.jsp");
		dispatcher.forward(request, response);
	}
}