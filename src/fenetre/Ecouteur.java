package fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import fenetre.PlayPause;
import loadVideo.LoadVideo;
import segmentation.HSV;
import segmentation.Segmentation;

public class Ecouteur implements ActionListener{
	int numImg=0;
	int c=0;
	LoadVideo video;
	boolean videoSeg = false;
	JButton bOpen = new JButton("Ouvrir");
	JButton bCommencer = new JButton("Commencer");
	JButton bPlay = new JButton("Play");
	JButton bselect = new JButton("Selectionner le centre de la balle");
	JButton bselect2 = new JButton("Selectionner les lignes");
	JButton bPause = new JButton("Pause");
	JButton bPrecedent = new JButton("Précédent");
	JButton bSuivant = new JButton("Suivant");
	JTextField tChemin = new JTextField("Chemin");
	JTextField txtNum = new JTextField();
	JRadioButton plusmoins= new JRadioButton("Plus 1 moins 1");
	JRadioButton plus= new JRadioButton("Plus 1");
	JRadioButton moins= new JRadioButton("Moins 1");
	ButtonGroup choixGam = new ButtonGroup();
	JFrame frame = new JFrame("Baby-Foot");
	JPanel panelImage = new JPanel();
	JLabel lImage = new JLabel();
	
	PlayPause p = new PlayPause(numImg,video,frame,panelImage,lImage,txtNum);
	
	public Ecouteur(int numImg, int c,int[][] barycentres, boolean videoSeg, LoadVideo video, JButton bOpen, JButton bCommencer, JButton bPlay,
			JButton bselect, JButton bselect2, JButton bPause, JButton bPrecedent, JButton bSuivant, JTextField tChemin,
			JTextField txtNum, JRadioButton plusmoins, JRadioButton plus, JRadioButton moins, ButtonGroup choixGam,
			JFrame frame, JPanel panelImage, JLabel lImage) {
		super();
		this.numImg = numImg;
		this.c = c;
		this.videoSeg = videoSeg;
		this.video = video;
		this.bOpen = bOpen;
		this.bCommencer = bCommencer;
		this.bPlay = bPlay;
		this.bselect = bselect;
		this.bselect2 = bselect2;
		this.bPause = bPause;
		this.bPrecedent = bPrecedent;
		this.bSuivant = bSuivant;
		this.tChemin = tChemin;
		this.txtNum = txtNum;
		this.plusmoins = plusmoins;
		this.plus = plus;
		this.moins = moins;
		this.choixGam = choixGam;
		this.frame = frame;
		this.panelImage = panelImage;
		this.lImage = lImage;
		
	}



	public void actionPerformed(ActionEvent e){
	if (e.getSource()== bselect){
    		c=0;
    		System.out.println("select");
		}
		
		if (e.getSource()== bselect2){
    		c=10;
    		System.out.println("select2");
		}
		
		if ((e.getSource()==bOpen) ){
			try 
			{
				// Si FenetreOuvrir() renvoie un fichier vidéo

				JFileChooser ch = new FenetreOuvrir();
				String str = ch.getSelectedFile().getAbsolutePath();
				//System.out.println(str);
				String replacedStr = str.replace('\\', '/'); // Pour avoir un chemin exploitable
				//System.out.println(replacedStr);
				afficherImage(replacedStr);
				int nbimg=video.getSize();
				int nbAffiche=numImg+1;
				tChemin.setText(replacedStr);
				txtNum.setText("N°"+nbAffiche+"/"+nbimg);
				txtNum.setEnabled(true);
				bPlay.setEnabled(true);
				bPrecedent.setEnabled(true);
				bPause.setEnabled(true);
				bSuivant.setEnabled(true);
				bCommencer.setEnabled(true);
				bselect.setEnabled(true);
				bselect2.setEnabled(true);
				frame.validate();

			} catch (NullPointerException e3) {
				// Si le fichier n'est pas une video ou que l'utilisateur appuie sur Annuler
				// FenetreOuvrir() renvoie null
				return;
			}
		}
		if (e.getSource()==bPrecedent){
			int nbimg=video.getSize();
			if (numImg>0){
				numImg--;

				ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
				lImage.setIcon(image);
				panelImage.add(lImage);
				panelImage.repaint();
				int nbAffiche=numImg+1;
				txtNum.setText("N°"+nbAffiche+"/"+nbimg);

				frame.validate();

			}
		}
		if (e.getSource()==bSuivant){
			int nbimg=video.getSize();
			if (numImg<nbimg-1){
				numImg++;

				ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
				lImage.setIcon(image);
				panelImage.add(lImage);
				panelImage.repaint();
				int nbAffiche=numImg+1;
				txtNum.setText("N°"+nbAffiche+"/"+nbimg);

				frame.validate();

			}
		}

		if (e.getSource()==bPlay){
			if (!p.isAlive())
			{
				p = new PlayPause(numImg,video,frame,panelImage,lImage,txtNum);	
				//System.out.println("play: "+numImg);
				p.start();				
						
			}
		}

		if (e.getSource()==bPause){
			p.arret();
			//System.out.println("paused");		
			numImg=p.getNumImg();
		}

		if (e.getSource()==txtNum){
			txtNum.selectAll();
			int nbimg=video.getSize();
			String ch=txtNum.getText();
			int numEntre;

			try{
				numEntre = Integer.parseInt(ch);
				//System.out.println(numEntre);
			} catch (NumberFormatException e2) {
				// Si l'utilisateur n'entre pas un nombre entier
				System.out.println("Invalid number");
				numEntre=-1;
			}


			// Lorsque l'utilisateur entre une image précise

			if (numEntre>0 && numEntre<=nbimg){
				numImg=numEntre-1;

				ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));

				lImage.setIcon(image);

				panelImage.add(lImage);

				panelImage.repaint();
				int nbAffiche=numImg+1;
				txtNum.setText("N°"+nbAffiche+"/"+nbimg);

				frame.validate();
			} else
			{
				int nbAffiche=numImg+1;
				txtNum.setText("N°"+nbAffiche+"/"+nbimg);
			}
		}
		
		if (e.getSource() == bCommencer) {
			numImg=0;
			System.out.println("Début segmentation");
			Mat img = Highgui.imread("Capture.png");
			Segmentation test = new Segmentation(img, 95, 193, 173);
			HSV hsv = new HSV(img, 1247, 593);	//coordonnées du ballon sur l'image référence
			int h, s, v;
			h = hsv.getH();
			s = hsv.getS();
			v = hsv.getV();

			// test sur une vidéo

			int nbImg = video.getSize();
			// barycentres=new int[sizeVideo][sizeVideo];
			int x = 0;
			int y = 0;
			
			Mat frame2 = new Mat();

			double[] data = { 0, 0, 255 };
			double tpsSys = System.currentTimeMillis();
			for (int i = 0; i < nbImg; i++) {
				frame2 = video.getFrame(i);
				System.out.println("Segmentation de l'image " + (i + 1) + "/" + nbImg);
				test = new Segmentation(video.getFrame(i), h, s, v);
				x = test.getX_();
				y = test.getY_();
//				if (test.getX_()>video.getWidth()*0.4 && test.getX_()<video.getWidth()*0.6)
//				{
//					i++;	// Si la balle se trouve au milieu de l'image, on saute 1 image sur 2 (zone non interessante)
//				}

				Point centre=new Point(x,y);
				int rayon = 10;
				Scalar color = new Scalar (0,0,255);
				// Tracer un cercle rouge représentant le barycentre
				
				Core.circle(frame2, centre, rayon, color, -1); // -1: rempli le cercle
				
								
			}
			
			
			tpsSys = System.currentTimeMillis() - tpsSys;
			System.out.println("Temps utilisé pour réaliser la segmentation : " + tpsSys + " ms");
			videoSeg = true;
			ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
			
			lImage.setIcon(image);
			panelImage.add(lImage);
			int nbAffiche=numImg+1;
			txtNum.setText("N°"+nbAffiche+"/"+nbImg);
			frame.validate();
			
			bCommencer.setEnabled(false);
		}
	}
	public int getC() {
		return c;
	}
	
	public void afficherImage(String chemin) {

		numImg=0;
		
		if(lImage.getMaximumSize().getHeight()>0 && lImage.getMaximumSize().getWidth()>0) // Si on a déjà ouvert une video auparavant
		{
			video.libererVideo();	// on libère la mémoire
			lImage.removeAll();
			System.out.println("video deleted");
		}
		VideoCapture cap = new VideoCapture(chemin);
		//System.out.println(cap + " : " + cap.isOpened());

		if (cap.isOpened()) {
			System.out.println("Success to open file");
			video = new LoadVideo(cap);
			ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
			//Image img =Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight());
			lImage.setIcon(image);
			panelImage.add(lImage);

			frame.validate();
		} else {
			System.out.println("Failure to open file");

		}
		// Instanciation d'un objet JPanel


	}
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
