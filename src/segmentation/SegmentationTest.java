package segmentation;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import loadVideo.LoadVideo;

public class SegmentationTest {
	public static void main(String[] args) {
		System.loadLibrary("opencv_java2413");
		// Test sur une image

		Mat img=Highgui.imread("D:/Users/Baptiste/Pictures/testImage1.png");
		//Segmentation test=new Segmentation(img, 95, 193, 173);
		HSV hsv=new HSV(img, 842, 354);
		int h,s,v;
		h=hsv.getH();
		s=hsv.getS();
		v=hsv.getV();
		
		// test sur une vidéo
		VideoCapture cap = new VideoCapture("D:/Users/Baptiste/Pictures/S2ButDroite.MP4");
		LoadVideo video = new LoadVideo(cap);
		
		int sizeVideo = video.getSize();
		int X[]=new int[sizeVideo];
		int Y[]=new int[sizeVideo];
		double tpsSys = System.currentTimeMillis();
		for (int i = 0; i < sizeVideo; i++) {
			System.out.println("Segmentation de l'image " + (i + 1) + "/" + sizeVideo);
			Segmentation test = new Segmentation(video.getFrame(i), h, s, v);
			X[i]=test.getX_();
			Y[i]=test.getY_();
		}
		int[][] barycentres={X,Y};
		tpsSys = System.currentTimeMillis() - tpsSys;
		System.out.println("Temps utilisé pour réaliser la segmentation : " + tpsSys + " ms");
		video.displayVideoBarycentres(30, 0, barycentres);

	}
}
