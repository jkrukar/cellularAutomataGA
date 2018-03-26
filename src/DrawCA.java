//
///**
// * @author Brandon wade
// *
// */
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.util.Random;
//
//import javax.swing.*;
//
//
//public class DrawCA extends JPanel {
//
//	/****************************************************************************************
///*                          Global Variables
///****************************************************************************************/
//
//	/*base constructor that iniatlize random rules */
//	/* There is a better way to initalize the rules instead of the constructor
//	 * I will optimize this later on
//	 */
//	CA generateRules = new CA();
//
//	private int width = 800;  // width of jframe window
//	private int height = 500; //height of jfram window
//
//	private int radius = 1; //how many neighbors are we checking?
//
//	private int blackCount = 0; // how many 1's in first generation
//	private int whiteCount = 0;  // how many 0's in first generation
//
//	private int lastgen_blackCount = 0;  // how many 1's in last generation
//	private int lastgen_whiteCount = 0;  // how many 0's in last generation
//
//	private int numOfGenerations = 95; // # of generations that will be displayed
//
//	private int[] rule30 = generateRules.rule30; // rule30 = {0,1,1,1,1,0,0,0}
//	private boolean[] rule30_b = generateRules.rule30_b;
//
//	//need to covert Trues and False to 0's and 1's
//	private int[] randomrule = generateRules.randomRuleSet;
//
//	/** How many black and white **/
//	private static int[] firstGeneration;
//
//
//	private int[] lastGeneration;
//
//
//	/** if mix fail, if all 0's fail, if all 1's passed **/
//	private boolean Pass = false;
//
//    /** Pass in either an arrary of True and False's or 0 and 1's for the first parameter **/
//	//CA ca = new CA(rule30,width,height); // random rule set
//	//CA ca = new CA(rule30,width,height); // rule 30
//	CA ca = new CA(rule30_b,width,height); // rule 30 as booleans
//
//
//
//
//	/***************************************************************************************
//	 *
//	 * @param g
///***************************************************************************************/
//	@Override
//	protected void paintComponent(Graphics g)
//	{
//
//		super.paintComponent(g);
//
//		for(int i = 0; i < numOfGenerations;i++)
//		{
//			ca.generate1D();
//			displayCA(g);
//		}
//
//		loadFirstGeneration();
//		loadLastGeneration();
//
//
//		printResults();
//
//	}
//	/***************************************************************************************
// /*
/// * @return the size of the window
///***************************************************************************************/
//	@Override
//	public Dimension getPreferredSize() {
//		// so that our GUI is big enough
//		return new Dimension(width,height);
//	}
//
//	/***************************************************************************************
///*  create the GUI that will display CA
///*   Currently there is a bug if you resize the window a new generation will be populated
///*   So for now i took away the ability to resize the frame, will look into more later
///*
///***************************************************************************************/
//	// create the GUI explicitly on the Swing event thread
//	private static void createAndShowGui()
//	{
//		DrawCA mainPanel = new DrawCA();
//
//		JFrame frame = new JFrame("CA");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().add(mainPanel);
//		frame.pack();
//		frame.setLocationByPlatform(true);
//		frame.setResizable(false);
//		frame.setVisible(true);
//	}
//
//	/**********************************************************************************************
//	 *
//	 * @param g
//	 *  This method is straight forward:
//	 *  1. Loop through the grid looking for either a 0 or 1
//	 *  2. If 0 draw a a white rectangle, 1 draw a black rectangle at current i position in matrix
///********************************************************************************************/
//
//	void displayCA(Graphics g)
//	{
//		int generation = ca.generation; // current generation to operate on
//		int[] cells = ca.cells;    // matrix of 0 and 1's
//
//
//		for (int i = 0; i < cells.length; i++)
//		{
//			if (cells[i] == 1)
//
//			{
//				g.setColor(Color.BLACK);
//			}
//			// Off State
//			else
//			{
//				g.setColor(Color.WHITE);
//			}
//
//			// multiplying by anything bigger than 3
//			// makes the rect too big, values worth
//			// playing with
//			g.drawRect(i*3, generation*3,3,3);
//		}
//	}
///*************************************************************************
// * Retrieve cells 152-159 which represent the last generation string and
// *  store it in an array called lastGeneration
// /**************************************************************************/
//	public void loadLastGeneration()
//	{
//		int[] cells = ca.cells;    // matrix of 0 and 1's
//		lastGeneration = new int[8];
//		int start = 0;
//		for(int i = cells.length - 8; i < cells.length; i++)
//		{
//			lastGeneration[start] = cells[i];
//
//			if(lastGeneration[start] == 0)
//			{
//				lastgen_whiteCount++;
//
//			}
//			else
//			{
//				lastgen_blackCount++;
//			}
//
//			start++;
//
//		}
//	}
//
//	public void loadFirstGeneration()
//	{
//		int[] cells = ca.cells;    // matrix of 0 and 1's
//		firstGeneration = new int[8];
//		for(int i = 0; i < 8; i++)
//		{
//			firstGeneration[i] = cells[i];
//			if(firstGeneration[i] == 0)
//			{
//				whiteCount = whiteCount + 1;
//			}
//			else
//			{
//				blackCount++;
//			}
//
//		}
//	}
///************************************************************************
///*
///*   Print the results of the First Generation:
//*
//*   String of 0 and 1's representing the state
//*   Number of 1's
//*   Number of 0's
//*
///*
///************************************************************************/
//
//	public void printFirstGeneration()
//	{
//		System.out.println("First Generation Results");
//
//		for(int i = 0; i < 8; i++)
//		{
//			System.out.print(firstGeneration[i]);
//		}
//
//		System.out.println();
//		System.out.println("Number of 1's = " + blackCount);
//		System.out.println("Number of 0's = " + whiteCount);
//	}
///************************************************************************
///*
///*   Print the results of the Last Generation:
//*
//*   String of 0 and 1's representing the state
//*   Number of 1's
//*   Number of 0's
//*
///*
///************************************************************************/
//	public void printLastGeneration()
//	{
//
//
//		System.out.println("Last Generation Results");
//
//		for(int i = 0; i < 8; i++)
//		{
//			System.out.print(lastGeneration[i]);
//
//		}
//
//
//		System.out.println();
//		System.out.println("Number of 1's = " + lastgen_blackCount);
//		System.out.println("Number of 0's = " + lastgen_whiteCount);
//
//		System.out.println("Pass or Fail?");
//		/** IF the last generation contains 8 1's then we pass **/
//		if(lastgen_blackCount == 8)
//		{
//			System.out.println("Pass");
//			Pass = true;
//		}
//		/** If the last generation contains a mixture of 0's and 1's fail **/
//		else
//		{
//
//			System.out.println("Fail");
//			Pass = false;
//		}
//	}
///***********************************************************************
// *  Print the results of the first and last generation
///***********************************************************************/
//
//	public void printResults()
//	{
//		printFirstGeneration();
//		printLastGeneration();
//	}
//
//
///************************************************************************
// *  								Main Function
// * @param args
// *************************************************************************/
//	public static void main(String[] args)
//	{
//
//
//		SwingUtilities.invokeLater(new Runnable(){
//			public void run(){
//				createAndShowGui();
//
//			}
//		});
//
//	}
//
//}
