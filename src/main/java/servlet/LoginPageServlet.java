package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//このサーブレットのパスの設定．
//SampleBBS/LoginPageServletにアクセスされたとき，このサーブレットが動作．
@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
	//決まり文句
	private static final long serialVersionUID = 1L;
       
	//コンストラクタ(省略可能)	
    public LoginPageServlet() {
        super();
    }

    //GETアクセスされた場合に動作する．
    //GETアクセスになるのは，ハイパーリンクでアクセスされた場合や，method="get"のformからアクセスされた場合．
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//下記2行は決まり文句．./WEB-INF/jsp/login.jspのページを表示する．
		RequestDispatcher dispatcher = request.getRequestDispatcher("./jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	//POSTアクセスされた場合に動作する．
	//POSTアクセスになるのは，method="post"のformからアクセスされた場合
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//doGetと同じ動作をしたいので，丸投げ
		doGet(request, response);
	}

}
