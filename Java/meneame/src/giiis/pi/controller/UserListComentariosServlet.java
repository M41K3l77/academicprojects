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
import giiis.pi.model.User;

/**
 * Servlet implementation class UserListComentariosServlet
 */
@WebServlet(
		name="UserListComentariosServlet",
		urlPatterns={"/newsrestricted/UserListComentariosServlet"}
		)
public class UserListComentariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListComentariosServlet() {
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
		User user=(User) session.getAttribute("user");
		
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		UserDAO userDao=new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		// controlar si se solicita pagina, el calculo se pasa ya hecho del jsp
		String usercommentpageactual=request.getParameter("actualpagecommentuser");
		Long actualpagecommentuser=null;
		if(usercommentpageactual != null){
			if(!usercommentpageactual.matches("[0-9]+") || usercommentpageactual.matches("[0]")){
				actualpagecommentuser=(long) 1;
				System.out.println("valor de actualpagecommentuser request si no cumple la exprexRg "+actualpagecommentuser);
			}else{
				actualpagecommentuser=Long.valueOf(usercommentpageactual).longValue();
			}
			session.setAttribute("actualpagecommentuser",actualpagecommentuser);
		}else{
			actualpagecommentuser=(Long) session.getAttribute("actualpagecommentuser");
			if(actualpagecommentuser==null){// en este punto no esta ni como parametro en la request ni en sesion
				actualpagecommentuser=(long) 1;
				session.setAttribute("actualpagecommentuser",actualpagecommentuser);
			}
		}
		String offsetcomments=String.valueOf((actualpagecommentuser-1)*10);
		// todos los comentarios del usuario
		List <Comment> usercomments=commentDao.getAllByOwner(user.getId(), offsetcomments);
		// noticia asociada al comentario
		List <News> commentNews=new ArrayList<News>();
		// numero de comentarios asociado a la noticia presentada
		List <Long> countCommetNews=new ArrayList<Long>();
		// nombres de los usuarios que crearon las noticias
		List <User> users=new ArrayList<User>();
		// posicion del comentario en la noticia a la que esta asociado
		List <Long> commetNewsPosition=new ArrayList<Long>();
		//evitar que pase de pagina si no hay mas comentarios
		if(usercomments.isEmpty() && actualpagecommentuser>1){
			offsetcomments=String.valueOf(((actualpagecommentuser-1)*10)-10);
			usercomments=commentDao.getAllByOwner(user.getId(), offsetcomments);
			session.setAttribute("actualpagecommentuser",actualpagecommentuser-1);
		}
		
		for (int i = 0; i < usercomments.size(); i++) {
			commentNews.add(newsDao.get(usercomments.get(i).getNews()));
			countCommetNews.add(commentDao.getCommentCountByNews(usercomments.get(i).getNews()));
			users.add(userDao.get(newsDao.get(usercomments.get(i).getNews()).getOwner()));
			// buscar posicion del comentario el la noticia (sería mejor tener un atributo en el comentario)
			List <Comment> newscomments=commentDao.getAllByNews(commentNews.get(i).getId());// comentarios de la noticia
			Long idcomentario=usercomments.get(i).getId();
			boolean encontrado=false; int j=0;
			while (j < newscomments.size() && !encontrado) {
				if(idcomentario == newscomments.get(j).getId()){
					commetNewsPosition.add(new Long(j+1));
					encontrado=true;
				}else{
					j++;
				}				
			}
		}
		
		List<News> mostHittedNewsList =newsDao.getMostHittedOrLiked("hits");
		List<News> mostLikedNewsList =newsDao.getMostHittedOrLiked("likes");
		request.setAttribute("mostHittedNewsList",mostHittedNewsList);
		request.setAttribute("mostLikedNewsList",mostLikedNewsList);
		request.setAttribute("commentNews",commentNews);
		request.setAttribute("usercomments",usercomments);
		request.setAttribute("commetNewsPosition",commetNewsPosition);
		request.setAttribute("users",users);
		request.setAttribute("countCommetNews",countCommetNews);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameUserListComentarios.jsp");
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
