package Frame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import Tools.ImageEncode64;
import Tools.Tools;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.JobAttributes.DialogType;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.cert.PKIXRevocationChecker.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

import org.jdesktop.xswingx.PromptSupport;

import Object.Account;
import Object.GroupChat;
import Object.Message;
import Object.trungbmtLabel;

import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class ChatFrameMain extends JFrame implements Runnable, KeyListener {

	private JPanel contentPane;
	private JPanel chatPanel = new JPanel();
	private final JPanel titlePanel = new JPanel();
	private JTextField ChatField;
	private String currentChatUser=null;
	public DataOutputStream outChat;
	private DataInputStream inChat;
	private String username;
	private GridBagConstraints gbc;
	private ArrayList<trungbmtLabel> messagelabel = new ArrayList<trungbmtLabel>();
	private ArrayList<Message> listMessage = new ArrayList<Message>();
	public ArrayList<Account> listuserArray = new ArrayList<Account>();
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
	private SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<GroupChat> ListGroup = new ArrayList<GroupChat>();
	public Socket chat;
	private JScrollPane chatScroll;
	private ChatVideoFrame meChatvideo;
	public static Color myChatcolor = new Color(201, 236, 208);
	public static Color himChatColor = new Color(255, 102, 204);
	private boolean threadstop = false;
	private CallWaitingAccept CallWaitingAccept;
	private Account thisUser = new Account();
	private JPanel listuser;
	private boolean isGroupChat = false;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public ChatFrameMain(Account user) {

		thisUser.setUsername(user.getUsername());
		thisUser.setPassword(user.getPassword());
		chat = LoginFrame.clientlogin;
		try {
			outChat= new DataOutputStream(chat.getOutputStream());
			inChat = new DataInputStream(chat.getInputStream());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		
		this.username=user.getUsername();

		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1103, 647);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 196, 222));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		chatPanel.setBackground(Color.WHITE);
		chatScroll = new JScrollPane(chatPanel);
		chatPanel.setLayout(new GridBagLayout());
		chatPanel.setBounds(251, 67, 582, 488);
		chatScroll.setBounds(340, 67, 747, 482);
		chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(chatScroll);
		
		
		listuser = new JPanel();
		listuser.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(128, 128, 128), new Color(112, 128, 144), new Color(112, 128, 144), new Color(112, 128, 144)));
		listuser.setBackground(new Color(0, 141, 176));
		listuser.setLayout(new GridLayout(7, 1, 0, 0));
		listuser.setAlignmentX(Component.LEFT_ALIGNMENT);
		listuser.setSize(320, 482);
		
		JScrollPane scrollPane = new JScrollPane(listuser);
		scrollPane.setBounds(10, 67, 320, 482);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(scrollPane); 
		titlePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), null, null, null));
		
		
		titlePanel.setBackground(new Color(100, 149, 237));
		titlePanel.setBounds(10, 0, 320, 68);
		contentPane.add(titlePanel);
		
		JPanel titleCurrentChatter = new JPanel();
		titleCurrentChatter.setBackground(new Color(100, 149, 237));
		titleCurrentChatter.setBounds(340, 0, 505, 68);
		contentPane.add(titleCurrentChatter);
		titleCurrentChatter.setLayout(null);
		
		JLabel callvideoLabel = new JLabel("Video Call");
		callvideoLabel.setBackground(new Color(135, 206, 250));
		callvideoLabel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(128, 128, 128), new Color(128, 128, 128), null, null));
		callvideoLabel.setBounds(76, 3, 191, 60);
		titleCurrentChatter.add(callvideoLabel);
		callvideoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendRequestCall("video");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				callvideoLabel.setBackground(Color.DARK_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				callvideoLabel.setBackground(Color.LIGHT_GRAY);
			}
		});
		callvideoLabel.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		callvideoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		callvideoLabel.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/camera.png")), 60));
		callvideoLabel.setOpaque(true);
		
		JLabel himchatColorChange = new JLabel("<html>Their Chat Color</html>");
		himchatColorChange.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), null, null));
		himchatColorChange.setBounds(404, 3, 91, 57);
		titleCurrentChatter.add(himchatColorChange);
		himchatColorChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newcolor = JColorChooser.showDialog(null, "Choose a color", himChatColor);
				if(newcolor!=himChatColor) {
					himChatColor= newcolor;
					if(isGroupChat) printAllMessageOfGroup(currentChatUser);
					else printAllMessageOfUser(currentChatUser);
				}
			}
		});
		himchatColorChange.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/selectbackground.png")),30));
		himchatColorChange.setOpaque(true);
		himchatColorChange.setHorizontalAlignment(SwingConstants.CENTER);
		himchatColorChange.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		himchatColorChange.setBackground(new Color(135, 206, 250));
		
		JLabel myChatColorChange = new JLabel("<html>My Chat Color</html>");
		myChatColorChange.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), null, null));
		myChatColorChange.setBounds(308, 3, 86, 57);
		titleCurrentChatter.add(myChatColorChange);
		myChatColorChange.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/selectbackground.png")),30));
		myChatColorChange.setOpaque(true);
		myChatColorChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newcolor = JColorChooser.showDialog(null, "Choose a color", myChatcolor);
				if(newcolor!=myChatcolor) {
					myChatcolor= newcolor;
					if(isGroupChat) printAllMessageOfGroup(currentChatUser);
					else printAllMessageOfUser(currentChatUser);
					
				}
			}
		});
		myChatColorChange.setHorizontalAlignment(SwingConstants.CENTER);
		myChatColorChange.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		myChatColorChange.setBackground(new Color(135, 206, 250));
		
		
		JPanel PanelUserInfo = new JPanel();
		PanelUserInfo.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(70, 130, 180), new Color(70, 130, 180), null, null));
		PanelUserInfo.setBackground(new Color(0,141,176));
		PanelUserInfo.setBounds(843, 0, 244, 68);
		contentPane.add(PanelUserInfo);
		PanelUserInfo.setLayout(null);
		
		JLabel btnNewButton = new JLabel();
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				threadstop= true;
				if(meChatvideo!=null) meChatvideo.stopChatVideo();
				try {
					chat.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				ChatFrameMain.this.dispose();
				new LoginFrame().setVisible(true);
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(184, 9, 50, 50);
		btnNewButton.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/logout.png")), 50));
		PanelUserInfo.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Profile(thisUser, true, ChatFrameMain.this.outChat).setVisible(true);;
			}
		});
		panel_1.setBounds(10, 11, 171, 46);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(128, 128, 128), new Color(128, 128, 128), null, null));
		panel_1.setBackground(SystemColor.activeCaption);
		PanelUserInfo.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel usernameinfo = new JLabel("{User_Name}", SwingConstants.CENTER);
		panel_1.add(usernameinfo);
		usernameinfo.setForeground(Color.white);
		usernameinfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Profile(thisUser, true, ChatFrameMain.this.outChat).setVisible(true);;
			}
		});
		usernameinfo.setText(user.getDisplayname());
		usernameinfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel = new JLabel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(this.getParent().getBackground());
				g2.setStroke(new BasicStroke(100));
				g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
				g2.setStroke(new BasicStroke(1));
				g2.setColor(Color.white);
				g2.drawOval(0, 0, 32, 32);
				
			}
		};
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e2) {
				new Profile(thisUser, true, ChatFrameMain.this.outChat).setVisible(true);;
			}
		});
		lblNewLabel.setIcon(Tools.Resize2(user.getAvatar(), 32,32));
		panel_1.add(lblNewLabel);
		
		ChatField = new JTextField();
		ChatField.setBackground(SystemColor.inactiveCaptionBorder);
		ChatField.setBounds(466, 560, 529, 37);
		contentPane.add(ChatField);
		ChatField.setColumns(10);
		ChatField.setFont(new Font("Italic", Font.PLAIN, 20));
		ChatField.addKeyListener(this);
		ChatField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		PromptSupport.setPrompt("Type something to send...", ChatField);
		
		JLabel lbsendmess = new JLabel("");
		lbsendmess.setBounds(1005, 560, 30, 33);
		lbsendmess.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lbsendmess);
		lbsendmess.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendCurrentMess();
			}
		});
		lbsendmess.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/sendmessage.png")), 25));
		
		JLabel emojilabel = new JLabel();
		EmojiFrame emojiFrame = new EmojiFrame(ChatFrameMain.this);
		emojilabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(emojiFrame.isVisible()) emojiFrame.setVisible(false);
				else emojiFrame.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				emojilabel.setBounds(emojilabel.getX()-9, emojilabel.getY()-9, 50, 50);
				emojilabel.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/emoji.png")), 50));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				emojilabel.setBounds(emojilabel.getX()+9, emojilabel.getY()+9, 32, 32);
				emojilabel.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/emoji.png")), 32));
			}
		});
		emojilabel.setBounds(1045, 560, 32, 32);
		emojilabel.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/emoji.png")), 32));
		contentPane.add(emojilabel);
		
		JLabel imgadd = new JLabel("");
		imgadd.setBounds(382, 560, 32, 32);
		imgadd.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/addimage.png")), 32));
		imgadd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File img = ImageEncode64.ImageSelect();
				if(img!=null) {
					sendImage(img);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				imgadd.setBounds(imgadd.getX()-9, imgadd.getY()-9, 50, 50);
				imgadd.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/addimage.png")), 50));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				imgadd.setBounds(imgadd.getX()+9, imgadd.getY()+9, 32, 32);
				imgadd.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/addimage.png")), 32));
			}
		});
		contentPane.add(imgadd);
		
		JLabel addfile = new JLabel("");
		addfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file = Tools.FileSelect();
				if(file!=null) {
					sendFile(file);
				}
			}
		});
		addfile.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/addfile.png")), 32));
		addfile.setBounds(340, 560, 32, 32);
		contentPane.add(addfile);
		
		JLabel stickerlb = new JLabel();
		StickerFrame stickerframe = new StickerFrame(ChatFrameMain.this);
		stickerlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(stickerframe.isVisible()) stickerframe.setVisible(false);
				else stickerframe.setVisible(true);
			}
		});
		stickerlb.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/emoji.png")), 32));
		stickerlb.setBounds(424, 560, 32, 32);
		contentPane.add(stickerlb);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), null, null));
		panel.setBackground(new Color(95, 158, 160));
		panel.setBounds(10, 548, 320, 60);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel newGroupChat = new JLabel("New Group Chat");
		newGroupChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String result = JOptionPane.showInputDialog("Nhập tên nhóm");
				if(result!=null&&!result.isEmpty()) {
					if(result.length()<35) {
						try {
							GroupChat a = new GroupChat();
							a.setName(result);
							a.listmember.add(username);
							ListGroup.add(a);
							String outString = "CreateGroup\n"+result;
							outChat.writeUTF(outString);
							CleanListUserandGroup();
							PrintAllGroup(ListGroup);
							newPrintAllUser(listuserArray);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else JOptionPane.showMessageDialog(ChatFrameMain.this, "Tên nhóm phải ngắn hơn 35 kí tự!");
				}
			}
		});
		newGroupChat.setFont(new Font("Tahoma", Font.PLAIN, 17));
		newGroupChat.setOpaque(true);
		newGroupChat.setHorizontalAlignment(SwingConstants.CENTER);
		newGroupChat.setIcon(Tools.Resize(new ImageIcon(ChatFrameMain.class.getResource("/img/newgroup.png")), 32));
		newGroupChat.setBounds(10, 11, 300, 38);
		panel.add(newGroupChat);
	}
	public void sendFile(File file) {
		if(currentChatUser!=null) {
			try {
				String sendTime = simpleDateFormat.format(Calendar.getInstance().getTime());
				FileInputStream readfile = new FileInputStream(file);
				byte[] filebytes = new byte[(int) file.length()];
				readfile.read(filebytes);
				ArrayList<byte[]> FileByteArray = new ArrayList<byte[]>();
				ByteBuffer bb = ByteBuffer.wrap(filebytes);
				byte[] temp;
				while(bb.remaining()>50000) {
					temp = new byte[50000];
					bb.get(temp, 0, temp.length);
					FileByteArray.add(temp);
				}
				byte[] remaining = new byte[bb.remaining()];
				bb.get(remaining, 0, remaining.length);
				FileByteArray.add(remaining);
				String output = "file\n"+FileByteArray.size()+"\n"+remaining.length+"\n"+currentChatUser+"\n"+sendTime+"\n"+file.getName();
				outChat.writeUTF(output);

				for(int i=0;i <FileByteArray.size();i++) {
					outChat.write(FileByteArray.get(i));
				}
				Message tempmess = new Message("file", "", username, currentChatUser, Calendar.getInstance().getTime());
				tempmess.setFile(file);
				listMessage.add(tempmess);
				PrintOneFile(username, file, file.getName());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void sendImage(File img) {
		if(currentChatUser!=null) {
			try{
				String sendTime = simpleDateFormat.format(Calendar.getInstance().getTime());
				String imageEncode = ImageEncode64.encoder(img);
				ArrayList<String> imageEncodeArray = new ArrayList<String>();
				while(imageEncode.length()>30000) {
					imageEncodeArray.add(imageEncode.substring(0, 30000));
					imageEncode= imageEncode.substring(30000, imageEncode.length());
				}
				imageEncodeArray.add(imageEncode.substring(0, imageEncode.length()));
				String output = "image\n"+imageEncodeArray.size()+"\n"+username+"\n"+currentChatUser+"\n"+sendTime;
				outChat.writeUTF(output);
				for(int i=0;i <imageEncodeArray.size();i++) {
					outChat.writeUTF(imageEncodeArray.get(i));
				}
				Message temp = new Message("image", "", username, currentChatUser, Calendar.getInstance().getTime());
				temp.setImg(new ImageIcon(img.getAbsolutePath()));
				listMessage.add(temp);
				PrintOneImage(new ImageIcon(img.getAbsolutePath()), username);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void sendCurrentMess() {
		if(currentChatUser!=null) {
			try {
				Message newmess= new Message("message",ChatField.getText(), username, currentChatUser, Calendar.getInstance().getTime());			
				String sendTime = simpleDateFormat.format(newmess.getSendTime());
				outChat.writeUTF("message\n"+newmess.getMessage()+"\n"+newmess.getSender()+"\n"+newmess.getReceiver()+"\n"+sendTime);
				ChatField.setText("");
				listMessage.add(newmess);
				if(newmess.getReceiver().equalsIgnoreCase(currentChatUser)) PrintOneMess(newmess);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public void sendRequestCall(String typeCall) {
		if(currentChatUser!=null) {
			try {
				outChat.writeUTF("requestcall"+"\n"+typeCall+"\n"+username+"\n"+currentChatUser+"\n");
				CallWaitingAccept = new CallWaitingAccept("SendRequest",typeCall, currentChatUser, this.outChat);
				CallWaitingAccept.setVisible(true);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void OpenCallVideo(InetAddress _friendip,  int _friendport) {
		ChatVideoFrame a = new ChatVideoFrame(this.chat.getLocalPort(), _friendip, _friendport);
		a.setVisible(true);
	}
	public void listeningNewMess() {
		while(!threadstop) {
			String newmessString;
			try {
				newmessString = inChat.readUTF();
				System.out.println(newmessString+"\n------------");
				if(getType(newmessString).equalsIgnoreCase("message")) {
					Message newmess= new Message(getType(newmessString),getMessage(newmessString), getSender(newmessString), getReceiver(newmessString), getDateTime(newmessString));
					listMessage.add(newmess);
					if(currentChatUser.equalsIgnoreCase(newmess.getSender())||newmess.getReceiver().equalsIgnoreCase(currentChatUser)) {
						PrintOneMess(newmess);
					}
				}
				if(getType(newmessString).equalsIgnoreCase("image")) {
					String imgString="";
					Message newmess= new Message(getType(newmessString),getMessage(newmessString), getSender(newmessString), getReceiver(newmessString), getDateTime(newmessString));
					int ArraySize = Integer.valueOf(getMessage(newmessString));
					for(int i=0; i<ArraySize;i ++) {
						imgString+=inChat.readUTF();
					}
					if(currentChatUser.equalsIgnoreCase(newmess.getSender())||newmess.getReceiver().equalsIgnoreCase(currentChatUser)) {
						ImageIcon img = ImageEncode64.decoder(imgString);
						PrintOneImage(img, newmess.getSender());
					}
				}
				if(getType(newmessString).equalsIgnoreCase("file")) {
					Message newmess= new Message(getType(newmessString),getMessage(newmessString), getSender(newmessString), getString6(newmessString), getDateTime(newmessString));
					int ArraySize = Integer.valueOf(getMessage(newmessString));
					byte[] filebyte;
					ArrayList<byte[]> byteArray = new ArrayList<byte[]>();
					int leng= 0;
					
					for(int i=0; i<ArraySize-1;i ++) {
						filebyte = new byte[50000];
						inChat.read(filebyte);
						byteArray.add(filebyte);
						leng+=filebyte.length;
					}
					filebyte= new byte[Integer.valueOf(getString3(newmessString))];
					inChat.read(filebyte);
					byteArray.add(filebyte);
					leng+=filebyte.length;
					
					newmess.setFilearraybytes(byteArray);
					File file;
					file = Tools.ByteArrayToFile(Tools.ArrayListByteArrayToByteArray(newmess.getFilearraybytes()), getString5(newmessString));
					if(currentChatUser.equalsIgnoreCase(newmess.getSender())||newmess.getReceiver().equalsIgnoreCase(currentChatUser)) {
						PrintOneFile(newmess.getSender(), file, getString5(newmessString));
					}
					newmess.setFile(file);
					listMessage.add(newmess);
				}
				if(getType(newmessString).equalsIgnoreCase("requestcall")) {
					String typecall = getMessage(newmessString);
					String userrequest = getSender(newmessString);
					CallWaitingAccept = new CallWaitingAccept("ReceiveRequest",typecall, userrequest, this.outChat);
					CallWaitingAccept.setmyport(chat.getLocalPort());
					CallWaitingAccept.setFriendIp(InetAddress.getByName(getString3(newmessString)));
					CallWaitingAccept.setFriendport(Integer.parseInt(getString4(newmessString)));
					CallWaitingAccept.setVisible(true);
				}
				if(getType(newmessString).equalsIgnoreCase("acceptcall")) {
					InetAddress friendip = InetAddress.getByName(getMessage(newmessString));
					int frindport = Integer.parseInt(getSender(newmessString));

					System.out.println("Receiver Accet: "+friendip+"/"+frindport);
					OpenCallVideo(friendip, frindport);
				}
				if(getString0(newmessString).equalsIgnoreCase("ResponseRequestcall")) {

					if(getString2(newmessString).equalsIgnoreCase("Accept")) {
						CallWaitingAccept.StopWaiting();
						CallWaitingAccept.dispose();
						if(getString1(newmessString).equalsIgnoreCase("video")) {
							InetAddress _friendip = InetAddress.getByName(getReceiver(newmessString));
							int _friendport = Integer.parseInt(getStringTime(newmessString));
							System.out.println("Chat Video: "+_friendip+"/"+_friendport);
							OpenCallVideo(_friendip, _friendport);
						}
					}
					if(getString2(newmessString).equalsIgnoreCase("Deny")) {
						System.out.println("Deny MESS");
						CallWaitingAccept.DenyCall();
					}
				}
				if(getString0(newmessString).equalsIgnoreCase("Userdata")) {
					thisUser.setEmail(getString1(newmessString));
					try {

						thisUser.setAvatar(ImageEncode64.decoder(getString2(newmessString)));
					}catch (Exception e) {
						thisUser.setAvatar(null);
					}
					try {
						thisUser.setDisplayname(getString3(newmessString));
					}catch (Exception e) {
						thisUser.setDisplayname(thisUser.getUsername());
					}
				}
			}catch(SocketException e2) {
				threadstop = true;
				System.out.println("Disconnected!");
				
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
 	private void PrintOneMess(Message newmess) {
		trungbmtLabel tempmess = new trungbmtLabel(newmess.getMessage());
		tempmess.setToolTipText("<html> <strong>"+format1.format(Calendar.getInstance().getTime())+"<br>"+format2.format(Calendar.getInstance().getTime())+"</html>");
		if(newmess.getSender().equalsIgnoreCase(username)) {
			tempmess.setHorizontalAlignment(JLabel.RIGHT);
		} else tempmess.setHorizontalAlignment(JLabel.LEFT);
		tempmess.rePaintMess();

		if(isGroupChat) {
			Account chater = getAccountFromUsername(newmess.getSender());
			JPanel message = new JPanel();
			message.setBackground(Color.white);
			JLabel userimage = new JLabel() {

				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(this.getParent().getBackground());
					g2.setStroke(new BasicStroke(100));
					g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.black);
					g2.drawOval(0, 0, 40, 40);
					
				}
			};
			
			userimage.setIcon(Tools.Resize2(chater.getAvatar(), 40, 40));
			if(newmess.getSender().equalsIgnoreCase(username)) {
				message.add(tempmess);
				message.add(userimage);
				message.setLayout(new FlowLayout(FlowLayout.RIGHT));
			} else {
				message.add(userimage);
				message.add(tempmess);
				message.setLayout(new FlowLayout(FlowLayout.LEFT));
			}
			chatPanel.add(message,gbc);
		} else chatPanel.add(tempmess,gbc);
		
		gbc.gridy++;
		chatPanel.revalidate();
		this.ScrollDown();
	}
	private void PrintOneImage(ImageIcon img, String sender) {
		JLabel newImage = new JLabel();
		newImage.setToolTipText("<html> <strong>"+format1.format(Calendar.getInstance().getTime())+"<br>"+format2.format(Calendar.getInstance().getTime())+"</html>");
		if(img.getIconHeight()==img.getIconWidth()&& img.getIconHeight()==64) {
			newImage.setIcon(Tools.Resize(img, 64));
		} else newImage.setIcon(Tools.Resize(img, 128));
		
		newImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowImage showimg = new ShowImage(img);
				showimg.setVisible(true);
			}
		});
		if(sender.equalsIgnoreCase(username)) newImage.setHorizontalAlignment(JLabel.RIGHT);
		else newImage.setHorizontalAlignment(JLabel.LEFT);
		if(isGroupChat) {
			Account chater = getAccountFromUsername(sender);
			JPanel message = new JPanel();
			message.setBackground(Color.white);
			JLabel userimage = new JLabel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(this.getParent().getBackground());
					g2.setStroke(new BasicStroke(100));
					g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.black);
					g2.drawOval(0, 0, 40, 40);
					
				}
			};
			
			userimage.setIcon(Tools.Resize2(chater.getAvatar(), 40, 40));
			if(sender.equalsIgnoreCase(username)) {
				message.add(newImage);
				message.add(userimage);
				message.setLayout(new FlowLayout(FlowLayout.RIGHT));
			} else {
				message.add(userimage);
				message.add(newImage);
				message.setLayout(new FlowLayout(FlowLayout.LEFT));
			}
			chatPanel.add(message,gbc);
		} else chatPanel.add(newImage,gbc);
		gbc.gridy+=2;
		chatPanel.revalidate();
		this.ScrollDown();
	}
	private void PrintOneFile(String sender, File file, String name) {
		JLabel newImage = new JLabel("<html><font color=#808080 size=1.5>"+sender+" has just sent a file<br><font color=#000000 size=4><strong>"+name);
		newImage.setToolTipText("<html><strong>"+name+"<br>"+format1.format(Calendar.getInstance().getTime())+"<br>"+format2.format(Calendar.getInstance().getTime())+"</html>");
		Icon icon = FileSystemView.getFileSystemView().getSystemIcon( file );
		newImage.setIcon(Tools.Resize((ImageIcon) icon, 32));
		newImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String[] options = new String[2];
					options[0] = new String("Save File");
					options[1] = new String("Open File");
					int result = JOptionPane.showOptionDialog(null, name, "File Option", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
					if(result == 0 ) {
						boolean resultsave = Tools.SaveFile(file, name);
						if(resultsave) JOptionPane.showMessageDialog(null, "Done!");
						else JOptionPane.showMessageDialog(null, "Failed!");
					}
					else if (result == 1) Desktop.getDesktop().open(file);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		if(sender.equalsIgnoreCase(username)) newImage.setHorizontalAlignment(JLabel.RIGHT);
		else newImage.setHorizontalAlignment(JLabel.LEFT);
		
		if(isGroupChat) {
			Account chater = getAccountFromUsername(sender);
			JPanel message = new JPanel();
			message.setBackground(Color.white);
			JLabel userimage = new JLabel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(this.getParent().getBackground());
					g2.setStroke(new BasicStroke(100));
					g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.black);
					g2.drawOval(0, 0, 40, 40);
					
				}
			};
			
			userimage.setIcon(Tools.Resize2(chater.getAvatar(), 40, 40));
			if(sender.equalsIgnoreCase(username)) {
				message.add(newImage);
				message.add(userimage);
				message.setLayout(new FlowLayout(FlowLayout.RIGHT));
			} else {
				message.add(userimage);
				message.add(newImage);
				message.setLayout(new FlowLayout(FlowLayout.LEFT));
			}
			chatPanel.add(message,gbc);
		} else chatPanel.add(newImage,gbc);
		
		
		gbc.gridy+=2;
		chatPanel.revalidate();
		this.ScrollDown();
	}
	public void getOldmessage(String message) {
		String[] messagelist= message.split("\n");
		if(messagelist.length>1) {    
			for(int i=1;i < messagelist.length; i ++) {
				String messagetemp[] = messagelist[i].split("--");
				try {
					String messagetext = messagetemp[0];
					String sendertext = messagetemp[1];
					String receivertext = messagetemp[2];
					Date sendTime = simpleDateFormat.parse(messagetemp[3]);
					String type = messagetemp[4];
					Message temp = new Message(type ,messagetext, sendertext, receivertext, sendTime);
					if(temp.getType().equalsIgnoreCase("image")) temp.setImg(ImageEncode64.decoder(temp.getMessage()));
					listMessage.add(temp);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void ScrollDown() {
		chatScroll.revalidate();
		this.revalidate();
		JScrollBar vertical = chatScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	private void CleanListUserandGroup() {
		listuser.removeAll();
	}
	public void newPrintAllUser(ArrayList<Account> _listuser) {
		_listuser.forEach(e -> {
			if(!e.getUsername().equalsIgnoreCase(thisUser.getUsername())) {
				JLabel userimage = new JLabel() {

					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						g2.setColor(this.getParent().getBackground());
						g2.setStroke(new BasicStroke(100));
						g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
						g2.setStroke(new BasicStroke(1));
						g2.setColor(Color.white);
						g2.drawOval(0, 0, 50, 50);
						
					}
				};
				userimage.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e2) {
						new Profile(e, false, null).setVisible(true);;
					}
				});
				JLabel tempuser = new JLabel(e.getDisplayname());
				userimage.setIcon(Tools.Resize2(e.getAvatar(), 50,50));
				tempuser.setFont(new Font("Arial", Font.BOLD, 20));
				tempuser.addMouseListener(new MouseListener() {
					@Override
					public void mousePressed(MouseEvent e2) {
						currentChatUser= e.getUsername();
						isGroupChat= false;
						System.out.println("Current Chat: "+currentChatUser);
						printAllMessageOfUser(currentChatUser);
						setSelectedUser(listuser,tempuser);
					}
					@Override
					public void mouseClicked(MouseEvent e) {
					}
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
				JPanel tempPanel = new JPanel();
				userimage.setBounds(10, 10, 50, 50);
				tempuser.setBounds(70, 10, 200, 50);
				tempPanel.setLayout(null);
				tempPanel.setBackground(listuser.getBackground());
				tempPanel.add(userimage);
				tempPanel.add(tempuser);
				
				listuser.add(tempPanel);
			}
		});
	}
	public void PrintAllGroup(ArrayList<GroupChat> _listGroup) {
		_listGroup.forEach(e -> {
				JLabel userimage = new JLabel() {

					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						g2.setColor(this.getParent().getBackground());
						g2.setStroke(new BasicStroke(100));
						g2.drawOval(-50, -50, this.getWidth()+100, this.getHeight()+100);
						g2.setStroke(new BasicStroke(1));
						g2.setColor(Color.white);
						g2.drawOval(0, 0, 50, 50);
						
					}
				};
				userimage.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e2) {
						new GroupProfile(e, outChat, listuserArray, ChatFrameMain.this).setVisible(true);
					}
				});
				JLabel tempuser = new JLabel(e.getName());
				userimage.setIcon(Tools.Resize2(e.getAvatar(), 50,50));
				tempuser.setFont(new Font("Arial", Font.BOLD, 20));
				tempuser.addMouseListener(new MouseListener() {
					@Override
					public void mousePressed(MouseEvent e2) {
						currentChatUser= e.getName();
						isGroupChat= true;
						System.out.println("Current Chat: "+currentChatUser);
						printAllMessageOfGroup(e.getName());
						setSelectedUser(listuser,tempuser);
					}
					@Override
					public void mouseClicked(MouseEvent e) {
					}
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
				JPanel tempPanel = new JPanel();
				userimage.setBounds(10, 10, 50, 50);
				tempuser.setBounds(70, 10, 200, 50);
				tempPanel.setLayout(null);
				tempPanel.setBackground(listuser.getBackground());
				tempPanel.add(userimage);
				tempPanel.add(tempuser);
				
				listuser.add(tempPanel);
		});
	}
	public void setSelectedUser(JPanel jpn, JLabel user) {
		for(Component lb: jpn.getComponents()) {
			((JPanel) lb).getComponent(1).setForeground(Color.black);
			((JPanel) lb).setBackground(listuser.getBackground());
			((JPanel) lb).setBorder(null);
		}
		user.setForeground(Color.white);
		((JPanel)user.getParent()).setBorder(BorderFactory.createRaisedBevelBorder());
		((JPanel)user.getParent()).setBackground(new Color(24, 164, 199));
	}
	public void printAllMessageOfUser(String chatuser) {
		chatPanel.removeAll();
		chatPanel.repaint();
		messagelabel.clear();
		gbc.gridy=0;
		listMessage.forEach(e-> {
			if((e.getSender().equalsIgnoreCase(username)&&e.getReceiver().equalsIgnoreCase(chatuser))||(e.getSender().equalsIgnoreCase(chatuser)&&e.getReceiver().equalsIgnoreCase(username))) {
				if(e.getType().equalsIgnoreCase("message")) {
					PrintOneMess(e);
				}
				if(e.getType().equalsIgnoreCase("image")) {
					PrintOneImage(e.getImg(), e.getSender());
				}
				if(e.getType().equalsIgnoreCase("file")) {
					PrintOneFile(e.getSender(), e.getFile(), e.getFile().getName());
				}
			}
//			messagelabel.add(tempmess);
		});
//		messagelabel.forEach(e -> {
//			chatPanel.add(e,gbc);
//			gbc.gridy++;
//		});
		chatPanel.revalidate();
		this.ScrollDown();
	}
	public void printAllMessageOfGroup(String groupchat) {
		chatPanel.removeAll();
		chatPanel.repaint();
		messagelabel.clear();
		gbc.gridy=0;
		listMessage.forEach(e-> {
			if((e.getReceiver().equalsIgnoreCase(groupchat))) {
				if(e.getType().equalsIgnoreCase("message")) {
					PrintOneMess(e);
				}
				if(e.getType().equalsIgnoreCase("image")) {
					PrintOneImage(e.getImg(), e.getSender());
				}
				if(e.getType().equalsIgnoreCase("file")) {
					PrintOneFile(e.getSender(), e.getFile(), e.getFile().getName());
				}
			}
		});
		chatPanel.revalidate();
		this.ScrollDown();
	}
	private Account getAccountFromUsername(String username) {
		for(Account temp: listuserArray) {
			if(temp.getUsername().equalsIgnoreCase(username))
				return temp;
		}
		return null;
	}
	public void addEmoji(String emoji) {
		ChatField.setText(ChatField.getText()+emoji);
	}
	public Date getDateTime(String string) throws ParseException {
		Date a = simpleDateFormat.parse(getStringTime(string));
		return a;
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
	public static String getString6(String string) {
		String[]result= string.split("\n");
		return result[6];
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
	@Override
	public void run() {
		try {
			this.listeningNewMess();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	boolean CtrlPress= false;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER&& e.getSource().equals(ChatField)) {
			sendCurrentMess();
		}
		if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) && e.getSource().equals(ChatField)) {
			Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();
			if(c.getContents(null)!=null&&c.getContents(null).isDataFlavorSupported(DataFlavor.imageFlavor)) {
				try {
					Image a = (Image) c.getContents(null).getTransferData(DataFlavor.imageFlavor);
					BufferedImage bimg = Tools.imageToBufferedImage(a);
					File fileScreenShot = new File ("lastScreenSend.png");
					ImageIO.write(bimg, "png", fileScreenShot);
					sendImage(fileScreenShot);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
