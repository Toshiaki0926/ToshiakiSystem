package servlet.s;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Component;
import dao.ReadDao;

@WebServlet("/ChildComponentListPageServlet")
public class ChildComponentListPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		ReadDao dao = new ReadDao();

		String idParam = request.getParameter("component_id");

		System.out.println("id: " + idParam);
		int componentId = Integer.parseInt(idParam);

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存した現在のsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");
		
		System.out.println(sourceId);
		System.out.println(componentId);

		//受け取った部品idに含まれる子部品をすべて取得
		List<Integer> componentIds = dao.getChildComponentList(sourceId, componentId);
		//部品IDの重複を排除するためにセットに変換
		Set<Integer> uniqueComponentIds = new HashSet<>(componentIds);
		//重複を排除した部品リストを取得
		List<Component> components = dao.getComponentsByIds(uniqueComponentIds);

		// リストをリクエスト属性にセット
		request.setAttribute("Components", components);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/s/childComponentList.jsp");
		dispatcher.forward(request, response);
	}
}