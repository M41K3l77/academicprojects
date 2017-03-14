package giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
import giiis.pi.dao.JDBCUserDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.dao.UserDAO;
import giiis.pi.model.Comment;
import giiis.pi.model.News;

/**
 * Servlet implementation class NoticiaServlet
 */
@WebServlet(
		name="NoticiaServlet",
		urlPatterns={"/NoticiaServlet"})
public class NoticiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticiaServlet() {
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
		String idnotice=request.getParameter("noticeid");
		Long noticeid=null;//Long.valueOf(idnotice).longValue();
		if(idnotice==null){// entonces se viene de crear un comentario y hay que sacar el id de sesion de la sesion
			noticeid=Long.valueOf((String) session.getAttribute("noticeid")).longValue();
			session.removeAttribute("noticeid");
		}else{
			noticeid=Long.valueOf(idnotice).longValue();
		}
		// controlar si se solicita pagina, el calculo se pasa ya hecho del jsp
		String commentpageactual=request.getParameter("actualpagecomment");
		Long actualpagecomment=null;
		if(commentpageactual != null){
			if(!commentpageactual.matches("[0-9]+") || commentpageactual.matches("[0]")){
				actualpagecomment=(long) 1;
			}else{
				actualpagecomment=Long.valueOf(commentpageactual).longValue();
			}
			session.setAttribute("actualpagecomment",actualpagecomment);
		}else{
			actualpagecomment=(Long) session.getAttribute("actualpagecomment");
			if(actualpagecomment==null){// en este punto no esta ni como parametro en la request ni en sesion
				actualpagecomment=(long) 1;
				session.setAttribute("actualpagecomment",actualpagecomment);
			}
		}
		String offcomments=String.valueOf((actualpagecomment-1)*10);
		// necesito sacar la noticia a partir de su id, los comentarios, numero de comentarios, nombre del usuario y lista de usuarios de comentarios
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News notice=newsDao.get(noticeid);
		UserDAO userDao= new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		String usernews=userDao.get(notice.getOwner()).getName();
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		List<Comment> comments=commentDao.getAllByNews(noticeid, offcomments);
		// evitar que pase de pagina si ya no hay mas comentarios
		if(comments.isEmpty() && actualpagecomment>1){
			offcomments=String.valueOf(((actualpagecomment-1)*10)-10);
			comments=commentDao.getAllByNews(noticeid, offcomments);
			session.setAttribute("actualpagecomment",actualpagecomment-1);
		}
		
		Long countCommetNews=(long) comments.size();
		ArrayList<String> userNamescommentList = new ArrayList<String>();
		for (int i = 0; i < comments.size(); i++) {
			userNamescommentList.add(userDao.get(comments.get(i).getOwner()).getName());
		}
		List<News> mostHittedNewsList =newsDao.getMostHittedOrLiked("hits");
		List<News> mostLikedNewsList =newsDao.getMostHittedOrLiked("likes");
		request.setAttribute("mostHittedNewsList",mostHittedNewsList);
		request.setAttribute("mostLikedNewsList",mostLikedNewsList);
		request.setAttribute("notice", notice);
		request.setAttribute("usernews", usernews);
		request.setAttribute("comments", comments);
		request.setAttribute("countCommetNews", countCommetNews);
		request.setAttribute("userNamescommentList", userNamescommentList);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameNoticia.jsp");
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
