package giiis.pi.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*urlPatterns={"/UserListNoticiasServlet","/BorrarNoticiaServlet",
		"/EditarHistoriaServlet","/EnviarComentarioServlet",
		"/EnviarHistoriaServlet"}*/
/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
,          urlPatterns={"/newsrestricted/*","/rest/news/newsrestricted/*","/rest/comments/newsrestricted/*","/rest/users/newsrestricted/*"})
public class LoginFilter implements Filter {
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		if(session.getAttribute("user") == null) {
			logger.info("No hay usuario logeado");
			res.sendRedirect(req.getContextPath()+"/AccessLoginViewServlet");
		}else{
			// pass the request along the filter chain
			logger.info("El usuario esta logeado");
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
