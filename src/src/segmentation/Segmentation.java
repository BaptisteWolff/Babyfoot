package segmentation;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static java.lang.Math.*;

public class Segmentation {
	private int x_ = 0, y_ = 0;

	public Segmentation(Mat frame, double h, double s, double v) {

		Mat hsvImage = new Mat();

		// convert the frame to HSV
		Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_RGB2BGR);
		Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_BGR2HSV);

		int sommeX = 0;
		int sommeY = 0;
		int nbPixels = 0;

		int height = frame.height();
		int width = frame.width();

		// On parcours tous les pixels de l'image pour détecter la balle
		boolean colBall =false;
		boolean derniereCol =false;
		boolean balldr=false;
		boolean ballga=false;		
		int moit=(int)width/2;
		
		for (int x = 0; x < moit; x++) {
			for (int y = 0; y < height; y++) {
				boolean condition1=false;
				boolean condition2=false;
				int xg = moit-x;
				int xd = moit+x;
				
				if (!ballga && xg>=0)
				{
					condition1 = 
							(hsvImage.get(y, xd)[0] > max(0, h - 10)) && (hsvImage.get(y, xd)[0] < min(255, h + 5))
							&& (hsvImage.get(y, xd)[1] > max(0, s - 60)) && (hsvImage.get(y, xd)[1] < min(255, s + 50))
							&& (hsvImage.get(y, xd)[2] > max(0, v - 60)) && (hsvImage.get(y, xd)[2] < min(255, v + 50));
				}
				if (!balldr && xd<width)
				{
					condition2 = 
							(hsvImage.get(y, xg)[0] > max(0, h - 10)) && (hsvImage.get(y, xg)[0] < min(255, h + 5))
							&& (hsvImage.get(y, xg)[1] > max(0, s - 60)) && (hsvImage.get(y, xg)[1] < min(255, s + 50))
							&& (hsvImage.get(y, xg)[2] > max(0, v - 60)) && (hsvImage.get(y, xg)[2] < min(255, v + 50));
				}
				
				if (condition1) { // si on detecte un point de la balle
					sommeX += xd;
					sommeY += y;
					nbPixels++;
					colBall=true;
					balldr=true;
				}
				if (condition2) { // si on detecte un point de la balle
					sommeX += xg;
					sommeY += y;
					nbPixels++;
					colBall=true;
					ballga=true;
				}
			}
			if (colBall)
			{
				derniereCol=true;
			}
			if (!colBall && derniereCol)
			{
				break;
			}
			colBall=false;
		}

		if (nbPixels == 0) {
			nbPixels = 1;
		}

		// On récupère les barycentres
		int X = (int) (sommeX / nbPixels);
		int Y = (int) (sommeY / nbPixels);
		x_ = X;
		y_ = Y;
		
		hsvImage.release();
	}

	public int getX_() {
		return x_;
	}

	public int getY_() {
		return y_;
	}

}
