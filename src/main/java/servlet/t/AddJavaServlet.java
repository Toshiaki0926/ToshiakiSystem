package servlet.t;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.Source_file;
import dao.WriteDao;
import divide.PythonExecutor;

@WebServlet("/AddJavaServlet")
@MultipartConfig
public class AddJavaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doPostと同じ動作をしたいので，丸投げ
		doPost(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ファイルとファイル名の取得
		Part filePart = request.getPart("javaFile"); // "javaFile" from jsp
		String fileName = request.getParameter("fileName"); // file name from form input

		// ファイル名の検証
		String submittedFileName = filePart.getSubmittedFileName();
		if (submittedFileName == null || !submittedFileName.endsWith(".java")) {
			// エラーをリクエストに設定して登録ページに戻る
			request.getRequestDispatcher("AddJavaPageServlet").forward(request, response);
			return;
		}

		// ファイルの内容を読み取る
		StringBuilder fileContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append("\n");
			}
		}

		// Daoを使ってデータベースに保存
		WriteDao dao = new WriteDao();
		Source_file sourceCode = new Source_file();
		sourceCode.setSource_Name(fileName); // ファイル名を設定
		sourceCode.setSource_Code(fileContent.toString()); // ファイルの内容を設定

		//ファイルとファイル名を保存
		int sourceId = dao.insertSource_Code2(sourceCode); // データベースに保存し、生成されたsource_idを取得
		PythonExecutor.parseCodeToJson(fileContent.toString(), sourceId);

		// 保存完了後、リダイレクトしてAdminページに戻る
		response.sendRedirect("AdminServlet");
	}
}
