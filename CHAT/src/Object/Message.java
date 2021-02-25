package Object;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

public class Message {
	private String message;
	private Date sendTime;
	private String sender;
	private String receiver;
	private String type;
	private File file;
	private ImageIcon img;
	private ArrayList<byte[]> filearraybytes;
	public ImageIcon getImg() {
		return img;
	}
	public ArrayList<byte[]> getFilearraybytes() {
		return filearraybytes;
	}
	public void setFilearraybytes(ArrayList<byte[]> filearraybytes) {
		this.filearraybytes = filearraybytes;
	}
	public void setImg(ImageIcon img) {
		this.img = img;
	}
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
	public Message(String type,String message, String sender, String receiver, Date sendTime) {
		this.message=message;
		this.sender=sender;
		this.receiver=receiver;
		this.sendTime=sendTime;
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Message() {
		
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Timestamp getSQLsendTime() {
		Timestamp ts=new Timestamp(this.getSendTime().getTime());
		return ts;
	}
	public String getStringTime() {
		String a;
		a = simpleDateFormat.format(this.getSendTime());
		return a;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String toString() {
		return ("------------------\nMessage: "+this.message+"\nSender: "+this.sender+"\nReceiver: "+this.receiver+"\nSendTime: "+this.sendTime+"\n--------------");
	}
}
