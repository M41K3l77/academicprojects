package giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import giiis.pi.model.Comment;

public interface CommentDAO {
	
	public List<Comment> getAll();
	public List<Comment>  getAllByOwner(long owner);
	public List<Comment>  getAllByOwner(long owner, String offsetcomments);
	public List<Comment> getAllByNews(long news);
	public List<Comment> getAllByNews(long news, String offsetcomments);
	public Comment get(long id);	
	public long add(Comment comment);
	public boolean save(Comment comment);
	public boolean delete(long id);
	public Long getCommentCountByNews(long news);
	
	public void setConnection(Connection conn);
}
