package fenetre;

import java.awt.BasicStroke;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import events.Events;
import events.Player;
import loadVideo.LoadVideo;
import sauvegarde.Sauvegarde;
import segmentation.HSV;
import segmentation.Segmentation;

public class Fenetre {
	boolean segmentation = false;
	int regle = 0;
	int numImg = 0;
	int imgRefBall;
	int xBall, yBall;
	int c = 1;
	int X[] = new int[8];
	int Y[] = new int[8];
	LoadVideo video;
	JButton bOpen = new JButton("Ouvrir");
	JButton bCommencer = new JButton("Commencer");
	JButton bPlay = new JButton("Play");
	JButton bSelectCentre = new JButton("centre de la balle");
	JButton bSelectSGa = new JButton("ligne de sortie Gauche");
	JButton bSelectBGa = new JButton("ligne de but Gauche");
	JButton bSelectBDr = new JButton("ligne de but Droite");
	JButton bSelectSDr = new JButton("ligne de sortie Droite");
	JButton bPause = new JButton("Pause");
	JButton bPrecedent = new JButton("Précédent");
	JButton bSuivant = new JButton("Suivant");
	JButton bSauvegarder = new JButton("Sauvegarder");

	JTextField tChemin = new JTextField("Chemin");
	JTextField txtNum = new JTextField();
	JRadioButton plusmoins = new JRadioButton("Plus 1 moins 1");
	JRadioButton plus = new JRadioButton("Plus 1");
	JRadioButton moins = new JRadioButton("Moins 1");
	ButtonGroup choixGam = new ButtonGroup();
	JFrame frame = new JFrame("Baby-Foot");
	JPanel panelImage = new JPanel();
	JLabel lImage = new JLabel();

	int[][] barycentres = { { 0 }, { 0 } };
	boolean videoSeg = false;
	EcouteClic clic = new EcouteClic(panelImage);

	JButton bScores = new JButton("Calcul des scores"); // Scores
	// ---- Joueur 1 ----
	JTextArea txtScoreGoalName1 = new JTextArea("Buts");
	JTextArea txtScoreGamelleName1 = new JTextArea("Gamelles");
	JTextArea txtScoreOutName1 = new JTextArea("Sorties");
	JTextArea txtScorej1 = new JTextArea("Joueur 1");
	JTextArea txtScore1 = new JTextArea("0");
	JTextArea txtScore1Goal = new JTextArea("0");
	JTextArea txtScore1Gamelle = new JTextArea("0");
	JTextArea txtScore1Out = new JTextArea("0");
	// ---- Joueur 2 ----
	JTextArea txtScoreGoalName2 = new JTextArea("Buts");
	JTextArea txtScoreGamelleName2 = new JTextArea("Gamelles");
	JTextArea txtScoreOutName2 = new JTextArea("Sorties");
	JTextArea txtScorej2 = new JTextArea("Joueur 2");
	JTextArea txtScore2 = new JTextArea("0");
	JTextArea txtScore2Goal = new JTextArea("0");
	JTextArea txtScore2Gamelle = new JTextArea("0");
	JTextArea txtScore2Out = new JTextArea("0");

	// PlayPause p = new PlayPause(numImg,video,frame,panelImage,lImage,txtNum);

	public Fenetre() {
		// Création de la fenêtre et du container

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// Plein ecran
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le
																// processus
																// lorsqu'on
																// clique sur la
																// croix rouge

		JPanel panelPrincipal = new JPanel();
		JPanel panelGauche = new JPanel();
		JPanel panelGauche1 = new JPanel();
		JPanel panelGauche2 = new JPanel();
		JPanel panelGauche3 = new JPanel();
		JPanel panelDroit = new JPanel();
		JPanel panelLecture = new JPanel();
		JPanel panelChemin = new JPanel();
		JPanel panelGroup = new JPanel();
		JPanel panelGroup2 = new JPanel();
		JPanel panelLignes1 = new JPanel();
		JPanel panelLignes2 = new JPanel();
		JPanel panelGroup3 = new JPanel();
		JPanel panelScores = new JPanel();
		JPanel panelScores1 = new JPanel();
		JPanel panelScores2 = new JPanel();
		JPanel panelScores3 = new JPanel();

		// Définition du gestionnaire de placement
		panelPrincipal.setLayout(new BorderLayout());
		panelGauche.setLayout(new BorderLayout());
		panelGauche1.setLayout(new BorderLayout());
		panelGauche2.setLayout(new BorderLayout());
		panelGauche3.setLayout(new BorderLayout());
		panelDroit.setLayout(new BorderLayout());
		panelLecture.setLayout(new FlowLayout());
		panelChemin.setLayout(new FlowLayout());
		panelScores1.setLayout(new FlowLayout());
		panelScores2.setLayout(new FlowLayout());
		panelScores.setLayout(new BorderLayout());
		panelGroup3.setLayout(new BorderLayout());

		// panelImage.setLayout(new CardLayout(10,10));

		// Création des composants
		// JLabel label = new JLabel("Entrer votre nom");

		// Ajout des composants au container chemin
		panelChemin.add(bOpen);
		panelChemin.add(tChemin);

		// Ajout des composants au container group
		choixGam.add(plusmoins);
		choixGam.add(plus);
		choixGam.add(moins);
		panelGroup.add(plusmoins);
		panelGroup.add(plus);
		panelGroup.add(moins);

		// Ajout des composants au container group2
		panelGroup2.add(bCommencer);
		panelGroup2.add(bSelectCentre);
		panelGroup2.add(bSauvegarder);

		// Ajout des composants au container Lignes1

		panelLignes1.add(bSelectSGa);
		panelLignes1.add(bSelectBGa);

		// Ajout des composants au container Lignes2

		panelLignes2.add(bSelectBDr);
		panelLignes2.add(bSelectSDr);

		// Ajout des composants au container Scores

		panelScores1.add(bScores);
		panelScores1.add(txtScorej1);
		panelScores1.add(txtScore1);
		panelScores1.add(txtScorej2);
		panelScores1.add(txtScore2);
		panelScores2.add(txtScoreGoalName1);
		panelScores2.add(txtScore1Goal);
		panelScores2.add(txtScoreGamelleName1);
		panelScores2.add(txtScore1Gamelle);
		panelScores2.add(txtScoreOutName1);
		panelScores2.add(txtScore1Out);
		panelScores3.add(txtScoreGoalName2);
		panelScores3.add(txtScore2Goal);
		panelScores3.add(txtScoreGamelleName2);
		panelScores3.add(txtScore2Gamelle);
		panelScores3.add(txtScoreOutName2);
		panelScores3.add(txtScore2Out);
		panelScores.add(panelScores1, BorderLayout.NORTH);
		panelScores.add(panelScores2, BorderLayout.WEST);
		panelScores.add(panelScores3, BorderLayout.EAST);

		// Ajout des composants au container Gauche1
		panelGauche1.add(panelChemin, BorderLayout.NORTH);
		panelGauche1.add(panelGroup, BorderLayout.CENTER);
		panelGauche1.add(panelGroup2, BorderLayout.SOUTH);

		// Ajout des composants au container Gauche2
		panelGauche2.add(panelLignes1, BorderLayout.NORTH);
		panelGauche2.add(panelLignes2, BorderLayout.CENTER);

		// Ajout des composants au container Gauche2
		panelGauche3.add(panelScores, BorderLayout.NORTH);

		// Ajout des composants au container Gauche
		panelGauche.add(panelGauche1, BorderLayout.NORTH);
		panelGauche.add(panelGauche3, BorderLayout.CENTER);
		panelGauche.add(panelGauche2, BorderLayout.SOUTH);

		// Ajout des composants au container Lecture
		panelLecture.add(txtNum);
		panelLecture.add(bPrecedent);
		panelLecture.add(bPlay);
		panelLecture.add(bPause);
		panelLecture.add(bSuivant);

		// Definition de la taille des zones de texte

		// Ajout des composant au container Image
		// On prend la résolution de l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		Dimension dimIm = new Dimension(width * 2 / 3, height * 2 / 3);// Dimension
																		// image
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
		// Définit sa taille : Prend les 4/5 de l'écran
		frame.setSize((int) screenSize.getWidth() * 4 / 5, (int) screenSize.getHeight() * 4 / 5);
		// Nous demandons maintenant à  notre objet de se positionner au centre
		frame.setLocationRelativeTo(null);
		// Affichage de la fenêtre
		frame.setVisible(true);

		Ecouteur listen = new Ecouteur();
		Focus focus = new Focus(txtNum);

		// Boutons play non disponible au début
		bPlay.setEnabled(false);
		bPrecedent.setEnabled(false);
		bPause.setEnabled(false);
		bSuivant.setEnabled(false);
		tChemin.setEnabled(false);
		txtNum.setEnabled(false);
		bSelectCentre.setEnabled(false);
		bSelectSGa.setEnabled(false);
		bSelectBGa.setEnabled(false);
		bSelectBDr.setEnabled(false);
		bSelectSDr.setEnabled(false);
		bCommencer.setEnabled(false);
		txtScorej1.setEditable(false);
		txtScorej2.setEditable(false);
		txtScore1.setEditable(false);
		txtScore2.setEditable(false);

		bOpen.addActionListener(listen);
		bSuivant.addActionListener(listen);
		bPrecedent.addActionListener(listen);
		txtNum.addActionListener(listen);
		bPlay.addActionListener(listen);
		bPause.addActionListener(listen);
		bCommencer.addActionListener(listen);
		txtNum.addFocusListener(focus);
		bSelectCentre.addActionListener(listen);
		bSelectSGa.addActionListener(listen);
		bSelectBGa.addActionListener(listen);
		bSelectBDr.addActionListener(listen);
		bSelectSDr.addActionListener(listen);
		plusmoins.addActionListener(listen);
		plus.addActionListener(listen);
		moins.addActionListener(listen);
		bSauvegarder.addActionListener(listen);
		bScores.addActionListener(listen);
		panelImage.addMouseListener(clic);

	}

	// Pour gérer le play/pause, il faut créer une classe héritée de thread.
	public class EcouteClic extends MouseAdapter {
		private JPanel pan;
		public int x = 0;
		public int y = 0;
		int X[] = new int[8];
		int Y[] = new int[8];

		public EcouteClic(JPanel pan) {
			this.pan = pan;

		}

		public void mouseClicked(MouseEvent e) {
			Graphics g = pan.getGraphics();
			Graphics2D g1 = (Graphics2D) g;
			Graphics2D g2 = (Graphics2D) g;
			Graphics2D g3 = (Graphics2D) g;
			Graphics2D g4 = (Graphics2D) g;

			if (c == 0) {
				int x = e.getX();
				int y = e.getY();
				imgRefBall = numImg;
				xBall = x;
				yBall = y;
				Mat img = video.getFrame(imgRefBall);

				// On va prendre les coordonnées stoquées lors du clique sur le
				// ballon:
				HSV hsv = new HSV(img, xBall, yBall); // coordonnées du ballon
														// sur l'image référence

				System.out.println(hsv.getH() + ", " + hsv.getS() + ", " + hsv.getV());

				System.out.println("coordonnées" + e.getX() + ", " + e.getY());
				g.drawOval(e.getX(), e.getY(), 5, 5);
				g.dispose();
				bCommencer.setEnabled(true);
				c++;
			}

			if (c == 10) {

				g1.setColor(new Color(100, 100, 100));
				g1.setStroke(new BasicStroke(3));
				g1.drawLine(X[0], Y[0], X[1], Y[1]);
				X[0] = e.getX();
				Y[0] = e.getY();
				c++;
			}
			if (c == 11) {
				X[1] = e.getX();
				Y[1] = e.getY();
				g1.setColor(Color.red);
				g1.setStroke(new BasicStroke(3));
				g1.drawLine(X[0], Y[0], X[1], Y[1]);
				g1.dispose();

			}
			if (c == 12) {
				g2.setColor(new Color(100, 100, 100));
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(X[2], Y[2], X[3], Y[3]);
				X[2] = e.getX();
				Y[2] = e.getY();
				c++;
			}
			if (c == 13) {
				X[3] = e.getX();
				Y[3] = e.getY();
				g2.setColor(Color.green);
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(X[2], Y[2], X[3], Y[3]);
				g2.dispose();

			}
			if (c == 14) {
				g3.setColor(new Color(100, 100, 100));
				g3.setStroke(new BasicStroke(3));
				g3.drawLine(X[4], Y[4], X[5], Y[5]);
				X[4] = e.getX();
				Y[4] = e.getY();
				c++;
			}
			if (c == 15) {
				X[5] = e.getX();
				Y[5] = e.getY();
				g3.setColor(Color.green);
				g3.setStroke(new BasicStroke(3));
				g3.drawLine(X[4], Y[4], X[5], Y[5]);
				g3.dispose();

			}
			if (c == 16) {
				g4.setColor(new Color(100, 100, 100));
				g4.setStroke(new BasicStroke(3));
				g4.drawLine(X[6], Y[6], X[7], Y[7]);
				X[6] = e.getX();
				Y[6] = e.getY();
				c++;
			}
			if (c == 17) {
				X[7] = e.getX();
				Y[7] = e.getY();
				g4.setColor(Color.red);
				g4.setStroke(new BasicStroke(3));
				g4.drawLine(X[6], Y[6], X[7], Y[7]);
				g4.dispose();

			}

		}

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

		public int[] getX() {
			return X;
		}

		public int[] getY() {
			return Y;
		}
	}

	public class Ecouteur implements ActionListener {

		PlayPause p = new PlayPause(numImg, video, frame, panelImage, lImage, txtNum, segmentation, barycentres);

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bScores) { // ---- Scores ----
				Events events = new Events(barycentres, video.getSize(), clic.getX(), clic.getY());
				events.detection(); // Calcul des scores
				System.out.println("Calcul des scores");
				// ---- Joueur1 ----
				txtScore1Goal.setText("" + events.getPlayer1().getNbGoal());
				txtScore1Gamelle.setText("" + events.getPlayer1().getNbGamelle());
				txtScore1Out.setText("" + events.getPlayer1().getNbOut());
				// ---- Joueur2 ----
				txtScore2Goal.setText("" + events.getPlayer2().getNbGoal());
				txtScore2Gamelle.setText("" + events.getPlayer2().getNbGamelle());
				txtScore2Out.setText("" + events.getPlayer2().getNbOut());

			}

			if (e.getSource() == plusmoins) {
				regle = 0;
			}
			if (e.getSource() == plus) {
				regle = 1;
			}
			if (e.getSource() == moins) {
				regle = 2;
			}
			if (e.getSource() == bSauvegarder) {
				int nbimg = video.getSize();
				Events e1 = new Events(barycentres, nbimg, X, Y);
				Player p1 = e1.getPlayer1();
				Player p2 = e1.getPlayer2();
				Sauvegarde.write(p1, p2, regle);
			}
			if (e.getSource() == bSelectCentre) {
				c = 0;
				System.out.println("select");
			}

			if (e.getSource() == bSelectSGa) {
				c = 10;
				System.out.println("select2");
				bSelectBGa.setEnabled(true);
			}
			if (e.getSource() == bSelectBGa) {
				c = 12;
				System.out.println("select3");
				bSelectBDr.setEnabled(true);
			}
			if (e.getSource() == bSelectBDr) {
				c = 14;
				System.out.println("select4");
				bSelectSDr.setEnabled(true);
			}
			if (e.getSource() == bSelectSDr) {
				c = 16;
				System.out.println("select5");
			}

			if ((e.getSource() == bOpen)) {
				try {
					// Si FenetreOuvrir() renvoie un fichier vidéo

					JFileChooser ch = new FenetreOuvrir();
					String str = ch.getSelectedFile().getAbsolutePath();
					// System.out.println(str);
					String replacedStr = str.replace('\\', '/'); // Pour avoir
																	// un chemin
																	// exploitable
					// System.out.println(replacedStr);
					afficherImage(replacedStr);
					int nbimg = video.getSize();
					int nbAffiche = numImg + 1;
					tChemin.setText(replacedStr);
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);
					txtNum.setEnabled(true);
					bPlay.setEnabled(true);
					bPrecedent.setEnabled(true);
					bPause.setEnabled(true);
					bSuivant.setEnabled(true);
					bSelectCentre.setEnabled(true);
					bSelectSGa.setEnabled(true);
					frame.validate();
					segmentation = false;

				} catch (NullPointerException e3) {
					// Si le fichier n'est pas une video ou que l'utilisateur
					// appuie sur Annuler
					// FenetreOuvrir() renvoie null
					return;
				}
			}
			if (e.getSource() == bPrecedent) {
				int nbimg = video.getSize();
				if (numImg > 0) {
					numImg--;
					Mat frame2 = video.getFrame(numImg);
					if (segmentation == true) {
						int x = barycentres[0][numImg];
						int y = barycentres[1][numImg];
						Point centre = new Point(x, y);
						int rayon = 10;
						Scalar color = new Scalar(0, 0, 255);
						// Tracer un cercle rouge représentant le barycentre

						Core.circle(frame2, centre, rayon, color, -1); // -1:
																		// rempli
																		// le
																		// cercle
					}
					ImageIcon image = new ImageIcon(Mat2bufferedImage(frame2, video.getWidth(), video.getHeight()));
					lImage.setIcon(image);
					panelImage.add(lImage);
					panelImage.repaint();
					int nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);

					frame.validate();

				}
			}
			if (e.getSource() == bSuivant) {
				int nbimg = video.getSize();
				if (numImg < nbimg - 1) {
					numImg++;
					Mat frame2 = new Mat();
					frame2 = video.getFrame(numImg);
					if (segmentation == true) {
						int x = barycentres[0][numImg];
						int y = barycentres[1][numImg];
						Point centre = new Point(x, y);
						int rayon = 10;
						Scalar color = new Scalar(0, 0, 255);
						// Tracer un cercle rouge représentant le barycentre

						Core.circle(frame2, centre, rayon, color, -1); // -1:
																		// rempli
																		// le
																		// cercle
					}
					ImageIcon image = new ImageIcon(Mat2bufferedImage(frame2, video.getWidth(), video.getHeight()));
					lImage.setIcon(image);
					panelImage.add(lImage);
					panelImage.repaint();
					int nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);

					frame.validate();

				}
			}

			if (e.getSource() == bPlay) {
				if (!p.isAlive()) {
					p = new PlayPause(numImg, video, frame, panelImage, lImage, txtNum, segmentation, barycentres);
					// System.out.println("play: "+numImg);
					p.start();

				}
			}

			if (e.getSource() == bPause) {
				p.arret();
				// System.out.println("paused");
				numImg = p.getNumImg();
			}

			if (e.getSource() == txtNum) {
				txtNum.selectAll();
				int nbimg = video.getSize();
				String ch = txtNum.getText();
				int numEntre;

				try {
					numEntre = Integer.parseInt(ch);
					// System.out.println(numEntre);
				} catch (NumberFormatException e2) {
					// Si l'utilisateur n'entre pas un nombre entier
					System.out.println("Invalid number");
					numEntre = -1;
				}

				// Lorsque l'utilisateur entre une image précise

				if (numEntre > 0 && numEntre <= nbimg) {
					numImg = numEntre - 1;
					Mat frame2 = video.getFrame(numImg);
					if (segmentation == true) {
						int x = barycentres[0][numImg];
						int y = barycentres[1][numImg];
						Point centre = new Point(x, y);
						int rayon = 10;
						Scalar color = new Scalar(0, 0, 255);
						// Tracer un cercle rouge représentant le barycentre

						Core.circle(frame2, centre, rayon, color, -1); // -1:
																		// rempli
																		// le
																		// cercle
					}
					ImageIcon image = new ImageIcon(Mat2bufferedImage(frame2, video.getWidth(), video.getHeight()));

					lImage.setIcon(image);

					panelImage.add(lImage);

					panelImage.repaint();
					int nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);

					frame.validate();
				} else {
					int nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbimg);
				}
			}

			if (e.getSource() == bCommencer) {
				numImg = 0;
				System.out.println("Début segmentation");
				Segmentation test;
				Mat img;
				HSV hsv;
				int h, s, v;

				try {
					img = video.getFrame(imgRefBall);

					// On va prendre les coordonnées stoquées lors du clique sur
					// le ballon:
					hsv = new HSV(img, xBall, yBall); // coordonnées du ballon
														// sur l'image référence

					h = hsv.getH();
					s = hsv.getS();
					v = hsv.getV();

					// test sur une vidéo

					int nbImg = video.getSize();

					int X[] = new int[nbImg];
					int Y[] = new int[nbImg];

					video.startSeg(); // Début de la segmentation
					double tpsSys = System.currentTimeMillis();
					for (int i = 0; i < nbImg; i++) {

						System.out.println("Segmentation de l'image " + (i + 1) + "/" + nbImg);
						test = new Segmentation(video.getFrameForSeg(i), h, s, v);
						X[i] = test.getX_();
						Y[i] = test.getY_();

						// if (test.getX_()>video.getWidth()*0.4 &&
						// test.getX_()<video.getWidth()*0.6)
						// {
						// i++; // Si la balle se trouve au milieu de l'image,
						// on saute 1 image sur 2 (zone non interessante)
						// }

					}

					video.endSeg(); // Fin de la segmentation
					barycentres[0] = X;
					barycentres[1] = Y;
					tpsSys = System.currentTimeMillis() - tpsSys;
					System.out.println("Temps utilisé pour réaliser la segmentation : " + tpsSys + " ms");
					videoSeg = true;
					ImageIcon image = new ImageIcon(
							Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));

					lImage.setIcon(image);
					panelImage.add(lImage);
					int nbAffiche = numImg + 1;
					txtNum.setText("N°" + nbAffiche + "/" + nbImg);
					frame.validate();

					bCommencer.setEnabled(false);
					bSelectCentre.setEnabled(false);

					segmentation = true;

				} catch (NullPointerException x) // Si on n'a pas encore
													// sélectionné la balle
				{
					System.out.println("La balle n'a pas été sélectionnée");
				}

			}

		}

		public void afficherImage(String chemin) {

			numImg = 0;

			if (lImage.getMaximumSize().getHeight() > 0 && lImage.getMaximumSize().getWidth() > 0) // Si
																									// on
																									// a
																									// déjà
																									// ouvert
																									// une
																									// video
																									// auparavant
			{
				// video.libererVideo(); // on libère la mémoire
				lImage.removeAll();
				System.out.println("video deleted");
			}
			VideoCapture cap = new VideoCapture(chemin);
			// System.out.println(cap + " : " + cap.isOpened());

			if (cap.isOpened()) {
				System.out.println("Success to open file");
				video = new LoadVideo(chemin);
				ImageIcon image = new ImageIcon(
						Mat2bufferedImage(video.getFrame(numImg), video.getWidth(), video.getHeight()));
				// Image img =Mat2bufferedImage(video.getFrame(numImg),
				// video.getWidth(), video.getHeight());
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
