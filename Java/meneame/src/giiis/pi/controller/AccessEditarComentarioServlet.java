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

import giiis.pi.dao.CommentDAO;
import giiis.pi.dao.JDBCCommentDAOImpl;
import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.Comment;
import giiis.pi.model.News;
import giiis.pi.model.User;

/**
 * 
 * @author miguel
 * 
 */
@WebServlet("/newsrestricted/AccessEditarComentarioServlet")
public class AccessEditarComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessEditarComentarioServlet() {
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
		User user=(User) session.getAttribute("user");
		Connection conn=(Connection) getServletContext().getAttribute("dbConn");
		String idcomment=request.getParameter("commentid");
		Long commentid=null;
		if(idcomment==null){
			commentid=(Long) session.getAttribute("commentid");
			session.removeAttribute("commentid");// importante ya que si no valido el texto a editar se vuelve por aqui
		}else{
			commentid=Long.valueOf(idcomment);
		}
		
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		Comment comment=commentDao.get(commentid);
		if(user.getId() == comment.getOwner()){
			String textcomment=comment.getText();
			request.setAttribute("textcomment", textcomment);
			request.setAttribute("commentid", commentid);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameEditarComentario.jsp");
			view.forward(request,response);
		}else{
			response.sendRedirect(request.getContextPath()+"/LogoutServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
