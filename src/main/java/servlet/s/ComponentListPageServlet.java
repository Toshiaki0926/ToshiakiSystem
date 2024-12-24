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

import beans.Component;
import dao.ReadDao;

@WebServlet("/ComponentListPageServlet")
public class ComponentListPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		ReadDao dao = new ReadDao();

		String idParam = request.getParameter("source_id");
		System.out.println("id: " + idParam);
		int sourceId = Integer.parseInt(idParam);
		
		String sourceName = dao.getSource_name(sourceId);
		
		request.getSession().setAttribute("sourceId" , sourceId);

		//ソースコードに含まれる親部品なしの部品をすべて取得
		List<Integer> componentIds = dao.getPNComponentList(sourceId);
		//部品IDの重複を排除するためにセットに変換
		Set<Integer> uniqueComponentIds = new HashSet<>(componentIds);
		//重複を排除した部品リストを取得
		List<Component> components = dao.getComponentsByIds(uniqueComponentIds);

		// リストをリクエスト属性にセット
		request.setAttribute("SourceName", sourceName);
		request.setAttribute("Components", components);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/s/componentList.jsp");
		dispatcher.forward(request, response);
	}
}