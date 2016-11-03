package fenetre;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;



public class FenetreOuvrir extends JFileChooser {
	public FenetreOuvrir(){
		String fileName;
	//	JFileChooser chooser = new ();
	    int status = this.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	       File file = this.getSelectedFile();
	       if (file == null) {
	           return;
	       }
	       else{
	       fileName = this.getSelectedFile().getAbsolutePath(); 
	       }
	   }
	   
	}
}     
    

