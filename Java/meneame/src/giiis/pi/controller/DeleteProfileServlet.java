package giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import giiis.pi.dao.JDBCUserDAOImpl;
import giiis.pi.dao.UserDAO;
import giiis.pi.model.User;

/**
 * Servlet implementation class DeleteProfileServlet
 */
@WebServlet("/newsrestricted/DeleteProfileServlet")
public class DeleteProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Connection conn=(Connection) getServletContext().getAttribute("dbConn");
		User user=(User) session.getAttribute("user");

		// se da debaja al usuario
		UserDAO userDao=new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		userDao.delete(user.getId());
		// quita al usuario de la sesion
		String filtro="fecha";
		String category="todas";
		session.removeAttribute("user");
		session = request.getSession(false);		
		if (session != null){
			session.invalidate();
		}
		// volvemos a abrir una sesion con solo filtro y categoria y sin datos personales del usuario
		session = request.getSession(true);
		session.setAttribute("filtro", filtro);
		session.setAttribute("category", category);

		response.sendRedirect(request.getContextPath()+"/MeneameHomeServlet");
	}

}
