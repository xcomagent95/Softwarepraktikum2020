package org.lakedetection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ROPsTest extends TestCase{

	/**
     * Create the test case*/
     
     public static void main (String[] args){
		/*float[][] b = new float[6][5];
		b[0][0] = 120.0f;
		b[0][1] = 120.0f;
		b[0][2] = 120.0f;
		b[0][3] = 120.0f;
		b[0][4] = 120.0f;
		b[1][0] = 140.0f;
		b[1][1] = 140.0f;
		b[1][2] = 140.0f;
		b[1][3] = 140.0f;
		b[1][4] = 120.0f;
		b[2][0] = 160.0f;
		b[2][1] = 160.0f;
		b[2][2] = 160.0f;
		b[2][3] = 140.0f;
		b[2][4] = 120.0f;
		b[3][0] = 180.0f;
		b[3][1] = 180.0f;
		b[3][2] = 160.0f;
		b[3][3] = 140.0f;
		b[3][4] = 120.0f;
		b[4][0] = 200.0f;
		b[4][1] = 180.0f;
		b[4][2] = 160.0f;
		b[4][3] = 140.0f;
		b[4][4] = 120.0f;
		b[5][0] = 200.0f;
		b[5][1] = 180.0f;
		b[5][2] = 160.0f;
		b[5][3] = 140.0f;
		b[5][4] = 120.0f;
		
		
		float[][] c = new float[6][5];
		c[0][0] = 100.0f;
		c[0][1] = 100.0f;
		c[0][2] = 100.0f;
		c[0][3] = 100.0f;
		c[0][4] = 100.0f;
		c[1][0] = 120.0f;
		c[1][1] = 120.0f;
		c[1][2] = 120.0f;
		c[1][3] = 120.0f;
		c[1][4] = 100.0f;
		c[2][0] = 140.0f;
		c[2][1] = 140.0f;
		c[2][2] = 140.0f;
		c[2][3] = 120.0f;
		c[2][4] = 100.0f;
		c[3][0] = 160.0f;
		c[3][1] = 160.0f;
		c[3][2] = 140.0f;
		c[3][3] = 120.0f;
		c[3][4] = 100.0f;
		c[4][0] = 180.0f;
		c[4][1] = 160.0f;
		c[4][2] = 140.0f;
		c[4][3] = 120.0f;
		c[4][4] = 100.0f;
		c[5][0] = 180.0f;
		c[5][1] = 160.0f;
		c[5][2] = 140.0f;
		c[5][3] = 120.0f;
		c[5][4] = 100.0f;
		*/
		
    	float[][] d = new float[15][15];
    	
    	// d befüllen mit 10 in größe 15x15
    	// ANDERE TESTMATRIX NOTWENDIG!!!!!
    	for(int i=0; i<7; i++) {
    		for(int j=0; j<15; j++) {
    			d[i][j] = 0.0f;
    		}
    	}
    	for(int i=8; i<15; i++) {
    		for(int j=5; j<15; j++) {
    			d[i][j] = 0.0f;
    		}
    	}
    	d[7][0] = 0.0f;
    	d[7][1] = 0.0f;
    	d[7][2] = 0.0f;
    	d[7][3] = 0.0f;
    	d[7][4] = 0.0f;
    	d[7][5] = 0.0f;
    	d[7][6] = 0.0f;
    	d[7][7] = 1000.0f;
    	d[7][8] = 0.0f;
    	d[7][9] = 0.0f;
    	d[7][10] = 0.0f;
    	d[7][11] = 0.0f;
    	d[7][12] = 0.0f;
    	d[7][13] = 0.0f;
    	d[7][14] = 0.0f;
    	
    	 
		ROPs rops = new ROPs();
		//rops.show(d);
		System.out.println();
		//rops.show(rops.smoothing(d));
		System.out.println();
		//rops.show(rops.gaussFilter(d));
		//rops.show(rops.connect(b,c));
		System.out.println("???");
		
		//System.out.println("b.length: " +b.length + ", b[0].length: " + b[0].length); 
				
	}
     
     /* @param testName name of the test case
     */
	public ROPsTest( String testName )
    {
        super( testName );
    }
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ROPsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSeeErkennen()
    {
        assertTrue( true );
    }
}
