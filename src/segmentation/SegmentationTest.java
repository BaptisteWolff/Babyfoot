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

		Mat img=Highgui.imread("Capture.png");
		Segmentation test;
		HSV hsv=new HSV(img, 1247, 593);
		int h,s,v;
		h=hsv.getH();
		s=hsv.getS();
		v=hsv.getV();
		
		// test sur une vidéo
		VideoCapture cap = new VideoCapture("S3GammelleDroite.MP4");
		LoadVideo video = new LoadVideo(cap);
		
		int sizeVideo = video.getSize();
		int X[]=new int[sizeVideo];
		int Y[]=new int[sizeVideo];
		double tpsSys = System.currentTimeMillis();
		for (int i = 0; i < sizeVideo; i++) {
			System.out.println("Segmentation de l'image " + (i + 1) + "/" + sizeVideo + " - " + (int)(i+1)*100/sizeVideo + " %");
			test = new Segmentation(video.getFrame(i), h, s, v);
			X[i]=test.getX_();
			Y[i]=test.getY_();
//			if (test.getX_()>video.getWidth()*0.4 && test.getX_()<video.getWidth()*0.6)
//			{
//				i++;	// Si la balle se trouve au milieu de l'image, on saute 1 image sur 2 (zone non interessante)
//			}
		}
		int[][] barycentres={X,Y};
		tpsSys = System.currentTimeMillis() - tpsSys;
		System.out.println("Temps utilisé pour réaliser la segmentation : " + tpsSys + " ms");
		video.displayVideoBarycentres(30, 0, barycentres);

	}
}
