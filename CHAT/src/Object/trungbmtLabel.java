package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import Frame.ChatFrameMain;

public class trungbmtLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	public static final String myColor = "#00a4d1";
	public static final String theirColor = "#dbdbdb";
	public static final int lengmax = 50;
	public String defaulttext;
	public trungbmtLabel(String text) {
		super(text);
		defaulttext= catdong(text);
        setFont(new Font("Arial", Font.BOLD, 15));
        setOpaque(false);
    }
	public void rePaintMess() {

        String myvar;
	    if(getHorizontalAlignment()==JLabel.RIGHT) {
	    	myvar = "<div style=\"border: 1px solid #000000; background-color: "+myColor+"; color: white; padding: 8 16;\">"+defaulttext+"</div>";
	    } else {
	    	myvar = "<div style=\"border: 1px solid #000000; background-color: "+theirColor+"; color: black; padding: 8 16;\">"+defaulttext+"</div>";
	    }
        this.setText("<html>"+myvar+"</html>");
	}
	private String catdong(String text) {
		if(text.length()<=lengmax) return text;
		for(int i=lengmax; i>lengmax/2;i --) {
			if(text.charAt(i)==32) {
				return text.substring(0, i)+"<br>"+catdong(text.substring(i, text.length()-1));
			}
		} 
		return text.substring(0, 50)+"<br>"+catdong(text.substring(50, text.length()-1));
		
	}
//	private int getChieudai(String text) {
//		FontMetrics fm = this.getFontMetrics(this.getFont());
//		if(text.length()>35) return(fm.stringWidth(text.substring(0, 35)));
//		else return fm.stringWidth(text)+20;
//		
//	}

}
