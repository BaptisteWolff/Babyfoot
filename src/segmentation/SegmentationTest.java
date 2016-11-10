package segmentation;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class SegmentationTest {
	public static void main(String[] args) {
		System.loadLibrary("opencv_java2413");
		Mat img=Highgui.imread("D:/Users/Baptiste/Pictures/testImage1.png");
		new Segmentation(img);
	}
}
