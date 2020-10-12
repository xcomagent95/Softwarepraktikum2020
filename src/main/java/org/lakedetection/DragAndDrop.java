package org.lakedetection;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.ArrayList;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
 
/**
 * @author Stefan, https://st-page.de
 * 
 */
public class DragAndDrop extends JFrame
{
 
    private static final long serialVersionUID = 1L;
 
    public DragAndDrop()
    {
    	setSize(new Dimension(363, 363));
    	setPreferredSize(new Dimension(300, 200));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Java Drag And Drop");
	SpringLayout springLayout = new SpringLayout();
	getContentPane().setLayout(springLayout);
 
	JLabel lblDraganddrop = new JLabel("Drag And Drop!");
	springLayout.putConstraint(SpringLayout.NORTH, lblDraganddrop, 10, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, lblDraganddrop, 10, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, lblDraganddrop, 315, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, lblDraganddrop, -10, SpringLayout.EAST, getContentPane());
	lblDraganddrop.setHorizontalTextPosition(SwingConstants.CENTER);
	lblDraganddrop.setHorizontalAlignment(SwingConstants.CENTER);
	lblDraganddrop.setBorder(new LineBorder(new Color(0, 0, 0)));
	lblDraganddrop.setAlignmentX(Component.CENTER_ALIGNMENT);
	lblDraganddrop.setVerticalAlignment(SwingConstants.CENTER);
	lblDraganddrop.setVerticalTextPosition(SwingConstants.CENTER);
	lblDraganddrop.setFont(new Font("Tahoma", Font.PLAIN, 48));
	getContentPane().add(lblDraganddrop);
	
	setVisible(true);
	
	new DropTarget(lblDraganddrop, new DropTargetListener()
	{
	    public void drop(DropTargetDropEvent dtde)
	    {
			try
			{
			    Transferable tr = dtde.getTransferable();
			    DataFlavor[] flavors = tr.getTransferDataFlavors();
			    ArrayList<File> fileNames = new ArrayList<File>();
			    for (int i = 0; i < flavors.length; i++)
			    {
					if (flavors[i].isFlavorJavaFileListType())
					{
					    dtde.acceptDrop(dtde.getDropAction());
					    @SuppressWarnings("unchecked")
					    java.util.List<File> files = (java.util.List<File>) tr.getTransferData(flavors[i]);
					    for (int k = 0; k < files.size(); k++)
					    {
						fileNames.add(files.get(k));
						// Die Dateien werden mit dem spezifischen Programm geöffnet. 
						Desktop.getDesktop().open(files.get(k));
						// Der Dateiname wird in der Comandozeile ausgegeben.
						///System.out.print(files.get(k)); // Besser ist die Rückgabe eines String
						String x = files.get(k).toString();
						Loadzip dataset = new Loadzip(x);
						
						RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, 300, 300); //VV-Band
				  		ROPs rops = new ROPs();

				  		float[][] medianArray = rops.medianFilter(amplitude_vv.getArray());
				  		float[][] medianAndGaussArray = rops.medianFilter(rops.gaussFilter(amplitude_vv.getArray()));
				  		float[][] blackArray = rops.makeBlack(medianAndGaussArray);
				  		int[][] normalisedArray = ArrayUtils.normaliseValues(blackArray, ArrayUtils.getMin(blackArray), ArrayUtils.getMax(blackArray));

				  		//float[][] gaussArray = rops.gaussFilter(amplitude_vv.getArray());
				  		//int[][] normalisedArray = ArrayUtils.normaliseValues(gaussArray, ArrayUtils.getMin(gaussArray), ArrayUtils.getMax(gaussArray));
				  		// int[][] normalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), amplitude_vv.getLowestPixel(), amplitude_vv.getHighestPixel());
				  		int[][] rgbArray = ArrayUtils.convertToRGB(normalisedArray);
				  		ArrayUtils.arrayToImage(rgbArray, "/Users/josefinabalzer/Desktop/TestBilder/", "TestPic");
					    }
		 
					    dtde.dropComplete(true);
					}
			    }
			    return;
			}
			
			catch (Throwable t)
			{
			    t.printStackTrace();
			}
		
		//dtde.rejectDrop();
	    }
 
	    @Override
	    public void dragEnter(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dragOver(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dropActionChanged(DropTargetDragEvent dtde)
	    {}
 
	    @Override
	    public void dragExit(DropTargetEvent dte)
	    {}
 
	});
	
    }
   
    
    public static void main (String[] args) {
    	new DragAndDrop();
    }
 }