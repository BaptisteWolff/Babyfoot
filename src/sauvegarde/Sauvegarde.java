package sauvegarde;
import java.io.File;
//import org.ho.yaml.Yaml;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import events.Events;
import events.Player;


public class Sauvegarde {
	
	public static void write(Player j1, Player j2, int regle, String nomVid, Events e1){

		int score1=0;
		int score2=0;
		ArrayList <String> listeEvent = e1.getlisteEvent();
		ArrayList <Integer> gam1=j1.getGamelle();
		ArrayList <Integer> gam2=j2.getGamelle();
		ArrayList <Integer> goal1=j1.getGoal();
		ArrayList <Integer> goal2=j2.getGoal();
		ArrayList <Integer> out1=j1.getOut();
		ArrayList <Integer> out2=j2.getOut();
		int nbGam1=j1.getNbGamelle();
		int nbGam2=j2.getNbGamelle();
		int nbGoal1=j1.getNbGoal();
		int nbGoal2=j2.getNbGoal();
		int nbOut1=j1.getNbOut();
		int nbOut2=j2.getNbOut();

		// on calcul le score
		switch (regle)
		{
		case 1: 	// regle +1
			nbGam1=nbGam1;
			nbGam2=nbGam2;
			break;
		case 2:		// regle -1
			nbGam1=-nbGam2;
			nbGam2=-nbGam1;
			break;
		default:	// regle +1/-1 (regle=0 ou regle par défaut)
			nbGam1=nbGam1-nbGam2;
			nbGam2=nbGam2-nbGam1;

		}
		score1=nbGoal1+nbGam1;
		score2=nbGoal2+nbGam2;


		System.out.println("nbgam1  "+nbGam1);
		System.out.println("nbgam2 "+nbGam2);
		System.out.println("nbOut1  "+nbOut1);
		System.out.println("nbOut2 "+nbOut2);
		System.out.println("nbGoal1  "+nbGoal1);
		System.out.println("nbGoal2 "+nbGoal2);
		System.out.println("score1  "+score1);
		System.out.println("score2 "+score2);

		try {
			ArrayList <String> te=new ArrayList<String>();
			String str=nomVid.concat(".yml");
			System.out.println(str);
			BufferedWriter fichier = new BufferedWriter(new FileWriter(str));
			fichier.write(nomVid);
			fichier.newLine();
			fichier.newLine();
			fichier.write("Equipe 1 (gauche):"+score1);
			fichier.newLine();
			fichier.write("    But: "+(nbGoal1));

		
			fichier.newLine();
			fichier.write("    Gamelle: "+ (nbGam1));
		
			fichier.newLine();
			fichier.write("    Sortie: "+(nbOut1));
			
			fichier.newLine();
			fichier.newLine();
			fichier.write("Equipe 2 (droite):"+score2);
			fichier.newLine();
			fichier.write("    But: "+(nbGoal2));
			
			fichier.newLine();
			fichier.write("    Gamelle: "+ (nbGam2));
			
			fichier.newLine();
			fichier.write("    Sortie: "+(nbOut2));
			

			fichier.newLine();
			fichier.newLine();
			fichier.write("Evénements:");
			//Collections.sort(listeEvent);
			for(int i=0;i<listeEvent.size();i++){
				fichier.newLine();
				fichier.write("    "+listeEvent.get(i).toString());
			}
			fichier . close ();
			System.out.println("ok");


		} catch (Exception e) {
			e.printStackTrace();

		}   
	}

}


