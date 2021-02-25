package Object;
import java.io.Serializable;
import java.util.ArrayList;


public class GroupChat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 719996357585109749L;
	private String name;
	private String avatarUTF;
	public ArrayList<String> listmember;
	public GroupChat() {
		this.name = null;
		this.avatarUTF = null;
		this.listmember = new ArrayList<String>();
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
	public String getListmemberSQL() {
		String listmemberString = "";
		for(String s: listmember) {
			listmemberString+= (s+"\n");
		}
		return listmemberString;
	}
	public void setListmember(String listmemberString) {
		String a[] = listmemberString.split("\n");
		for(int i=0; i< a.length; i++) {
			listmember.add(a[i]);
		}
	}
	public boolean hasUser(String username) {
		for(int i=0; i< getListmember().get(0).length(); i ++) {
			if(getListmember().get(0).charAt(i)==(char) '\n') System.out.println(true);
		}
		for(int i=0; i< getListmember().size() ; i++) {
			if(getListmember().get(i).equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
		
	}
	
}
