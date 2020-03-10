package org.lakedetection;

public class SeeErkennen {

	
	
	/* || Bilder übereinanderlegen ||
	 * 
	 * Es liegen 2 2D-Arrays vor. 
	 * Nun sollen beide Arrays durchlaufen werden 
	 * und die Werte an den jeweils gleichen Pos. miteinander addiert 
	 * und durch 2 dividiert werden. 
	 * */

	public static float[][] connect(float[][] b1, float[][] b2){
		
		float[][] a = new float[b1.length][b1.length];
		
		for(int i=0; i<b1.length; i++) {
			for(int j=0; j<b1[i].length; j++) { 
				a[i][j] = (b1[i][j] + b2[i][j]) / 2;
			}
		} 
		return a;
	}
	
	// Hilfsfkt. zum Anzeigen einer Matrix 5 x 5
	public static void show(float[][] a) {
		for(int i=0; i<5; i++) {
			System.out.print(a[i][0] + " ");
		}
		System.out.println();
		for(int i=0; i<5; i++) {
			System.out.print(a[i][1] + " ");
		}
		System.out.println();

		for(int i=0; i<5; i++) {
			System.out.print(a[i][2] + " ");
		}
		System.out.println();

		for(int i=0; i<5; i++) {
			System.out.print(a[i][3] + " ");
		}		
		System.out.println();
		for(int i=0; i<5; i++) {
			System.out.print(a[i][4] + " ");
		}
		
	}
	
	
	///////////////////////
	/* || Wasserflächen erkennen ||
	 * 
	 * Mit der Schwellwertfunktion arbeiten 
	 * und alle Wasserflächen schwarz einfärben.
	 * */
	
	public static float[][] makeBlack(float[][] a) {
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[i].length; j++) {
				if(a[i][j] >= 150) { // Schwellwert muss noch gesetzt werden
					a[i][j] = 0;
				}
				else a[i][j] = 255; // max. ist mittelwert der maxima von vh und vv (4142,5) __ jetzt doch geändert auf 255
			}
		}
		return a;
	}
	
	// Test mithilfe einer beliebigen 5x5-Matrix war erfolgreich
	
	/* Fkt. die die Fläche glättet -- Test war erfolgreich
	 * */
	public static float[][] smoothing(float[][] a){
		float[][] b = new float[a.length][a.length];
		
		for(int i=1; i<(a.length)-1; i++) {
			for(int j=1; j<(a[i].length)-1; j++) {
				float x = ( a[i-1][j-1] + a[i-1][j] + a[i-1][j+1] + a[i][j-1] + a[i][j] + a[i][j+1] + a[i+1][j-1] + a[i+1][j] + a[i+1][j+1]) / 9;
				b[i][j] = x; 
			}
		}
		return b;
	}
}
