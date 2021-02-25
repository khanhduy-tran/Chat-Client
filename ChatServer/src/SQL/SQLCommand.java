package SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Object.Account;
import Object.GroupChat;
import Object.Message;

public class SQLCommand {
	public static boolean login(String user, String password) {
		String sql=("SELECT * FROM account");
		ResultSet rs = null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next())
				if(rs.getString(1).equals(user)) if(rs.getString(2).equals(password)) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void UpdateProfileDisplayName(String username , String newdata) {

		String sql=("UPDATE account SET displayname='"+newdata+"' WHERE username='"+username+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void UpdateProfilePassword(String username , String newdata) {

		String sql=("UPDATE account SET password='"+newdata+"' WHERE username='"+username+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void UpdateProfileAvatar(String username , String newdata) {

		String sql=("UPDATE account SET avatar='"+newdata+"' WHERE username='"+username+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void UpdateGroupChatName(String groupchatname , String newdata) {

		String sql=("UPDATE groupchat SET name='"+newdata+"' WHERE username='"+groupchatname+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void UpdateGroupChatAvatar(String groupchatname , String newdata) {

		String sql=("UPDATE groupchat SET avatar='"+newdata+"' WHERE username='"+groupchatname+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Account getAccountData(String username) {
		String sql=("SELECT * FROM account WHERE username='"+username+"'");
		ResultSet rs = null;
		Account temp = null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				temp = new Account();
				temp.setUsername(rs.getString(1));
				temp.setPassword(rs.getString(2));
				temp.setEmail(rs.getString(3));
				temp.setAvatarString(rs.getString(4));
				temp.setDisplayname(rs.getString(5));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public static GroupChat getGroupWithGroupName(String groupname) {
		String sql=("SELECT * FROM groupchat WHERE name='"+groupname+"'");
		ResultSet rs = null;
		GroupChat temp= null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				temp = new GroupChat();
				temp.setName(rs.getString(2));
				temp.setAvatarUTF(rs.getString(3));
				temp.setListmember(rs.getString(4));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public static void addMemberGroup(String groupname, String username) {
		GroupChat group = getGroupWithGroupName(groupname);
		if(!group.hasUser(username)) {
			group.listmember.add(username);
			String sql=("UPDATE groupchat SET member='"+group.getListmemberSQL()+"' WHERE name='"+groupname+"'");
			try {
				PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
				ps.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void kickMemberGroup(String groupname, String username) {
		GroupChat group = getGroupWithGroupName(groupname);
		if(group.hasUser(username)) {
			group.listmember.remove(username);
			String sql=("UPDATE groupchat SET member='"+group.getListmemberSQL()+"' WHERE name='"+groupname+"'");
			try {
				PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
				ps.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static ArrayList<GroupChat> getListGroup(String username) {
		String sql=("SELECT * FROM groupchat");
		ResultSet rs = null;
		ArrayList<GroupChat> result = new ArrayList<GroupChat>();
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				GroupChat temp = new GroupChat();
				temp.setName(rs.getString(2));
				temp.setAvatarUTF(rs.getString(3));
				temp.setListmember(rs.getString(4));
				if(temp.hasUser(username)) {
					result.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static ArrayList<Account> getAllAccount() {
		String sql=("SELECT * FROM account");
		ResultSet rs = null;
		ArrayList<Account> result = new ArrayList<Account>();
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Account temp = new Account();
				temp.setUsername(rs.getString(1));
				temp.setPassword(rs.getString(2));
				temp.setEmail(rs.getString(3));
				temp.setAvatarString(rs.getString(4));
				temp.setDisplayname(rs.getString(5));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static ArrayList<String> getAllUsername() {
		String sql=("SELECT * FROM account");
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static boolean isNameAlreadyExist(String user) {
		String sql=("SELECT * FROM account");
		ResultSet rs = null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) if(rs.getString(1).equals(user)) return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isEmailAlreadyExist(String email) {
		String sql=("SELECT * FROM account");
		ResultSet rs = null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) if(rs.getString(3).equals(email)) return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void addAccount(Account a) {		
		String sql=("INSERT INTO account(username, password, email) VALUES ('"+a.getUsername()+"','"+a.getPassword()+"','"+a.getEmail()+"')");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void addMessage(Message a) {		
		String sql=("INSERT INTO message(message, sender, receiver, sendTime, type) VALUES ('"+a.getMessage()+"','"+a.getSender()+"','"+a.getReceiver()+"','"+a.getSQLsendTime()+"','"+a.getType()+"')");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void addGroupChat(GroupChat a) {		
		String sql=("INSERT INTO groupchat(name, member) VALUES ('"+a.getName()+"','"+a.getListmemberSQL()+"')");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void deleteGroupChat(String groupname) {		
		String sql=("DELETE FROM groupchat WHERE name='"+groupname+"'");
		String sql2=("DELETE FROM message WHERE receiver ='"+groupname+"'");
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			ps.executeUpdate();
			ps = ConnectDB.conn.prepareCall(sql2);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static ArrayList<Message> getAllOldMessage() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
		ArrayList<Message> result = new ArrayList<Message>();
		String sql=("SELECT * FROM message");
		ResultSet rs = null;
		try {
			PreparedStatement ps = ConnectDB.conn.prepareCall(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Message temp = new Message();
				temp.setMessage(rs.getString(1));
				temp.setSender(rs.getString(2));
				temp.setReceiver(rs.getString(3));
				temp.setSendTime(rs.getTimestamp(4));
				temp.setType(rs.getString(5));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
