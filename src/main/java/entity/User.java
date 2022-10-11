package entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String tel;
	private Role role;
	private String info; 
	private boolean isBlocked;
	private double rating;
	private String currentLang;
	
	public User() {
	}
	
	
	public User(int id, String email, String password, String name, String surname, String tel, Role role, String info, boolean isBlocked, double rating, String currentLang) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.tel = tel;
		this.role = role;
		this.info = info;
		this.isBlocked = isBlocked;
		this.rating = rating;
		this.currentLang = currentLang;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


	public boolean getIsBlocked() {
		return isBlocked;
	}


	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public String getCurrentLang() {
		return currentLang;
	}


	public void setCurrentLang(String currentLang) {
		this.currentLang = currentLang;
	}


	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", surname=" + surname + ", role=" + role + " " + (isBlocked ? "BLOCKED" : "") + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}




	
	
}
