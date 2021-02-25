package Tools;

import java.awt.AlphaComposite;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.sound.sampled.AudioFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Frame.ShowImage;

public class Tools {
	public static byte[] ArrayListByteArrayToByteArray(ArrayList<byte[]> array)
	{
		byte[] allbyte = array.stream().collect(
		        () -> new ByteArrayOutputStream(),
		        (b, e) -> {
		            try {
		                b.write(e);
		            } catch (IOException e1) {
		                throw new RuntimeException(e1);
		            }
		        },
		        (a, b) -> {}).toByteArray();
		return allbyte;
	}
	public static File ByteArrayToFile(byte[] bytearr, String filename) {
		FileOutputStream os;
		File temp=null;
		try {
			temp= new File("filetemp\\"+filename);
			temp.createNewFile();
			os = new FileOutputStream(temp);
			os.write(bytearr); 
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static boolean SaveFile(File file, String filename) {

		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");  
		fileChooser.setSelectedFile(new File("C:\\"+filename));
		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    try {
				Files.copy(file.toPath(), fileToSave.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			
			}
		    return true;
		} else return false;
		
	}
	public static File FileSelect() {
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		JFileChooser j = new JFileChooser("d:"); 
		int result = j.showDialog(null, "Select File");
		if (result == JFileChooser.APPROVE_OPTION) { 
			return new File(j.getSelectedFile().getAbsolutePath());
		} else return null;
	}
	public static ImageIcon Resize(ImageIcon a,int Height) {
		ImageIcon Result;
		double scale= Double.valueOf(a.getIconHeight())/Height;
		int Width=(int) Math.round(a.getIconWidth()/scale);
		Result= new ImageIcon(a.getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
		return Result;
	}
	public static ImageIcon ResizeWidth(ImageIcon a,int Width) {
		ImageIcon Result;
		double scale= Double.valueOf(a.getIconWidth())/Width;
		int Height=(int) Math.round(a.getIconHeight()/scale);
		Result= new ImageIcon(a.getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
		return Result;
	}
	public static ImageIcon Resize2(ImageIcon a, int Width,int Height) {
		ImageIcon Result;
		Result= new ImageIcon(a.getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
		return Result;
	}
	public static File getFilelocation() {
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		JFileChooser j = new JFileChooser("d:"); 
		j.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File (*jpg, *png)", "jpg", "png");
		j.setFileFilter(filter);
		int result = j.showDialog(null, "Select File");
		if (result == JFileChooser.APPROVE_OPTION) { 
			if(j.getSelectedFile().length()>60000) {
				JOptionPane.showMessageDialog(null, "Kích thước ảnh phải nhỏ hơn 60kb");
				return null;
			} else return new File(j.getSelectedFile().getAbsolutePath());
		} else return null;
	}
	public static Icon toCircle(ImageIcon ico) {
		Image img = ico.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, 50, 50, null);
		ImageIcon newIco = new ImageIcon(bi);
	    return newIco;
	}
	public static BufferedImage imageToBufferedImage(Image im) {
	   BufferedImage bi = new BufferedImage
	      (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
	   Graphics bg = bi.getGraphics();
	   bg.drawImage(im, 0, 0, null);
	   bg.dispose();
	   return bi;
	}
	public static AudioFormat getAudioFormat(){
		   
        float sampleRate = 8000.0F;

        int sampleSizeInBits = 16;

        int channels = 2;

        boolean signed = true;

        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,bigEndian);
    }
}
