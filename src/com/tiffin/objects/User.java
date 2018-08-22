package com.tiffin.objects;

public class User {

	private String userId;
	private String userName;
	private String password;
	private String phone;
	private String email;
	private String location;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String userName, String password, String phone, String email, String location) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.location = location;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return userId;
	}

}
