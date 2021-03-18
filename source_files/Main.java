package neuralNetwork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {
	
	// Some constants
	public static final int SCREEN_WIDTH = 1920; // Width of the 
	public static final int SCREEN_HEIGHT = 1080; // Height of the frame
	
	public static final int HIDDEN_LAYER_NODES = 16; // Number of nodes in the hidden layers
	public static final int OUTPUT_LAYER_NODES = 10; // Number of nodes in the output layer
	
	public static final int INPUT_LAYER = 0; // Input layer value
	public static final int HIDDEN_LAYER1 = 1; // Hidden layer 1 value
	public static final int HIDDEN_LAYER2 = 2; // Hidden layer 2 value
	public static final int OUTPUT_LAYER = 3; // Output layer value
	
	public static final int LAST_NINE_IMAGES = 9; // How many images should be in the lastNineImages arraylist
	
	public static final int AVERAGE_STOCHASTIC_DERIVATIVES = 10; // Used to average the weights and biases in the stochastic weight matrices and bias vectors
	public static final int MOD_BY_TEN = 10; // Used to check which image we are in within an image group
	
	public static final int GRADIENT_COMPONENT_UNITS = 1; // Multiplied with ∂C/∂b and ∂C/∂w to accelerate learning if these values are small (this is an experimental value, but it should be 1)
	
	public static InputFrame frame; // The frame that will display the game
	public static PaintPanel paint; // The panel that draws the objects
	
	public static FileInputStream fisImages; // File input stream for the images
	public static DataInputStream disImages; // Data input stream, which we use to read these pixel values
	public static FileInputStream fisLabels; // File input stream for the labels
	public static DataInputStream disLabels; // Data input stream, which we use to read these label values
	
	public static int numImages; // Number of images in the training file
	public static int numLabels; // Number of labels in the training file
	public static int count; // How much training data we've gone through
	
	public static int rows; // Number of rows for a single image
	public static int cols; // Numbers of columns for a single image
	
	public static int[][] currentImage; // Matrix containing the current grayscale pixels of an image
	public static int currentLabel = -1; // Current label for the current image in the above matrix (-1 means no image loaded)
	public static ArrayList<int[][]> lastNineImages; // Holds the last 9 images (not including the one we're currently looking at) we've iterated through
	
	public static Node inputLayer[]; // Nodes for the input layer
	public static Node hiddenLayer1[]; // Nodes for the input layer
	public static Node hiddenLayer2[]; // Nodes for the input layer
	public static Node outputLayer[]; // Nodes for the input layer
	
	public static float weightMatrix1[][]; // Weight matrix between input layer and hidden layer 1
	public static float weightMatrix2[][]; // Weight matrix between hidden layer 1 and hidden layer 2
	public static float weightMatrix3[][]; // Weight matrix between hidden layer 2 and output layer 
	
	public static float[] biasVector1; // Bias vector for hidden layer 1
	public static float[] biasVector2; // Bias vector for hidden layer 2
	public static float[] biasVector3; // Bias vector for output layer

	public static float partialDerivativeWeightMatrix1[][]; // Partial derivative weight matrix between input layer and hidden layer 1
	public static float partialDerivativeWeightMatrix2[][]; // Partial derivative weight matrix between hidden layer 1 and hidden layer 2
	public static float partialDerivativeWeightMatrix3[][]; // Partial derivative weight matrix between hidden layer 2 and output layer 
	
	public static float[] partialDerivativeBiasVector1; // Partial derivative bias vector for hidden layer 1
	public static float[] partialDerivativeBiasVector2; // Partial derivative bias vector for hidden layer 2
	public static float[] partialDerivativeBiasVector3; // Partial derivative bias vector for output layer
	
	public static float stochasticPartialDerivativeWeightMatrix1[][]; // Average of the 10 partial derivatives in the weight matrix between input layer and hidden layer 1
	public static float stochasticPartialDerivativeWeightMatrix2[][]; // Average of the 10 partial derivatives in the weight matrix between hidden layer 1 and hidden layer 2
	public static float stochasticPartialDerivativeWeightMatrix3[][]; // Average of the 10 partial derivatives in the weight matrix between hidden layer 2 and output layer 
	
	public static float[] stochasticPartialDerivativeBiasVector1; // Average of the 10 partial derivatives in the bias vector for hidden layer 1
	public static float[] stochasticPartialDerivativeBiasVector2; // Average of the 10 partial derivatives in the bias vector for hidden layer 2
	public static float[] stochasticPartialDerivativeBiasVector3; // Average of the 10 partial derivatives in the bias vector for output layer
	
	public static float desiredOutput[]; // An array (vector) containing all of the desired activations for a given image

	public static boolean inTraining = true; // While this is true, we train the neural network (when false, we enter the accuracy test)
	public static boolean loaded = false; // Tells the PaintPanel when it can start painting
	public static boolean next = false; // Controlled by the user-tells when to load the next image
	public static boolean first = true; // Prevents the first (blank) image to be copied into the lastNineImages arraylist
	public static boolean autoAdvance = false; // Auto-advance option
	public static boolean darkMode = false; // Option to turn on a dark background
	public static boolean disableWeightRestriction = false; // When this option is enabled, weights are restricted to [-1, 1] (note that it's recommended that this option remain constant throughout training)
	public static boolean inTesting = false; // Tells when the program is testing the neural network
	public static boolean fileWritten = false; // Tells whether a file has been written
	
	public static float cost; // The cost of the current image displayed
	public static float totalCost; // The total cost of the last 10 images displayed
	
	public static BufferedImage costFunction; // Stores the image of the cost function
	public static BufferedImage avgCostFunction; // Stores the image of the average cost function
	
	public static int guessedNumber = 0; // The number that the neural network guesses after processing an image
	
	public static int numCorrect = 0; // Used during the accuracy test phase to determine the % of images read correctly
	
	public static BufferedWriter networkFile; // Ultimately writes the network and statistics file
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		// Check to make sure the test images and labels are there
		try {
			fisImages = new FileInputStream("t10k-images.idx3-ubyte");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File \"t10k-images.idx3-ubyte\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		
		try {
			fisLabels = new FileInputStream("t10k-labels.idx1-ubyte");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File \"t10k-labels.idx1-ubyte\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		
		// Create file streams
		try {
			fisImages = new FileInputStream("train-images.idx3-ubyte");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File \"train-images.idx3-ubyte\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		disImages = new DataInputStream(fisImages);
		
		try {
			fisLabels = new FileInputStream("train-labels.idx1-ubyte");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File \"train-labels.idx1-ubyte\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		disLabels = new DataInputStream(fisLabels);
		
		// Create desiredOutput array
		desiredOutput = new float[OUTPUT_LAYER_NODES];
		
		// Load equation images
		try {
			costFunction = ImageIO.read(new File("costFunction.png"));
		} catch (IOException e) {
			System.out.println("ERROR: File \"costFunction.png\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		try {
			avgCostFunction = ImageIO.read(new File("avgCostFunction.png"));
		} catch (IOException e) {
			System.out.println("ERROR: File \"avgCostFunction.png\" is missing. Closing program...");
			Thread.sleep(5000);
			System.exit(0);
		}
		
		// Read the initial values of each file
		System.out.println("Magic number label file: " + disLabels.readInt() + ".");
		System.out.println("Magic number image file: " + disImages.readInt() + ".");
		
		System.out.println("Loaded " + (numLabels = disLabels.readInt()) + " labels.");
		System.out.println("Loaded " + (numImages = disImages.readInt()) + " images.");

		System.out.println("Loaded " + (rows = disImages.readInt()) + " rows.");
		System.out.println("Loaded " + (cols = disImages.readInt()) + " cols.");
		
		// Create the GUI
		createFrame();
		createPanel();
		
		// Create image matrix and the lastNineImages arraylist
		currentImage = new int[rows][cols];
		lastNineImages = new ArrayList<int[][]>();
		count = 0;
		
		// Create the node layers
		inputLayer = new Node[rows * cols];
		for(int i = 0; i < inputLayer.length; i ++) {
			inputLayer[i] = new Node(INPUT_LAYER);
		}
		
		hiddenLayer1 = new Node[HIDDEN_LAYER_NODES];
		for(int i = 0; i < hiddenLayer1.length; i ++) {
			hiddenLayer1[i] = new Node(HIDDEN_LAYER1);
		}
		
		hiddenLayer2 = new Node[HIDDEN_LAYER_NODES];
		for(int i = 0; i < hiddenLayer2.length; i ++) {
			hiddenLayer2[i] = new Node(HIDDEN_LAYER2);
		}
		
		outputLayer = new Node[OUTPUT_LAYER_NODES];
		for(int i = 0; i < outputLayer.length; i ++) {
			outputLayer[i] = new Node(OUTPUT_LAYER);
		}
		
		// Create and randomize weight matrices (rows correspond to the node number at the end of the weight connection while columns correspond to the node number at the beginning of the weight connection
		weightMatrix1 = new float[HIDDEN_LAYER_NODES][rows * cols];
		for(int i = 0; i < weightMatrix1.length; i ++) {
			for(int j = 0; j < weightMatrix1[i].length; j ++) {
				weightMatrix1[i][j] = (float) ((Math.random() * 2) - 1);
			}
		}
		
		weightMatrix2 = new float[HIDDEN_LAYER_NODES][HIDDEN_LAYER_NODES];
		for(int i = 0; i < weightMatrix2.length; i ++) {
			for(int j = 0; j < weightMatrix2[i].length; j ++) {
				weightMatrix2[i][j] = (float) ((Math.random() * 2) - 1);
			}
		}
		
		weightMatrix3 = new float[OUTPUT_LAYER_NODES][HIDDEN_LAYER_NODES];
		for(int i = 0; i < weightMatrix3.length; i ++) {
			for(int j = 0; j < weightMatrix3[i].length; j ++) {
				weightMatrix3[i][j] = (float) ((Math.random() * 2) - 1);
			}
		}
		
		// Initialize the gradient matricies
		partialDerivativeWeightMatrix1 = new float[HIDDEN_LAYER_NODES][rows * cols];
		partialDerivativeWeightMatrix2 = new float[HIDDEN_LAYER_NODES][HIDDEN_LAYER_NODES];
		partialDerivativeWeightMatrix3 = new float[OUTPUT_LAYER_NODES][HIDDEN_LAYER_NODES]; 
		
		// Create and randomize the bias vectors with biases in the range -5 to 5
		biasVector1 = new float[HIDDEN_LAYER_NODES];
		for(int i = 0; i < biasVector1.length; i ++) {
			biasVector1[i] = (float) (Math.random() * 10 - 5);
		}
		
		biasVector2 = new float[HIDDEN_LAYER_NODES];
		for(int i = 0; i < biasVector2.length; i ++) {
			biasVector2[i] = (float) (Math.random() * 10 - 5);
		}
		
		biasVector3 = new float[OUTPUT_LAYER_NODES];
		for(int i = 0; i < biasVector3.length; i ++) {
			biasVector3[i] = (float) (Math.random() * 10 - 5);
		}	
		
		// Create the partial derivative bias vectors
		partialDerivativeBiasVector1 = new float[HIDDEN_LAYER_NODES];
		partialDerivativeBiasVector2 = new float[HIDDEN_LAYER_NODES];
		partialDerivativeBiasVector3 = new float[OUTPUT_LAYER_NODES];

		// Initialize the stochastic weight matrices (what we actually use to adjust the weight values)
		stochasticPartialDerivativeWeightMatrix1 = new float[HIDDEN_LAYER_NODES][rows * cols];
		stochasticPartialDerivativeWeightMatrix2 = new float[HIDDEN_LAYER_NODES][HIDDEN_LAYER_NODES];
		stochasticPartialDerivativeWeightMatrix3 = new float[OUTPUT_LAYER_NODES][HIDDEN_LAYER_NODES]; 
		
		// Initialize the stochastic bias vectors (what we actually use to adjust the bias values)
		stochasticPartialDerivativeBiasVector1 = new float[HIDDEN_LAYER_NODES];
		stochasticPartialDerivativeBiasVector2 = new float[HIDDEN_LAYER_NODES];
		stochasticPartialDerivativeBiasVector3 = new float[OUTPUT_LAYER_NODES];
		
		// Begin painting
		loaded = true;
		
		// The loop for training begins
		while(inTraining) {
			// Check if auto-advance is on and automatically set next to true if it's on
			if(autoAdvance) {
				next = true;
			}
			
			// Go to the next image when the user presses space or enter
			if(next) {
				// Stop the while loop from loading more images
				next = false;
				
				// Don't continue if we're done training
				if(!checkCompleted()) {
					// Get inputs
					createImage();
					
					// Check if we are on the first image of an image group
					if(checkFirstImage()) {
						// We don't check using getTenImages() because the PaintPanel still needs the values to print, so we clear them in the next image group
						clearStochasticWeightsAndBiases();
					}
					
					// Feed forward
					feedForward();
					
					// Calculate the cost and average cost of the current image using the cost function
					calculateCost();
					calculateAverageCost();
					
					// Guess the output
					guessedNumber = guess();
					
					// Finally calculate partial derivatives (and backpropagate when we sum the average gradient vector of the past 10 images)
					backPropagate();
				}
			}
			
			// Redraw the frame
			paint.repaint();
		}
		
		// Change file streams to load in the testing images
		fisImages = new FileInputStream("t10k-images.idx3-ubyte");
		disImages = new DataInputStream(fisImages);
		
		fisLabels = new FileInputStream("t10k-labels.idx1-ubyte");
		disLabels = new DataInputStream(fisLabels);
		
		// Read the initial values of each file
		System.out.println();
		System.out.println("Magic number label file: " + disLabels.readInt() + ".");
		System.out.println("Magic number image file: " + disImages.readInt() + ".");
		
		System.out.println("Loaded " + (numLabels = disLabels.readInt()) + " labels.");
		System.out.println("Loaded " + (numImages = disImages.readInt()) + " images.");

		System.out.println("Loaded " + (rows = disImages.readInt()) + " rows.");
		System.out.println("Loaded " + (cols = disImages.readInt()) + " cols.");
		
		// Reset count (we will use this along with numCorrect to determine accuracy percentage)
		count = 0;
		
		// Clear the partial derivatives of weights and biases since we aren't updating them for the accuracy test
		clearPartialDerivativeWeightsAndBiases();
		
		// Clear the costs and partial derivatives in activations as well
		clearCosts();
		clearPartialDerivativeActivations();
		
		// The loop for accuracy testing begins
		while(inTesting) {
			// Check if auto-advance is on and automatically set next to true if it's on
			if(autoAdvance) {
				next = true;
			}
			
			// Go to the next image when the user presses space or enter
			if(next) {
				// Stop the while loop from loading more images
				next = false;
				
				// Don't continue if we're done testing
				if(!checkCompleted()) {
					// Get inputs
					createImage();
					
					// Feed forward
					feedForward();
					
					// Calculate the cost and average cost of the current image using the cost function (Not used, but still good to look at for testing)
					calculateCost();
					calculateAverageCost();
					
					// Guess the output
					guessedNumber = guess();
						
					// Check this guess
					guessedCorrect();
				}
			}
				
			// Check if the user printed the file
			if(fileWritten) {
				// Stop the computations
				inTesting = false;
				
				// Write the file
				writeFile();
			}
			
			// Redraw the frame
			paint.repaint();
		}
		
		// At this point, we're done
		while(true) {
			// Now we just wait for the user to close the program
			paint.repaint();
		}
	}

	public static void calculateAverageCost() {
		int n = (count - 1) % 10 + 1;
		// Reset the averageCost if we're looking at a new data group
		if(n == 1) {
			Main.totalCost = 0;
		}
		
		// Sum the current cost into the average
		totalCost += cost;
	}
	
	public static void calculateCost() {
		// Cost is the sum of the squared differences of the network's activation outputs and the desired outputs
		cost = 0;
		for(int i = 0; i < OUTPUT_LAYER_NODES; i ++) {
			cost += Math.pow(outputLayer[i].activation - desiredOutput[i], 2);
		}
	}
	
	public static void createPanel() {
		// Create and add the paint panel to the InputFrame
		paint = new PaintPanel();
		paint.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.add(paint);
	    paint.setFocusable(true);
	}
	
	public static void createFrame() {
		//Create the window
	    frame = new InputFrame();
	    frame.setDefaultCloseOperation(InputFrame.EXIT_ON_CLOSE);
	    frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	    
	    //Display the window.
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);     
	    frame.setFocusable(true);
	    frame.addKeyListener(frame);
	}
	
	public static void createImage() throws IOException {
		// Store the last image and remove the 10th image (if there is any)
		// Clone the current image first
		if(!first) {
			lastNineImages.add(0, cloneCurrentImage());
		}
		else {
			first = false;
		}
		
		if(lastNineImages.size() > LAST_NINE_IMAGES) {
			lastNineImages.remove(LAST_NINE_IMAGES);
		}
		
		// Apparently, to draw the image, you draw columns from left to right instead of rows from top to bottom
		int inputNodeCount = 0;
		for(int i = 0; i < cols; i ++) {
			for(int j = 0; j < rows; j ++) {
				currentImage[j][i] = disImages.readUnsignedByte();
				inputLayer[inputNodeCount].unsquishedActivation = currentImage[j][i];
				
				inputNodeCount ++;
			}
		}
		
		// Squish all inputLayer activations
		squishInputActivations();
		
		// Advance image count and set the label
		count ++;
		currentLabel = disLabels.readUnsignedByte();
		
		// Reset the desired output array and fill the desired node with 1.0
		for(int i = 0; i < desiredOutput.length; i ++) {
			desiredOutput[i] = 0;
		}
		desiredOutput[currentLabel] = 1.0f;
	}
	
	public static int[][] cloneCurrentImage() {
		int[][] clone = new int[rows][cols];
		
		for(int i = 0; i < cols; i ++) {
			for(int j = 0; j < rows; j ++) {
				clone[j][i] = currentImage[j][i];
			}
		}
		
		return clone;
	}
	
    public static void invertImages() {
    	// Invert the color of the cost function image for dark mode
        for (int i = 0; i < costFunction.getWidth(); i ++) {
            for (int j = 0; j < costFunction.getHeight(); j ++) {
            	// Get the color of the pixel
                Color color = new Color(costFunction.getRGB(i, j));
                color = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                costFunction.setRGB(i, j, color.getRGB());
            }
        }
        
        // Now do the same for the average cost function
        for (int i = 0; i < avgCostFunction.getWidth(); i ++) {
            for (int j = 0; j < avgCostFunction.getHeight(); j ++) {
            	// Get the color of the pixel
                Color color = new Color(avgCostFunction.getRGB(i, j));
                color = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                avgCostFunction.setRGB(i, j, color.getRGB());
            }
        }
    }
	
    public static void squishInputActivations() {
    	// NOTE: THIS IS NOT THE SAME AS THE SIGMOID FUNCTION. THIS FUNCTION JUST TAKES THE PROPORTION OF 255 FOR THE UNSQUISHED ACTIVATION 
    	for(int i = 0; i < inputLayer.length; i ++) {
    		inputLayer[i].activation = inputLayer[i].unsquishedActivation / 255.0f; 
    	}
    }
    
    public static void feedForward() {
    	// Feed input layer into hidden layer 1
    	matrixMultiplication(weightMatrix1, inputLayer, biasVector1, hiddenLayer1);
    	
    	// Feed hidden layer 1 into hidden layer 2
    	matrixMultiplication(weightMatrix2, hiddenLayer1, biasVector2, hiddenLayer2);
    	
    	// Feed hidden layer 1 into hidden layer 2
    	matrixMultiplication(weightMatrix2, hiddenLayer1, biasVector2, hiddenLayer2);
    	
    	// Feed hidden layer 2 into output layer
    	matrixMultiplication(weightMatrix3, hiddenLayer2, biasVector3, outputLayer);
    }
    
    public static void matrixMultiplication(float weightMatrix[][], Node layerVector[], float biasVector[], Node resultingVector[]) {
    	// First clear the resulting vector (don't clear the input layer because it's constantly being updated already
    	clearResultingVector(resultingVector);
    	
    	// In terms of linear algebra, to calculate the resultingVector (i.e. the layer we are feeding forward to), we simply use the Ax + b = r formula
    	// where A is the weight matrix, x is the layer vector containing the activations of the nodes we want to feed forward, b is the bias vector, and
    	// r is the resulting vector we are feeding into
    	for(int r = 0; r < weightMatrix.length; r ++) {
    		// Columns of weightMatrix, rows of layerVector, rows of biasVector, and rows of resultingVector are assumed to be equal
    		for(int c = 0; c < weightMatrix[0].length; c ++) {
    			// Multiply row of weightMatric to column of layerVector and sum the result
    			resultingVector[r].unsquishedActivation += weightMatrix[r][c] * layerVector[c].activation;
    		}
    		
    		// Then add the bias
    		resultingVector[r].unsquishedActivation += biasVector[r];
    		
    		// Finally, squish the resulting activation value by using the sigmoid function
    		resultingVector[r].activation = sigmoid(resultingVector[r].unsquishedActivation);
    	}
    }
    
    public static void clearResultingVector(Node resultingVector[]) {
    	for(int i = 0; i < resultingVector.length; i ++) {
    		// Don't need to change normal activation because it will be calculated anyway
    		resultingVector[i].unsquishedActivation = 0;
    	}
    }
    
	// The sigmoid function used to translate the numbers to a value in the range of 0 to 1
	public static float sigmoid(float x) {
		return (float) (1 / (1 + Math.pow((float) Math.E, -1 * x)));
	}
	
	public static int guess() {
		// Determine the output node with the highest activation and return its index
		int guessIndex = 0;
		
		for(int i = 1; i < outputLayer.length; i ++) {
			if(outputLayer[i].activation > outputLayer[guessIndex].activation) {
				guessIndex = i;
			}
		}
		
		return guessIndex;
	}
    
	// The derivative of the sigmoid function
	public static float derivativeSigmoid(float x) {
		return (float) (Math.pow((float) Math.E, -1 * x) / Math.pow(1 + Math.pow((float) Math.E, -1 * x), 2));
	}
	
	public static void backPropagate() {
		// Start propagation from the output weight layer
		deriveWeightMatrix3();
		
		// Propagate the last bias vector before moving on to the next weight layer
		deriveBiasVector3();
		
		// Store summation calculations of ∂C/∂a(L-1) for the hidden layer 2 activations
		deriveHiddenLayer2Activations();
		
		// Now propagate to the second weight layer
		deriveWeightMatrix2();
		
		// Propagate the second bias vector before moving on to the next weight layer
		deriveBiasVector2();
		
		// Store summation calculations of ∂C/∂a(L-2) for the hidden layer 1 activations
		deriveHiddenLayer1Activations();
		
		// Now propagate to the first weight layer
		deriveWeightMatrix1();
		
		// Finally, propagate the first bias vector
		deriveBiasVector1();
		
		// Sum up these weight and bias values in the stochastic matrices and vectors
		stochasticSum();
		
		// If we traversed 10 images, adjust the weights and biases
		if(checkTenImages()) {
			// Now we tell the neural network to learn
			adjustWeightsAndBiases();
		}
	}
	
	public static void deriveWeightMatrix3() {
		// Calculate ∂C/∂w(L) for all weights in the last weight layer
		// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
		for(int j = 0; j < outputLayer.length; j ++) {
			for(int i = 0; i < hiddenLayer2.length; i ++) {
				// Formula: ∂C/∂w(L) = ∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂w(L)
				partialDerivativeWeightMatrix3[j][i] = 2 * (outputLayer[j].activation - desiredOutput[j]) * derivativeSigmoid(outputLayer[j].unsquishedActivation) * hiddenLayer2[i].activation;
				
				// MUST USE THE NEGATIVE GRADIENT VECTOR
				partialDerivativeWeightMatrix3[j][i] *= -1;
			}
		}
	}
	
	public static void deriveBiasVector3() {
		// Calculate ∂C/∂b(L) for all weights in the last weight layer
		// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
		for(int j = 0; j < outputLayer.length; j ++) {
			// Formula: ∂C/∂w(L) = ∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂b(L)
			partialDerivativeBiasVector3[j] = 2 * (outputLayer[j].activation - desiredOutput[j]) * derivativeSigmoid(outputLayer[j].unsquishedActivation) * 1;
				
			// MUST USE THE NEGATIVE GRADIENT VECTOR
			partialDerivativeBiasVector3[j] *= -1;
		}
	}
	
	public static void deriveHiddenLayer2Activations() {
		// Reset the partial derivatives first
		clearPartialDerivativesToActivations(hiddenLayer2);
		
		// Calculate ∂C/∂a(L-1) for all activation nodes in hidden layer 2 (this is just to make calculations easier for later weights, specifically the Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) part in deriveWeightMatrix2())
		// This double for loop will traverse the rows first before moving to the next column in the weight matrix (sum derivatives of each node)
		for(int i = 0; i < hiddenLayer2.length; i ++) {
			for(int j = 0; j < outputLayer.length; j ++) {
				// Formula: ∂C/∂a(L-1) = ∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)
				hiddenLayer2[i].partialDerivative += 2 * (outputLayer[j].activation - desiredOutput[j]) * derivativeSigmoid(outputLayer[j].unsquishedActivation) * weightMatrix3[j][i];
			}
		}
	}
	
	public static void deriveWeightMatrix2() {
		// Calculate ∂C/∂w(L-1) for all weights in the second weight layer
		// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
		for(int i = 0; i < hiddenLayer2.length; i ++) {
			for(int h = 0; h < hiddenLayer1.length; h ++) {
				// Formula: ∂C/∂w(L-1) = Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂w(L-1)
				// Remember that hiddenLayer2[j].partialDerivative stores ∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1) in the summation part of the formula
				partialDerivativeWeightMatrix2[i][h] = hiddenLayer2[i].partialDerivative * derivativeSigmoid(hiddenLayer2[i].unsquishedActivation) * hiddenLayer1[h].activation;
				
				// MUST USE THE NEGATIVE GRADIENT VECTOR
				partialDerivativeWeightMatrix2[i][h] *= -1;
			}
		}
	}
	
	public static void deriveBiasVector2() {
	// Calculate ∂C/∂w(L-1) for all weights in the second weight layer
	// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
	for(int i = 0; i < hiddenLayer2.length; i ++) {
			// Formula: ∂C/∂b(L-1) = Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂b(L-1)
			// Remember that hiddenLayer2[j].partialDerivative stores ∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1) in the summation part of the formula
			partialDerivativeBiasVector2[i] = hiddenLayer2[i].partialDerivative * derivativeSigmoid(hiddenLayer2[i].unsquishedActivation) * 1;
			
			// MUST USE THE NEGATIVE GRADIENT VECTOR
			partialDerivativeBiasVector2[i] *= -1;
		}
	}
	
	public static void deriveHiddenLayer1Activations() {
		// Reset the partial derivatives first
		clearPartialDerivativesToActivations(hiddenLayer1);
		
		// Calculate ∂C/∂a(L-2) for all activation nodes in hidden layer 1 (this is just to make calculations easier for later weights, specifically the Σ(Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2)) part in deriveWeightMatrix1())
		// This double for loop will traverse the rows first before moving to the next column in the weight matrix (sum derivatives of each node)
		for(int h = 0; h < hiddenLayer1.length; h ++) {
			for(int i = 0; i < hiddenLayer2.length; i ++) {
				// Formula: ∂C/∂a(L-2) = Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2)
				hiddenLayer1[h].partialDerivative += hiddenLayer2[i].partialDerivative * derivativeSigmoid(hiddenLayer2[i].unsquishedActivation) * weightMatrix2[i][h];
			}
		}
	}
	
	public static void deriveWeightMatrix1() {
		// Calculate ∂C/∂w(L-1) for all weights in the second weight layer
		// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
		for(int h = 0; h < hiddenLayer1.length; h ++) {
			for(int g = 0; g < inputLayer.length; g ++) {
				// Formula: ∂C/∂w(L-2) = Σ(Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2)) * ∂a(L-2)/∂z(L-2) * ∂z(L-2)/∂w(L-2)
				// Remember that hiddenLayer1[j].partialDerivative stores Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2) in the summation part of the formula
				partialDerivativeWeightMatrix1[h][g] = hiddenLayer1[h].partialDerivative * derivativeSigmoid(hiddenLayer1[h].unsquishedActivation) * inputLayer[g].activation;
				
				// MUST USE THE NEGATIVE GRADIENT VECTOR
				partialDerivativeWeightMatrix1[h][g] *= -1;
			}
		}
	}
	
	public static void deriveBiasVector1() {
	// Calculate ∂C/∂w(L-1) for all weights in the second weight layer
	// This double for loop will traverse the columns first before moving to the next row in the weight matrix (the indices might look weird because I'm following my notes)
	for(int h = 0; h < hiddenLayer1.length; h ++) {
			// Formula: ∂C/∂b(L-2) = Σ(Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2)) * ∂a(L-2)/∂z(L-2) * ∂z(L-2)/∂b(L-2)
			// Remember that hiddenLayer1[j].partialDerivative stores Σ(∂C/∂a(L) * ∂a(L)/∂z(L) * ∂z(L)/∂a(L-1)) * ∂a(L-1)/∂z(L-1) * ∂z(L-1)/∂a(L-2) in the summation part of the formula
			partialDerivativeBiasVector1[h] = hiddenLayer1[h].partialDerivative * derivativeSigmoid(hiddenLayer1[h].unsquishedActivation) * 1;
			
			// MUST USE THE NEGATIVE GRADIENT VECTOR
			partialDerivativeBiasVector1[h] *= -1;
		}
	}
	
	public static void clearPartialDerivativesToActivations(Node [] layer) {
		// Set all of the partial derivatives with respect to the activations to 0 for resetting
		for(int i = 0; i < layer.length; i ++) {
			layer[i].partialDerivative = 0;
		}
	}
	
	public static void stochasticSum() {
		// Add the recently calculate derivatives in the stochastic matrices and vectors
		// Add weight matrix 1
		for(int i = 0; i < partialDerivativeWeightMatrix1.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix1[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix1[i][j] += partialDerivativeWeightMatrix1[i][j];
			}
		}
		
		// Add bias vector 1
		for(int i = 0; i < partialDerivativeBiasVector1.length; i ++) {
			stochasticPartialDerivativeBiasVector1[i] += partialDerivativeBiasVector1[i];
		}
		
		// Add weight matrix 2
		for(int i = 0; i < partialDerivativeWeightMatrix2.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix2[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix2[i][j] += partialDerivativeWeightMatrix2[i][j];
			}
		}
		
		// Add bias vector 2
		for(int i = 0; i < partialDerivativeBiasVector2.length; i ++) {
			stochasticPartialDerivativeBiasVector2[i] += partialDerivativeBiasVector2[i];
		}
		
		// Add weight matrix 3
		for(int i = 0; i < partialDerivativeWeightMatrix3.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix3[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix3[i][j] += partialDerivativeWeightMatrix3[i][j];
			}
		}
		
		// Add bias vector 3
		for(int i = 0; i < partialDerivativeBiasVector3.length; i ++) {
			stochasticPartialDerivativeBiasVector3[i] += partialDerivativeBiasVector3[i];
		}
	}
	
	public static boolean checkTenImages() {
		// Checks if we traversed 10 images
		if(count % MOD_BY_TEN == 0) {
			return true;
		}
		
		return false;
	}	
	
	public static boolean checkFirstImage() {
		// Checks if we hit the first image in an image group
		if(count % MOD_BY_TEN == 1) {
			return true;
		}
		
		return false;
	}
	
	public static void adjustWeightsAndBiases() {
		// Use partial derivatives to change the weights and biases (For this project, I'll adjust the weights and biases by moving them by GRADIENT_COMPONENT_UNITS unit(s), which is simply changing the weight and bias by the value of the partial derivative)
		// Edit the first weight matrix
		for(int i = 0; i < weightMatrix1.length; i ++) {
			for(int j = 0; j < weightMatrix1[0].length; j ++) {
				weightMatrix1[i][j] += (stochasticPartialDerivativeWeightMatrix1[i][j] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
				// Keep the value within -1 and 1
				weightMatrix1[i][j] = boundValue(weightMatrix1[i][j]);
			}
		}

		// Edit the second weight matrix
		for(int i = 0; i < weightMatrix2.length; i ++) {
			for(int j = 0; j < weightMatrix2[0].length; j ++) {
				weightMatrix2[i][j] += (stochasticPartialDerivativeWeightMatrix2[i][j] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
				// Keep the value within -1 and 1
				weightMatrix2[i][j] = boundValue(weightMatrix2[i][j]);
			}
		}
		
		// Edit the third weight matrix
		for(int i = 0; i < weightMatrix3.length; i ++) {
			for(int j = 0; j < weightMatrix3[0].length; j ++) {
				weightMatrix3[i][j] += (stochasticPartialDerivativeWeightMatrix3[i][j] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
				// Keep the value within -1 and 1
				weightMatrix3[i][j] = boundValue(weightMatrix3[i][j]);
			}
		}
		
		// Edit the first bias vector
		for(int i = 0; i < biasVector1.length; i ++) {
			biasVector1[i] += (stochasticPartialDerivativeBiasVector1[i] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
			// Keep the value within -1 and 1
			biasVector1[i] = boundValue(biasVector1[i]);
		}
		
		// Edit the second bias vector
		for(int i = 0; i < biasVector2.length; i ++) {
			biasVector2[i] += (stochasticPartialDerivativeBiasVector2[i] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
			// Keep the value within -1 and 1
			biasVector2[i] = boundValue(biasVector2[i]);
		}
		
		// Edit the third bias vector
		for(int i = 0; i < biasVector3.length; i ++) {
			biasVector3[i] += (stochasticPartialDerivativeBiasVector3[i] / AVERAGE_STOCHASTIC_DERIVATIVES) * GRADIENT_COMPONENT_UNITS;
				
			// Keep the value within -1 and 1
			biasVector3[i] = boundValue(biasVector3[i]);
		}
	}
	
	public static float boundValue(float value) {
		// Check if this feature is enabled
		if(!disableWeightRestriction) {
			// Don't let the value go above 1
			if(value > 1) {
				return 1;
			}
			else if(value < -1) {
				return -1;
			}
			else {
				return value;
			}
		}
		else {
			// If disabled, then just return the value
			return value;
		}
	}
	
	public static void clearStochasticWeightsAndBiases() {
		// Clears the stochastic weight matrices and biases for the next 10 images
		// Change weight matrix 1
		for(int i = 0; i < partialDerivativeWeightMatrix1.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix1[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix1[i][j] = 0;
			}
		}
		
		// Change bias vector 1
		for(int i = 0; i < partialDerivativeBiasVector1.length; i ++) {
			stochasticPartialDerivativeBiasVector1[i] = 0;
		}
		
		// Change weight matrix 2
		for(int i = 0; i < partialDerivativeWeightMatrix2.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix2[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix2[i][j] = 0;
			}
		}
		
		// Change bias vector 2
		for(int i = 0; i < partialDerivativeBiasVector2.length; i ++) {
			stochasticPartialDerivativeBiasVector2[i] = 0;
		}
		
		// Change weight matrix 3
		for(int i = 0; i < partialDerivativeWeightMatrix3.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix3[0].length; j ++) {
				stochasticPartialDerivativeWeightMatrix3[i][j] = 0;
			}
		}
		
		// Change bias vector 3
		for(int i = 0; i < partialDerivativeBiasVector3.length; i ++) {
			stochasticPartialDerivativeBiasVector3[i] = 0;
		}
	}
	
	public static boolean checkCompleted() {
		// Check if we completed a session (training session or accuracy test session)
		if(count > numImages - 1 || count > numLabels - 1) {
			return true;
		}
		
		return false;
	}
	
	public static void guessedCorrect() {
		// Checks if the program guessed the value correctly and add 1 to the numCorrect count if correct (used during accuracy test)
		if(guessedNumber == currentLabel) {
			numCorrect ++;
		}
	}	
	
	public static void clearPartialDerivativeWeightsAndBiases() {
		// Clears the stochastic weight matrices and biases for the next 10 images
		// Change weight matrix 1
		for(int i = 0; i < partialDerivativeWeightMatrix1.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix1[0].length; j ++) {
				partialDerivativeWeightMatrix1[i][j] = 0;
			}
		}
		
		// Change bias vector 1
		for(int i = 0; i < partialDerivativeBiasVector1.length; i ++) {
			partialDerivativeBiasVector1[i] = 0;
		}
		
		// Change weight matrix 2
		for(int i = 0; i < partialDerivativeWeightMatrix2.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix2[0].length; j ++) {
				partialDerivativeWeightMatrix2[i][j] = 0;
			}
		}
		
		// Change bias vector 2
		for(int i = 0; i < partialDerivativeBiasVector2.length; i ++) {
			partialDerivativeBiasVector2[i] = 0;
		}
		
		// Change weight matrix 3
		for(int i = 0; i < partialDerivativeWeightMatrix3.length; i ++) {
			for(int j = 0; j < partialDerivativeWeightMatrix3[0].length; j ++) {
				partialDerivativeWeightMatrix3[i][j] = 0;
			}
		}
		
		// Change bias vector 3
		for(int i = 0; i < partialDerivativeBiasVector3.length; i ++) {
			partialDerivativeBiasVector3[i] = 0;
		}
	}
	
	public static void clearPartialDerivativeActivations() {
		// Clears the partial derivatives of the cost function with respect to the activations of these hidden layers since we are not using them in testing
		for(int i = 0; i < hiddenLayer1.length; i ++) {
			hiddenLayer1[i].partialDerivative = 0;
		}
		
		for(int i = 0; i < hiddenLayer2.length; i ++) {
			hiddenLayer2[i].partialDerivative = 0;
		}
	}
	
	public static void clearCosts() {
		// Clears both cost functions in preparation for accuracy testing
		totalCost = 0;
		cost = 0;
	}
	
	public static void writeFile() throws IOException {
		// Writes the network and statistics file
		System.out.println();
		System.out.println("Writing file... Please wait.");
		
		// Get the current date and time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
		LocalDateTime date = LocalDateTime.now();
		
		// Name this file
		networkFile = new BufferedWriter(new FileWriter("neuralNetwork_" + formatter.format(date) + ".txt"));
		
		// Write the following:
		// Total testing images
		networkFile.write(numImages + "\n");
		
		// Number of images guessed correctly
		networkFile.write(numCorrect + "\n");
		
		// Accuracy
		String accuracy = ((PaintPanel.CONVERT_TO_PERCENTAGE * Main.numCorrect / Main.count) * PaintPanel.ROUNDING) / PaintPanel.ROUNDING + "";
		networkFile.write(accuracy + "\n");
		
		// Input node count
		networkFile.write(rows * cols + "\n");

		// Hidden layer 1 node count
		networkFile.write(HIDDEN_LAYER_NODES + "\n");

		// Hidden layer 2 node count
		networkFile.write(HIDDEN_LAYER_NODES + "\n");

		// Output layer node count
		networkFile.write(OUTPUT_LAYER_NODES + "\n");
		
		// Weight matrix 1 values (use strings since we can't write floats)
		String weights = "";
		for(int r = 0; r < weightMatrix1.length; r ++) {
			for(int c = 0; c < weightMatrix1.length; c ++) {
				weights += weightMatrix1[r][c];
				
				// Don't add space after the last value
				if(c < weightMatrix1.length - 1) {
					weights += " ";
				}
			}
			networkFile.write(weights + "\n");
		}
		
		// Weight matrix 2 values
		for(int r = 0; r < weightMatrix2.length; r ++) {
			for(int c = 0; c < weightMatrix2.length; c ++) {
				weights += weightMatrix2[r][c];
				
				// Don't add space after the last value
				if(c < weightMatrix2.length - 1) {
					weights += " ";
				}
			}
			networkFile.write(weights + "\n");
		}
		
		// Weight matrix 3 values
		for(int r = 0; r < weightMatrix3.length; r ++) {
			for(int c = 0; c < weightMatrix3.length; c ++) {
				weights += weightMatrix3[r][c];
				
				// Don't add space after the last value
				if(c < weightMatrix3.length - 1) {
					weights += " ";
				}
			}
			networkFile.write(weights + "\n");
		}
		
		// Bias vector 1 values
		for(int r = 0; r < biasVector1.length; r ++) {
			networkFile.write(biasVector1[r] + "\n");
		}
		
		// Bias vector 2 values
		for(int r = 0; r < biasVector2.length; r ++) {
			networkFile.write(biasVector2[r] + "\n");
		}
		
		// Bias vector 3 values
		for(int r = 0; r < biasVector3.length; r ++) {
			networkFile.write(biasVector3[r] + "\n");
		}
		
		// Close and complete the buffered writer
		networkFile.close();
		
		// Conclude the program
		System.out.println("File written. Thank you for using this neural network trainer!");
	}
}
