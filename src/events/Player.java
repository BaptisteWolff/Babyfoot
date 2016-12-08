package events;

import java.util.ArrayList;

public class Player {
	private ArrayList<Integer> goal = new ArrayList<Integer>();
	private ArrayList<Integer> gamelle = new ArrayList<Integer>();
	private ArrayList<Integer> out = new ArrayList<Integer>();

	public ArrayList<Integer> getGoal() {
		return goal;
	}

	public void addGoal(int nImage) {
		this.goal.add(nImage);
	}

	public ArrayList<Integer> getGamelle() {
		return gamelle;
	}

	public void addGamelle(int nImage) {
		this.gamelle.add(nImage);
	}

	public ArrayList<Integer> getOut() {
		return out;
	}

	public void addOut(int nImage) {
		this.out.add(nImage);
	}
	
	public int getNbGoal(){
		return goal.size();
	}
	
	public int getNbGamelle(){
		return gamelle.size();
	}
	
	public int getNbOut(){
		return out.size();
	}
}
