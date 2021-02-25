package Frame;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Base64;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.Webcam;

import Tools.ImageEncode64;
import Tools.Tools;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class ChatVideoFrame extends JFrame {

	private JPanel contentPane;
	private JLabel labelvideo, lbyourcam;
	private static Webcam webcam;
	private DatagramPacket PacketSend, PacketReceiver;
	private InetAddress friendip;
	private int myport, friendport;
	private VideoSender senderwebcam;
	private Receiver CallReceiver;
	private VoiceSender sendervoice;
	public DatagramSocket udpsocket;
	static Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
	SourceDataLine audio_out;
    TargetDataLine audio_in;
    static boolean iscallingVoice = false;
    static boolean iscallingVideo = false;
    public void audioStart(){
        try {
            AudioFormat format = Tools.getAudioFormat();
            DataLine.Info info_in = new DataLine.Info(TargetDataLine.class, format);
            DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info_in)) {
                System.out.println("Line for in not supported");
                System.exit(0);
            }
            if(!AudioSystem.isLineSupported(info_out)){
                System.out.println("Line for out not supported");
                System.exit(0);
            }
            audio_out = (SourceDataLine)AudioSystem.getLine(info_out);
            audio_out.open(format);
            audio_out.start();
            
            audio_in = (TargetDataLine) AudioSystem.getLine(info_in);
            audio_in.open(format);
            audio_in.start();            
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        
    }

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public ChatVideoFrame(int _myport, InetAddress _friendip , int _friendport) {

		this.myport = _myport;
		this.friendip = _friendip;
		this.friendport= _friendport;
		System.out.println("Calling :"+this.friendip+"/"+this.friendport);
		try {
			udpsocket = new DatagramSocket(myport);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1184, 681);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lbyourcam = new JLabel("");
		lbyourcam.setBackground(Color.CYAN);
		lbyourcam.setBounds(424, 0, 200, 150);
		lbyourcam.setOpaque(true);
		panel.add(lbyourcam);
		
		labelvideo = new JLabel();
		labelvideo.setFont(new Font("Arial", Font.BOLD, 25));
		labelvideo.setBounds(0, 0, 624, 441);
		panel.add(labelvideo);


		iscallingVoice= true;
		iscallingVideo= true;
		senderwebcam = new VideoSender();
		senderwebcam.start();
		sendervoice = new VoiceSender();
		sendervoice.start();
		CallReceiver = new Receiver();
		CallReceiver.start();
		


		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	stopChatVideo();
		    }
		});
	}
	public void stopChatVideo() {
		iscallingVideo = false;
		iscallingVoice = false; 
        udpsocket.close();
        webcam.close();
        dispose();
	}
	class VoiceSender extends Thread{
		@Override
		public void run() {
			audioStart();
            Long pack = 0l;
			byte byte_buff[] = new byte[512];
			byte[] byteEncode = new byte[1024];
			DatagramPacket voiceSend;
			while(iscallingVoice) {
				try {
					audio_in.read(byte_buff, 0, byte_buff.length);
					byteEncode = ("voice--" + Base64.getEncoder().encodeToString(byte_buff) + "--").getBytes();
					voiceSend = new DatagramPacket(byteEncode, byteEncode.length, friendip, friendport);
                    udpsocket.send(voiceSend);
                    System.out.println("sended "+pack);
					
				}catch (Exception e) {
				}
			}
            System.out.println("call in recorder: recorder is stop");
            audio_in.drain();
            audio_in.close();
            
            System.out.println("call in recorder: audio is drain and close");
			
		}
	}  
	class VideoSender extends Thread{
		@Override
		public void run() {
			
			webcam = Webcam.getDefault();
			webcam.setViewSize(new Dimension(640, 480));
			webcam.open();
			while(iscallingVideo) {
				
				try {
					Image image = webcam.getImage();
					ImageIcon imgicon = new ImageIcon(image);
					lbyourcam.setIcon(Tools.Resize(imgicon, 150));
					sendDataVideo(imgicon);
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	class Receiver extends Thread {
		@Override
		public void run() {
			while(iscallingVideo) {
				try {
					byte[] udpByte = new byte[62000];
					PacketReceiver = new DatagramPacket(udpByte, udpByte.length);
					udpsocket.receive(PacketReceiver);
					String UDPString = new String(PacketReceiver.getData());
					if(getType(UDPString).equalsIgnoreCase("video")) {
						System.out.println("Received video");
						ImageIcon img = ImageEncode64.decoder(getMessage(UDPString));
						if(img!=null) labelvideo.setIcon(Tools.Resize2(img, 640, 480));
					}
					if(getType(UDPString).equalsIgnoreCase("voice")) {
						System.out.println("Received Voice");
						String voice = getMessage(UDPString);
						byte[] buffer = new byte[1024];
						buffer = Base64.getDecoder().decode(voice);
						audio_out.write(buffer, 0, buffer.length);
					}
					
				}catch (Exception e) {
				}
			}
		}
		
	}
	private void sendDataVideo(ImageIcon webcam) {
		
		try {
			String img = ImageEncode64.ConvertImageIconToBase64String(webcam);
			
			img = "video--" +img+"--";
			byte[] outData = new byte[62000];
			outData = img.getBytes();
			PacketSend = new DatagramPacket(outData, outData.length, friendip, friendport);
			udpsocket.send(PacketSend);
			System.out.println("Sended Cam");

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private String getData() {
		ImageIcon img;
		try {
			byte[] udpByte = new byte[62000];
			PacketReceiver = new DatagramPacket(udpByte, udpByte.length);
			udpsocket.receive(PacketReceiver);
			String UDPString = new String(PacketReceiver.getData());
			if(getType(UDPString).equalsIgnoreCase("video")) {
				return getMessage(UDPString);
			}
			if(getType(UDPString).equalsIgnoreCase("voice")) {
				return getMessage(UDPString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private ImageIcon getImageTake() {
		ImageIcon img;
		try {
			byte[] udpByte = new byte[62000];
			PacketReceiver = new DatagramPacket(udpByte, udpByte.length);
			udpsocket.receive(PacketReceiver);
			String UDPString = new String(PacketReceiver.getData());
			if(getType(UDPString).equalsIgnoreCase("video")) {
				img = ImageEncode64.decoder(getMessage(UDPString));
				return img;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getReceiver(String string) {
		String[]result= string.split("--");
		return result[3];
	}
	public static String getSender(String string) {
		String[]result= string.split("--");
		return result[2];
	}
	public static String getMessage(String string) {
		String[]result= string.split("--");
		return result[1];
	}
	public static String getType(String string) {
		String[]result= string.split("--");
		return result[0];
	}
}
