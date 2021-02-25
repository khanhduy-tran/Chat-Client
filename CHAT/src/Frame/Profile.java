package Frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

import Tools.ImageEncode64;
import Tools.Tools;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;

import Object.Account;

public class Profile extends JFrame {

	private JPanel contentPane;
	private JTextField tfDisplayName;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private ImageIcon newAvatar=null;
	private File imgurl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile(null, true, null);
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
	public Profile(Account user, boolean isMyuser, DataOutputStream dataout) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(588, 483);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 139, 139), new Color(0, 139, 139), new Color(30, 144, 255), new Color(30, 144, 255)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUndecorated(true);
		
		JButton exiticon = new JButton("");
		exiticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Profile.this.dispose();
			}
		});
		exiticon.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/exit.png")), 40));
		exiticon.setBounds(538, 11, 40, 40);
		exiticon.setContentAreaFilled(false);
		contentPane.add(exiticon);
		
		JPanel imgpanel = new JPanel();
		imgpanel.setBounds(42, 91, 150, 200);
		contentPane.add(imgpanel);
		imgpanel.setLayout(null);
		
		JLabel lbimg = new JLabel("");
		lbimg.setBounds(0, 0, 150, 200);
		imgpanel.add(lbimg);
		
		JButton selectImgBTN = new JButton("Select Picture");
		selectImgBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isMyuser) {
					File imgurl= Tools.getFilelocation();
					if(imgurl!=null) {
						Profile.this.imgurl = imgurl;
						newAvatar = new ImageIcon(imgurl.getAbsolutePath());
						lbimg.setIcon(Tools.Resize(newAvatar,200));
					}
				} else {
					new ShowImage(user.getAvatar()).setVisible(true);
				}
			}
		});
		selectImgBTN.setOpaque(true);
		selectImgBTN.setFocusable(false);
		selectImgBTN.setBackground(new Color(100, 149, 237));
		selectImgBTN.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		selectImgBTN.setBounds(42, 315, 150, 33);
		contentPane.add(selectImgBTN);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(135, 206, 250), null, null, null), null));
		panel_1.setBackground(new Color(135, 206, 250));
		panel_1.setBounds(228, 91, 316, 257);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Display Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(32, 11, 111, 20);
		panel_1.add(lblNewLabel);
		
		tfDisplayName = new JTextField();
		tfDisplayName.setBounds(32, 42, 263, 28);
		panel_1.add(tfDisplayName);
		tfDisplayName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(32, 90, 111, 20);
		panel_1.add(lblUsername);
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		tfUsername.setBounds(32, 121, 263, 28);
		tfUsername.setEditable(false);
		panel_1.add(tfUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(32, 171, 111, 20);
		panel_1.add(lblPassword);
		
		tfPassword = new JPasswordField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(32, 202, 263, 28);
		panel_1.add(tfPassword);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(30, 144, 255));
		panel_2.setBounds(0, 384, 588, 10);
		contentPane.add(panel_2);
		
		JButton saveChanged = new JButton("SAVE");
		saveChanged.setFocusable(false);
		saveChanged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!tfDisplayName.getText().equals(user.getDisplayname())) {
						dataout.writeUTF("UpdateProfile\ndisplayname\n"+tfDisplayName.getText());
					}
					if(newAvatar!=null) {
						String AvatarString = ImageEncode64.encoder(Profile.this.imgurl);
						dataout.writeUTF("UpdateProfile\navatar\n"+AvatarString);
					}
					dataout.writeUTF("UpdateProfile\npassword\n"+new String(tfPassword.getPassword()));
					dispose();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		saveChanged.setOpaque(true);
		saveChanged.setBackground(new Color(100, 149, 237));
		saveChanged.setFont(new Font("Trebuchet MS", Font.BOLD, 23));
		saveChanged.setBounds(212, 432, 132, 40);
		contentPane.add(saveChanged);
		
		JLabel lblNewLabel_1 = new JLabel("USER PROFILE");
		lblNewLabel_1.setFont(new Font("Unispace", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(30, 23, 200, 52);
		contentPane.add(lblNewLabel_1);
		
		try {
			tfDisplayName.setText(user.getDisplayname());
			tfUsername.setText(user.getUsername());
			tfPassword.setText(user.getPassword());
			lbimg.setIcon(Tools.Resize(user.getAvatar(), 200));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!isMyuser) {
			tfDisplayName.setEditable(false);
			saveChanged.setVisible(false);
			tfPassword.setVisible(false);
			lblPassword.setVisible(false);
			selectImgBTN.setText("VIEW IMG");
		}
	}
}
