package Frame;

import java.awt.EventQueue; 


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Object.Account;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterFrame extends JFrame {

	public JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
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
	ImageIcon iconbackground = new ImageIcon("img//registerbackground.png");
	private JTextField username;
	private JPasswordField password;
	private JLabel userpass;
	private JLabel showpass;
	private JButton exiticon;
	private ImageIcon showpassicon = Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/showpass.png")),20);
	private ImageIcon hidepassicon = Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/hidepass.png")),20);
	private JPasswordField confirmpassword;
	private JLabel userpassconfirmshow;
	private JTextField email;
	private JLabel emaillb;
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(889,964);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		
		JLabel userlabel = new JLabel();
		userlabel.setIcon(Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/wolfuser.png")), 50));
		userlabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		userlabel.setForeground(Color.WHITE);
		userlabel.setBounds(224, 682, 72, 60);
		contentPane.add(userlabel);
		
		username = new JTextField("Type username here");
		username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("Type username here")){
					username.setText("");
					username.setForeground(Color.black);
					username.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(username.getText().equals("")) {
					username.setText("Type username here");
					username.setForeground(new Color(192,192,192));
					username.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
				}
			}
		});
		username.setForeground(new Color(192,192,192));
		username.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		username.setBounds(294, 698, 348, 39);
		username.setHorizontalAlignment(JTextField.CENTER);
		username.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (username.getText().length() >= 20 ) 
		            e.consume(); 
		    }  
		});
		contentPane.add(username);
		
		
		
		JLabel registerlabell = new JLabel("REGISTER ACCOUNT");
		registerlabell.setForeground(SystemColor.inactiveCaption);
		registerlabell.setFont(new Font("Unispace", Font.BOLD, 35));
		registerlabell.setBounds(306, 629, 336, 74);
		contentPane.add(registerlabell);
		
		password = new JPasswordField("Type password here");
		char defaultEcho = password.getEchoChar();
		password.setEchoChar((char)0);
		password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(String.valueOf(password.getPassword()).equals("Type password here")){
					password.setText("");
					password.setForeground(Color.black);
					password.setFont(new Font("Tahoma", Font.PLAIN, 28));
					password.setEchoChar(defaultEcho);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(password.getPassword().length==0) {
					password.setText("Type password here");
					password.setForeground(new Color(192,192,192));
					password.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
					password.setEchoChar((char)0);
				}
			}
		});
		password.setForeground(new Color(192,192,192));
		password.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		password.setBounds(294, 800, 348, 39);
		password.setHorizontalAlignment(JTextField.CENTER);
		password.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (password.getPassword().length >= 20 ) 
		            e.consume(); 
		    }  
		});
		contentPane.add(password);
		
		userpass = new JLabel();
		userpass.setIcon(Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/wolfpassword.png")),40));
		userpass.setForeground(Color.WHITE);
		userpass.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		userpass.setBounds(229, 789, 55, 60);
		contentPane.add(userpass);
		
		JButton loginbutton = new JButton("REGISTER");
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAccountRegister();
			}
		});
		loginbutton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 28));
		loginbutton.setBounds(366, 914, 162, 39);
		loginbutton.setFocusable(false);
		loginbutton.setBackground(SystemColor.activeCaption);
		contentPane.add(loginbutton);
		
		showpass = new JLabel("");
		showpass.setIcon(showpassicon);
		showpass.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if((!String.valueOf(password.getPassword()).equals("Type password here"))) {

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
		showpass.setBounds(652, 814, 55, 39);
		contentPane.add(showpass);
		
		exiticon = new JButton("");
		exiticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exiticon.setIcon(Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/exit.png")), 40));
		exiticon.setBounds(849, 0, 40, 40);
		exiticon.setContentAreaFilled(false);
		contentPane.add(exiticon);
		
		confirmpassword = new JPasswordField("Confirm your password here");
		confirmpassword.setEchoChar((char)0);
		confirmpassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(String.valueOf(confirmpassword.getPassword()).equals("Confirm your password here")){
					confirmpassword.setText("");
					confirmpassword.setForeground(Color.black);
					confirmpassword.setFont(new Font("Tahoma", Font.PLAIN, 28));
					confirmpassword.setEchoChar(defaultEcho);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(confirmpassword.getPassword().length==0) {
					confirmpassword.setText("Confirm your password here");
					confirmpassword.setForeground(new Color(192,192,192));
					confirmpassword.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
					confirmpassword.setEchoChar((char)0);
				}
			}
		});
		confirmpassword.setForeground(new Color(192,192,192));
		confirmpassword.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		confirmpassword.setHorizontalAlignment(SwingConstants.CENTER);
		confirmpassword.setBounds(294, 850, 348, 39);
		contentPane.add(confirmpassword);
		
		JLabel userpassconfirm = new JLabel();
		userpassconfirm.setIcon(Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/wolfpassword.png")),40));
		userpassconfirm.setForeground(Color.WHITE);
		userpassconfirm.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		userpassconfirm.setBounds(229, 839, 55, 60);
		contentPane.add(userpassconfirm);
		
		userpassconfirmshow = new JLabel("");
		userpassconfirmshow.setIcon(showpassicon);
		userpassconfirmshow.setBounds(652, 864, 55, 39);		
		userpassconfirmshow.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if((!String.valueOf(confirmpassword.getPassword()).equals("Confirm your password here"))) {

					if(userpassconfirmshow.getIcon().equals(showpassicon)) {
						userpassconfirmshow.setIcon(hidepassicon);
						confirmpassword.setFont(new Font("Arial", Font.PLAIN, 18));
						confirmpassword.setEchoChar((char)0);
					} else {
						confirmpassword.setEchoChar(defaultEcho);
						confirmpassword.setFont(new Font("Tahoma", Font.PLAIN, 28));
						userpassconfirmshow.setIcon(showpassicon);
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				userpassconfirmshow.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				userpassconfirmshow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPane.add(userpassconfirmshow);
		
		email = new JTextField("Type your email here");
		email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(email.getText().equals("Type your email here")){
					email.setText("");
					email.setForeground(Color.black);
					email.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(email.getText().equals("")) {
					email.setText("Type your email here");
					email.setForeground(new Color(192,192,192));
					email.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
				}
			}
		});
		email.setForeground(new Color(192,192,192));
		email.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.setBounds(294, 750, 348, 39);
		email.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (email.getText().length() >= 40 ) 
		            e.consume(); 
		    }  
		});
		contentPane.add(email);
		
		emaillb = new JLabel();
		emaillb.setForeground(Color.WHITE);
		emaillb.setFont(new Font("Lucida Handwriting", Font.PLAIN, 17));
		emaillb.setIcon(Tools.Resize(new ImageIcon(RegisterFrame.class.getResource("/img/wolfemail.png")),50));
		emaillb.setBounds(224, 740, 72, 60);
		contentPane.add(emaillb);
		
		JLabel backToLogin = new JLabel("Back To Login");
		backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					LoginFrame loginframe = new LoginFrame();
					loginframe.setVisible(true);
					dispose();
				}
			}
		});
		backToLogin.setForeground(SystemColor.menu);
		backToLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backToLogin.setBounds(548, 887, 94, 39);
		contentPane.add(backToLogin);
		
		JLabel background = new JLabel();
		background.setForeground(SystemColor.scrollbar);
		background.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		background.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/registerbackground.png")));
		background.setLocation(0, 0);
		background.setSize(889,964);
		contentPane.add(background);
		setLocationRelativeTo(null);
	}
	private Account getAccountRegister() {
		try {
			CatchException.CheckString.CheckUserName(username.getText());
			CatchException.CheckString.CheckEmail(email.getText());
			CatchException.CheckString.CheckRegisterPassword(String.valueOf(password.getPassword()), String.valueOf(confirmpassword.getPassword()));
			
			Socket clientregister = new Socket("localhost",6000);
			DataOutputStream outClient= new DataOutputStream(clientregister.getOutputStream());
			outClient.writeUTF("register\n"+username.getText()+"\n"+String.valueOf(password.getPassword())+"\n"+email.getText());
			
			DataInputStream inclient = new DataInputStream(clientregister.getInputStream());
			int result = inclient.readInt();
			if(result==1) {

				this.dispose();
				LoginFrame login = new LoginFrame();
				login.setVisible(true);
				JOptionPane.showMessageDialog(login, "Đăng kí thành công!");
			} else {
				JOptionPane.showMessageDialog(this, "Địa chỉ email hoặc tài khoản đã tồn tại!");
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return null;
	}
	public JPanel getContentPanel() {
		return this.contentPane;
	}
}
