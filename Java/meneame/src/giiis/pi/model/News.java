package giiis.pi.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import giiis.pi.model.adapter.DateAdapter;
import giiis.pi.model.adapter.TimeAdapter;

@XmlRootElement
public class News {
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "owner")
	private long owner;
	@XmlElement(name = "dateStamp")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateStamp;
	@XmlElement(name = "timeStamp")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time timeStamp;
	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "text")
	private String text;
	@XmlElement(name = "url")
	private String url;
	@XmlElement(name = "category")
	private String category;
	@XmlElement(name = "likes")
	private int likes;
	@XmlElement(name = "hits")
	private int hits;
	
	
	
	public News(){
		Calendar calendar = new GregorianCalendar();
		dateStamp = calendar.getTime();
		timeStamp = new Time(calendar.getTimeInMillis());
	
	}
	
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	public Time getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}

	public boolean validate(Map<String, String> messages) {
		// TODO Auto-generated method stub
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
		if(urlValid && category.matches(categoryRegex) && (title.length()>0 && title.length()<=150) && (text.length()>0 && text.length()<=400)) {
			
			return true;
		}else{
			messages.put("error", "Empty ...");
			return false;
		}
	}
	
	

}
