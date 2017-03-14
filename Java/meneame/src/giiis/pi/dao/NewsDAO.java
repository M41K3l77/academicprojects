package giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import giiis.pi.model.News;



public interface NewsDAO {
	
	public List<News> getAll(String offsetnews);
	public List<News> getAllSorted(String filtro, String offsetnews);
	public List<News> getAllByOwner(long owner, String offsetnews);
	public List<News> getAllByOwnerSorted(long owner, String filtro, String offsetnews);
	public List<News> getAllByCategory(String category, String offsetnews);
	public List<News> getAllByCategorySorted(String category, String filtro, String offsetnews);
	/*public List<News> getMostHitted();
	public List<News> getMostLiked();*/
	public List<News>getMostHittedOrLiked(String mosthittedorliked);
	public News get(long id);	
	public long add(News news);
	public boolean save(News news);
	public boolean delete(long id);
	
	public void setConnection(Connection conn);
}
