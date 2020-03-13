package org.lakedetection;

/**
 * author: OTI2020 && heilandoo
 * title: SoPraTestBand 
 * aim: testing and implementaion of floodfill-similar algorithem to detect connected areas of same color
 * date: 11.03.2020; 11:20 am 
 **/

public class FloodFillAlgo {
	private static int px = 0;
	private static int py = 0;
	private static int anzPixel = 0;
	private static float[][] ausgangsArr = {
			{0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,255},
			{0  ,255,255,255,255,255,255,255,0  ,0  },
			{0  ,255,255,255,255,255,255,255,255,0  },
			{0  ,0  ,255,255,0  ,0  ,0  ,255,255,0  },
			{0  ,255,255,255,0  ,255,0  ,255,255,0  },
			{0  ,0  ,255,255,0  ,0  ,0  ,255,0  ,0  },
			{0  ,0  ,255,255,255,255,255,0  ,0  ,0  },
			{0  ,255,255,255,255,255,0  ,0  ,0  ,0  },
			{0  ,0  ,0  ,255,255,255,0  ,0  ,255,255},
			{255,0  ,0  ,0  ,0  ,0  ,0  ,255,255,255}};
	
	/*
	//Kernstuek des Algor. ist die checkNeighbour()
	public static void main(String[] args){
		checkNeighbour(px, py);
		presentPixelAnzahl();
		}
	*/
	
	//pixelCounter() setzt den Zaehler anzPixel bei jedem Aufruf einshöher,
	//was aber nur passiert, wenn checkNeighbour() eine bisher ununtersuchte 
	//Nachbarzelle mit dem Wert 255 aufspuert
	public static int pixelCounter() {
		anzPixel += 1;
		//System.out.println("Die momentane Anzahl der zusammenhaengenden und untersuchten Pixel: " + anzPixel);
		return anzPixel;
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
	public static void checkNeighbour(float px_in, float py_in) {
		if((py < (ausgangsArr[px].length - 1)) && (ausgangsArr[px][py + 1] == 255.0f)) {
			pixelCounter();
			py += 1;
			ausgangsArr[px][py] = 100.01f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			py -= 1;
		}//else System.err.println("unten");
		
		if((py < (ausgangsArr[px].length - 1)) && (px > 0) && (ausgangsArr[px - 1][py + 1] == 255.0f)) {
			pixelCounter();
			px -= 1;
			py += 1;
			ausgangsArr[px][py] = 100.02f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
			py -= 1;
		}//else System.err.println("unten links");
		
		if((px > 0) && (ausgangsArr[px - 1][py] == 255.0f)) {
			pixelCounter();
			px -= 1;
			ausgangsArr[px][py] = 100.03f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
		}//else System.err.println("links");
		
		if((py > 0) && (px > 0) && (ausgangsArr[px - 1][py - 1] == 255.0f)) {
			pixelCounter();
			px -= 1;
			py -= 1;
			ausgangsArr[px][py] = 100.04f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px += 1;
			py += 1;
		}//else System.err.println("oben links");

		if((py > 0) && (ausgangsArr[px][py - 1] == 255.0f)) {
			pixelCounter();
			py -= 1;
			ausgangsArr[px][py] = 100.05f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			py += 1;
		}//else System.err.println("oben");

		if((px < (ausgangsArr.length - 1)) && (py >0) && (ausgangsArr[px + 1][py - 1] == 255.0f)) {
			pixelCounter();
			px += 1;
			py -= 1;
			ausgangsArr[px][py] = 100.06f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
			py += 1;
		}//else System.err.println("oben rechts");
		
		if((px < (ausgangsArr.length - 1)) && (ausgangsArr[px + 1][py] == 255.0f)) {
			pixelCounter();
			px += 1;
			ausgangsArr[px][py] = 100.07f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
		}//else System.err.println("rechts");

		if((px < (ausgangsArr.length - 1)) && (py < (ausgangsArr[px].length - 1)) && (ausgangsArr[px + 1][py + 1] == 255.0f)) {
			pixelCounter();
			px += 1;
			py += 1;
			ausgangsArr[px][py] = 100.08f;
			//System.out.println(ausgangsArr[px][py]);
			checkNeighbour(px, py);
			px -= 1;
			py -= 1;
		}//else System.err.println("unten rechts");
    }
	public static void presentPixelAnzahl() {
		System.out.println("Die finale Anzahl der zusammenhaengenden und untersuchten Pixel mit ");
		System.out.println("der urspruenglich abgespeicherten Gleitkommerzahl 255.0 belauft sich auf " + anzPixel + "  :)");
		System.out.print("Die Wasserfläche hat damit eine angenäherte Oberfläche von " + anzPixel * 25 + " m^2");
	}
	
}
