package Object;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private String message;
	private Date sendTime;
	private String sender;
	private String receiver;
	private String type;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
	public Message(String type,String message, String sender, String receiver, Date sendTime) {
		
	}
	public Message() {
		
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getStringTime() {
		String a;
		a = simpleDateFormat.format(this.getSendTime());
		return a;
	}
	public Timestamp getSQLsendTime() {
		Timestamp ts=new Timestamp(this.getSendTime().getTime());
		return ts;
	}
	

}
