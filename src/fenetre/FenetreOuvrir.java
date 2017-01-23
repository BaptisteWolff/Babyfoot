package fenetre;


import java.io.File;
import javax.swing.JFileChooser;



public class FenetreOuvrir extends JFileChooser {
	public FenetreOuvrir(){
		String fileName;
		String slash = System.getProperty("file.separator");
		// On change le r�pertoire par d�faut
		try{
			this.setCurrentDirectory(new File (System.getProperty("user.home") + slash + "Documents" 
												+ slash + "Big Macadam" + slash + "videos"));
		} catch (Exception e)
		{}
	    int status = this.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	       File file = this.getSelectedFile();
	       
	       
	       // On v�rifie qu'il s'agit bien d'une video
	       String extension = "invalid extension";
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
	             System.out.println("Le fichier s�lectionn� n'est pas une vid�o");
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





    

