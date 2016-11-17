package loadVideo;

import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class LoadVideo {
	private int width_;
	private int height_;
	private ArrayList<Mat> frames_ = new ArrayList<Mat>();

	// http://stackoverflow.com/questions/17401852/open-video-file-with-opencv-java
	public LoadVideo(VideoCapture cap) {
		width_ = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
		height_ = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
		int i = 0;
		while (i < (int) cap
				.get(7)) /* cap.get(7) retourne le nombre de frames de cap */ {
			Mat frame = new Mat();
			cap.read(frame);
			frames_.add(frame);
			i++;
		}
		System.out.println("video loaded");
	}

	// Créer une vidéo vide

	public LoadVideo(int height, int width) {
		super();
		this.width_ = width;
		this.height_ = height;
	}



	// Affiche la vidéo dans une nouvelle fenêtre
	// Sources pour afficher la vidéo à un raffraichissement donné :
	// http://stackoverflow.com/questions/771206/how-do-i-cap-my-framerate-at-60-fps-in-java
	public void displayVideo(int framesPerSecond,int startFrame) {
		JFrame jframe = new JFrame("video");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(width_ + 10, height_ + 35);
		jframe.setVisible(true);
		ImageIcon image = new ImageIcon();
		int i = startFrame;

		int skipTicks = 1000 / framesPerSecond;

		double nextFrameTick = System.currentTimeMillis();
		int loops;

		while (i < frames_.size()) {
			loops = 0;
			image = new ImageIcon(Mat2bufferedImage(frames_.get(i), width_, height_));
			while (System.currentTimeMillis() < nextFrameTick + skipTicks && loops < 10000000) {
	            loops++;
			}
			nextFrameTick = System.currentTimeMillis();
			vidpanel.setIcon(image);
			//vidpanel.repaint();
			i++;
		}
		System.out.println("end");
	}

	// Retourne une image de la vidéo
	public Mat getFrame(int numFrame) {
		return frames_.get(numFrame);
	}

	public int getHeight() {
		return height_;
	}

	public int getWidth() {
		return width_;
	}

	public int getSize() {
		return frames_.size();
	}
	
	public void addFrame(Mat frame){
		frames_.add(frame);
	}

	// Affiche une image de la vidéo dans une nouvelle fenêtre
	public void displayFrame(int numFrame) {
		// Highgui.imwrite(imgStr,m);
		JFrame frame = new JFrame("Image " + numFrame + "/" + frames_.size());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		// Inserts the image icon
		ImageIcon image = new ImageIcon(Mat2bufferedImage(frames_.get(numFrame), width_, height_));
		frame.setSize(image.getIconWidth() + 10, image.getIconHeight() + 35); // taille
																				// GUI
		// Draw the Image data into the BufferedImage
		JLabel label1 = new JLabel(" ", image, JLabel.CENTER);
		frame.getContentPane().add(label1);

		frame.validate();
		frame.setVisible(true);
	}

	// http://www.codeproject.com/Tips/752511/How-to-Convert-Mat-to-BufferedImage-Vice-Versa
	public static BufferedImage Mat2bufferedImage(Mat in, int width, int height) {
		BufferedImage out;
		Mat in2 = new Mat();
		Imgproc.cvtColor(in, in2, Imgproc.COLOR_RGB2BGR);
		byte[] data = new byte[width * height * (int) in2.elemSize()];
		int type;
		in2.get(0, 0, data);

		if (in2.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;

		out = new BufferedImage(width, height, type);

		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;

	}
}
