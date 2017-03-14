package giiis.pi.resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


@Path("/comments")
public class CommentResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentsJSON() throws SQLException{

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		List<Comment> comments = commentDao.getAll();

		return comments;		
	}
	
	@GET
	@Path("/newsrestricted/comment/{commentid: [0-9]+}")	  
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentOwnerByIdJSON(@PathParam("commentid") long commentid, @Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		Comment comment = commentDao.get(commentid);
		if (comment == null) {
			throw new CustomNotFoundException("Comentario ("+ commentid + ") is not found");
		}
		if(comment != null && comment.getOwner() == user.getId()){
			commentDao.get(commentid);
		}else{
			throw new CustomBadRequestException("Error in id");
		}
		return comment; 
	}
	
	@GET
	@Path("/notice/{noticeid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentsByNewsJSON(@PathParam("noticeid") Long noticeid, @QueryParam("offsetcomments") String offsetcomments) throws SQLException{

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		List<Comment> comments=commentDao.getAllByNews(noticeid, offsetcomments);

		return comments;		
	}
	
	@GET
	@Path("/newsrestricted/user/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentsByUserJSON(@PathParam("userid") long userid, @QueryParam("offsetcomments") String offsetcomments
			,@Context HttpServletRequest request) throws SQLException{

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		User user = (User) request.getSession().getAttribute("user");
		
		commentDao.setConnection(conn);

		List <Comment> usercomments=null;
		if(user.getId() == userid){
			usercomments=commentDao.getAllByOwner(userid, offsetcomments);
		}else{
			throw new CustomBadRequestException("Errors in parameters");
		}		
		
		return usercomments;		
	}
	
	//getCommentCountByNews
	@GET
	@Path("commentcount/{newsid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getCommentCountByNewsJSON(@PathParam("newsid") Long newsid) throws SQLException{

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		Long countcomments=commentDao.getCommentCountByNews(newsid);

		return countcomments;		
	}
	
	@POST
	@Path("/newsrestricted/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Comment nuevoComment, @Context HttpServletRequest request) {	

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Response res;
		if(user.getId() == nuevoComment.getOwner()){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		Comment comment = new Comment();
		comment.setNews(nuevoComment.getNews());
		comment.setOwner(user.getId());
		comment.setText(nuevoComment.getText());
		

		Map<String, String> messages = new HashMap<String, String>();
		if (!comment.validate(messages))
			throw new CustomBadRequestException("Errors in parameters");
		//save comment in DB
		long id = commentDao.add(comment);
		// el insertar un comentario le da +1 al karma de la noticia
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News news=newsDao.get(comment.getNews());
		news.setLikes(news.getLikes()+1);
		newsDao.save(news);
		//
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
	
	@PUT
	@Path("/newsrestricted/comment/{commentid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Comment commentUpdate, @PathParam("commentid") long commentid, @Context HttpServletRequest request) throws Exception{
		Response response = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);


		//Comprobamos que existe el comentario
		Comment comment = commentDao.get(commentUpdate.getId());
		if(comment != null){
			if (comment.getId()!=commentid || comment.getOwner()!= user.getId()){
				throw new CustomBadRequestException("Error in id");
			}else{
				Map<String, String> messages = new HashMap<String, String>();
				if (commentUpdate.validate(messages)){
					commentDao.save(commentUpdate);

				}else {
					throw new CustomBadRequestException("Errors in parameters");
				}

			}
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}		
		return response;

	}
	///////
	@PUT
	@Path("/newsrestricted/comment/{commentid: [0-9]+}/votecomment")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response voteComment(Comment commentUpdate, @PathParam("commentid") long commentid, @Context HttpServletRequest request) throws Exception{
		Response response = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);


		//Comprobamos que existe el comentario
		Comment comment = commentDao.get(commentUpdate.getId());
		if(comment != null){
			if (comment.getId()!=commentid){
				throw new CustomBadRequestException("Error in id");
			}else{
				Map<String, String> messages = new HashMap<String, String>();
				if (commentUpdate.validate(messages)){
					commentDao.save(commentUpdate);

				}else {
					throw new CustomBadRequestException("Errors in parameters");
				}

			}
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}			
		return response;

	}
	///////

	@DELETE
	@Path("/newsrestricted/{commentid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("commentid") long commentid, @Context HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		//Comprobamos que existe el comentario
		Comment comment = commentDao.get(commentid);
		if(comment != null && comment.getOwner() == user.getId()){
			commentDao.delete(commentid);
		}else{
			throw new CustomBadRequestException("Error in id");
		}

		return Response.noContent().build(); //204 no content 
	}
}
