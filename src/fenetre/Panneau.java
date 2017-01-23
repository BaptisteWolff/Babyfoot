package fenetre;


import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Panneau extends JPanel {

	private Image img_;

	// constructeur avec le parametre d'entree : chemin vers le fichier
	public Panneau(Image img) {
		this.img_=img;
	}
	public void paintComponent(Graphics g){
		int imgWidth = this.getWidth()*2/3;
		int imgHeight = this.getHeight()*2/3;
		int posx = this.getWidth()-imgWidth-10;
		int posy = 10;
		g.drawImage(img_, posx, posy ,imgWidth, imgHeight, this);
		//Pour une image de fond (Taille de la fenêtre)
		//g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);                
	} 



}