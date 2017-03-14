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
import giiis.pi.model.User;

/**
 * Servlet implementation class EditarComentarioServlet
 */
@WebServlet("/newsrestricted/EditarComentarioServlet")
public class EditarComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarComentarioServlet() {
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
		User user=(User) session.getAttribute("user");
		Connection conn=(Connection) getServletContext().getAttribute("dbConn");
		Long commentid=Long.valueOf(request.getParameter("commentid"));
		String usercomment=request.getParameter("usercomment");
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		Comment comment=commentDao.get(commentid);
		
		if(user.getId() != comment.getOwner()){
			response.sendRedirect(request.getContextPath()+"/LogoutServlet");
		}else{
			if(usercomment!=null && (usercomment.length()>0 && usercomment.length()<=400)){
				comment.setText(usercomment);
				commentDao.save(comment);
				response.sendRedirect("UserListComentariosServlet");
			}else{
				if(usercomment==null){
					String errorcomentario="falta texto";
					session.setAttribute("errorcomentario", errorcomentario);
				}else if(usercomment!=null && !(usercomment.length()>0 && usercomment.length()<=400)){
					String errorcomentario="longitud de texto incorrecta";
					session.setAttribute("errorcomentario", errorcomentario);
				}
				session.setAttribute("commentid", commentid);// se elimina en AccessEditarComentarioServlet
				response.sendRedirect("AccessEditarComentarioServlet");
			}
		}
	}

}
