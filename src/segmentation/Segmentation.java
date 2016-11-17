package segmentation;

import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import static java.lang.Math.*;

public class Segmentation {
	private int x_ = 0, y_ = 0;

	public Segmentation(Mat frame, double h, double s, double v) {

		Mat hsvImage = new Mat();

		// convert the frame to HSV
		Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		int sommeX = 0;
		int sommeY = 0;
		int nbPixels = 0;

		int height = frame.height();
		int width = frame.width();

		// On parcours tous les pixels de l'image pour détecter la balle
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((hsvImage.get(y, x)[0] > max(0, h - 20)) && (hsvImage.get(y, x)[0] < min(255, h + 20))
						&& (hsvImage.get(y, x)[1] > max(0, s - 30)) && (hsvImage.get(y, x)[1] < min(255, s + 40))
						&& (hsvImage.get(y, x)[2] > max(0, v - 70)) && (hsvImage.get(y, x)[2] < min(255, v + 80))) {
					sommeX += x;
					sommeY += y;
				}
			}
		}

		if (nbPixels == 0) {
			nbPixels = 1;
		}

		// On récupère les barycentres
		int X = (int) (sommeX / nbPixels);
		int Y = (int) (sommeY / nbPixels);

		x_ = X;
		y_ = Y;

		// ***** Test ********

		// double[] data2 = hsvImage.get(Y, X);
		// System.out.println(""+data2[0]+" "+data2[1]+" "+data2[2]);

		// System.out.println("x = " + X + "; y = " + Y);

		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/hsvImage.jpg",
		// hsvImage);
	}

	public int getX_() {
		return x_;
	}

	public int getY_() {
		return y_;
	}

}
