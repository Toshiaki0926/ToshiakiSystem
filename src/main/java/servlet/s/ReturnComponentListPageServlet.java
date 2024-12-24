package servlet.s;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReturnComponentListPageServlet")
public class ReturnComponentListPageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存した現在のsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		response.sendRedirect("ComponentListPageServlet?source_id=" + sourceId);
	}

	//POSTアクセスされた場合は，doGetに丸投げ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}