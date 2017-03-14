package giiis.pi.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "email")
	private String email;
	@XmlElement(name = "password")
	private String password;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate(Map<String, String> messages){
		String usernameRegex="[a-zA-Z][a-zA-Z0-9]{2,12}";
		String passwordRegex="[a-zA-Z0-9_]{6,12}";
		String emailRegex="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if((password.length()>0 && password.length()<=40) && (email.length()>0 && email.length()<=50) && (name.length()>0 && name.length()<=40) 
				&& name.matches(usernameRegex) && password.matches(passwordRegex) && email.matches(emailRegex)) {
			
			return true;
		}else{
			messages.put("error", "error");
			return false;
		}
	}
}
