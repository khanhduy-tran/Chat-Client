package Frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Tools.Tools;

import javax.swing.JLabel;

public class ShowImage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowImage frame = new ShowImage(new ImageIcon("C:\\Users\\m\\Pictures\\background\\3eYlBDR.jpg"));
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
	public ShowImage(ImageIcon img) {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1571, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		if(img.getIconHeight()>900) {
			img= Tools.Resize(img, 900);
		}
		if(img.getIconWidth()>1600) {
			img = Tools.ResizeWidth(img, 1600);
		}
		
		JButton exiticon = new JButton("");
		exiticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exiticon.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/exit.png")), 40));
		exiticon.setBounds(1532, 0, 40, 40);
		exiticon.setContentAreaFilled(false);
		contentPane.add(exiticon);
		
		JLabel lbImage = new JLabel();
		lbImage.setHorizontalAlignment(JLabel.CENTER);
		lbImage.setBounds(0, 0, 1584, 900);
		lbImage.setIcon(img);
		contentPane.add(lbImage);
	}
}
