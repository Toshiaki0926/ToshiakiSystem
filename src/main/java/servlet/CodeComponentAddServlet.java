package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ComponentList;
import dao.Dao;

@WebServlet("/CodeComponentAddServlet")
public class CodeComponentAddServlet extends HttpServlet{
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
		
		String source_id = request.getParameter("source_id");//現在のソースコードid
		int sourceId = Integer.parseInt(source_id);
		
		String parent_id = request.getParameter("parentId"); // 親ID（nullの可能性あり）
	    Integer parentId = (parent_id != null && !parent_id.isEmpty()) ? Integer.parseInt(parent_id) : null;
		
		String codes = request.getParameter("codes"); // JSPで送信されたコード全体

		Dao dao = new Dao();

		ComponentList component = new ComponentList();
		
		//確認用
		component.setComponent_id(selectComponentId);
		component.setSource_id(sourceId);
		component.setParent_id(parentId);
		component.setComponent_code(codes);
		
		//デバッグ用
//		System.out.println(codes);
//		System.out.println(component.getComponent_code());
		
		dao.insertComponentList(component);
		

		response.sendRedirect("ComponentEditorPageServlet?source_id=" + sourceId);
	}

}
