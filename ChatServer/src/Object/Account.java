package Object;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5964396494125977697L;
	private String username;
	private String email;
	private String password;
	private ImageIcon avatar;
	private String avatarString;
	private String displayname;

	public String getDisplayname() {
		if(this.displayname==null) return this.username;
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
	public void setAvatarString(String avatar) {
		this.avatarString = avatar;
	}
	public String getAvaString() {
		return this.avatarString;
	}
	public ImageIcon getAvatar() {
		return avatar;
	}
	public Account() {
		this.email=null;
		this.username=null;
		this.password=null;
		this.displayname=null;
	}
	public Account(String username, String pass) {
		this.email=null;
		this.username=username;
		this.password=pass;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
}
