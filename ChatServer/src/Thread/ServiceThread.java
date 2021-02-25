package Thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.imageio.IIOException;

import Object.Account;
import Object.GroupChat;
import Object.Message;
import SQL.SQLCommand;
import Socket.ServerSocketConnect;

public class ServiceThread extends Thread{
	private Socket socket;
	private Account user;
	private DataOutputStream dataout;
	private DataInputStream datain;
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
	private InetAddress getInetAddress() {
		InetAddress inetaddr = this.socket.getInetAddress();
		if(this.getSocket().getInetAddress().isLoopbackAddress()) {
			try {
				inetaddr=this.socket.getLocalAddress().getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return inetaddr;
	}
	public ServiceThread(Socket socket, Account user) {
		this.socket = socket;
		this.user= user;
		try {
			datain = new DataInputStream(this.socket.getInputStream());
			dataout = new DataOutputStream(this.socket.getOutputStream());
			Account userCHAT = SQLCommand.getAccountData(user.getUsername());
			String userData = "Userdata\n"+userCHAT.getEmail()+"\n"+userCHAT.getAvaString()+"\n"+userCHAT.getDisplayname();
			dataout.writeUTF(userData);
		} catch (IOException e) {e.printStackTrace();}
		try {
			System.out.println("New connection with client# " + this.user.getUsername() + " at " + getInetAddress().getHostAddress());
		} catch (Exception e) {
			System.out.println("Disconect with client# " + this.user.getUsername() + " at " + getInetAddress().getHostAddress());
			ServerSocketConnect.removeThread(this);
		}
	} 
	@Override
    public void run() {
		Message newmess = new Message();
		try {
            while(true) {
            	String messageString = datain.readUTF();
//        		System.out.println(messageString+"\n------------");
            	if(getType(messageString).equalsIgnoreCase("message")) {
            		newmess.setType("message");
                	newmess.setMessage(getMessage(messageString));
                	newmess.setSender(getSender(messageString));
                	newmess.setReceiver(getReceiver(messageString));
                	newmess.setSendTime(getTime(getStringTime(messageString)));
                	SQL.SQLCommand.addMessage(newmess);
                	ServiceThread a;
                	if((a= ServerSocketConnect.getThreadWithName(newmess.getReceiver()))!=null) {
                    	a.sendMessage(newmess);
                	} else {
                    	GroupChat group = SQLCommand.getGroupWithGroupName(newmess.getReceiver());
                    	if(group!=null) {
                        	group.getListmember().forEach( e ->{
                        		ServiceThread b;
                        		if((b= ServerSocketConnect.getThreadWithName(e))!=null&&!newmess.getSender().equalsIgnoreCase(e)) {
                                	b.sendMessage(newmess);
                        		}
                        	});
                    	}
                	}
            	}
            	if(getType(messageString).equalsIgnoreCase("image")) {
            		String imgstring= "";
            		ArrayList<String> imgArraylist = new ArrayList<String>();
            		int ArraySize = Integer.valueOf(getMessage(messageString));
            		for(int i=0; i<ArraySize; i++) {
            			imgArraylist.add(datain.readUTF());
            			imgstring+=imgArraylist.get(i);
            		}
            		newmess.setType("image");
                	newmess.setMessage(String.valueOf(ArraySize));
                	newmess.setSender(getSender(messageString));
                	newmess.setReceiver(getReceiver(messageString));
                	newmess.setSendTime(getTime(getStringTime(messageString)));
            		
                	ServiceThread a;
            		if((a= ServerSocketConnect.getThreadWithName(newmess.getReceiver()))!=null) {
            			a.sendImage(newmess, imgArraylist);
                	} else {
                    	GroupChat group = SQLCommand.getGroupWithGroupName(newmess.getReceiver());
                    	if(group!=null) {
                        	group.getListmember().forEach( e ->{
                        		ServiceThread b;
                        		if((b= ServerSocketConnect.getThreadWithName(e))!=null&&!newmess.getSender().equalsIgnoreCase(e)) {
                                	b.sendImage(newmess, imgArraylist);
                        		}
                        	});
                    	}
                	}
                	newmess.setMessage(imgstring);
                	SQL.SQLCommand.addMessage(newmess);
            	}
            	if(getType(messageString).equalsIgnoreCase("file")) {
            		byte[] file;
            		ArrayList<byte[]> arrayofbytes = new ArrayList<byte[]>();
            		int ArraySize = Integer.valueOf(getMessage(messageString));
            		int length =0;
            		int LengOfLastByte = Integer.valueOf(getSender(messageString));
            		
            		for(int i=0; i<ArraySize-1; i++) {
            			file = new byte[50000];
            			datain.read(file);
            			arrayofbytes.add(file);
            			length += arrayofbytes.get(i).length;
            			System.out.println(file.length);
            		}
            		file = new byte[LengOfLastByte];
            		datain.read(file);
        			arrayofbytes.add(file);
        			length += file.length;
            		
            		
            		newmess.setType("file");
                	newmess.setMessage(String.valueOf(ArraySize));
                	newmess.setSender(this.getUser().getUsername());
                	newmess.setReceiver(getReceiver(messageString));
                	newmess.setSendTime(getTime(getStringTime(messageString)));
                	
            		System.out.println("Total length: "+length);
                	ServiceThread a;
            		if((a= ServerSocketConnect.getThreadWithName(newmess.getReceiver()))!=null) {
            			a.sendFile(newmess, arrayofbytes, getFileName(messageString), file.length);
                	} else {
                    	GroupChat group = SQLCommand.getGroupWithGroupName(newmess.getReceiver());
                    	if(group!=null) {
                    		int lengthfile= file.length;
                        	group.getListmember().forEach( e ->{
                        		ServiceThread b;
                        		if((b= ServerSocketConnect.getThreadWithName(e))!=null&&!newmess.getSender().equalsIgnoreCase(e)) {
                                	b.sendFile(newmess, arrayofbytes, getFileName(messageString), lengthfile);
                        		}
                        	});
                    	}
                	}
            	}
            	if(getType(messageString).equalsIgnoreCase("requestcall")) {
                	ServiceThread a;
            		if((a= ServerSocketConnect.getThreadWithName(getReceiver(messageString)))!=null) {
//            			String dataoutstring = "acceptcall\n"+getMessage(messageString)+a.getSocket().getLocalAddress().getHostAddress()+"\n"+a.getSocket().getPort()+"\n";
            			System.out.println(a.getUser().getUsername()+" Online");
            			String sendrequestToUser = "requestcall\n"+getMessage(messageString)+"\n"+this.user.getUsername()+"\n"+this.getInetAddress().getHostAddress()+"\n"+this.socket.getPort()+"\n";
            			a.dataout.writeUTF(sendrequestToUser);
            			//dataout.writeUTF(dataoutstring);
            		}
            	}
            	if(getType(messageString).equalsIgnoreCase("ResponseRequestcall")) {
            		ServiceThread a;
            		if((a= ServerSocketConnect.getThreadWithName(getReceiver(messageString)))!=null) {
            				String dataoutstring = "ResponseRequestcall\n"+getMessage(messageString)+"\n"+getSender(messageString)+"\n"+this.getInetAddress().getHostAddress()+"\n"+this.getSocket().getPort()+"\n";
            				a.dataout.writeUTF(dataoutstring);
            		}
            	}
            	if(getType(messageString).equalsIgnoreCase("UpdateProfile")) {
            		if(getString1(messageString).equalsIgnoreCase("password")) {
            			String newData = getString2(messageString);
            			SQLCommand.UpdateProfilePassword(this.user.getUsername(), newData);
            		}
            		if(getString1(messageString).equalsIgnoreCase("displayname")) {
            			String newData = getString2(messageString);
            			SQLCommand.UpdateProfileDisplayName(this.user.getUsername(), newData);
            		}
            		if(getString1(messageString).equalsIgnoreCase("avatar")) {
            			String newData = getString2(messageString);
            			SQLCommand.UpdateProfileAvatar(this.user.getUsername(), newData);
            		}
            	}
            	if(getType(messageString).equalsIgnoreCase("CreateGroup")) {
            		GroupChat temp = new GroupChat();
            		temp.setName(getString1(messageString));
            		temp.listmember.add(user.getUsername());
            		SQLCommand.addGroupChat(temp);
            	}
            	if(getType(messageString).equalsIgnoreCase("DeleteGroup")) {
            		SQLCommand.deleteGroupChat(getString1(messageString));
            	}
            	if(getType(messageString).equalsIgnoreCase("UpdateGroup")) {
            		if(getString1(messageString).equalsIgnoreCase("MemberAdd")) {
            			System.out.println("UPDATED GROUP");
            			SQL.SQLCommand.addMemberGroup(getString2(messageString), getString3(messageString));
            		}
            		if(getString1(messageString).equalsIgnoreCase("MemberKick")) {
            			System.out.println("UPDATED GROUP");
            			SQL.SQLCommand.kickMemberGroup(getString2(messageString), getString3(messageString));
            		}
            		
            	}
            }
		} catch (SocketException e) {
			System.out.println("Dissconect "+  this.user.getUsername() + " at " + socket);
			ServerSocketConnect.listClient.remove(this);
			System.out.println("Total Connected: " +ServerSocketConnect.listClient.size());
		} catch (EOFException e) {
			System.out.println("Dissconect "+  this.user.getUsername() + " at " + socket);
			ServerSocketConnect.listClient.remove(this);
			System.out.println("Total Connected: " +ServerSocketConnect.listClient.size());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public void sendMessage(Message newmess) {
		try {
			dataout.writeUTF("message\n"+newmess.getMessage()+"\n"+newmess.getSender()+"\n"+newmess.getReceiver()+"\n"+newmess.getStringTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendImage(Message newmess, ArrayList<String> imgString) {
		try {
			dataout.writeUTF("image\n"+newmess.getMessage()+"\n"+newmess.getSender()+"\n"+newmess.getReceiver()+"\n"+newmess.getStringTime());
			for(int i=0;i<imgString.size();i++) {
				dataout.writeUTF(imgString.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendFile(Message newmess, ArrayList<byte[]> filebytes, String filename, int lastByteArrayLeng) {
		try {
			dataout.writeUTF("file\n"+newmess.getMessage()+"\n"+newmess.getSender()+"\n"+lastByteArrayLeng+"\n"+newmess.getStringTime()+"\n"+filename+"\n"+newmess.getReceiver());
			for(int i=0;i<filebytes.size();i++) {
				dataout.write(filebytes.get(i));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Date getTime(String time) throws ParseException {
		Date a = simpleDateFormat.parse(time);
		return a;
	}
	public static String getFileName(String string) {
		String[]result= string.split("\n");
		return result[5];
	}	
	public static String getStringTime(String string) {
		String[]result= string.split("\n");
		return result[4];
	}	
	public static String getReceiver(String string) {
		String[]result= string.split("\n");
		return result[3];
	}	
	public static String getSender(String string) {
		String[]result= string.split("\n");
		return result[2];
	}	
	public static String getMessage(String string) {
		String[]result= string.split("\n");
		return result[1];
	}	
	public static String getType(String string) {
		String[]result= string.split("\n");
		return result[0];
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Account getUser() {
		return user;
	}
	public void setUser(Account user) {
		this.user = user;
	}
	public DataOutputStream getDataout() {
		return dataout;
	}
	public void setDataout(DataOutputStream dataout) {
		this.dataout = dataout;
	}
	public DataInputStream getDatain() {
		return datain;
	}
	public void setDatain(DataInputStream datain) {
		this.datain = datain;
	}
	public static String getString5(String string) {
		String[]result= string.split("\n");
		return result[5];
	}	
	public static String getString4(String string) {
		String[]result= string.split("\n");
		return result[4];
	}	
	public static String getString3(String string) {
		String[]result= string.split("\n");
		return result[3];
	}	
	public static String getString2(String string) {
		String[]result= string.split("\n");
		return result[2];
	}	
	public static String getString1(String string) {
		String[]result= string.split("\n");
		return result[1];
	}	
	public static String getString0(String string) {
		String[]result= string.split("\n");
		return result[0];
	}

}
