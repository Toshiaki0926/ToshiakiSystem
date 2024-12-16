package servlet.t;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Component;
import dao.Dao;

@WebServlet("/UpdateComponentServlet")
@MultipartConfig
public class UpdateComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// 部品名の取得
		String componentDescription = request.getParameter("componentDescription");

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したcomponentIdを取得
		int componentId = (int) session.getAttribute("componentId");

		System.out.println("Component Description: " + componentDescription);
		System.out.println("Component Id: " + componentId);

		Component component = new Component(componentId, componentDescription);

		// Daoを使ってデータベースに保存
		Dao dao = new Dao();
		dao.updateComponent(component);

		// リダイレクトして同じページに戻る
		response.sendRedirect("AdminServlet");
	}
}
