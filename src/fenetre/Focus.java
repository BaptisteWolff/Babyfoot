package fenetre;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class Focus implements FocusListener{
	JTextField txtNum = new JTextField();
	
	public Focus(JTextField txtNum) {
		super();
		this.txtNum = txtNum;
	}

	public void focusGained(FocusEvent e){
		if (e.getSource()==txtNum){
			txtNum.selectAll();
		}
	}

	public void focusLost(FocusEvent e) {
		
	}
}