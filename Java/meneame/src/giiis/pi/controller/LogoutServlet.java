package giiis.pi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(
		name="LogoutServlet",
		urlPatterns={"/LogoutServlet"}
		)
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		// Estos datos nos interesa tenerlos despues del deslogueo y que no se pierdan
		// estos dos datos no son confidenciales
		String filtro=(String) session.getAttribute("filtro");
		String category=(String) session.getAttribute("category");
		session = request.getSession(false);		
		if (session != null){
			session.removeAttribute("user");
			//session.removeAttribute("username");
			session.invalidate();
		}
		// volvemos a abrir una sesion con solo filtro y categoria y sin datos personales del usuario
		session = request.getSession(true);
		if(category == null){
			category="todas";
		}
		if(filtro == null){
			filtro="fecha";
		}
		session.setAttribute("filtro", filtro);
		session.setAttribute("category", category);
		response.sendRedirect(request.getContextPath()+"/AccessLoginViewServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
