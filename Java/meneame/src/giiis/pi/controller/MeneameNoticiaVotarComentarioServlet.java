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
import giiis.pi.model.Comment;

/**
 * Servlet implementation class MeneameNoticiaVotarComentarioServlet
 */
@WebServlet(
		name="MeneameNoticiaVotarComentarioServlet",
		urlPatterns={"/newsrestricted/MeneameNoticiaVotarComentarioServlet"}
		)
public class MeneameNoticiaVotarComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeneameNoticiaVotarComentarioServlet() {
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
		//se realizan las opersaciones necesarias para lo del karma y se debe pasar el id
		// de la noticia commentnoticeid
		String noticeid=request.getParameter("commentnoticeid");
		Long commentid=Long.valueOf(request.getParameter("commentid")).longValue();
		int commentLikes=Integer.valueOf(request.getParameter("voto")).intValue();
		// votar comentario
		synchronized (conn) {
			CommentDAO commentDao=new JDBCCommentDAOImpl();
			commentDao.setConnection(conn);
			Comment comment=commentDao.get(commentid);
			comment.setLikes(comment.getLikes()+commentLikes);
			commentDao.save(comment);
		}
				
		session.setAttribute("noticeid", noticeid);
		response.sendRedirect(request.getContextPath()+"/NoticiaServlet");
	}

}
