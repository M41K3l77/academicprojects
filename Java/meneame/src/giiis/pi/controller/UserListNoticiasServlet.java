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
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.News;
import giiis.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		name="UserListNoticiasServlet",
		urlPatterns={"/newsrestricted/UserListNoticiasServlet"}
		)
public class UserListNoticiasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListNoticiasServlet() {
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
		
		String filtro=request.getParameter("filtro");
		if(filtro != null){
			//si se paso por parametro el valor desde el jsp se guarda en sesion
			// si fuera null, ya se tiene uno en sesion (ya tiene uno por defecto
			// desdes que se inicio la aplicacion)
			session.setAttribute("filtro",filtro);
		}else{
			filtro=(String) session.getAttribute("filtro");
			if(filtro==null){// en este punto no esta ni como parametro en la request ni en sesion
				filtro="fecha";
				session.setAttribute("filtro",filtro);
			}
		}
		// controlar si se solicita pagina, el calculo se pasa ya hecho del jsp
		String userpageactual=request.getParameter("actualpageuser");
		Long actualpageuser=null;
		if(userpageactual != null){
			if(!userpageactual.matches("[0-9]+") || userpageactual.matches("[0]")){
				actualpageuser=(long) 1;
			}else{
				actualpageuser=Long.valueOf(userpageactual).longValue();
			}
			session.setAttribute("actualpageuser",actualpageuser);
		}else{
			actualpageuser=(Long) session.getAttribute("actualpageuser");
			if(actualpageuser==null){// en este punto no esta ni como parametro en la request ni en sesion
				actualpageuser=(long) 1;
				session.setAttribute("actualpageuser",actualpageuser);
			}
		}
		String offsetnews=String.valueOf((actualpageuser-1)*10);
		// consultar segun la categoria y traerlas por defecto ordenadas por tiempo ?????????????
		List <News> userNews=null;		
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		userNews=newsDao.getAllByOwnerSorted(user.getId(), filtro, offsetnews);
		if(userNews.isEmpty() && actualpageuser>1){
			offsetnews=String.valueOf(((actualpageuser-1)*10)-10);
			userNews=newsDao.getAllByOwnerSorted(user.getId(), filtro, offsetnews);
			session.setAttribute("actualpageuser",actualpageuser-1);
		}
		
		
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		List <Long> countCommetNews=new ArrayList<Long>();
		for (int i = 0; i < userNews.size(); i++) {
			countCommetNews.add(commentDao.getCommentCountByNews(userNews.get(i).getId()));
		}
		
		List<News> mostHittedNewsList =newsDao.getMostHittedOrLiked("hits");
		List<News> mostLikedNewsList =newsDao.getMostHittedOrLiked("likes");
		request.setAttribute("mostHittedNewsList",mostHittedNewsList);
		request.setAttribute("mostLikedNewsList",mostLikedNewsList);
		request.setAttribute("usernews",userNews);
		request.setAttribute("countCommetNews",countCommetNews);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameUserListNoticias.jsp");
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
