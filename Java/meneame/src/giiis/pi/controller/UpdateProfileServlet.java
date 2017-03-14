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
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/newsrestricted/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateProfileServlet() {
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
		// recuperando datos introducidos por el usuario
		String passwordRegex="[a-zA-Z0-9_]{6,12}";
		String emailRegex="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		String newuseremail=request.getParameter("newuseremail");
		String newuserpassword1=request.getParameter("newuserpassword1");
		String newuserpassword2=request.getParameter("newuserpassword2");
		// validacion de datos
		if((newuserpassword1.length()>0 && newuserpassword1.length()<=40) && (newuserpassword2.length()>0 && newuserpassword2.length()<=40)
				&& (newuseremail.length()>0 && newuseremail.length()<=50) && newuserpassword1.matches(passwordRegex)  
				&& newuserpassword2.matches(passwordRegex) && newuseremail.matches(emailRegex)){
			// cumple con la validacion de datos
			if(!newuserpassword1.equals(newuserpassword2)){
				// error en confirmacion de la contraseña nueva
				String errorpassword="error contraseñas diferentes";
				session.setAttribute("errorpassword", errorpassword);
				response.sendRedirect("AccessUpdateProfileServlet");
			}else{
				// Se puede registrar al usuario
				synchronized (conn) {
					UserDAO userDao=new JDBCUserDAOImpl();
					userDao.setConnection(conn);
					user.setEmail(newuseremail);
					user.setPassword(newuserpassword1);
					userDao.save(user);
					session.setAttribute("user",user);
				}
				response.sendRedirect(request.getContextPath()+"/MeneameHomeServlet");
			}
		}else{
			String errorformatoemail="";			
			if(!newuseremail.matches(emailRegex)){
				errorformatoemail="email no reconicido";
				session.setAttribute("errorformatoemail", errorformatoemail);
			}
			String errorformatopassword="";
			if(!newuserpassword1.matches(passwordRegex) || !newuserpassword2.matches(passwordRegex)){
				errorformatopassword="contraseña minimo 6 y máximo 12";
				session.setAttribute("errorformatopassword", errorformatopassword);
			}
			response.sendRedirect("AccessUpdateProfileServlet");
		}

	}

}
