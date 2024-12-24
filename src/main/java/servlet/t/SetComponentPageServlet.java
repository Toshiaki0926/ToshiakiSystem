package servlet.t;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Component;
import dao.ReadDao;

@WebServlet("/SetComponentPageServlet")
public class SetComponentPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存した現在のsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		// チェックされた行idを取得
		String[] selectedCodes = request.getParameterValues("selectedCodes");

		// チェックボックスが何も選択されていない場合、リダイレクト
		if (selectedCodes == null || selectedCodes.length == 0) {
			response.sendRedirect("SelectCodeLinePageServlet?source_id=" + sourceId);
			return; // 処理を終了
		}

		//選択した行idを格納するリスト
		List<Integer> selectedLineIds = new ArrayList<>();

		//行idを整数型にキャスト
		for (String str : selectedCodes) {
            selectedLineIds.add(Integer.parseInt(str)); // 文字列を整数に変換して追加
        }

		// 変換後の確認（デバッグ用）
		for (int num : selectedLineIds) {
			System.out.println("Selected Line Id: " + num);
		}
		
		//選択された行番号をsessionに保存
		request.getSession().setAttribute("lineIds" , selectedLineIds);

		// サンプル: データベースから `CodeLine` を取得するDAO
		ReadDao dao = new ReadDao();
		List<String> Codes = dao.getCodeLines(selectedLineIds);

		StringBuilder builder = new StringBuilder();
		for (String code : Codes) {
			builder.append(code).append("\n"); // 各行に改行を追加
		}
		String strCode = builder.toString().trim();

		System.out.println(strCode);

		List<Component> components = dao.getComponentDescriptions(); // DAOメソッドで取得
		// リストをリクエスト属性にセット
		request.setAttribute("Codes", strCode);
		request.setAttribute("Components", components);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/t/setComponent.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGetと同じ動作をしたいので，丸投げ
		doGet(request, response);
	}

}
