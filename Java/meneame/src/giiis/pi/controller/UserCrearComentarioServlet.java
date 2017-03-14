package giiis.pi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AccessCrearComentarioViewServlet
 */
@WebServlet(
		name="UserCrearComentarioServlet",
		urlPatterns={"/newsrestricted/UserCrearComentarioServlet"}
		)
public class UserCrearComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCrearComentarioServlet() {
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
		String noticeid=request.getParameter("noticeid");
		if(noticeid==null){
			// cogerlo de sesion por que no valido al intentar insertarlo y vuelve aqui
			noticeid=(String) session.getAttribute("noticeid");
			// se quita de sesion ya que en la vista de crear comentario se puede ir a otras vista
			session.removeAttribute("noticeid");
		}
		
		request.setAttribute("noticeid", noticeid);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameEnviarComentario.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
