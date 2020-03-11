package org.lakedetection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SeeErkennenTest extends TestCase{

	/**
     * Create the test case*/
     
     public static void main (String[] args){
		float[][] b = new float[6][5];
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
		
		
		
		SeeErkennen.show(SeeErkennen.connect(b,c));
		
		//System.out.println("b.length: " +b.length + ", b[0].length: " + b[0].length); 
				
	}
     
     /* @param testName name of the test case
     */
	public SeeErkennenTest( String testName )
    {
        super( testName );
    }
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SeeErkennenTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSeeErkennen()
    {
        assertTrue( true );
    }
}
