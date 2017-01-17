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
	boolean valid = false;
	int[] X;
	int[] Y;

	public PlayPause(int numImg, LoadVideo video, JFrame frame, JPanel panelImage, JLabel lImage, JTextField txtNum,
			boolean segmentation, int[][] barycentres, boolean valid, int[] X,int[]Y) {
		super();
		this.numImg = numImg;
		this.video = video;
		this.frame = frame;
		this.panelImage = panelImage;
		this.lImage = lImage;
		this.txtNum = txtNum;
		this.barycentres = barycentres;
		this.segmentation = segmentation;
		this.valid = valid;
		this.X = X;
		this.Y = Y;
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
				// Tracer un cercle rouge représentant le barycentre

				Core.circle(frame2, centre, rayon, color, -1); // -1: rempli le cercle
			}
			
			if (valid == true) {
				int x0=X[0];
				int y0 = Y[0];
				Point P0 = new Point(x0, y0);
				
				int x1 = X[1];
				int y1 = Y[1];
				Point P1 = new Point(x1, y1);
				
				int x2=X[2];
				int y2= Y[2];
				Point P2 = new Point(x2, y2);
				
				int x3 = X[3];
				int y3 = Y[3];
				Point P3 = new Point(x3, y3);
				
				int x4=X[4];
				int y4 = Y[4];
				Point P4 = new Point(x4, y4);
				
				int x5 = X[5];
				int y5 = Y[5];
				Point P5 = new Point(x5, y5);
				
				int x6=X[6];
				int y6 = Y[6];
				Point P6 = new Point(x6, y6);
				
				int x7 = X[7];
				int y7 = Y[7];
				Point P7 = new Point(x7, y7);
				
				Scalar color = new Scalar(0, 0, 255);
				Scalar color2 = new Scalar(0, 255, 0);
				// Tracer une ligne rouge
				
				Core.line(frame2, P0, P1, color,3);
				Core.line(frame2, P2, P3, color2,3);
				Core.line(frame2, P4, P5, color2,3);
				Core.line(frame2, P6, P7, color,3);

				
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
				txtNum.setText("N°" + nbAffiche + "/" + nbimg);
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