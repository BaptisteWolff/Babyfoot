package segmentation;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Segmentation {
	private Mat img_;

	public Segmentation(Mat frame) {
		/*Mat hsv = new Mat();
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(img, hsv, Imgproc.COLOR_RGB2HSV);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv4.jpg", hsv);
		Imgproc.threshold(hsv, hsv, 175, 255, Imgproc.THRESH_TOZERO);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv2.jpg", hsv);
		Imgproc.threshold(hsv, hsv, 195, 255, Imgproc.THRESH_TOZERO_INV);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv.jpg", hsv);*/
		
		Mat blurredImage = new Mat();
		Mat hsvImage = new Mat();
		Mat mask = new Mat();
		Mat morphOutput = new Mat();

		// remove some noise
		Imgproc.blur(frame, blurredImage, new Size(7, 7));

		// convert the frame to HSV
		Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		// threshold HSV image
		Imgproc.threshold(hsvImage, mask, 180, 255, Imgproc.THRESH_TOZERO);
		Imgproc.threshold(mask, mask, 200, 255, Imgproc.THRESH_TOZERO_INV);
		
		// morphological operators
		// dilate with large element, erode with small ones
		 Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
		 Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

		 Imgproc.erode(mask, morphOutput, erodeElement);
		
		//Test
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test1.jpg", blurredImage);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test2.jpg", hsvImage);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test3.jpg", mask);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/BigMaccadam/test4.jpg", morphOutput);
	}

}
