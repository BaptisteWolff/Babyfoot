package loadVideo;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class LoadVideo {
	private int width;
	private int height;
	private int size = 0;
	VideoCapture cap;
	String videoName;
	int nLastFrame = -1; // Number of the last frame loaded
	int nLastFrameRec = -1; // Number of the last frame recorded and saved in
							// the arraylist frames.
	Mat currentFrame;
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
		nLastFrame = numFrame;

		if (numFrame <= nLastFrameRec && numFrame >= nLastFrameRec - frames.size() + 1) {
			// Frame already saved in frames
			return frames.get(numFrame - (nLastFrameRec - frames.size() + 1));
		}

		if (diff == 1) { // play video
			currentFrame = new Mat();
			cap.read(currentFrame);
			frames.add(currentFrame);
			if (numFrame > 49) {
				frames.remove(0);
			}
		}

		if (diff > 1) {
			while (diff > 49) {
				cap.grab();
				diff--;
			}
			while (diff > 0) {
				currentFrame = new Mat();
				cap.read(currentFrame);
				frames.add(currentFrame);
				if (frames.size() > 49) {
					frames.remove(0);
				}
				diff--;
			}
		}

		if (diff <= -1) {
			cap = new VideoCapture(videoName);
			int i = 0;
			frames.clear();
			while (i <= numFrame) {
				cap.grab();
				if (i > numFrame - 49) {
					currentFrame = new Mat();
					cap.retrieve(currentFrame);
					frames.add(currentFrame);
				}
				i++;
			}
		}

		nLastFrameRec = numFrame;
		return currentFrame;
	}

	public void startSeg(int nbStartFrame) {
		currentFrame = new Mat();
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
		cap.release();
		currentFrame.release();
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

}
