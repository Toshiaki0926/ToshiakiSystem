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

@WebServlet("/SelectChildCodeLinePageServlet")
public class SelectChildCodeLinePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		Dao dao = new Dao();

		String listIdParam = request.getParameter("selectedListId");
		String comIdParam = request.getParameter("selectedComponentId");
		
		System.out.println(listIdParam);
		System.out.println(comIdParam);

		int listId = Integer.parseInt(listIdParam);
		int componentId = Integer.parseInt(comIdParam);
		
		List<Integer> componentLineIds = dao.getComponentLineIds(listId);
		
		List<CodeLine> componentLines = dao.getComponentLines(componentLineIds);

		//sessionにsource_idを保存、このsessionで現在のsource_idが取得できる
		request.getSession().setAttribute("parentId" , componentId);

		// リストをリクエスト属性にセット
		request.setAttribute("ComponentLines", componentLines);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/t/selectChildCodeLine.jsp");
		dispatcher.forward(request, response);
	}
}