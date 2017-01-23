package events;

import java.util.ArrayList;

public class Events {
	int[][] barycentres = { { 0 }, { 0 } };
	int sizeVideo = 0;
	Player player1 = new Player();
	Player player2 = new Player();
	int[] goalLinesX = { 0 }, goalLinesY = { 0 };
	ArrayList <String> listeEvent = new ArrayList <String>();

	public Events(int[][] barycentres, int sizeVideo, int goalLinesX[], int goalLinesY[]) {
		super();
		this.barycentres = barycentres;
		this.sizeVideo = sizeVideo;
		this.goalLinesX = goalLinesX;
		this.goalLinesY = goalLinesY;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public ArrayList<String> getlisteEvent() {
		return listeEvent;
	}

	public void setSizeVideo(int sizeVideo) {
		this.sizeVideo = sizeVideo;
	}

	public void detection() {

		int X[] = barycentres[0];
		int Y[] = barycentres[1];

		int nImage = 0; // numéro de l'image traitée
		int count = 0; // nbr d'images pendant lesquelles la balle n'est pas
		// détectée
		boolean eventGoal = false; // Balle présente dans une des cages
		boolean eventOut = false; // Balle en dehors du terrain

		int playerNum = 0;

		while (nImage < sizeVideo) {
			int x = X[nImage];
			int y = Y[nImage];

			// Balle détectée
			if (x > 0 && y > 80 && y < 560) {
				if (eventOut == false) { // Balle sur le terrain
					// --------------- Joueur 2 ----------------------
					int xMoyGoal1=(goalLinesX[2] + goalLinesX[3])/2;

					if (x < xMoyGoal1) { // ligne1 : but cages joueur 1
						int xMoyOut1=(goalLinesX[0] + goalLinesX[1])/2;

						if (x < xMoyOut1) { // ligne0 : sortie côté joueur 1
							player2.addOut(nImage);
							listeEvent.add(nImage+" Sortie 2");
							eventOut = true;
							eventGoal = false;
							//player2.addOut(count);
						} else { 									// balle dans les cages
							eventGoal = true;
							playerNum = 2;
						}
					} else if (eventGoal == true && playerNum==2) { // La balle retourne sur le terrain
						player2.addGamelle(nImage - count);
						listeEvent.add((nImage - count)+" Gamelle 2");
						eventGoal = false;
					}

					// --------------- Joueur 1 ----------------------
					int xMoyGoal2=(goalLinesX[4] + goalLinesX[5])/2;
					if (x > xMoyGoal2) { // ligne3 : but cages joueur 2
						int xMoyOut2=(goalLinesX[6] + goalLinesX[7])/2;

						if (x > xMoyOut2) { // ligne4  :  sortie  côté  joueur  2
							player1.addOut(nImage);
							listeEvent.add(nImage+" Sortie 1");
							eventOut = true;
							eventGoal = false;
							//player2.addOut(count);
						} else { // balle dans les cages
							eventGoal = true;
							playerNum = 1;
						}
					} else if (eventGoal == true && playerNum==1) { // La balle retourne sur le
						// terrain
						player1.addGamelle(nImage - count);
						listeEvent.add((nImage - count)+" Gamelle 1");
						eventGoal = false;
					}
				} else { // Balle en dehors du terrain
					// Détection d'une remise en jeu après une sortie
					if (x > goalLinesX[0] && x > goalLinesX[1] && x < goalLinesX[4] && x < goalLinesX[5]) {
						eventOut = false;
					}
				}


				count = 0;
			} else { // Balle non détectée
				if (eventOut == false && eventGoal == true && count >= 12) { // Goal
					// détecté
					if (playerNum == 1) {
						player1.addGoal(nImage - count);
						listeEvent.add((nImage - count)+" But 1");
					}
					if (playerNum == 2) {
						player2.addGoal(nImage - count);
						listeEvent.add((nImage - count)+" But 2");
					}
					eventGoal = false;
				}
				count++;
			}
			nImage++;
		}
	}
}
