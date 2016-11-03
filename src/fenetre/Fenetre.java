package fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import loadVideo.LoadVideo;

import javax.swing.JFileChooser;

public class Fenetre extends JFrame implements ActionListener {
	private JButton bOpen = new JButton("Ouvrir");
	private JButton bPlay = new JButton("Play");

	public Fenetre() {

		// On prend la r�solution de l'�cran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		// D�finit un titre pour notre fen�tre
		this.setTitle("Baby-Foot");
		// D�finit sa taille : 400 pixels de large et 100 pixels de haut

		// fenetre.setBackground(Color.GRAY);
		// this.setSize(width, height); // Fenetre qui prend tout l'�cran
		// Nous demandons maintenant � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		// Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Plein ecran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		bOpen.addActionListener(this);
		// Affichage des boutons:

		// Bouton ouvrir
		// On ajoute le bouton au content pane de la JFrame

		// On d�finit le layout � utiliser sur le content pane
		GridLayout g1 = new GridLayout();
		g1.setColumns(10);
		g1.setRows(10);
		g1.setHgap(10);
		g1.setVgap(10);
		this.setLayout(g1);

		this.getContentPane().add(bOpen);
		this.getContentPane().add(bPlay);

		// Et enfin, la rendre visible
		this.setVisible(true);
		go();
	}

	private void go() {
		// Cette m�thode ne change pas
	}
	/*
	 * public void mousePressed(MouseEvent event) { //Nous changeons le fond de
	 * notre image pour le jaune lors du clic gauche, avec le fichier
	 * fondBoutonClic.png try { JFileChooser ch=new FenetreOuvrir();
	 * this.afficherImage(ch.getSelectedFile().getAbsolutePath()); }
	 * 
	 * }
	 */

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == bOpen) {
			JFileChooser ch = new FenetreOuvrir();
			System.out.println(ch.getSelectedFile().getAbsolutePath());
			String str = ch.getSelectedFile().getAbsolutePath();
			String replacedStr = str.replace('\\', '/');
			System.out.println(replacedStr);
			afficherImage(str);
		}

	}

	public void afficherImage(String chemin) {
		VideoCapture cap = new VideoCapture(chemin);

		System.out.println(cap.isOpened());
		if (cap.isOpened()) {
			System.out.println("Success");
			LoadVideo video = new LoadVideo(cap);
			System.out.println("ok");
			Image img = Mat2bufferedImage(video.getFrame(20), video.getWidht(), video.getHeight());
			System.out.println("ok2");
			JPanel pan = new Panneau(img);
			// On pr�vient notre JFrame que notre JPanel sera son content pane
			this.setVisible(false);
			this.setContentPane(pan);
			this.setVisible(true);
			this.validate();
		} else {
			System.out.println("Failure");

		}
		// Instanciation d'un objet JPanel

	}

	// http://www.codeproject.com/Tips/752511/How-to-Convert-Mat-to-BufferedImage-Vice-Versa
	public static BufferedImage Mat2bufferedImage(Mat in, int width, int height) {
		BufferedImage out;
		byte[] data = new byte[width * height * (int) in.elemSize()];
		int type;
		in.get(0, 0, data);

		if (in.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;

		out = new BufferedImage(width, height, type);

		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;
	}
}