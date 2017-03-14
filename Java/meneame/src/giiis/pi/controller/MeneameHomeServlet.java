package giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import giiis.pi.model.News;
import giiis.pi.model.User;

/**
 * Servlet implementation class MeneameHomeServlet
 */
@WebServlet(
		name="MeneameHomeServlet",
		urlPatterns={"/MeneameHomeServlet"})
public class MeneameHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeneameHomeServlet() {
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
		String category=request.getParameter("category");
		if(category != null){
			//si se paso por parametro el valor desde el jsp se guarda en sesion
			// si fuera null, ya se tiene uno en sesion (ya tiene uno por defecto
			// desdes que se inicio la aplicacion)
			session.setAttribute("category",category);
		}else{
			category=(String) session.getAttribute("category");
			if(category==null){// en este punto no esta ni como parametro en la request ni en sesion
				category="todas";
				session.setAttribute("category",category);
			}
		}
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
		String pageactual=request.getParameter("actualpage");
		Long actualpage=null;
		if(pageactual != null){
			if(!pageactual.matches("[0-9]+") || pageactual.matches("[0]")){
				actualpage=(long) 1;
			}else{
				actualpage=Long.valueOf(pageactual).longValue();
			}
			session.setAttribute("actualpage",actualpage);
		}else{
			actualpage=(Long) session.getAttribute("actualpage");
			if(actualpage==null){// en este punto no esta ni como parametro en la request ni en sesion
				actualpage=(long) 1;
				session.setAttribute("actualpage",actualpage);
			}
		}
		String offsetnews=String.valueOf((actualpage-1)*10);
		// consultar segun la categoria y traerlas por defecto ordenadas por tiempo ?????????????
		NewsDAO newsDao= new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		CommentDAO commentDao= new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		UserDAO userDao=new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		List <Long> countCommetNews=new ArrayList<Long>(); 
		List<News> newsList =null;
		if(category.equals("todas")){// todas las noticias
			newsList=newsDao.getAllSorted(filtro, offsetnews);
			if(newsList.isEmpty() && actualpage>1){
				offsetnews=String.valueOf(((actualpage-1)*10)-10);
				newsList=newsDao.getAllSorted(filtro, offsetnews);
				session.setAttribute("actualpage",actualpage-1);
			}
		}else{// solo las filtradas por categoria y filtro
			newsList=newsDao.getAllByCategorySorted(category, filtro, offsetnews);
			if(newsList.isEmpty() && actualpage>1){
				offsetnews=String.valueOf(((actualpage-1)*10)-10);
				newsList=newsDao.getAllByCategorySorted(category, filtro, offsetnews);
				session.setAttribute("actualpage",actualpage-1);
			}
		}		
		Map<News, User> newsMap = new LinkedHashMap<News, User>();		
		Iterator<News> it = newsList.iterator();		
		while(it.hasNext()) {
			News news = (News) it.next();
			User user = userDao.get(news.getOwner());
			countCommetNews.add(commentDao.getCommentCountByNews(news.getId()));
			newsMap.put(news, user);			
		}
		List<News> mostHittedNewsList =newsDao.getMostHittedOrLiked("hits");
		List<News> mostLikedNewsList =newsDao.getMostHittedOrLiked("likes");
		request.setAttribute("mostHittedNewsList",mostHittedNewsList);
		request.setAttribute("mostLikedNewsList",mostLikedNewsList);
		request.setAttribute("newsMap",newsMap);
		request.setAttribute("countCommetNews",countCommetNews);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/MeneameHome.jsp");
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
