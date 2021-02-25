package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Vector;

import Object.Account;
import Object.GroupChat;
import Object.Message;
import SQL.ConnectDB;
import SQL.SQLCommand;
import Thread.*;

public class ServerSocketConnect {
	public static ArrayList<ServiceThread> listClient = new ArrayList<ServiceThread>();
	public static ArrayList<Message> AllOldMessage = new ArrayList<Message>();
	public static void login() throws IOException {
		ServerSocket login = new ServerSocket(6000);
		while(true) {

			try {
				Socket skloginregister = login.accept();
				DataInputStream datain = new DataInputStream(skloginregister.getInputStream());
				String input = datain.readUTF();
				if(getType(input).equalsIgnoreCase("login")) {

					Account temp = new Account();
					temp.setUsername(getUsername(input));
					temp.setPassword(getPasswordOrText(input));
					DataOutputStream dataout = new DataOutputStream(skloginregister.getOutputStream());
					
					if(SQL.SQLCommand.login(temp.getUsername(), temp.getPassword())) {
						dataout.writeInt(1);
						ObjectOutputStream objout = new ObjectOutputStream(skloginregister.getOutputStream());
						ArrayList<Account> listuser = SQLCommand.getAllAccount();
						objout.writeObject(listuser);
						ArrayList<GroupChat> listgroup = SQLCommand.getListGroup(temp.getUsername());
						objout.writeObject(listgroup);
						
						AllOldMessage= SQL.SQLCommand.getAllOldMessage();
						ArrayList<String> OldMessOfUser = convertAllMessArrayListString(getListMessageofUserName(temp.getUsername()));
						dataout.writeInt(OldMessOfUser.size());
						for(int i=0; i< OldMessOfUser.size();i++) {
							dataout.writeUTF(OldMessOfUser.get(i));
						}
						
						ServiceThread svthread = new ServiceThread(skloginregister, temp);
						svthread.start();
						listClient.add(svthread);

					} else dataout.writeInt(0);
				} else if(getType(input).equalsIgnoreCase("register")) {
					DataOutputStream dataout = new DataOutputStream(skloginregister.getOutputStream());
					
					Account temp = new Account();
					temp.setUsername(getUsername(input));
					temp.setPassword(getPasswordOrText(input));
					temp.setEmail(getEmail(input));
					
					if(!SQL.SQLCommand.isEmailAlreadyExist(temp.getEmail())&&!SQL.SQLCommand.isNameAlreadyExist(temp.getUsername())) {
						SQL.SQLCommand.addAccount(temp);
						dataout.writeInt(1);
					} else dataout.writeInt(0);
				} else skloginregister.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				login.close();
			}
		}
	}
	public static ServiceThread getThreadWithName(String username) {
		for(int i=0;i<listClient.size();i++) {
			if(listClient.get(i).getUser().getUsername().equalsIgnoreCase(username)) {
				return listClient.get(i);
			}
		}
		return null;
	}
	public static ArrayList<Message> getListMessageofUserName(String username) {
		ArrayList<Message> result = new ArrayList<Message>();
		ArrayList<GroupChat> listgroup =SQL.SQLCommand.getListGroup(username);
		for(int i=0; i<AllOldMessage.size();i++) {
			if(AllOldMessage.get(i).getReceiver().equalsIgnoreCase(username)||AllOldMessage.get(i).getSender().equalsIgnoreCase(username)) {
				result.add(AllOldMessage.get(i));
			}
			for(int j=0; j< listgroup.size();j ++) {
				if(AllOldMessage.get(i).getReceiver().equalsIgnoreCase(listgroup.get(j).getName())&&!AllOldMessage.get(i).getSender().equalsIgnoreCase(username)) {
					result.add(AllOldMessage.get(i));
				}
			}
		}
		return result;
	}

	public static ArrayList<String> convertAllMessArrayListString(ArrayList<Message> a) {
		String AllOldMessage="oldmessage\n";
		ArrayList<String> Result = new ArrayList<String>();
		for(int i=0;i<a.size();i++) {
			AllOldMessage+= (a.get(i).getMessage()+"--"+a.get(i).getSender()+"--"+a.get(i).getReceiver()+"--"+a.get(i).getStringTime()+"--"+a.get(i).getType()+"\n");
		}
		while(AllOldMessage.length()>50000) {
			Result.add(AllOldMessage.substring(0, 50000));
			AllOldMessage= AllOldMessage.substring(50000, AllOldMessage.length());
		}
		Result.add(AllOldMessage.substring(0, AllOldMessage.length()));
		return Result;
	}
	public static String convertAccountToString(ArrayList<String> account, String notinclude) {
		String a= "";
		for(int i=0; i<account.size();i++) {
			if(!account.get(i).equalsIgnoreCase(notinclude)) a+=(account.get(i)+"\n");
			
		}
		return a;
	}
	public static String getEmail(String string) {
		String[]result= string.split("\n");
		return result[3];
	}
	public static String getPasswordOrText(String string) {
		String[]result= string.split("\n");
		return result[2];
	}
	public static String getUsername(String string) {
		String[]result= string.split("\n");
		return result[1];
	}
	public static String getType(String string) {
		String[]result= string.split("\n");
		return result[0];
	}
	public static void removeThread(ServiceThread a) {
		for(int i=0; i<listClient.size();i++) {
			if(listClient.get(i).equals(a)) {
				listClient.remove(i);
			}
		}
	}
	public static void main(String args[]) {
		try {
			new ConnectDB();
			login();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
