package Tools;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEncode64 {
	public static File ImageSelect() {
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		JFileChooser j = new JFileChooser("d:"); 
		j.setAcceptAllFileFilterUsed(false); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (*.png, *.jpg, *.gif)", "jpg", "png", "gif");
		j.addChoosableFileFilter(filter);
		int result = j.showDialog(null, "Select Image");
		if (result == JFileChooser.APPROVE_OPTION) { 
			return new File(j.getSelectedFile().getAbsolutePath());
		} else return null;
	}
	public static ImageIcon decoder(String base64Image) {
	    byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
	    ImageIcon a = new ImageIcon(imageByteArray);
	    return a;
	}
	public static String encoder(File imagePath) {
		String base64Image = "";
		try (FileInputStream imageInFile = new FileInputStream(imagePath)) {
			byte imageData[] = new byte[(int) imagePath.length()];
			imageInFile.read(imageData);
		    base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
		    System.out.println("Image not found" + e);
		} catch (IOException ioe) {
		    System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
	public static String encoderfromIMGICON(ImageIcon img) {
		String base64Image= "";
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", b );
			base64Image = Base64.getEncoder().encodeToString(b.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64Image;
	}
	public static String ConvertImageIconToBase64String(ImageIcon ii) {
		 BufferedImage image = new BufferedImage(ii.getIconWidth(),
		 ii.getIconHeight(), BufferedImage.TYPE_INT_RGB);

		 Graphics g = image.createGraphics();

		 ii.paintIcon(null, g, 0, 0);
		 g.dispose();

		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 try {
		 ImageIO.write(image, "jpg", b);
		 } catch (Exception ex) {
		 }
		 byte[] imageInByte = b.toByteArray();

		 return Base64.getEncoder().encodeToString(imageInByte);
	}
}
