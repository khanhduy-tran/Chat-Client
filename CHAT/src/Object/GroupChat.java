package Object;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tools.ImageEncode64;

public class GroupChat implements Serializable{

	private static final long serialVersionUID = 719996357585109749L;
	private String name;
	private String avatarUTF;
	public ArrayList<String> listmember;
	private ImageIcon avatar;
	public GroupChat() {
		this.name = null;
		this.avatarUTF = null;
		this.avatar= null;
		this.listmember = new ArrayList<String>();
	}

	public ImageIcon getAvatar() {
		if(avatarUTF!=null) return ImageEncode64.decoder(this.avatarUTF);
		if(avatar==null&&avatarUTF==null) return new ImageIcon(Account.class.getResource("/img/wolfuser.png"));
		return avatar;
	}
	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatarUTF() {
		return avatarUTF;
	}
	public void setAvatarUTF(String avatarUTF) {
		this.avatarUTF = avatarUTF;
	}
	public ArrayList<String> getListmember() {
		return listmember;
	}
	public void setListmember(String listmemberString) {
		String a[] = listmemberString.split("\n");
		for(int i=0; i< a.length; i++) {
			listmember.add(a[i]);
		}
	}
	public boolean hasUser(String username) {
		for(int i=0; i< getListmember().size() ; i++) {
			if(getListmember().get(i).equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
		
	}
	
}
