package giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import giiis.pi.model.News;



public class JDBCNewsDAOImpl implements NewsDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCNewsDAOImpl.class.getName());

	@Override
	public List<News> getAll(String offsetnews) {

		if (conn == null) return null;
						
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News LIMIT 10 OFFSET "+offsetnews);
						
			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList: "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
					+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}

	@Override
	public List<News> getAllByOwner(long owner, String offsetnews) {
		
		if (conn == null) return null;
						
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News WHERE owner="+owner+" LIMIT 10 OFFSET "+offsetnews);

			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList by owner("+owner+": "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
				+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}
	
	@Override
	public List<News> getAllByCategory(String category, String offsetnews) {
		
		if (conn == null) return null;
						
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News WHERE category ='"+category+"'"+" LIMIT 10 OFFSET "+offsetnews);

			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList by category("+category+": "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
				+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}
	
	@Override
	public News get(long id) {
		if (conn == null) return null;
		
		News news = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News WHERE id ="+id);			 
			if (!rs.next()) return null; 
			news  = new News();	 
			news.setId(rs.getLong("id"));
			news.setOwner(rs.getLong("owner"));
			news.setDateStamp(rs.getDate("datestamp"));
			news.setTimeStamp(rs.getTime("timestamp"));
			news.setTitle(rs.getString("title"));
			news.setText(rs.getString("text"));
			news.setUrl(rs.getString("url"));
			news.setCategory(rs.getString("category"));
			news.setLikes(rs.getInt("likes"));
			news.setHits(rs.getInt("hits"));
			
			logger.info("fetching news by id("+id+": "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
			+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news;
	}
	
	

	@Override
	public long add(News news) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO News (owner,datestamp,timestamp,title,text,url,category) VALUES('"+
									news.getOwner()+"','"+
									new java.sql.Date(news.getDateStamp().getTime())+"','"+
									news.getTimeStamp()+"','"+
									news.getTitle()+"','"+
									news.getText()+"','"+
									news.getUrl()+"','"+									
									news.getCategory()+"')",Statement.RETURN_GENERATED_KEYS);
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);		
									
				logger.info("creating News:("+id+": "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
				+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean save(News news) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE News SET title='"+
									news.getTitle()+"', text='"+
									news.getText()+"', url='"+
									news.getUrl()+"', category='"+
									news.getCategory()+"', likes='"+
									news.getLikes()+"', hits='"+
									news.getHits()+"' WHERE id = "+news.getId());
				logger.info("updating News: "+news.getId()+" "+news.getOwner()+" "+news.getTitle()
				+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
				done=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM News WHERE id ="+id);
				logger.info("deleting News: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public List<News> getAllByCategorySorted(String category, String filtro, String offsetnews) {

		String sortedElement=null;		
		if(filtro.equals("fecha")){
			sortedElement="datestamp  DESC, timestamp DESC";
		}else{
			sortedElement="hits DESC";
		}
		if (conn == null) return null;
		
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News WHERE category ='"+category+"'"+" ORDER BY "+sortedElement+" LIMIT 10 OFFSET "+offsetnews);

			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList by category sorted("+category+": "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
				+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}
	
	@Override
	public List<News> getAllSorted(String filtro, String offsetnews) {
		String sortedElement=null;
		if(filtro.equals("fecha")){
			sortedElement="datestamp  DESC, timestamp DESC";
		}else{
			sortedElement="hits DESC";
		}

		if (conn == null) return null;
						
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News"+" ORDER BY "+sortedElement+" LIMIT 10 OFFSET "+offsetnews);
						
			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList: "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
					+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}
	
	@Override
	public List<News> getAllByOwnerSorted(long owner, String filtro, String offsetnews) {
		
		String sortedElement=null;
		if(filtro.equals("fecha")){
			sortedElement="datestamp  DESC, timestamp DESC";
		}else{
			sortedElement="hits DESC";
		}
		
		if (conn == null) return null;
						
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News WHERE owner="+owner+" ORDER BY "+sortedElement+" LIMIT 10 OFFSET "+offsetnews);

			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList by owner("+owner+": "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
				+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newsList;
	}
	
	@Override
	public List<News> getMostHittedOrLiked(String mosthittedorliked) {
		if (conn == null) return null;
		
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News ORDER BY "+mosthittedorliked+" DESC LIMIT 4");
						
			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList most hitted: "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
					+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsList;
	}
	
	/*@Override
	public List<News> getMostHitted() {
		if (conn == null) return null;
		
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News ORDER BY hits DESC LIMIT 4");
						
			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList most hitted: "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
					+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsList;
	}*/

	/*@Override
	public List<News> getMostLiked() {
		if (conn == null) return null;
		
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News SELECT * FROM News ORDER BY likes DESC LIMIT 4");
						
			while ( rs.next() ) {
				News news = new News();
				news.setId(rs.getLong("id"));
				news.setOwner(rs.getLong("owner"));
				news.setDateStamp(rs.getDate("datestamp"));
				news.setTimeStamp(rs.getTime("timestamp"));
				news.setTitle(rs.getString("title"));
				news.setText(rs.getString("text"));
				news.setUrl(rs.getString("url"));
				news.setCategory(rs.getString("category"));
				news.setLikes(rs.getInt("likes"));
				news.setHits(rs.getInt("hits"));
				newsList.add(news);
				logger.info("fetching newsList most liked: "+news.getId()+" "+news.getOwner()+" "+news.getDateStamp()+" "+news.getTimeStamp()+" "+news.getTitle()
					+" "+news.getText()+" "+news.getUrl()+" "+news.getCategory()+" "+news.getLikes()+" "+news.getHits());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsList;
	}*/
	
	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
