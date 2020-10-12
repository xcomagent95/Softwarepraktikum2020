package org.lakedetection;

/**
 * author: OTI2020 && heilandoo
 * title: SoPraTestBand 
 * aim: count coherent Pixels of same color
 * latest date: 11.03.2020; 11:20 am 
 **/

public class FloodFillAlgo {
	private static int px;
	private static int py;
	private static int numPixel = 0;
	private static int[][] inputArr;
	
	//Kernstuek des Algor. ist die checkNeighbour()
	public static void calcAndPrintNumOfConnenctedPixel(int[][] inInputArr, int inPx, int inPy){
		px = inPx;
		py = inPy;
		inputArr = inInputArr;
		checkNeighbour(px, py);
		presentPixelCount();
		}
		
	//pixelCounter() setzt den Zaehler anzPixel bei jedem Aufruf einshöher,
	//was aber nur passiert, wenn checkNeighbour() eine bisher ununtersuchte 
	//Nachbarzelle mit dem Wert 255 aufspuert
	private static int pixelCounter() {
		numPixel += 1;
		// Dies wird bei jedem neu gefundenem Pixel geprintet
		//System.out.println("Die momentane Anzahl der zusammenhaengenden und untersuchten Pixel: " + anzPixel);
		return numPixel;
	}
	
	//checkNeighbour() fragt alle 8 Nachbarzellen des vorgegebenen Ausgangspixels, 	
	//ob diese existieren, d.h. innerhalb des float[][] ausgangsArr liegen,
	//und ob diese den Wert 255 speichern. Wenn diese Bedingungen erfuellt sind, 
	//wird pixelCounter() aufgerufen, der Wert, der an entsprechender Stelle im 
	//ausgangsArr gespeichert ist wird veraendert, damit das Pixel im rekursiven
	//Verfahren nicht erneut mitgezaehlt wird. Anschließend wird der neue Pixelwert 
	//in der Konsole ausgegeben, sodass der Vorgang besser nachvollziehbar ist. 
	//Später, wenn alles einwandtfrei funktioniert, koennen die betreffenden Zeilen
	//auskommentiert werden(ebenso die else-Anweisungen). Die letzte Zeile der 
	//jeweiligen if-Abfragen enthaelt den Rekursionsschritt, der den untersuchten 
	//Nachbarpixel zum neuen Startpixel macht.
	private static void checkNeighbour(int inPx, int inPy) {
		if((py < (inputArr[px].length - 1)) && (inputArr[px][py + 1] == 255)) {
			pixelCounter();
			py += 1;
			inputArr[px][py] = 101;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			py -= 1;
		}//else System.err.println("unten");
		
		if((py < (inputArr[px].length - 1)) && (px > 0) && (inputArr[px - 1][py + 1] == 255)) {
			pixelCounter();
			px -= 1;
			py += 1;
			inputArr[px][py] = 102;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
			py -= 1;
		}//else System.err.println("unten links");
		
		if((px > 0) && (inputArr[px - 1][py] == 255)) {
			pixelCounter();
			px -= 1;
			inputArr[px][py] = 103;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
		}//else System.err.println("links");
		
		if((py > 0) && (px > 0) && (inputArr[px - 1][py - 1] == 255)) {
			pixelCounter();
			px -= 1;
			py -= 1;
			inputArr[px][py] = 104;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
			py += 1;
		}//else System.err.println("oben links");

		if((py > 0) && (inputArr[px][py - 1] == 255)) {
			pixelCounter();
			py -= 1;
			inputArr[px][py] = 105;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			py += 1;
		}//else System.err.println("oben");

		if((px < (inputArr.length - 1)) && (py >0) && (inputArr[px + 1][py - 1] == 255)) {
			pixelCounter();
			px += 1;
			py -= 1;
			inputArr[px][py] = 106;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
			py += 1;
		}//else System.err.println("oben rechts");
		
		if((px < (inputArr.length - 1)) && (inputArr[px + 1][py] == 255)) {
			pixelCounter();
			px += 1;
			inputArr[px][py] = 107;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
		}//else System.err.println("rechts");

		if((px < (inputArr.length - 1)) && (py < (inputArr[px].length - 1)) && (inputArr[px + 1][py + 1] == 255)) {
			pixelCounter();
			px += 1;
			py += 1;
			inputArr[px][py] = 108;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
			py -= 1;
		}//else System.err.println("unten rechts");
    }
	private static void presentPixelCount() {
		System.out.println("Die finale Anzahl der zusammenhaengenden und untersuchten Pixel mit ");
		System.out.println("der urspruenglich abgespeicherten Gleitkommerzahl 255.0 belaeuft sich auf " + numPixel + "  :)");
		System.out.println("Die Wasserflaeche hat damit eine angenaeherte Oberflaeche von " + numPixel * 25 + " m^2");
	}
	
}
