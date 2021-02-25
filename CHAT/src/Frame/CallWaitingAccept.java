package Frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Tools.MusicPlay;
import Tools.Tools;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class CallWaitingAccept extends JFrame {

	private JPanel contentPane;
	public Thread ringring;
	private int myport;
	private InetAddress friendIP;
	private int friendport;
	JLabel lbtitle;
	private MusicPlay send;
	public void setmyport(int port) {
		this.myport = port;
	}
	public void setFriendport(int port) {
		this.friendport = port;
	}
	public void setFriendIp(InetAddress friendIP) {
		this.friendIP = friendIP;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CallWaitingAccept frame = new CallWaitingAccept("ReceiveRequest","Video", "trungbmt", null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 */
	public CallWaitingAccept(String type, String typecall, String _frienduser, DataOutputStream dataout) {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbtitle = new JLabel("Calling "+typecall);
		lbtitle.setBounds(5, 5, 424, 43);
		lbtitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbtitle.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 32));
		contentPane.add(lbtitle);
		
		JLabel lbimage = new JLabel("");
		lbimage.setHorizontalAlignment(SwingConstants.CENTER);
		lbimage.setIcon(Tools.Resize(new ImageIcon(CallWaitingAccept.class.getResource("/img/wolfuser.png")), 100));
		lbimage.setBounds(131, 60, 175, 175);
		contentPane.add(lbimage);
		
		JLabel accept = new JLabel("");
		accept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					StopWaiting();
					dispose();
					dataout.writeUTF("ResponseRequestcall\nvideo\nAccept\n"+_frienduser);
					ChatVideoFrame a = new ChatVideoFrame(myport, friendIP, friendport);
					a.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		accept.setHorizontalAlignment(SwingConstants.CENTER);
		accept.setIcon(Tools.Resize(new ImageIcon(CallWaitingAccept.class.getResource("/img/acceptcall.png")), 50));
		accept.setBounds(108, 308, 60, 60);
		contentPane.add(accept);
		
		JLabel deny = new JLabel("");
		deny.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					StopWaiting();
					dispose();
					dataout.writeUTF("ResponseRequestcall\nvideo\nDeny\n"+_frienduser);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		deny.setHorizontalAlignment(SwingConstants.CENTER);
		deny.setIcon(Tools.Resize(new ImageIcon(CallWaitingAccept.class.getResource("/img/denycall.png")), 50));
		deny.setBounds(257, 308, 60, 60);
		contentPane.add(deny);
		
		JLabel calluser = new JLabel(_frienduser);
		calluser.setHorizontalAlignment(SwingConstants.CENTER);
		calluser.setFont(new Font("Trebuchet MS", Font.PLAIN, 26));
		calluser.setBounds(0, 247, 429, 37);
		contentPane.add(calluser);
		

		if(type.equalsIgnoreCase("SendRequest")) {
			accept.setVisible(false);
			deny.setLocation(185, 308);
			send = new MusicPlay("sound\\sendcall.wav");
			ringring = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(15000);
						lbtitle.setForeground(Color.red);
						lbtitle.setText("No Answer!");
						send.MusicStop();
						send = new MusicPlay("sound\\noanswer.wav");
						Thread.sleep(5000);
						send.MusicStop();
						dispose();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			ringring.start();
			
		}
		if(type.equalsIgnoreCase("ReceiveRequest")) {
			send = new MusicPlay("sound\\receivecall.wav");
			ringring = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(15000);
						send.MusicStop();
						dispose();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			ringring.start();
		}
		
	}
	public void StopWaiting() {
		send.MusicStop();
		if( (ringring!=null) && (ringring.isAlive()) ) {
			CallWaitingAccept.this.dispose(); 
			ringring.stop();
			
		}
	}
	public void DenyCall() {
		send.MusicStop();
		System.out.println("Deny function");
		Thread a = new Thread() {
			@Override
			public void run() {
				ringring.stop();
				lbtitle.setForeground(Color.red);
				lbtitle.setText("No Answer!");
				send = new MusicPlay("sound\\noanswer.wav");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				send.MusicStop();
				dispose();
			}
		};
		a.start();
	}
}
