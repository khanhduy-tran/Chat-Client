package Frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

import Tools.ImageEncode64;
import Tools.Tools;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;

import Object.Account;
import Object.GroupChat;

import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GroupProfile extends JFrame {

	private JPanel contentPane;
	private JTextField tfGroupName;
	private ImageIcon newAvatar=null;
	private File imgurl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupProfile frame = new GroupProfile(null, null, null, null);
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
	public GroupProfile(GroupChat group, DataOutputStream _dataout, ArrayList<Account> listuser, ChatFrameMain chat) {
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
				GroupProfile.this.dispose();
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
		lbimg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ShowImage(group.getAvatar()).setVisible(true);
			}
		});
		lbimg.setBounds(0, 0, 150, 200);
		imgpanel.add(lbimg);
		
		JButton selectImgBTN = new JButton("Select Picture");
		selectImgBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File imgurl= Tools.getFilelocation();
				if(imgurl!=null) {
					GroupProfile.this.imgurl = imgurl;
					newAvatar = new ImageIcon(imgurl.getAbsolutePath());
					lbimg.setIcon(Tools.Resize(newAvatar,200));
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
		
		JLabel lblNewLabel = new JLabel("Group Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(32, 11, 111, 20);
		panel_1.add(lblNewLabel);
		
		tfGroupName = new JTextField();
		tfGroupName.setEditable(false);
		tfGroupName.setBounds(32, 42, 263, 28);
		panel_1.add(tfGroupName);
		tfGroupName.setColumns(10);
		
		JLabel lblPassword = new JLabel("List Member");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(32, 96, 111, 20);
		panel_1.add(lblPassword);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(30, 144, 255));
		panel_2.setBounds(0, 384, 588, 10);
		contentPane.add(panel_2);
		
		JButton saveChanged = new JButton("SAVE");
		saveChanged.setFocusable(false);
		saveChanged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(newAvatar!=null) {
						String AvatarString = ImageEncode64.encoder(GroupProfile.this.imgurl);
						_dataout.writeUTF("UpdateGroup\navatar\n"+group.getName()+"\n"+AvatarString);
					}
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
		
		JLabel lblNewLabel_1 = new JLabel("GROUP INFO");
		lblNewLabel_1.setFont(new Font("Unispace", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(30, 23, 200, 52);
		contentPane.add(lblNewLabel_1);

		String list[] = new String[group.getListmember().size()];
		for(int i=0; i< group.getListmember().size();i ++) {
			list[i] = group.getListmember().get(i);
		}
		JComboBox<String> listmember = new JComboBox<String>(list);
		listmember.setBounds(32, 127, 176, 33);
		panel_1.add(listmember);


		ArrayList<String> Arrlistnonmember = new ArrayList<String>();
		for(Account temp: listuser) {
			if(!group.hasUser(temp.getUsername())) {
				Arrlistnonmember.add(temp.getUsername());
			}
		}
		String listnonmemberString[] = new String[Arrlistnonmember.size()];
		for(int i=0; i< Arrlistnonmember.size();i ++) {
			listnonmemberString[i] = Arrlistnonmember.get(i);
		}
		JComboBox<String> listnonmember = new JComboBox<String>(listnonmemberString);
		listnonmember.setBounds(32, 213, 176, 33);
		panel_1.add(listnonmember);
		
		try {
			tfGroupName.setText(group.getName());
			
			JButton addnewMember = new JButton("ADD");
			addnewMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dataout= "UpdateGroup\nMemberAdd\n"+group.getName()+"\n"+listnonmember.getSelectedItem().toString();
					try {
						_dataout.writeUTF(dataout);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			addnewMember.setBounds(218, 213, 77, 33);
			panel_1.add(addnewMember);
			
			JButton kickmember = new JButton("KICK");
			kickmember.setBounds(218, 127, 77, 33);
			kickmember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dataout= "UpdateGroup\nMemberKick\n"+group.getName()+"\n"+listmember.getSelectedItem().toString();
					try {
						_dataout.writeUTF(dataout);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			panel_1.add(kickmember);
			
			JLabel lblListNonmember = new JLabel("List Non-Member");
			lblListNonmember.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblListNonmember.setBounds(32, 182, 176, 20);
			panel_1.add(lblListNonmember);
			
			lbimg.setIcon(Tools.Resize(group.getAvatar(), 200));
			
			JButton deleteGroup = new JButton("Delete Group");
			deleteGroup.setForeground(Color.white);
			deleteGroup.setFocusable(false);
			deleteGroup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int input = JOptionPane.showConfirmDialog(null, "Do you want to delete this group?", "Confirm your action...",
							JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
					if(input==0) {
						String dataout= "DeleteGroup\n"+group.getName();
						try {
							_dataout.writeUTF(dataout);
							dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			deleteGroup.setBackground(Color.RED);
			deleteGroup.setBounds(425, 350, 119, 23);
			contentPane.add(deleteGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
