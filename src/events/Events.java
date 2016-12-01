package events;

public class Events {
	int[][] barycentres;
	int sizeVideo;
	Player player1, player2;
	int[] goalLinesX, goalLinesY;

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

	public void detection() {
		int nImage = 0; // numéro de l'image traitée
		int count = 0; // nbr d'images pendant lesquelles la balle n'est pas
						// détectée
		boolean eventGoal = false; // Balle présente dans une des cages
		boolean eventOut = false; // Balle en dehors du terrain

		int playerNum = 0;

		while (nImage < sizeVideo) {
			int X[] = barycentres[0];
			int Y[] = barycentres[1];
			int x = X[nImage];
			int y = Y[nImage];

			// Balle détectée et sur le terrain
			if (x != 0 && y != 0 && eventOut == false) {
				// --------------- Joueur 1 ----------------------
				// ligne1 : but cages joueur 1
				if (x < goalLinesX[2] && y < goalLinesY[3]) {
					// ligne0 : sortie côté joueur 1
					if (x < goalLinesX[0] && x < goalLinesX[1]) {
						player1.addOut(nImage);
						eventOut = true;
						eventGoal = false;
					} else { // balle dans les cages
						eventGoal = true;
						playerNum = 1;
					}
				} else if (eventGoal == true) { // La balle retourne sur le
												// terrain
					player1.addGamelle(nImage);
					eventGoal = false;
				}

				// --------------- Joueur 2 ----------------------
				// ligne3 : but cages joueur 2
				if (x < goalLinesX[4] && y < goalLinesY[5]) {
					// ligne4 : sortie côté joueur 2
					if (x < goalLinesX[6] && x < goalLinesX[7]) {
						player1.addOut(nImage);
						eventOut = true;
						eventGoal = false;
					} else { // balle dans les cages
						eventGoal = true;
						playerNum = 2;
					}
				} else if (eventGoal == true) { // La balle retourne sur le
												// terrain
					player1.addGamelle(nImage);
					eventGoal = false;
				}

				count = 0;
			} else { // Balle non détectée ou en dehors du terrain
				// Goal détecté
				if (eventOut == false && eventGoal == true && count >= 12) {
					if (playerNum == 1) {
						player1.addGoal(nImage - count);
					}
					if (playerNum == 2) {
						player2.addGoal(nImage - count);
					}
					eventGoal = false;
				}
				count++;
			}
			nImage++;
		}
	}
}
