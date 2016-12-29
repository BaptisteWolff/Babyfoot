package fenetre;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import loadVideo.LoadVideo;

public class PlayPause extends Thread {
	boolean running = true;
	int numImg = 0;
	LoadVideo video;
	JFrame frame = new JFrame("Baby-Foot");
	JPanel panelImage = new JPanel();
	JLabel lImage = new JLabel();
	JTextField txtNum = new JTextField();
	boolean segmentation = false;
	int[][] barycentres = { { 0 }, { 0 } };

	public PlayPause(int numImg, LoadVideo video, JFrame frame, JPanel panelImage, JLabel lImage, JTextField txtNum,
			boolean segmentation, int[][] barycentres) {
		super();
		this.numImg = numImg;
		this.video = video;
		this.frame = frame;
		this.panelImage = panelImage;
		this.lImage = lImage;
		this.txtNum = txtNum;
		this.barycentres = barycentres;
		this.segmentation = segmentation;
	}

	public void arret() {
		running = false;
	}

	@Override
	public void run() {
		int nbimg = video.getSize();
		int i = numImg;
		int nbAffiche;
		long skipTicks = 1000 / 60; // 60 fps

		long nextFrameTick = System.currentTimeMillis();
		int loops;
		while (i < nbimg && running) {
			
			loops = 0;
			nextFrameTick = System.currentTimeMillis();
			long tot = nextFrameTick + skipTicks;
			Mat frame2 = new Mat();
			frame2 = video.getFrame(i);
			if (segmentation == true) {
				int x = barycentres[0][i];
				int y = barycentres[1][i];
				Point centre = new Point(x, y);
				int rayon = 10;
				Scalar color = new Scalar(0, 0, 255);
				// Tracer un cercle rouge repr�sentant le barycentre

				Core.circle(frame2, centre, rayon, color, -1); // -1: rempli le cercle
			}
			ImageIcon image = new ImageIcon(Mat2bufferedImage(frame2, video.getWidth(), video.getHeight()));
			while (System.currentTimeMillis() < tot && loops < 10000 && running) {

				lImage.setIcon(image);
				panelImage.add(lImage);
				panelImage.repaint();
				frame.validate();
				loops++;
				numImg = i;
				nbAffiche = numImg + 1;
				txtNum.setText("N�" + nbAffiche + "/" + nbimg);
			}
			
			i++;

		}
		if (i == nbimg) {
			running = false;
		}
		
	}
	// http://www.codeproject.com/Tips/752511/How-to-Convert-Mat-to-BufferedImage-Vice-Versa

	public int getNumImg() {
		return numImg;
	}

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