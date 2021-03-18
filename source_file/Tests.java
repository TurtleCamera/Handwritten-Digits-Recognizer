package neuralNetwork;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tests {
	// THIS CLASS IS JUST FOR TESTING PURPOSES
	// IT'S NOT PART OF THE FINAL PROGRAM
	public static void main(String[] args) {
		// Test matrix multiplication
		float [][] A1 = new float[3][4];
		Node [] x1 = new Node[4];
		float [] b1 = new float[4];
		Node [] r1 = new Node[3];
		
		A1[0][0] = 5;
		A1[0][1] = -2;
		A1[0][2] = 3;
		A1[0][3] = 1;
		A1[1][0] = -3;
		A1[1][1] = 6;
		A1[1][2] = 7;
		A1[1][3] = 2;
		A1[2][0] = 0;
		A1[2][1] = -1;
		A1[2][2] = 4;
		A1[2][3] = 8;
		
		x1[0] = new Node(0);
		x1[1] = new Node(0);
		x1[2] = new Node(0);
		x1[3] = new Node(0);
		
		x1[0].activation = 9;
		x1[1].activation = 5;
		x1[2].activation = 0;
		x1[3].activation = -3;
		
		b1[0] = 4;
		b1[1] = -3;
		b1[2] = 0;

		r1[0] = new Node(0);
		r1[1] = new Node(0);
		r1[2] = new Node(0);
		
		r1[0].activation = 36;
		r1[1].activation = -6;
		r1[2].activation = -29;
		
		testMatrixMultiplication(A1, x1, b1, r1);
		
		float [][] A2 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 
						  {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}};
		Node [] x2 = new Node[10];
		for(int i = 0; i < x2.length; i ++) {
			x2[i] = new Node(0);
			x2[i].activation = i;
		}
		float [] b2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		Node [] r2 = new Node[10];
		for(int i = 0; i < r2.length; i ++) {
			r2[i] = new Node(0);
			r2[i].activation = i;
			
			for(int j = 0; j < r2.length; j ++) {
				r2[i].activation += Math.pow(j, 2);
			}
		}
		
		testMatrixMultiplication(A2, x2, b2, r2);
		
		printFileName();
	}
	
	// REMOVE THE SIGMOID FUNCTION WHEN TESTING THIS!!!
	public static void testMatrixMultiplication(float [][] A, Node [] x, float [] b, Node [] r) {
		// Test if the output of method matrixMultiplication matches the resulting vector
		Node [] tempResult = new Node[r.length];
		for(int i = 0; i < r.length; i ++) {
			tempResult[i] = new Node(0);
		}
		
		Main.matrixMultiplication(A, x, b, tempResult);
		
		boolean passed = true;
		for(int i = 0; i < r.length; i ++) {
			// System.out.println(tempResult[i].activation + " " + r[i].activation);
			if(r[i].activation != tempResult[i].activation) {
				passed = false;
				break;
			}
		}
		
		isPassed(passed);
	}
	
	public static void printFileName() {
		// This is just to see if the date prints correctly
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
		LocalDateTime date = LocalDateTime.now();
		
		// Print file name
		System.out.println("neuralNetwork_" + formatter.format(date) + ".txt");
	}
	
	public static void isPassed(boolean passed) {
		if(passed) {
			System.out.println("PASSED");
		}
		else {
			System.out.println("FAILED");
		}
	}
}
