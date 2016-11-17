package segmentation;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import loadVideo.LoadVideo;

public class SegmentationTest {
	public static void main(String[] args) {
		System.loadLibrary("opencv_java2413");
		// Test sur une image

		
		//Mat img=Highgui.imread("D:/Users/Baptiste/Pictures/testImage1.png");
		//Segmentation test=new Segmentation(img);

		// test sur une vidéo

		System.loadLibrary("opencv_java2413");
		VideoCapture cap = new VideoCapture("D:/Users/Baptiste/Pictures/S2ButDroite.MP4");
		LoadVideo video = new LoadVideo(cap);
		LoadVideo videoSeg = new LoadVideo(video.getHeight(), video.getWidth());
		int sizeVideo = video.getSize();
		double tpsSys = System.currentTimeMillis();
		for (int i = 0; i < sizeVideo; i++) {
			System.out.println("Segmentation de l'image " + i + "/" + sizeVideo);
			Segmentation test = new Segmentation(video.getFrame(i));
			Mat frameOut = test.getFrameOut_();
			videoSeg.addFrame(frameOut);
		}
		tpsSys = System.currentTimeMillis() - tpsSys;
		System.out.println("Temps utilisé pour réaliser la segmentation : " + tpsSys +" ms");
		videoSeg.displayVideo(1);
		//Mat frame=video.getFrame(180);
		//Segmentation test2=new Segmentation(frame);

	}
}
