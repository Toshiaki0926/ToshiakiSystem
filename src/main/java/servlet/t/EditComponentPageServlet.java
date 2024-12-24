package servlet.t;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Component;
import dao.ReadDao;

@WebServlet("/EditComponentPageServlet")
public class EditComponentPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		ReadDao dao = new ReadDao();

		String idParam = request.getParameter("component_id");
		System.out.println("id: " + idParam);
		int componentId = Integer.parseInt(idParam);
		
		request.getSession().setAttribute("componentId" , componentId);
		
		Component component = dao.getComponentById(componentId);
		
		request.setAttribute("component" , component);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/t/editComponent.jsp");
		dispatcher.forward(request, response);
	}
}