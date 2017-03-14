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

import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.News;

/**
 * Servlet implementation class EditarHistoriaServlet
 */
@WebServlet(
		name="EditarHistoriaServlet",
		urlPatterns={"/newsrestricted/EditarHistoriaServlet"})
public class EditarHistoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarHistoriaServlet() {
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
		Connection conn=(Connection) getServletContext().getAttribute("dbConn");
		// A este servlet a demas de poder llegar desde el jsp lista de noticias de usuario para
		// editar una noticia, se llega tambien desde el servlet historiaeditadaservlet en el
		// caso de que no se validen los datos, y se llega desde un sendredirect por lo que 
		// el noticeid se debe sacar de sesion. De la request se saca si se viene de la lista de 
		// noticias del usuario
		String idnotice=request.getParameter("noticeid");
		Long noticeid=null;
		if(idnotice != null){
			noticeid=Long.valueOf(idnotice);
		}else{
			noticeid=(Long) session.getAttribute("noticeid");
			// se quita de sesion por que ya no es necesario
			session.removeAttribute("noticeid");
		}
		
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News notice=newsDao.get(noticeid);
		String category=notice.getCategory();
		String url=notice.getUrl();
		String title=notice.getTitle();
		String text=notice.getText();
		request.setAttribute("category", category);
		request.setAttribute("url", url);
		request.setAttribute("title", title);
		request.setAttribute("text", text);
		request.setAttribute("noticeid", noticeid);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameEditarHistoria.jsp");
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
