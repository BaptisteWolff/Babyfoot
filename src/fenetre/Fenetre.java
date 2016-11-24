package fenetre;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import loadVideo.LoadVideo;

public class Fenetre{

	int numImg=0;
	int c=0;
	LoadVideo video;
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

		
		Ecouteur listen=new Ecouteur(numImg,c,barycentres,videoSeg,video,bOpen,bCommencer,bPlay,bselect,bselect2,bPause,bPrecedent,bSuivant,tChemin,txtNum,plusmoins,plus,moins,choixGam,
		frame,panelImage,lImage);
		Focus focus=new Focus(txtNum);


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
		panelImage.addMouseListener(new EcouteClic(panelImage));
	}

	// Pour gérer le play/pause, il faut créer une classe héritée de thread.
	
	public class EcouteClic extends MouseAdapter{
        private JPanel pan;
        public int x=0;
        public int y=0;
    	int X[]=new int[4];
    	int Y[]=new int[4];
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
	
	

	
}
