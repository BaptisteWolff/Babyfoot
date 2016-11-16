package segmentation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Segmentation {
	private Mat frameOut_;
	private int x_ = 0, y_ = 0;

	public Segmentation(Mat frame) {

		Mat blurredImage = new Mat();
		Mat hsvImage = new Mat();
		Mat mask = new Mat();
		Mat morphOutput = new Mat();
		/*Mat morphOutput2 = new Mat();
		Mat morphOutput3 = new Mat();
		Mat morphOutput4 = new Mat();
		Mat morphOutput5 = new Mat();*/
		Mat frameOut = new Mat();

		frame.copyTo(frameOut);

		// remove some noise
		Imgproc.blur(frame, blurredImage, new Size(7, 7));

		// convert the frame to HSV
		Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		// threshold HSV image
		Imgproc.threshold(hsvImage, mask, 180, 255, Imgproc.THRESH_TOZERO);
		Imgproc.threshold(mask, mask, 215, 255, Imgproc.THRESH_TOZERO_INV);

		// morphological operators

		// On fait une ouverture pour supprimer les parasites plus petits que la
		// balle.

		int r = 9;

		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r, r));
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r, r));

		Imgproc.erode(mask, morphOutput, erodeElement);
		Imgproc.dilate(morphOutput, morphOutput, dilateElement);

		/*// On fait une ouverture pour supprimer la balle.
		int r2 = 10;

		// Mat erodeElement2 =
		// Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r2,
		// r2));
		// Mat dilateElement2 =
		// Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r2,
		// r2));

		Mat erodeElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2, r2));
		Mat dilateElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2, r2));

		Imgproc.erode(mask, morphOutput2, erodeElement2);
		Imgproc.dilate(morphOutput2, morphOutput2, dilateElement2);

		// on veut récupérer la balle mais il reste des élément de la même
		// taille que celle ci.
		Core.subtract(morphOutput, morphOutput2, morphOutput3);

		Mat erodeElement3 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2 / 2, 1.2 * r2));
		Mat dilateElement3 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2 / 2, 1.2 * r2));

		Imgproc.dilate(morphOutput3, morphOutput3, dilateElement3);
		Imgproc.erode(morphOutput3, morphOutput3, erodeElement3);
		Imgproc.erode(morphOutput3, morphOutput4, erodeElement3);
		Imgproc.dilate(morphOutput4, morphOutput4, dilateElement3);

		Core.subtract(morphOutput3, morphOutput4, morphOutput5);

		Imgproc.erode(morphOutput5, morphOutput5, erodeElement);
		Imgproc.dilate(morphOutput5, morphOutput5, dilateElement);*/

		int sommeX = 0;
		int sommeY = 0;
		int nbPixels = 0;

		for (int x = 0; x < morphOutput.width(); x++) {
			for (int y = 0; y < morphOutput.height(); y++) {

				if (morphOutput.get(y, x)[1] != 0.0) {
					sommeX += x;
					sommeY += y;
					nbPixels++;
					// Modifier la copie de l'image originale
					double[] data = frameOut.get(y, x);
					data[0] = 0;
					data[1] = 0;
					data[2] = 0;
					frameOut.put(y, x, data);
				}
			}
		}

		if (nbPixels == 0) {
			nbPixels = 1;
		}

		// On récupère les barycentres
		double X = sommeX / nbPixels;
		double Y = sommeY / nbPixels;

		x_ = (int) X;
		y_ = (int) Y;

		// System.out.println("x = " + X + "; y = " + Y);

		// Test

		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/blurredImage.jpg",
		// blurredImage);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/hsvImage.jpg",
		// hsvImage);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/mask.jpg",
		// mask);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/morphOutput.jpg", morphOutput);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/morphOutput2.jpg",
		// morphOutput2);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/morphOutput3.jpg",
		// morphOutput3);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/morphOutput4.jpg",
		// morphOutput4);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/morphOutput5.jpg", morphOutput5);
		// Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/frameOut.jpg", frameOut);

		frameOut_ = frameOut;
	}

	public Mat getFrameOut_() {
		return frameOut_;
	}

	public int getX_() {
		return x_;
	}

	public int getY_() {
		return y_;
	}

}
