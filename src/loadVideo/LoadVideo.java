package loadVideo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class LoadVideo {
	private int width;
	private int height;
	private int size = 0;
	VideoCapture cap;
	String videoName;
	int nLastFrame = -1; // Number of the last frame loaded
	int nLastFrameRec = -1; // Number of the last frame recorded and saved in
							// the arraylist frames.
	Mat currentFrame = new Mat();
	ArrayList<Mat> frames = new ArrayList<Mat>(); // Contains the last 50 frames

	// http://stackoverflow.com/questions/17401852/open-video-file-with-opencv-java
	public LoadVideo(String videoName) {
		this.cap = new VideoCapture(videoName);
		this.width = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
		this.height = (int) cap.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
		this.size = (int) cap.get(7); // cap.get(7) retourne le nombre de frames
										// de cap
		this.videoName = videoName;
		System.out.println("video loaded");
	}


	public Mat getFrame(int numFrame) {

		int diff = numFrame - nLastFrame;

		if (diff == 0) {
			return currentFrame;
		}

		if (numFrame <= nLastFrameRec && numFrame >= nLastFrameRec - frames.size()) {
			// Frame already saved in frames
			currentFrame = frames.get(numFrame - (nLastFrameRec - frames.size()));
			return currentFrame;
		}

		if (diff == 1) { // play video
			if (nLastFrameRec + 1 == numFrame) {
				Mat frame = new Mat();
				currentFrame.copyTo(frame);
				frames.add(frame);
				if (numFrame > 49) {
					frames.remove(0);
				}
			}
			cap.read(currentFrame);

		}

		if (diff > 1) {
			while (diff > 49) {
				cap.grab();
				diff--;
			}
			while (diff > 1) {
				Mat frame = new Mat();
				cap.read(frame);
				frames.add(frame);
				if (frames.size() > 49) {
					frames.remove(0);
				}
				diff--;
			}
			cap.read(currentFrame);
		}

		if (diff <= -1) {
			cap = new VideoCapture(videoName);
			int i = 0;
			frames.clear();
			while (i < numFrame - 1) {
				cap.grab();
				if (i > numFrame - 50) {
					Mat frame = new Mat();
					cap.retrieve(frame);
					frames.add(frame);
				}
				i++;
			}
			cap.read(currentFrame);
		}

		nLastFrameRec = numFrame;
		nLastFrame = numFrame;
		return currentFrame;
	}

	public void startSeg(int nbStartFrame) {
		cap = new VideoCapture(videoName);
		for (int i = 0; i < nbStartFrame; i++) {
			cap.grab();
		}
	}

	public Mat getFrameForSeg() {
		cap.read(currentFrame);
		return (currentFrame);
	}

	public void endSeg() {
		cap = new VideoCapture(videoName);
		nLastFrame = -1;
		nLastFrameRec = -1;
		frames.clear();
	}

	public ImageIcon getImage(int numImg) {
		ImageIcon image = new ImageIcon(Mat2bufferedImage(this.getFrame(numImg), this.getWidth(), this.getHeight()));
		return image;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getSize() {
		return size;
	}

	/* CONVERTIR UNE IMAGE Mat EN BufferedImage */
	// http://www.codeproject.com/Tips/752511/How-to-Convert-Mat-to-BufferedImage-Vice-Versa

	public static BufferedImage Mat2bufferedImage(Mat in, int width, int height) {
		BufferedImage out;
		Mat in2 = new Mat();
		Imgproc.cvtColor(in, in2, Imgproc.COLOR_RGB2BGR);
		byte[] data = new byte[width * height * (int) in2.elemSize()];
		int type;
		in2.get(0, 0, data);

		if (in2.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;

		out = new BufferedImage(width, height, type);

		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;

	}
}
