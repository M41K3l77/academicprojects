package giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.News;
import giiis.pi.model.User;

/**
 * Servlet implementation class BorrarNoticiaServlet
 */
@WebServlet(
		name="BorrarNoticiaServlet",
		urlPatterns={"/newsrestricted/BorrarNoticiaServlet"})
public class BorrarNoticiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrarNoticiaServlet() {
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
		Long noticeid=Long.valueOf(request.getParameter("noticeid"));
		User user=(User) session.getAttribute("user");
		// antes de nada comprobar que el id de la noticia a borrar es del usuario y no de otro
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News notice=newsDao.get(noticeid);
		if(notice.getOwner()==user.getId()){
			// borramos la noticia
			if(newsDao.delete(noticeid)){
				response.sendRedirect("UserListNoticiasServlet");
			}else{// si no se ha podido borrar
				request.setAttribute("noticeid", noticeid);
				response.sendRedirect("NoticiaServlet");
			}
		}else{// no es una noticia del usuario logeado
			response.sendRedirect(request.getContextPath()+"/LogoutServlet");
		}
	}

}
