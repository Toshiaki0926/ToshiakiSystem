package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ComponentList;
import dao.Dao;

@WebServlet("/ComponentSetServlet")
public class ComponentSetServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//doPostと同じ動作をしたいので，丸投げ
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String selectedComponentId = request.getParameter("component"); // プルダウンで選択された部品
		int selectComponentId = Integer.parseInt(selectedComponentId);

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存した現在のsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		String parent_id = request.getParameter("parentId"); // 親ID（nullの可能性あり）
		Integer parentId = (parent_id != null && !parent_id.isEmpty()) ? Integer.parseInt(parent_id) : null;

		String codes = request.getParameter("codes"); // JSPで送信されたコード全体

		Dao dao = new Dao();

		ComponentList component = new ComponentList();

		component.setComponent_id(selectComponentId);
		component.setSource_id(sourceId);
		component.setParent_id(parentId);
		component.setComponent_code(codes);

		//部品を保存し、生成されたlist_idを取得する
		int listId = dao.insertComponentList(component);
		
		//sessionに保存した現在のsourceIdを取得
		List<Integer> lineIds = (List<Integer>) session.getAttribute("lineIds");

		//部品の該当行を保存
		dao.insertListLines(listId, lineIds);


		response.sendRedirect("ComponentEditorPageServlet?source_id=" + sourceId);
	}

}
