package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.Dao;

//SampleBBS/LoginServletにアクセスされると動作
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public LoginServlet() {
        super();
    }

    //POSTアクセスの場合に動作
    //GETアクセスは送信するパラメータがURLに載ってしまうので，データの登録や認証などには使用すべきでない．
    //このサーブレットでは，idとパスワードを受け取ってログイン認証するので，POSTのみ受け付ける

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requestから受け取る値の文字コードをUTF-8に設定．これがないと，たまに文字化けする．
		request.setCharacterEncoding("UTF-8");
		
		//requestから，"id"というnameの値を取得
		String user_id = request.getParameter("user_id");
		//requestから，"password"というnameの値を取得
		String password = request.getParameter("password");

		//DBアクセスのためのクラスをインスタンス化
		Dao dao = new Dao();
		//idを渡し，そのidを持つユーザをDBから取得.Userはbeansパッケージに宣言してある．
		User user = dao.getUserById(user_id);

		//先ほどのidを持つユーザが存在し，かつパスワードが入力したものと一致していればArticleListServletにアクセスを移動．
		if(user != null && user.getPassword().equals(password)) {
			//Sessionオブジェクトを取得し，userIdという名前でidを格納．このセッションが生きている限り，いつでもuserIdを取り出せるようになる．
			request.getSession().setAttribute("userId" , user_id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("JavaListServlet");
			dispatcher.forward(request, response);
		}
		else {
			//認証失敗の場合は，ログインページに戻す．
			RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/login.jsp");
			dispatcher.forward(request, response);
		}

	}
	
	//POSTアクセスのみを受け付けたいので，doGetは定義しない．


}
