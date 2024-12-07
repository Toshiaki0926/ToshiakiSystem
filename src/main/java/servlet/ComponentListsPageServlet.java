package servlet;

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
import dao.Dao;

@WebServlet("/ComponentListsPageServlet")
public class ComponentListsPageServlet extends HttpServlet {
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

		//ソースコードに含まれる部品をすべて取得
		List<Integer> componentIds = dao.getComponentList(source_id);
		//部品IDの重複を排除するためにセットに変換
		Set<Integer> uniqueComponentIds = new HashSet<>(componentIds);
		//重複を排除した部品リストを取得
		List<Component> components = dao.getComponentsByIds(uniqueComponentIds);

		// リストをリクエスト属性にセット
		request.setAttribute("Components", components);
		request.setAttribute("SourceId", source_id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/componentLists.jsp");
		dispatcher.forward(request, response);
	}
}