package giiis.pi.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import giiis.pi.model.adapter.DateAdapter;
import giiis.pi.model.adapter.TimeAdapter;

@XmlRootElement
public class Comment {
	
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "owner")
	private long owner;
	@XmlElement(name = "news")
	private long news;
	@XmlElement(name = "dateStamp")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateStamp;
	@XmlElement(name = "timeStamp")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time timeStamp;
	@XmlElement(name = "text")
	private String text;
	@XmlElement(name = "likes")
	private int likes;
	
	
	
	
	public Comment(){
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
	public long getNews() {
		return news;
	}
	public void setNews(long news) {
		this.news = news;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public boolean validate(Map<String, String> messages){
		if(text==null || text.trim().isEmpty() || text.length()>400) {
			messages.put("error", "error comentario");
			return false;
		}else{
			return true;
		}
	}
	

}
