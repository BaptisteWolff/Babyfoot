package fenetre;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import loadVideo.LoadVideo;

public class PlayPause extends Thread {
	boolean running = true;
	int numImg = 0;
	int[][] barycentres;
	boolean videoSeg;
	LoadVideo video;
	JFrame frame = new JFrame("Baby-Foot");
	JPanel panelImage = new JPanel();
	JLabel lImage = new JLabel();
	JTextField txtNum = new JTextField();

	public PlayPause(int numImg, int[][] barycentres, boolean videoSeg, LoadVideo video, JFrame frame,
			JPanel panelImage, JLabel lImage, JTextField txtNum) {
		super();
		this.numImg = numImg;
		this.barycentres = barycentres;
		this.videoSeg = videoSeg;
		this.video = video;
		this.frame = frame;
		this.panelImage = panelImage;
		this.lImage = lImage;
		this.txtNum = txtNum;
	}

	public void arret() {
		running = false;
	}

	@Override
	public void run() {
		if (videoSeg == false) {

			int nbimg = video.getSize();
			int i = numImg;
			int nbAffiche;
			long skipTicks = 1000 / 60; // 60 fps

			long nextFrameTick = System.currentTimeMillis();
			int loops;
			while (i < nbimg && running) {

				numImg = i;

				loops = 0;
				nextFrameTick = System.currentTimeMillis();
				long tot = nextFrameTick + skipTicks;
				while (System.currentTimeMillis() < tot && loops < 10000 && running) {
					ImageIcon image = new ImageIcon(
							Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
					lImage.setIcon(image);
					panelImage.add(lImage);
					panelImage.repaint();
					frame.validate();
					loops++;
					nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);
				}
				numImg++;
				i++;
			}
		} else {
			int nbimg = video.getSize();
			int i = numImg;
			int nbAffiche;
			long skipTicks = 1000 / 60; // 60 fps

			long nextFrameTick = System.currentTimeMillis();
			int loops;

			int X[] = barycentres[0];
			int Y[] = barycentres[1];
			int x = 0;
			int y = 0;

			Mat frame2 = new Mat();

			double[] data = { 0, 0, 255 };

			while (i < nbimg && running) {

				numImg = i;

				loops = 0;
				x = X[i];
				y = Y[i];
				nextFrameTick = System.currentTimeMillis();
				frame2 = video.getFrame(i);
				long tot = nextFrameTick + skipTicks;

				for (int j = 0; j < 20; j++) {

					if (j + y - 10 <= video.getHeight() && y + j - 10 >= 0) {
						frame2.put(y + j - 10, x, data);
					}
				}
				for (int j = 0; j < 20; j++) {
					if (j + x - 10 <= video.getWidth() && x + j - 10 >= 0) {
						frame2.put(y, x + j - 10, data);
					}
				}
				ImageIcon image = new ImageIcon(Mat2bufferedImage(frame2, video.getWidth(), video.getHeight()));

				while (System.currentTimeMillis() < tot && loops < 10000 && running) {
					lImage.setIcon(image);
					panelImage.add(lImage);
					panelImage.repaint();
					frame.validate();
					loops++;
					nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);
				}
				numImg++;

				i++;
			}

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