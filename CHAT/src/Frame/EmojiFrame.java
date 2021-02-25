package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Tools.Tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EmojiFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		new EmojiFrame(null).setVisible(true);;
	}
	public EmojiFrame(ChatFrameMain chatframe) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(438, 508);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(new Color(255, 255, 255));
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel(new GridLayout(6, 5, 5, 5));
		panel.setBounds(26, 30, 385, 467);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		
		JButton exiticon = new JButton("");
		exiticon.setContentAreaFilled(false);
		exiticon.setBounds(410, 0, 30, 30);
		exiticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		
		exiticon.setIcon(Tools.Resize(new ImageIcon(LoginFrame.class.getResource("/img/exit.png")), 40));
		contentPane.add(exiticon);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource("emoji");
	    String path = url.getPath();
	    File[] a = new File(path).listFiles();
	    for(int i=0; i< a.length; i ++) {
	    	JLabel emoji = new JLabel();
	    	emoji.setIcon(new ImageIcon(a[i].getAbsolutePath()));
	    	final int index = i;
	    	emoji.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					chatframe.sendImage(a[index].getAbsoluteFile());
					setVisible(false);
				}
			});
			panel.add(emoji);
	    }
		
//		String[] Emoji = {"★", "☆", "✡", "✦", "✧", "✩", "✪", "✫", "✬", "✭", "✮", "✯", "✰", "⁂", "⁎", "⁑", "✢", "✣", "✤", "✥", "✱", "✲", "✳", "✴", "✵", "✶", "✷", "✸", "✹", "✺", "✻", "✼", "✽", "✾", "✿", "❀", "❁", "❂", "❃", "❇", "❈", "❉", "❊", "❋", "❄", "❆", "❅", "⋆", "≛", "♔", "♕", "♖", "♗", "♘", "♙", "♚", "♛", "♜", "♝", "♞", "♟", "♤", "♠", "♧", "♣", "♡", "♥", "♢", "♦", "°", "℃", "℉", "ϟ", "☀", "☁", "☂", "☃", "☉", "☼", "☽", "☾", "♁", "♨", "❄", "❅", "❆", "☇", "☈", "☄", "㎎", "㎏", "㎜", "㎝", "㎞", "㎡", "㏄", "㏎", "㏑", "㏒", "㏕", "♥", "♡", "❤", "❥", "❣", "❦", "❧", "დ", "ღ", "۵", "ლ", "ლ", "💙", "💛", "💜", "🖤", "💗", "💓", "💔", "💟", "💕", "💖", "💘", "💝", "💞", "🅰", "🅱", "🅾", "🅿"};
//		for(int i=0; i< Emoji.length; i++) {
//			JLabel temp = new JLabel(Emoji[i]);
//			temp.setFont(new Font("Italic", Font.BOLD, 30));
//			temp.addMouseListener(new MouseListener() {
//				public void mouseReleased(MouseEvent e) {
//					chatframe.addEmoji(temp.getText());
//					setVisible(false);
//				}
//				public void mousePressed(MouseEvent e) {
//				}
//				public void mouseExited(MouseEvent e) {
////					temp.setFont(new Font("Italic", Font.BOLD, 30));
//					temp.setForeground(Color.black);
//				}
//				public void mouseEntered(MouseEvent e) {
////					temp.setFont(new Font("Italic", Font.BOLD, 40));
//					temp.setForeground(new Color(102, 102, 153));
//				}
//				public void mouseClicked(MouseEvent e) {
//				}
//			});
//			panel.add(temp);
//		}
		
	}
}
