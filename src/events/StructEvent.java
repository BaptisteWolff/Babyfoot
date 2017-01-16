package events;

public class StructEvent implements Comparable {
	public int imageDepart;
	public char typeEvent;
	public int joueurConcerne;
	
	public StructEvent(int imageDepart,  char typeEvent,	int joueurConcerne) {
		this.imageDepart=imageDepart;
		this.typeEvent=typeEvent;
		this.joueurConcerne=joueurConcerne;
	}

	public int getImageDepart(){
		return this.imageDepart;
	}
	@Override
	public String toString() {
		String type="";
		if (typeEvent=='B')
		{
			type="But";
		}
		if (typeEvent=='G')
		{
			type="Gamelle";
		}
		if (typeEvent=='S')
		{
			type="Sortie";
		}
		return imageDepart + " " + type + " "+ joueurConcerne;
	}

	public int compareTo(Object o1) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	
	
}

