package giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import giiis.pi.dao.CommentDAO;
import giiis.pi.dao.JDBCCommentDAOImpl;
import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.Comment;
import giiis.pi.model.News;
import giiis.pi.model.User;

/**
 * Servlet implementation class BorrarComentarioServlet
 */
@WebServlet("/newsrestricted/BorrarComentarioServlet")
public class BorrarComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrarComentarioServlet() {
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
		Long commentid=Long.valueOf(request.getParameter("commentid"));
		User user=(User) session.getAttribute("user");
		// antes de nada comprobar que el id de la noticia a borrar es del usuario y no de otro
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		Comment comment=commentDao.get(commentid);
		if(user.getId() == comment.getOwner()){
			// borramos la noticia
			commentDao.delete(commentid);
			response.sendRedirect("UserListComentariosServlet");
		}else{
			response.sendRedirect(request.getContextPath()+"/LogoutServlet");
		}
		
	}

}
