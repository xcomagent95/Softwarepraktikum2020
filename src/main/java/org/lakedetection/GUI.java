package org.lakedetection;

import javax.swing.JOptionPane;

public class GUI {

		/*Bsp. von panjutorials.des
		 * 
		 * public static void main (String[] args) {
		
		String name1 = JOptionPane.showInputDialog("Gib den erste Namen ein!");
		String name2 = JOptionPane.showInputDialog("Gib den zweiten Namen ein!");
		
		int num1 = name1.length();
		int num2 = name2.length();
		int sum = num1+num2;
		
		JOptionPane.showMessageDialog(null, "Sie lieben sich zu " +sum + "%!", "Beweis", JOptionPane.PLAIN_MESSAGE);
		}
		*/
	
		public String loadPic() {
			String path = JOptionPane.showInputDialog("Geben Sie den Datepfad ein:");
			return path;
		}
	
		/*public String loadPicDD() {
			String path = JOptionPane.
			return path;
		}*/
	
}
