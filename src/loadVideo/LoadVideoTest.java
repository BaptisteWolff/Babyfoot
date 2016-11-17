package loadVideo;

import org.opencv.highgui.*;

public class LoadVideoTest {

	public static void main(String[] args) {
		System.loadLibrary("opencv_java2413");
		VideoCapture cap = new VideoCapture("D:/Users/Baptiste/Pictures/S2ButDroite.MP4");
		System.out.println(cap.isOpened());
		if (cap.isOpened()) {
			System.out.println("Success");
			LoadVideo video = new LoadVideo(cap);
			video.displayVideo(60,0);
			video.displayFrame(180);
		} else {
			System.out.println("Failure");

		}

	}
}