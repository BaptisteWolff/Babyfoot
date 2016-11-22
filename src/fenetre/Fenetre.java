package fenetre;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

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

import loadVideo.LoadVideo;

public class Fenetre{

	int numImg=0;
	LoadVideo video;
	JButton bOpen = new JButton("Ouvrir");
	JButton bCommencer = new JButton("Commencer");
	JButton bPlay = new JButton("Play");
	JButton bPause = new JButton("Pause");
	JButton bPrecedent = new JButton("PrÈcÈdent");
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
	public Fenetre() {

		// CrÈation de la fenÍtre et du container

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// Plein ecran
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix rouge
		
		
		JPanel panelPrincipal = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelDroit = new JPanel();
		JPanel panelLecture = new JPanel();
		JPanel panelChemin = new JPanel();
		JPanel panelGroup = new JPanel();

		// DÈfinition du gestionnaire de placement
		panelPrincipal.setLayout(new BorderLayout());
		panelGauche.setLayout(new BorderLayout());
		panelDroit.setLayout(new BorderLayout());
		panelLecture.setLayout(new FlowLayout());
		panelChemin.setLayout(new FlowLayout());

		//panelImage.setLayout(new CardLayout(10,10));

		// CrÈation des composants
		JLabel label = new JLabel("Entrer votre nom");


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
		panelGauche.add(panelGroup,BorderLayout.CENTER);
		panelGauche.add(bCommencer, BorderLayout.SOUTH);



		// Ajout des composants au container Lecture
		panelLecture.add(txtNum);
		panelLecture.add(bPrecedent);
		panelLecture.add(bPlay);
		panelLecture.add(bPause);		
		panelLecture.add(bSuivant);
		// Boutons play non disponible au d√©but
		bPlay.setEnabled(false);
		bPrecedent.setEnabled(false);
		bPause.setEnabled(false);
		bSuivant.setEnabled(false);
		tChemin.setEnabled(false);
		txtNum.setEnabled(false);
		// Definition de la taille des zones de texte
		

		//Ajout des composant au container Image
		// On prend la rÈsolution de l'Ècran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		Dimension dimIm=new Dimension(width*2/3, height*2/3);//Dimension image
		// Taille par dÈfaut lorsqu'on est pas en plein Ècran
		panelImage.setPreferredSize(dimIm);
		panelImage.setMaximumSize(dimIm);
		panelImage.setBorder(BorderFactory.createLineBorder(Color.black));


		// Ajout des composants au container droit
		panelDroit.add(panelImage, BorderLayout.NORTH);
		panelDroit.add(panelLecture, BorderLayout.CENTER);


		// Ajout des composants au container Principale
		panelPrincipal.add(panelGauche, BorderLayout.WEST);
		panelPrincipal.add(panelDroit, BorderLayout.EAST);


		// Ajout du container ‡ la fenÍtre
		frame.getContentPane().add(panelPrincipal);
		//DÈfinit sa taille : Prend les 4/5 de l'Ècran
	    frame.setSize((int) screenSize.getWidth()*4/5, (int) screenSize.getHeight()*4/5);
	    //Nous demandons maintenant ‡ notre objet de se positionner au centre
	    frame.setLocationRelativeTo(null);
		// Affichage de la fenÍtre
		frame.setVisible(true);		

		Ecouteur listen=new Ecouteur();

		bOpen.addActionListener(listen);
		bSuivant.addActionListener(listen);
		bPrecedent.addActionListener(listen);
		txtNum.addActionListener(listen);
		bPlay.addActionListener(listen);
		bPause.addActionListener(listen);

	}

	public class Ecouteur implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if ((e.getSource()==bOpen) ){
				try 
				{
					// Si FenetreOuvrir() renvoie un fichier vidÈo
					
					JFileChooser ch = new FenetreOuvrir();
					String str = ch.getSelectedFile().getAbsolutePath();
					//System.out.println(str);
					String replacedStr = str.replace('\\', '/'); // Pour avoir un chemin exploitable
					//System.out.println(replacedStr);
					afficherImage(replacedStr);
					int nbimg=video.getSize();
					int nbAffiche=numImg+1;
					tChemin.setText(replacedStr);
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);
					txtNum.setEnabled(true);
					bPlay.setEnabled(true);
					bPrecedent.setEnabled(true);
					bPause.setEnabled(true);
					bSuivant.setEnabled(true);
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
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);
					// On pr√©vient notre JFrame que notre JPanel sera son content pane

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
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);
					// On pr√©vient notre JFrame que notre JPanel sera son content pane

					frame.validate();

				}
			}

			if (e.getSource()==bPause){
				
								
			}
			
			
			if (e.getSource()==bPlay){
				int nbimg=video.getSize();
				int i = numImg;
				int nbAffiche;
				long skipTicks = 1000 / 60;
				
				long nextFrameTick = System.currentTimeMillis();
				int loops;
				while (i < nbimg && e.getSource()!=bPause) {

					numImg=i;
					
					
					nbAffiche=i+1;
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);

					loops = 0;
					nextFrameTick = System.currentTimeMillis();
					long tot = nextFrameTick+skipTicks;
					while (System.currentTimeMillis() < tot && e.getSource()!=bPause && loops<10000) {
						System.out.println(System.currentTimeMillis() + " / "+tot+" / "+loops+'\n');
						ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
						lImage.setIcon(image);
						panelImage.add(lImage);
						panelImage.repaint();
						frame.validate();
						loops++;
					}
					
					
					
					
					System.out.println(i+"  "+numImg+'\n');
					i++;
				}

			}


			if (e.getSource()==txtNum){
				int nbimg=video.getSize();
				String ch=txtNum.getText();
				int numEntre;
				
				try{
					numEntre = Integer.parseInt(ch);
					System.out.println(numEntre);
				} catch (NumberFormatException e2) {
					// Si l'utilisateur n'entre pas un nombre entier
					System.out.println("Invalid number");
					numEntre=-1;
				}
				

				// Lorsque l'utilisateur entre une image prÈcise

				if (numEntre>0 && numEntre<=nbimg){
					numImg=numEntre-1;

					ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));

					lImage.setIcon(image);

					panelImage.add(lImage);

					panelImage.repaint();
					int nbAffiche=numImg+1;
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);

					frame.validate();
				} else
				{
					int nbAffiche=numImg+1;
					txtNum.setText("N∞"+nbAffiche+"/"+nbimg);
				}
			}
		}
	}


	public void afficherImage(String chemin) {

		numImg=0;
		//System.out.println("le chemin est "+chemin);
		VideoCapture cap = new VideoCapture(chemin);
		//System.out.println(cap + " : " + cap.isOpened());

		if (cap.isOpened()) {
			System.out.println("Success to open file");
			video = new LoadVideo(cap);
			ImageIcon image = new ImageIcon(Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
			Image img =Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight());
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
