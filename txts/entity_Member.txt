package entity;

import java.io.Serializable;

public class Member implements Serializable{
	private int id;
	private String username;
	private String password;
	private String name;

	public Member() {
		super();
	}

	public Member(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
}
