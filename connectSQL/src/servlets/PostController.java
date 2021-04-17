package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.PostBean;
import beans.UserBean;
import database.SQLcon;

/**
 * Servlet implementation class PostController
 */
@WebServlet("/PostController")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if there is an user in session.
		if (request.getSession().getAttribute("user") != null) {
			// get the user out of session
			UserBean bean = (UserBean) request.getSession().getAttribute("user");

			// Validate username and password again
			if (bean.validate(bean)) {

				// get the session and the request to go to the postpage
				HttpSession session = request.getSession();
				session.setAttribute("user", bean);
				request.setAttribute("user", bean);

				RequestDispatcher rd = request.getRequestDispatcher("postpage.jsp");
				rd.forward(request, response);
			} else {
				// this only happens if the sessionid is removed, manually or because it timed
				// out and you try to go directly to the "postpage.jsp"
				// goto logout to clean up

				RequestDispatcher rd = request.getRequestDispatcher("Logout");
				rd.forward(request, response);

			}
		} else {
			// this should only happen if you try to goto "/Login" manually
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String content = request.getParameter("content");
		String tag = request.getParameter("tag");

		HttpSession session = request.getSession();
		UserBean bean = (UserBean) session.getAttribute("user");

		SQLcon.addPostMessagesSQL(bean.getName(), content, tag);

		PostBean postBean = new PostBean(SQLcon.getPostSql());

		request.setAttribute("user", bean);
		request.setAttribute("PostBean", postBean);

		RequestDispatcher rd = request.getRequestDispatcher("postpage.jsp");
		rd.forward(request, response);
	}

}
