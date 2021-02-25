package Frame;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Object.Account;
import Object.GroupChat;
import Tools.Tools;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements KeyListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	static Socket clientlogin;
	ImageIcon iconbackground = new ImageIcon("img//registerbackground.png");
	private JTextField username;
	private JPasswordField password;
	private JLabel userpass;
	private JLabel forgotpass;
	private JLabel showpass;
	private JButton exiticon;
	private ImageIcon showpassicon = Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/showpass.png")),20);
	private ImageIcon hidepassicon = Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/hidepass.png")),20);
	private JLabel registerlb;
	public LoginFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(889,964);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		
		JLabel userlabel = new JLabel();
		userlabel.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/wolfuser.png")), 50));
		userlabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		userlabel.setForeground(Color.WHITE);
		userlabel.setBounds(225, 711, 72, 60);
		contentPane.add(userlabel);
		
		username = new JTextField("Type your username here");
		username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("Type your username here")){
					username.setText("");
					username.setForeground(Color.black);
					username.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(username.getText().equals("")) {
					username.setText("Type your username here");
					username.setForeground(new Color(192,192,192));
					username.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
				}
			}
		});
		username.setForeground(new Color(192,192,192));
		username.addKeyListener(this);
		username.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		username.setBounds(294, 718, 348, 39);
		username.setHorizontalAlignment(JTextField.CENTER);
		username.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (username.getText().length() >= 20 ) 
		            e.consume(); 
		    }  
		});
		contentPane.add(username);
		
		JLabel loginlabel = new JLabel("MEMBER LOGIN");
		loginlabel.setForeground(SystemColor.inactiveCaption);
		loginlabel.setFont(new Font("Unispace", Font.BOLD, 35));
		loginlabel.setBounds(343, 633, 252, 74);
		contentPane.add(loginlabel);
		
		password = new JPasswordField("Type your password here");
		char defaultEcho = password.getEchoChar();
		password.setEchoChar((char)0);
		password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(String.valueOf(password.getPassword()).equals("Type your password here")){
					password.setText("");
					password.setForeground(Color.black);
					password.setFont(new Font("Tahoma", Font.PLAIN, 28));
					password.setEchoChar(defaultEcho);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(password.getPassword().length==0) {
					password.setText("Type your password here");
					password.setForeground(new Color(192,192,192));
					password.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
					password.setEchoChar((char)0);
				}
			}
		});
		password.setForeground(new Color(192,192,192));
		password.addKeyListener(this);
		password.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		password.setBounds(294, 782, 348, 39);
		password.setHorizontalAlignment(JTextField.CENTER);
		password.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (password.getPassword().length >= 20 ) 
		            e.consume(); 
		    }  
		});
		contentPane.add(password);
		
		userpass = new JLabel();
		userpass.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/wolfpassword.png")),50));
		userpass.setForeground(Color.WHITE);
		userpass.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		userpass.setBounds(225, 773, 72, 60);
		contentPane.add(userpass);
		
		forgotpass = new JLabel("Forgot password?");

		forgotpass.setCursor(new Cursor(Cursor.HAND_CURSOR));
		forgotpass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		forgotpass.setForeground(SystemColor.menu);
		forgotpass.setBounds(522, 819, 120, 39);
		contentPane.add(forgotpass);
		
		JButton loginbutton = new JButton("LOGIN");
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginClick();
			}
		});
		loginbutton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 28));
		loginbutton.setBounds(367, 867, 162, 39);
		loginbutton.setFocusable(false);
		loginbutton.setBackground(SystemColor.activeCaption);
		contentPane.add(loginbutton);
		
		showpass = new JLabel("");
		showpass.setIcon(showpassicon);
		showpass.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if((!String.valueOf(password.getPassword()).equals("Type your password here"))) {
					if(showpass.getIcon().equals(showpassicon)) {
						showpass.setIcon(hidepassicon);
						password.setFont(new Font("Arial", Font.PLAIN, 18));
						password.setEchoChar((char)0);
					} else {
						password.setEchoChar(defaultEcho);
						password.setFont(new Font("Tahoma", Font.PLAIN, 28));
						showpass.setIcon(showpassicon);
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				showpass.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				showpass.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		showpass.setBounds(652, 782, 55, 39);
		contentPane.add(showpass);
		
		exiticon = new JButton("");
		exiticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exiticon.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/exit.png")), 40));
		exiticon.setBounds(849, 0, 40, 40);
		exiticon.setContentAreaFilled(false);
		contentPane.add(exiticon);
		
		registerlb = new JLabel("Register");
		registerlb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		registerlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					RegisterFrame registerframe = new RegisterFrame();
					registerframe.setVisible(true);
					dispose();
				}
			}
		});
		registerlb.setForeground(SystemColor.menu);
		registerlb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		registerlb.setBounds(461, 819, 55, 39);
		contentPane.add(registerlb);
		
		JLabel background = new JLabel();
		background.setForeground(SystemColor.scrollbar);
		background.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		background.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/registerbackground.png")));
		background.setLocation(0, 0);
		background.setSize(889,964);
		contentPane.add(background);
		setLocationRelativeTo(null);
	}
	private void loginClick() {
		try {
			CatchException.CheckString.CheckLoginUser(username.getText());
			String usernamestring = username.getText();
			String passwordstring = String.valueOf(password.getPassword());
			Account user = new Account(usernamestring, passwordstring);
			
			clientlogin = new Socket("localhost",6000);
			DataOutputStream outClient= new DataOutputStream(clientlogin.getOutputStream());
			outClient.writeUTF("login\n"+usernamestring+"\n"+passwordstring);
			DataInputStream inclient = new DataInputStream(clientlogin.getInputStream());
			int result = inclient.readInt();
			
			if(result==1) {
				this.dispose();
				ChatFrameMain chat = new ChatFrameMain(user);
				
				ObjectInputStream ObjectIn = new ObjectInputStream(clientlogin.getInputStream());
				ArrayList<Account> ListAccount = (ArrayList<Account>) ObjectIn.readObject();
				ArrayList<GroupChat> ListGroup = (ArrayList<GroupChat>) ObjectIn.readObject();
				chat.listuserArray = ListAccount;
				chat.ListGroup = ListGroup;
				
				chat.PrintAllGroup(ListGroup);
				chat.newPrintAllUser(ListAccount);
				
				int SizeArray = inclient.readInt();
				String allOldMess = "";
				for(int i=0; i < SizeArray; i ++) {
					allOldMess+=inclient.readUTF();
				}	
				chat.getOldmessage(allOldMess);
				
				
				Thread a = new Thread(chat);
				a.start();
				chat.setVisible(true);
			}
			if(result==0) {
				JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác!");
				clientlogin.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if((e.getKeyCode()==KeyEvent.VK_ENTER)&&(e.getSource().equals(username)||e.getSource().equals(password))) {
			loginClick();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
