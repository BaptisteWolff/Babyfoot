package loadVideo;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class LoadVideo {
	private int width;
	private int height;
	private int size = 0;
	VideoCapture cap;
	String videoName;
	int nLastFrame = -1;
	Mat currentFrame = new Mat();
	Mat lastFrame = new Mat();
	AtomicInteger lastFrameLoaded = new AtomicInteger();
	// private ArrayList<Mat> frames_ = new ArrayList<Mat>();

	// http://stackoverflow.com/questions/17401852/open-video-file-with-opencv-java
	public LoadVideo(String videoName) {
		this.cap = new VideoCapture(videoName);
		this.width = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
		this.height = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
		// int i = 0;
		this.size = (int) cap.get(7); // cap.get(7) retourne le nombre de frames
										// de cap
		this.videoName = videoName;
		/*
		 * while (i < (int) cap.get(7)) // cap.get(7) retourne le nombre de
		 * frames de cap { Mat frame = new Mat(); cap.read(frame);
		 * frames_.add(frame); i++; }
		 */
		System.out.println("video loaded");
	}

	// Créer une vidéo vide

	public LoadVideo(int height, int width) {
		super();
		this.width = width;
		this.height = height;
	}

	// Affiche la vidéo dans une nouvelle fenêtre
	// Sources pour afficher la vidéo à un raffraichissement donné :
	// http://stackoverflow.com/questions/771206/how-do-i-cap-my-framerate-at-60-fps-in-java

	public void displayVideo(int framesPerSecond, int startFrame) {
		JFrame jframe = new JFrame("video");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(width + 10, height + 35);
		jframe.setVisible(true);
		ImageIcon image = new ImageIcon();
		int i = startFrame;

		int skipTicks = 1000 / framesPerSecond;

		double nextFrameTick = System.currentTimeMillis();
		int loops;

		while (i < size) {
			loops = 0;
			image = this.getImage(i);
			while (System.currentTimeMillis() < nextFrameTick + skipTicks && loops < 1000000000) {
				loops++;
			}
			nextFrameTick = System.currentTimeMillis();
			vidpanel.setIcon(image);
			// vidpanel.repaint();
			i++;
		}
		System.out.println("end");
	}

	/* POUR LIBERER L'ESPACE MEMOIRE DE LA VIDEO */

	/*
	 * public void libererVideo() { try { for(int i=0; i<frames_.size(); i++) {
	 * frames_.get(i).release(); } } catch (Exception x) {
	 * System.out.println("Echec à la suppression video"); } }
	 */

	/* AFFICHE UNE VIDEO AVEC LES BARYCENTRES */

	public void displayVideoBarycentres(int framesPerSecond, int startFrame, int barycentres[][]) {
		JFrame jframe = new JFrame("video");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(width + 10, height + 35);
		jframe.setVisible(true);
		ImageIcon image = new ImageIcon();
		int i = startFrame;

		int X[] = barycentres[0];
		int Y[] = barycentres[1];
		int x = 0;
		int y = 0;

		int skipTicks = 1000 / framesPerSecond;

		double nextFrameTick = System.currentTimeMillis();
		int loops;
		Mat frame1 = new Mat();
		frame1 = this.getFrame(0);

		double[] data = frame1.get(0, 0);
		data[0] = 0;
		data[1] = 0;
		data[2] = 255;

		while (i < size) {

			loops = 0;
			Mat frame = new Mat();
			frame = this.getFrame(i);
			x = X[i];
			y = Y[i];

			Point centre = new Point(x, y);
			int rayon = 10;
			Scalar color = new Scalar(0, 0, 255);
			// Tracer un cercle rouge représentant le barycentre

			Core.circle(frame, centre, rayon, color, -1); // -1: rempli le
															// cercle

			image = new ImageIcon(Mat2bufferedImage(frame, width, height));

			while (System.currentTimeMillis() < nextFrameTick + skipTicks && loops < 1000000000) {
				loops++;
			}
			nextFrameTick = System.currentTimeMillis();

			vidpanel.setIcon(image);
			// vidpanel.repaint();
			i++;
		}
		System.out.println("end");
	}

	public class FindLastFrame extends Thread {
		int numFrame = 0;

		public FindLastFrame(int numFrame) {
			super();
			this.numFrame = numFrame;
		}

		public void run() {
			cap = new VideoCapture(videoName);
			int i = 0;
			while (i < numFrame - 2) {
				cap.grab();
				i++;
			}
			cap.read(lastFrame);
			lastFrameLoaded.set(0);
		}
	}
	/* RETOURNE UNE frame DE LA VIDEO */

	public Mat getFrame(int numFrame) {
		while (lastFrameLoaded.get() != 0) { // On vérifie que le thread findLastFrame n'est pas en cours d'éxécution.
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("waitAborted");// TODO Auto-generated
													// catch block
				e.printStackTrace();
			}
		}
		int diff = numFrame - nLastFrame;
		if (diff == 0) {
			return currentFrame;
		}
		if (diff == 1) {
			currentFrame.copyTo(lastFrame);
			cap.read(currentFrame);
		}
		if (diff > 1) {
			while (diff > 2) {
				cap.grab();
				diff--;
			}
			cap.read(lastFrame);
			cap.read(currentFrame);
		}
		if (diff < 1) {
			cap = new VideoCapture(videoName);
			int i = 0;
			while (i < numFrame - 2) {
				cap.grab();
				i++;
			}
			cap.read(lastFrame);
			cap.read(currentFrame);
		}
		if (diff == -1) {
			lastFrameLoaded.set(1);
			lastFrame.copyTo(currentFrame);
			FindLastFrame t = new FindLastFrame(numFrame);
			t.start();

		}
		nLastFrame = numFrame;
		return currentFrame;
	}

	public void startSeg() {
		cap = new VideoCapture(videoName);
	}

	public Mat getFrameForSeg(int numFrame) {
		cap.read(currentFrame);
		return (currentFrame);
	}

	public void endSeg() {
		cap = new VideoCapture(videoName);
		nLastFrame = -1;
	}

	public ImageIcon getImage(int numImg) {
		ImageIcon image = new ImageIcon(Mat2bufferedImage(this.getFrame(numImg), this.getWidth(), this.getHeight()));
		return image;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getSize() {
		return size;
	}

	/*
	 * public void addFrame(Mat frame) { frames_.add(frame); }
	 */

	/* AFFICHE UNE IMAGE DE LA VIDEO DANS UNE NOUVELLE FENETRE */

	public void displayFrame(int numFrame) { // Highgui.imwrite(imgStr,m);

		JFrame frame = new JFrame("Image " + numFrame + "/" + size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		Mat image2 = new Mat();
		image2 = this.getFrame(numFrame); // Inserts the image icon
		ImageIcon image = new ImageIcon(Mat2bufferedImage(image2, width, height));
		frame.setSize(image.getIconWidth() + 10, image.getIconHeight() + 35); // taille
		// //
		// GUI
		// Draw the Image data into the BufferedImage
		JLabel label1 = new JLabel(" ", image, JLabel.CENTER);
		frame.getContentPane().add(label1);

		frame.validate();
		frame.setVisible(true);
	}

	/* CONVERTIR UNE IMAGE Mat EN BufferedImage */
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
