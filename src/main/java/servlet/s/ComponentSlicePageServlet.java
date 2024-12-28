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

import beans.CodeLine;
import beans.ListLine;
import beans.SliceCodeList;
import dao.ReadDao;
import dao.WriteDao;

@WebServlet("/ComponentSlicePageServlet")
public class ComponentSlicePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//sessionを取得
		HttpSession session = request.getSession(false);
		//sessionに保存したsourceIdを取得
		int sourceId = (int) session.getAttribute("sourceId");
		int componentId = (int) session.getAttribute("componentId");
		
		System.out.println(componentId);
		System.out.println(sourceId);

		ReadDao dao = new ReadDao();
		
		//ソースコードが全部で何行あるか取得
		int totalLines = dao.getMaxLineNumberBySourceId(sourceId) + 4; //+4はクラス定義とメソッド定義を無視してるため

		//部品のlist_idのリストを取得
		List<Integer> componentListIds = dao.getComponentListIds(componentId, sourceId);

		
		//list_idが一致するline_idを取得
		List<ListLine> lineIds = dao.getLineIds(componentListIds);
		
		List<Integer> ids = new ArrayList<>();
		for(ListLine id : lineIds) {
			ids.add(id.getLine_id());
		}
		
		//line_idに一致するコードと説明を取得
		List<CodeLine> sliceComponent = dao.getSliceComponent(ids);

		
		List<SliceCodeList> sliceCodeList = new ArrayList<>();
		for(ListLine listLine : lineIds) {
			for(CodeLine sliceCode : sliceComponent) {
				if(listLine.getLine_id() == sliceCode.getLineId()) {
					SliceCodeList sc = new SliceCodeList(listLine.getLine_id(), listLine.getList_id(), sliceCode.getLineNumber(), sliceCode.getCode(), sliceCode.getDescription());
					sliceCodeList.add(sc);
				}
			}
		}
		
		//ヒント要求履歴を保存
		//sessionからuserIdを取得
		String userId = (String) session.getAttribute("userId");

		WriteDao wDao = new WriteDao();

		//ヒント要求履歴を保存
		wDao.insertEvent(userId, componentId, sourceId, 2);
		
		
		// リストをリクエスト属性にセット
		request.setAttribute("SliceComponent", sliceCodeList);
		request.setAttribute("TotalLines", totalLines);
		
		//行番号全体と何行目かの表示は+2すればいい（クラス定義とメソッド定義を無視しして分解してるため）

		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/s/componentSlice.jsp");
		dispatcher.forward(request, response);
	}

}
