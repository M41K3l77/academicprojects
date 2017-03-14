package giiis.pi.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.logging.Logger;

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
 * Servlet implementation class EnviarHistoriaServlet
 */
@WebServlet(
		name="EnviarHistoriaServlet",
		urlPatterns={"/newsrestricted/EnviarHistoriaServlet"})
public class EnviarHistoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarHistoriaServlet() {
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
		User user= (User)session.getAttribute("user");
		String category=request.getParameter("category");
		String url=request.getParameter("url");
		// validar url
		boolean urlValid = true;
		try {
			URL uRL = new URL(url);
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			urlValid = false;
			logger.info("url malformed "+url);		
		}
		String categoryRegex="(ocio)|(actualidad)|(cultura)|(deporte)|(tecnologia)";
		String title=request.getParameter("title");
		String text=request.getParameter("text");
		// validacion resto datos en servidor
		if(urlValid && category.matches(categoryRegex) && (title.length()>0 && title.length()<=150) && (text.length()>0 && text.length()<=400)){
			NewsDAO newsDao= new JDBCNewsDAOImpl();
			newsDao.setConnection(conn);
			News notice=new News();
			notice.setOwner(user.getId());
			notice.setUrl(url);
			notice.setTitle(title);
			notice.setText(text);
			notice.setCategory(category);
			newsDao.add(notice);
			response.sendRedirect("UserListNoticiasServlet");
		}else{
			if(!urlValid){
				String errorurl="url mal formada";
				session.setAttribute("errorurl", errorurl);
			}
			if(!category.matches(categoryRegex)){
				String errorcategory="no es una de las categorias";
				session.setAttribute("errorcategory", errorcategory);
			}
			if(!(title.length()>0 && title.length()<=150)){
				String errortitulo="longitud texto incorrecto";
				session.setAttribute("errortitulo", errortitulo);
			}
			if(!(text.length()>0 && text.length()<=400)){
				String errordescripcion="longitud descripcion incorrecto";
				session.setAttribute("errordescripcion", errordescripcion);
			}
			response.sendRedirect("UserCrearHistoriaServlet");
		}
		
	}

}
