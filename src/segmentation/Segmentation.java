package segmentation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Segmentation{
	private Mat hsv;
	public Segmentation(Mat img){
		hsv=new Mat();
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(img, hsv, Imgproc.COLOR_RGB2HSV);
		Highgui.imwrite("C:/Users/Maxime Berthet/Pictures/hsv4.jpg",hsv);
		Imgproc.threshold(hsv, hsv, 175, 255, Imgproc.THRESH_TOZERO);
		Highgui.imwrite("C:/Users/Maxime Berthet/Pictures/hsv2.jpg",hsv);
		Imgproc.threshold(hsv, hsv, 195, 255, Imgproc.THRESH_TOZERO_INV);
		Highgui.imwrite("C:/Users/Maxime Berthet/Pictures/hsv.jpg",hsv);
		
	}

}
