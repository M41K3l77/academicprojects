package giiis.pi.resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import giiis.pi.dao.CommentDAO;
import giiis.pi.dao.JDBCCommentDAOImpl;
import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.model.Comment;
import giiis.pi.model.News;
import giiis.pi.model.User;
import giiis.pi.resources.exceptions.CustomBadRequestException;
import giiis.pi.resources.exceptions.CustomNotFoundException;

@Path("/news")
public class NewsResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/allnews")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getAllNewsJSON(@QueryParam("offsetnews") String offsetnews) throws SQLException {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(offsetnews==null){
			offsetnews="0";
		}
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		List<News> news= new ArrayList<News>();

		news = newsDao.getAll(offsetnews);

		return news;
	}

	@GET
	@Path("/allnewssorted")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getAllNewsSortedJSON(@QueryParam("filtro") String filtro, @QueryParam("offsetnews") String offsetnews) throws SQLException {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		if(filtro == null){
			filtro="fecha";
		}
		if(offsetnews==null){
			offsetnews="0";
		}
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		List<News> news= new ArrayList<News>();

		news = newsDao.getAllSorted(filtro, offsetnews);

		return news;
	}

	@GET
	@Path("/allnewsbycategorysorted")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getAllNewsByCategorySorted(@QueryParam("category") String category, @QueryParam("filtro") String filtro,
			@QueryParam("offsetnews") String offsetnews) throws SQLException {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		List<News> news= new ArrayList<News>();
		if(filtro == null){
			filtro="fecha";
		}
		if(offsetnews==null){
			offsetnews="0";
		}
		if(category == null || category.equals("todas")){
			news = newsDao.getAllSorted(filtro, offsetnews);
		}else{
			news = newsDao.getAllByCategorySorted(category, filtro, offsetnews);
		}
		return news;
	}

	@GET
	@Path("/newshittedorliked")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsgetMostHittedOrLiked(@QueryParam("mosthittedorliked") String mosthittedorliked) throws SQLException {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		List<News> mosthittedorlikedNewsList= new ArrayList<News>();
		mosthittedorlikedNewsList =newsDao.getMostHittedOrLiked(mosthittedorliked);

		return mosthittedorlikedNewsList;
	}

	@GET
	@Path("/newsrestricted/allnewsbyownersorted/{owner: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getAllNewsgetAllByOwnerSorted(@PathParam("owner") long owner, @QueryParam("filtro") String filtro,
			@QueryParam("offsetnews") String offsetnews,@Context HttpServletRequest request) throws SQLException {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		User user = (User) request.getSession().getAttribute("user");
		List<News> userNews=null;
		if(user.getId() == owner){
			NewsDAO newsDao = new JDBCNewsDAOImpl();
			newsDao.setConnection(conn);
			userNews= new ArrayList<News>();
			userNews=newsDao.getAllByOwnerSorted(owner, filtro, offsetnews);
		}else{
			throw new CustomBadRequestException("Errors in id");
		}
		
		return userNews;
	}

	@GET
	@Path("/{noticeid: [0-9]+}")	  
	@Produces(MediaType.APPLICATION_JSON)
	public News getNewsByIdJSON(@PathParam("noticeid") long noticeid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		News news = newsDao.get(noticeid);
		if (news == null) {
			throw new CustomNotFoundException("News ("+ noticeid + ") is not found");
		}

		return news; 
	}

	//POST que recibe datos de la nueva noticia
	@POST
	@Path("/newsrestricted/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(News noticiaNueva,@Context HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		
		Response res;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		System.out.println("user sesion: "+user.getId()+" propietario de la noticia: "+noticiaNueva.getOwner());
		if(user.getId() == noticiaNueva.getOwner()){
		News news = new News();			
		news.setUrl(noticiaNueva.getUrl());
		news.setTitle(noticiaNueva.getTitle());
		news.setText(noticiaNueva.getText());
		news.setCategory(noticiaNueva.getCategory());
		news.setOwner(user.getId());
		Map<String, String> messages = new HashMap<String, String>();
		
		if (!news.validate(messages))
			throw new CustomBadRequestException("Errors in parameters");
		//save order in DB
		long id = newsDao.add(news);

		res = Response //return 201 and Location: /orders/newid
				.created(
						uriInfo
						.getAbsolutePathBuilder()
						.path(Long.toString(id))
						.build())
				.contentLocation(
						uriInfo
						.getAbsolutePathBuilder()
						.path(Long.toString(id))
						.build())
				.build();
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return res; 
	}

	//PUT que actualiza a partir del objeto recibido
	@PUT
	@Path("/newsrestricted/{noticeid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(News newsUpdate, @PathParam("noticeid") long noticeid,@Context HttpServletRequest request) throws Exception{
		Response response = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);


		//Comprobamos que existe la noticia
		News news = newsDao.get(newsUpdate.getId());
		if(news != null){
			if (news.getId()!=noticeid || news.getOwner()!= user.getId()){
				throw new CustomBadRequestException("Error in id");
			}else{
				Map<String, String> messages = new HashMap<String, String>();
				if (newsUpdate.validate(messages)){
					newsUpdate.setLikes(news.getLikes());
					newsUpdate.setHits(news.getHits());
					newsDao.save(newsUpdate);

				}else {
					throw new CustomBadRequestException("Errors in parameters");
				}

			}
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}			
		return response;

	}

	//PUT menear noticia
	@PUT
	@Path("/{noticeid: [0-9]+}/menearnoticia")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response menearNoticia(News newsUpdate, @PathParam("noticeid") long noticeid) throws Exception{
		Response response = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		//Comprobamos que existe la noticia
		News news = newsDao.get(newsUpdate.getId());
		if(news != null){
			if (news.getId()!=noticeid){
				System.out.println("el id es incorrecto");
				throw new CustomBadRequestException("Error in id");
			}else{
				Map<String, String> messages = new HashMap<String, String>();
				if (newsUpdate.validate(messages)){
					// actualizarla
					newsDao.save(newsUpdate);
				}else {
					throw new CustomBadRequestException("Errors in parameters");
				}
			}
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}			
		return response;
	}
	
	@DELETE
	@Path("/newsrestricted/{noticeid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteNews(@PathParam("noticeid") long noticeid,@Context HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		//Comprobamos que existe la noticia
		News news = newsDao.get(noticeid);
		if(news != null && news.getOwner() == user.getId()){
			newsDao.delete(noticeid);
		}else{
			throw new CustomBadRequestException("Error in id");
		}		

		return Response.noContent().build(); //204 no content 
	}
}
