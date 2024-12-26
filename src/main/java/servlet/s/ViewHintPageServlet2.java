package servlet.s;

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

import beans.Source_file;
import dao.ReadDao;
import dao.WriteDao;
import hint.Main2;

@WebServlet("/ViewHintPageServlet2")
public class ViewHintPageServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String cIdParam = request.getParameter("component_id");
		int componentId = Integer.parseInt(cIdParam);

		//sessionにcomponent_idを保存
		request.getSession().setAttribute("componentId" , componentId);

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");

		ReadDao dao = new ReadDao();

		//部品の中で一番若いlist_idを取得
		int listId = dao.getComponentCode2(componentId, sourceId);

		//ソースコード情報取得
		Source_file sourceCode = dao.getSource_Code(sourceId);

		// 変数名を空欄に置き換える
		String hintCode = Main2.replaceVariables(sourceCode.getSource_Code());
		
		// 余計な空行を削除する
		String cleanHintCode = hintCode.replaceAll("(?m)^[ \t]*\r?\n", "");
		
		System.out.println(cleanHintCode);

		// 改行で文字列を分割
		String[] lines = cleanHintCode.split("\n");

		//部品に該当する行line_idを取得
		List<Integer> lineIds = dao.getComponentLineIds(listId);
		
		//line_idに一致するline_numberを取得
		List<Integer> lineNums = dao.getLineNumbers(lineIds);

		// ヒントを格納するリスト
		List<String> codeHint = new ArrayList<>();

		// リストから指定された行を取り出して結果に格納
		for (int lineIndex : lineNums) {
			// 1始まりの行番号を0始まりのインデックスに変換
		    int adjustedIndex = lineIndex - 1;

		    if (adjustedIndex >= 0 && adjustedIndex < lines.length) {
		        codeHint.add(lines[adjustedIndex]);
		    }
		}
		
		//リスト型のヒントコードをString型に変換
		String codeHintString = String.join("\n", codeHint);
		
		for(String line : lines) {
			System.out.println("linecode : "+ line);
		}
		
		for(String code : codeHint) {
			System.out.println(code);
		}

		//ヒント要求履歴を保存
		//sessionからuserIdを取得
		String userId = (String) session.getAttribute("userId");

		WriteDao wDao = new WriteDao();

		//ヒント要求履歴を保存
		wDao.insertEvent(userId, componentId, sourceId);

		// リストをリクエスト属性にセット
		request.setAttribute("CodeHint", codeHintString);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/s/viewHint.jsp");
		dispatcher.forward(request, response);
	}

}
