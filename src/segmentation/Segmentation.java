package segmentation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Segmentation {
	private Mat img_;

	public Segmentation(Mat frame) {
		/*
		 * Mat hsv = new Mat(); Imgproc.cvtColor(img, img,
		 * Imgproc.COLOR_RGB2BGR); Imgproc.cvtColor(img, hsv,
		 * Imgproc.COLOR_RGB2HSV);
		 * Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv4.jpg", hsv);
		 * Imgproc.threshold(hsv, hsv, 175, 255, Imgproc.THRESH_TOZERO);
		 * Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv2.jpg", hsv);
		 * Imgproc.threshold(hsv, hsv, 195, 255, Imgproc.THRESH_TOZERO_INV);
		 * Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv.jpg", hsv);
		 */

		Mat blurredImage = new Mat();
		Mat hsvImage = new Mat();
		Mat mask = new Mat();
		Mat morphOutput = new Mat();
		Mat morphOutput2 = new Mat();
		Mat morphOutput3 = new Mat();
		Mat morphOutput4 = new Mat();
		Mat morphOutput5 = new Mat();
		// remove some noise
		Imgproc.blur(frame, blurredImage, new Size(7, 7));

		// convert the frame to HSV
		Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		// threshold HSV image
		Imgproc.threshold(hsvImage, mask, 180, 255, Imgproc.THRESH_TOZERO);
		Imgproc.threshold(mask, mask, 215, 255, Imgproc.THRESH_TOZERO_INV);

		// morphological operators
		// dilate with large element, erode with small ones
		int r=9;
		
		//Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r, r));
		//Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r, r));
		
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r, r));
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r, r));
		
		Imgproc.erode(mask, morphOutput, erodeElement);
		Imgproc.dilate(morphOutput, morphOutput, dilateElement);
		
		int r2=10;
		
		//Mat erodeElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r2, r2));
		//Mat dilateElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(r2, r2));

		Mat erodeElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2, r2));
		Mat dilateElement2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2, r2));

		
		Imgproc.erode(mask, morphOutput2, erodeElement2);
		Imgproc.dilate(morphOutput2, morphOutput2, dilateElement2);
		
		
		Core.subtract(morphOutput, morphOutput2, morphOutput3);
		
		Mat erodeElement3 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2/2, 1.2*r2));
		Mat dilateElement3 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(r2/2, 1.2*r2));

		Imgproc.dilate(morphOutput3, morphOutput3, dilateElement3);
		Imgproc.erode(morphOutput3, morphOutput3, erodeElement3);
		Imgproc.erode(morphOutput3, morphOutput4, erodeElement3);
		Imgproc.dilate(morphOutput4, morphOutput4, dilateElement3);
		
		Core.subtract(morphOutput3, morphOutput4, morphOutput5);
		
		Imgproc.erode(morphOutput5, morphOutput5, erodeElement);
		Imgproc.dilate(morphOutput5, morphOutput5, dilateElement);
		
		// Test
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test1.jpg", blurredImage);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test2.jpg", hsvImage);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test3.jpg", mask);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test4.jpg", morphOutput);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test5.jpg", morphOutput2);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test6.jpg", morphOutput3);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test7.jpg", morphOutput4);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test8.jpg", morphOutput5);
	}
	

}
