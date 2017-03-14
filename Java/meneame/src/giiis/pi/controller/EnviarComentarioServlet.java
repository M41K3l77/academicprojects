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
 * Servlet implementation class EnviarComentarioServlet
 */
@WebServlet(
		name="EnviarComentarioServlet",
		urlPatterns={"/newsrestricted/EnviarComentarioServlet"})
public class EnviarComentarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarComentarioServlet() {
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
		// recuperar el id de la noticia y el texto del comentario y el usuaruio
		String noticeid=request.getParameter("noticeid");
		// si no esta en la request ha y que cogerlo de la sesion ya 
		// que pude haber un segundo intento de enviar el commentario si 
		// el primero no valido
		if(noticeid==null){
			noticeid=(String) session.getAttribute("noticeid");
		}
		String text=request.getParameter("usercomment");
		User user=(User) session.getAttribute("user");
		// validar texto a insertar en el comentario
		if(text!=null && (text.length()>0 && text.length()<=400)){
			//crear el comentario y guardarlo en la base de datos
			Comment comment=new Comment();
			comment.setText(text);
			comment.setOwner(user.getId());
			comment.setNews(Long.valueOf(noticeid).longValue());
			CommentDAO commentDao=new JDBCCommentDAOImpl();
			commentDao.setConnection(conn);
			commentDao.add(comment);
			// al añadir un comentario se sube el karma a la noticia
			NewsDAO newsDao= new JDBCNewsDAOImpl();
			newsDao.setConnection(conn);
			News notice=newsDao.get(Long.valueOf(noticeid).longValue());
			// un comentario da mas uno de karma
			int karmaNews=notice.getLikes()+1;
			notice.setLikes(karmaNews);
			newsDao.save(notice);
			// send redirect a la noticia
			session.setAttribute("noticeid", noticeid);// en Noticia servlet se quita de la sesion
			response.sendRedirect(request.getContextPath()+"/NoticiaServlet");
		}else{
			if(text==null){
				String errorcomentario="falta texto";
				session.setAttribute("errorcomentario", errorcomentario);
			}else if(text!=null && !(text.length()>0 && text.length()<=400)){
				String errorcomentario="longitud de texto incorrecta";
				session.setAttribute("errorcomentario", errorcomentario);
			}
			session.setAttribute("noticeid", noticeid);// en Noticia servlet se quita de la sesion
			response.sendRedirect("UserCrearComentarioServlet");
		}
		
	}

}
