package fenetre;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;



public class FenetreOuvrir extends JFileChooser {
	public FenetreOuvrir(){
		String fileName;
		
	    int status = this.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	       File file = this.getSelectedFile();
	       
	       
	       // On vérifie qu'il s'agit bien d'une video
	       String extension = "fail";
	       int i = file.getName().lastIndexOf('.');
	       if (i > 0) {
	           extension = file.getName().substring(i+1);
	       }
	       System.out.println(extension);
	       
    	   if (!extension.equals("mp4") && !extension.equals("MP4")
	    		   && !extension.equals("avi") && !extension.equals("AVI") 
	    		   && !extension.equals("wmv") && !extension.equals("WMV") 
	    		   && !extension.equals("mkv") && !extension.equals("MKV"))
	    		   {
	             this.setSelectedFile(null);
	             file = this.getSelectedFile();
	             System.out.println("Le fichier sélectionné n'est pas une vidéo");
	       }
	       
	       if (file == null) {
	           return;
	       }
	       else{
	    	   fileName = this.getSelectedFile().getAbsolutePath();
	    	   System.out.println("Chemin : " + fileName + "\nNom : " + file.getName());
	       }
	       
	      
	   }
	}
}     





    

