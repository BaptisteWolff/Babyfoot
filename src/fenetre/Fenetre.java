package fenetre;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
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

import loadVideo.LoadVideo;
import segmentation.HSV;
import segmentation.Segmentation;

public class Fenetre{

	int numImg=0;
	int c=1;
	LoadVideo video;
	JButton bOpen = new JButton("Ouvrir");
	JButton bCommencer = new JButton("Commencer");
	JButton bPlay = new JButton("Play");
	JButton bselect = new JButton("centre de la balle");
	JButton bselect2 = new JButton("ligne 1");
	JButton bselect3 = new JButton("ligne 2");
	JButton bselect4 = new JButton("ligne 3");
	JButton bselect5 = new JButton("ligne 4");
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
	int[][] barycentres;
	boolean videoSeg = false;
	//PlayPause p = new PlayPause(numImg,video,frame,panelImage,lImage,txtNum);


	public Fenetre() {
		// Création de la fenêtre et du container

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// Plein ecran
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix rouge


		JPanel panelPrincipal = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelDroit = new JPanel();
		JPanel panelLecture = new JPanel();
		JPanel panelChemin = new JPanel();
		JPanel panelGroup = new JPanel();
		JPanel panelGroup2 = new JPanel();

		// Définition du gestionnaire de placement
		panelPrincipal.setLayout(new BorderLayout());
		panelGauche.setLayout(new BorderLayout());
		panelDroit.setLayout(new BorderLayout());
		panelLecture.setLayout(new FlowLayout());
		panelChemin.setLayout(new FlowLayout());

		//panelImage.setLayout(new CardLayout(10,10));

		// Création des composants
		// JLabel label = new JLabel("Entrer votre nom");


		// Ajout des composants au container chemin
		panelChemin.add(bOpen);
		panelChemin.add(tChemin);

		// Ajout des composants au container gauche
		panelGauche.add(panelChemin, BorderLayout.NORTH);
		choixGam.add(plusmoins);
		choixGam.add(plus);
		choixGam.add(moins);
		panelGroup.add(plusmoins);
		panelGroup.add(plus);
		panelGroup.add(moins);
		panelGroup2.add(bselect2);
		panelGroup2.add(bselect3);
		panelGroup2.add(bselect4);
		panelGroup2.add(bselect5);
		panelGroup2.add(bCommencer);
		panelGroup2.add(bselect);
		panelGauche.add(panelGroup,BorderLayout.CENTER);
		panelGauche.add(panelGroup2, BorderLayout.SOUTH);
		



		// Ajout des composants au container Lecture
		panelLecture.add(txtNum);
		panelLecture.add(bPrecedent);
		panelLecture.add(bPlay);
		panelLecture.add(bPause);		
		panelLecture.add(bSuivant);
		// Boutons play non disponible au début
		bPlay.setEnabled(false);
		bPrecedent.setEnabled(false);
		bPause.setEnabled(false);
		bSuivant.setEnabled(false);
		tChemin.setEnabled(false);
		txtNum.setEnabled(false);
		bselect.setEnabled(false);
		bselect2.setEnabled(false);
		bselect3.setEnabled(false);
		bselect4.setEnabled(false);
		bselect5.setEnabled(false);
		bCommencer.setEnabled(false);
		// Definition de la taille des zones de texte


		//Ajout des composant au container Image
		// On prend la résolution de l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		Dimension dimIm=new Dimension(width*2/3, height*2/3);//Dimension image
		// Taille par défaut lorsqu'on est pas en plein écran
		panelImage.setPreferredSize(dimIm);
		panelImage.setMaximumSize(dimIm);
		panelImage.setBorder(BorderFactory.createLineBorder(Color.black));


		// Ajout des composants au container droit
		panelDroit.add(panelImage, BorderLayout.NORTH);
		panelDroit.add(panelLecture, BorderLayout.CENTER);


		// Ajout des composants au container Principale
		panelPrincipal.add(panelGauche, BorderLayout.WEST);
		panelPrincipal.add(panelDroit, BorderLayout.EAST);


		// Ajout du container à  la fenêtre
		frame.getContentPane().add(panelPrincipal);
		//Définit sa taille : Prend les 4/5 de l'écran
		frame.setSize((int) screenSize.getWidth()*4/5, (int) screenSize.getHeight()*4/5);
		//Nous demandons maintenant à  notre objet de se positionner au centre
		frame.setLocationRelativeTo(null);
		// Affichage de la fenêtre
		frame.setVisible(true);		

		
		Ecouteur listen=new Ecouteur();
		//frame,panelImage,lImage);
		Focus focus=new Focus(txtNum);
		//c=listen.getC();		
		EcouteClic clic =new EcouteClic(panelImage);

		bOpen.addActionListener(listen);
		bSuivant.addActionListener(listen);
		bPrecedent.addActionListener(listen);
		txtNum.addActionListener(listen);
		bPlay.addActionListener(listen);
		bPause.addActionListener(listen);
		bCommencer.addActionListener(listen);
		txtNum.addFocusListener(focus);
		bselect.addActionListener(listen);
		bselect2.addActionListener(listen);
		bselect3.addActionListener(listen);
		bselect4.addActionListener(listen);
		bselect5.addActionListener(listen);
		panelImage.addMouseListener(clic);
		
		
	}

	// Pour gérer le play/pause, il faut créer une classe héritée de thread.
	
	public class EcouteClic extends MouseAdapter{
        private JPanel pan;
        public int x=0;
        public int y=0;
    	int X[]=new int[8];
    	int Y[]=new int[8];
        public EcouteClic(JPanel pan){
            this.pan = pan;
       
        }
        
        public void mouseClicked (MouseEvent e){
        	
        	if (c==0){
        		x = e.getX();
                y = e.getY();
                System.out.println("coordonnées"+e.getX()+","+e.getY());
                Graphics g = pan.getGraphics();
                g.drawOval(e.getX(),e.getY(), 5, 5);
                g.dispose();
                bCommencer.setEnabled(true);
                c++;
        	}
        	
        	if (c==10){
        		X[0]=e.getX();
        		Y[0]=e.getY();
        		c++;
        	}
        	if (c==11){
        		X[1]=e.getX();
        		Y[1]=e.getY();
        		Graphics g = pan.getGraphics();
                g.drawLine(X[0], Y[0], X[1], Y[1]);
                g.dispose();
                
        	}
        	if (c==12){
        		X[2]=e.getX();
        		Y[2]=e.getY();
        		c++;
        	}
        	if (c==13){
        		X[3]=e.getX();
        		Y[3]=e.getY();
        		Graphics g = pan.getGraphics();
                g.drawLine(X[2], Y[2], X[3], Y[3]);
                g.dispose();
                
        	}
        	if (c==14){
        		X[4]=e.getX();
        		Y[4]=e.getY();
        		c++;
        	}
        	if (c==15){
        		X[5]=e.getX();
        		Y[5]=e.getY();
        		Graphics g = pan.getGraphics();
                g.drawLine(X[4], Y[4], X[5], Y[5]);
                g.dispose();
                
        	}
        	if (c==16){
        		X[6]=e.getX();
        		Y[6]=e.getY();
        		c++;
        	}
        	if (c==17){
        		X[7]=e.getX();
        		Y[7]=e.getY();
        		Graphics g = pan.getGraphics();
                g.drawLine(X[6], Y[6], X[7], Y[7]);
                g.dispose();
                
        	}
        
        
        }
        public int getx(){
    		return x;
    	}
        
        public int gety(){
    		return y;
    	}
        
        public int [] getX(){
    		return X;
    	}
        
        public int [] getY(){
    		return Y;
    	}
    }
	public class Ecouteur implements ActionListener{
		
		PlayPause p = new PlayPause(numImg,video,frame,panelImage,lImage,txtNum);

		public void actionPerformed(ActionEvent e){
		if (e.getSource()== bselect){
	    		c=0;
	    		System.out.println("select");
			}
			
			if (e.getSource()== bselect2){
	    		c=10;
	    		System.out.println("select2");
	    		bselect3.setEnabled(true);
			}
			if (e.getSource()== bselect3){
	    		c=12;
	    		System.out.println("select3");
	    		bselect4.setEnabled(true);
			}
			if (e.getSource()== bselect4){
	    		c=14;
	    		System.out.println("select4");
	    		bselect5.setEnabled(true);
			}
			if (e.getSource()== bselect5){
	    		c=16;
	    		System.out.println("select5");
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
//					if (test.getX_()>video.getWidth()*0.4 && test.getX_()<video.getWidth()*0.6)
//					{
//						i++;	// Si la balle se trouve au milieu de l'image, on saute 1 image sur 2 (zone non interessante)
//					}

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
