package giiis.pi.resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import giiis.pi.dao.JDBCNewsDAOImpl;
import giiis.pi.dao.JDBCUserDAOImpl;
import giiis.pi.dao.NewsDAO;
import giiis.pi.dao.UserDAO;
import giiis.pi.model.News;
import giiis.pi.model.User;
import giiis.pi.resources.exceptions.CustomBadRequestException;

@Path("/users")
public class UserResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request; 
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String authUsersJSON(@QueryParam("username") String username, @QueryParam("password")String password) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		User user=null;
		UserDAO userDao=new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		user=userDao.get(username);
		
		if(user == null){
			System.out.println("user null");
		}
		if(user!=null && user.getPassword().equals(password)){
			correct = "{\"ok\":\"true\",\"username\":\""+user.getName()+"\",\"email\":\""+user.getEmail()+"\",\"useroid\":"+user.getId()+",\"password\":\""+user.getPassword()+"\"}";
			
			this.request.getSession().setAttribute("user", user);
		}
		return correct;	
	}
	
	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserNameByIdJSON(@PathParam("userid") Long userid) throws SQLException{

		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO UserDao = new JDBCUserDAOImpl();
		UserDao.setConnection(conn);
		User user=UserDao.get(userid);
		if(user == null){
			System.out.println("user null");
		}
		if(user!=null){
			correct = "{\"ok\":\"true\",\"username\":\""+user.getName()+"\"}";
		
		}
		return correct;		
	}
	
	@POST
	@Path("/newsrestricted/logout/{username: [a-zA-Z][a-zA-Z0-9]{2,12}}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(@PathParam("username") String username,@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");
		if(user.getName().equals(username)){// evitar desloguear un usuario que no sea el suyo
			session = request.getSession(false);		
			if (session != null){
				session.removeAttribute("user");
				session.invalidate();
			}
			session = request.getSession(true);
			 
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("/newsrestricted/{userid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarUser(User userUpdate, @PathParam("userid") long userid) throws Exception{
		Response response = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		HttpSession session = request.getSession();
		User usersesion = (User) session.getAttribute("user");
		//Comprobamos que existe el usuario
		User user = userDao.get(userUpdate.getId());
		if(user != null){
			System.out.println("el usuario esta en la DB");
			if (usersesion.getId()!=userid || usersesion.getId()!= userUpdate.getId()){
				System.out.println("el id es incorrecto");
				throw new CustomBadRequestException("Error in id");
			}else{
				Map<String, String> messages = new HashMap<String, String>();
				if (userUpdate.validate(messages)){
					// actualizarla
					userDao.save(userUpdate);
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
	@Path("/newsrestricted/{userid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userid") long userid,@Context HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");

		if(user.getId() == userid){// solo se da de baja si es el usuario es el de la sesion
			Connection conn = (Connection) sc.getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(conn);
			userDao.delete(userid);
			session = request.getSession(false);		
			if (session != null){
				session.removeAttribute("user");
				session.invalidate();
			}
		}else{
			throw new CustomBadRequestException("Errors in parameters");
		}		

		return Response.noContent().build(); //204 no content 
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registroUser(User userNuevo,@Context HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		
		Response res;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		Map<String, String> messages = new HashMap<String, String>();
		if (userNuevo==null || !userNuevo.validate(messages)){
			res=Response.noContent().build();
		}else{
			// ver si ya existe en la base de datos
			User userEnBD=userDao.get(userNuevo.getName());
			if(userEnBD == null){// el usuario no existe y se puede registrar
				//save order in DB
				long id = userDao.add(userNuevo);
				userNuevo.setId(id);
				session.setAttribute("user", userNuevo);
				res = Response //return 201 and Location: /register/userid
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
				res=Response.noContent().build();
			}
			
		}
		
		return res; 
	}
}
