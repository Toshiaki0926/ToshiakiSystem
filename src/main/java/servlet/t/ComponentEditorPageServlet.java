package servlet.t;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CodeLine;
import dao.Dao;

@WebServlet("/ComponentEditorPageServlet")
public class ComponentEditorPageServlet extends HttpServlet {
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

		List<CodeLine> codeLine = dao.getCodeList(source_id);
		
		//sessionにsource_idを保存、このsessionで現在のsource_idが取得できる
		request.getSession().setAttribute("sourceId" , source_id);

		// リストをリクエスト属性にセット
		request.setAttribute("CodeList", codeLine);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/componentEditor.jsp");
		dispatcher.forward(request, response);
	}
}