package segmentation;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Segmentation{
	private Mat imgSeg_;
	public Segmentation(Mat img){
		Mat hsv=new Mat();
		Imgproc.cvtColor(img, hsv, Imgproc.COLOR_RGB2HSV);
		double threshValue;
		Imgproc.threshold(hsv.get(0), hsv, threshValue, 179.0, Imgproc.THRESH_BINARY_INV);
		Highgui.imwrite("D:/Users/Baptiste/Pictures/hsv.jpg",hsv);
		
	}

}
