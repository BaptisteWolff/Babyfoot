package segmentation;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HSV {
	// Detect the levels of hsv on a pixel of an rgb image converted to hsv.

	private int h, s, v;

	public HSV(Mat frame, int x, int y) {
		Mat hsvImage = new Mat();

		// convert the frame to HSV
		Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		double[] data = hsvImage.get(y, x);
		h = (int) data[0];
		s = (int) data[1];
		v = (int) data[2];
	}

	public int getH() {
		return h;
	}

	public int getS() {
		return s;
	}

	public int getV() {
		return v;
	}

}
