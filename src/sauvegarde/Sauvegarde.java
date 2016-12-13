package sauvegarde;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import events.Player;

public class Sauvegarde {

	public static void write(Player j1, Player j2, int regle){

		int score1=0;
		int score2=0;
		ArrayList <Integer> gam1=j1.getGamelle();
		ArrayList <Integer> gam2=j2.getGamelle();
		ArrayList <Integer> goal1=j1.getGoal();
		ArrayList <Integer> goal2=j2.getGoal();
		ArrayList <Integer> out1=j1.getOut();
		ArrayList <Integer> out2=j2.getOut();
		int nbGam1=gam1.size();
		int nbGam2=gam2.size();
		int nbGoal1=goal1.size();
		int nbGoal2=goal2.size();
		int nbOut1=out1.size();
		int nbOut2=out2.size();

		// on calcul le score

		if (regle==1){//regle plus 1
			if (nbGoal1!=0 && nbGam1!=0){
				score1=nbGoal1+nbGam1-2;
			}
			
			if (nbGoal2!=0 && nbGam2!=0){
				score2=nbGoal2+nbGam2-2;
			}
			
		}else if(regle==2){ //regle moins 1 
			if (nbGoal1!=0 && nbGam1!=0){
				score1=nbGoal1-nbGam2;
			}
			
			if (nbGoal2!=0 && nbGam2!=0){
				score2=nbGoal2-nbGam1;
			}
			
			
		
		}else  {//regle plus 1 moins 1
			score1=nbGoal1+nbGam1-nbGam2;
			score2=nbGoal2+nbGam2-nbGam1;
		}
		
		System.out.println("nbgam1  "+nbGam1);
		System.out.println("nbgam2 "+nbGam2);
		System.out.println("nbOut1  "+nbOut1);
		System.out.println("nbOut2 "+nbOut2);
		System.out.println("nbGoal1  "+nbGoal1);
		System.out.println("nbGoal2 "+nbGoal2);




		try {
			BufferedWriter fichier = new BufferedWriter(new FileWriter("NewDoc.txt"));
			fichier.write("Résumé du match");

			fichier.newLine();
			fichier.write("Score       Equipe 1          Equipe 2" );
			fichier.newLine();
			fichier.write("                "+score1+"                   "+score2 );
			fichier.newLine();
			fichier.newLine();
			fichier.write("------------Equipe 1---------------");
			fichier.newLine();
			fichier.write("***But*** : "+(nbGoal1));
			for(int i=0;i<nbGoal1;i++){
				fichier.newLine();
				fichier.write(goal1.get(i)+"\n");
			}
			fichier.newLine();
			fichier.write("***Gamelle*** : "+ (nbGam1));
			for(int i=0;i<nbGam1;i++){
				fichier.newLine();
				fichier.write(gam1.get(i)+"\n");
			}
			fichier.newLine();
			fichier.write("***Sortie*** : "+(nbOut1));
			for(int i=0;i<nbOut1;i++){
				fichier.newLine();
				fichier.write(out1.get(i)+"\n");
			}
			fichier.newLine();
			fichier.write("------------Equipe 2---------------");
			fichier.newLine();
			fichier.write("***But*** : "+(nbGoal2));
			for(int i=0;i<nbGoal2;i++){
				fichier.newLine();
				fichier.write(goal2.get(i)+"\n");
			}
			fichier.newLine();
			fichier.write("***Gamelle*** : "+ (nbGam2));
			for(int i=0;i<nbGam2;i++){
				fichier.newLine();
				fichier.write(gam2.get(i)+"\n");
			}
			fichier.newLine();
			fichier.write("***Sortie*** : "+(nbOut2));
			for(int i=0;i<nbOut2;i++){
				fichier.newLine();
				fichier.write(out2.get(i)+"\n");
			}
			fichier . close ();
			System.out.println("ok");

		} catch (Exception e) {
			e.printStackTrace();

		}    
	}

}


