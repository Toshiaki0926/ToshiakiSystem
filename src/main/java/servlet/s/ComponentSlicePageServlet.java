package servlet.s;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CodeLine;
import dao.Dao;

@WebServlet("/ComponentSlicePageServlet")
public class ComponentSlicePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String cIdParam = request.getParameter("component_id");
		int componentId = Integer.parseInt(cIdParam);

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");
		
		System.out.println(componentId);
		System.out.println(sourceId);

		Dao dao = new Dao();

		//部品のlist_idのリストを取得
		List<Integer> componentListIds = dao.getComponentListIds(componentId, sourceId);
		
		//list_idが一致するline_idを取得
		List<Integer> lineIds = dao.getLineIds(componentListIds);
		
		//line_idに一致するコードと説明を取得
		List<CodeLine> SliceComponent = dao.getSliceComponent(lineIds);

		// リストをリクエスト属性にセット
		request.setAttribute("SliceComponent", SliceComponent);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/componentSlice.jsp");
		dispatcher.forward(request, response);
	}

}
