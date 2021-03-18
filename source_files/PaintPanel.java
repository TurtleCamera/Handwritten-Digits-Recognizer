package neuralNetwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintPanel extends JPanel {

	// Some constants
	public static final int NODE_SIZE = 30; // Node size
	public static final int TEXT_SPACE = 10; // Space between node and text
	public static final int INPUT_TEXT_SPACE = 17; // Space between input number and node
	public static final int WEIGHT_LABEL_SPACE = 140; // Space between each weight label
	public static final int COST_TEXT_SPACE = 27; // Space to lower the cost to the equal sign in the cost function image/node
	public static final int AVERAGE_COST_TEXT_SPACE = 20; // Space to lower the cost to the equal sign in the average cost function image/node
	public static final int TRAVERSED_TEXT_SPACE = 30; // Space to lower the cost to the equal sign in the cost function image
	public static final int COST_FUNCTION_SPACE = 80; // Space between both cost function images
	public static final int LABEL_SPACE = 35; // Space between the stacked labels (next to the last nine images)
	public static final float ROUNDING = 100000.0f; // Used to round numbers to the 5th decimal place
	public static final float CONVERT_TO_PERCENTAGE = 100.0f; // Used to convert to percentage format
	public static final int WEIGHT_SPACE_LEFT = 200; // Amount of space the input weight is written on the left side
	public static final int WEIGHT_SPACE_RIGHT = 200; // Amount of space the output weight is written on the right side
	public static final int DOUBLE = 2; // Doubles a value
	public static final int HALF = 2; // Halves a value
	public static final int TRIPLE = 3; // Triples a value
	public static final int QUADRUPLE = 4; // Quadruples a value
	public static final int QUINTUPLE = 5; // Quintuples a value
	public static final int SEXTUPLE = 6; // Sextuples a value
	public static final int SEPTUPLE = 7; // Septuples a value
	public static final int OCTUPLE = 8; // Octuples a value
	public static final int NONUPLE = 9; // Nonuples a value
	public static final int DECUPLE = 10; // Decuples a value
	public static final int UNDECUPLE = 11; // Undecuples a value
	public static final int DUODECUPLE = 12; // Duodecuples a value
	public static final int TREDECUPLE = 13; // Tredecuples a value
	public static final int QUATTUORDECUPLE = 14; // Quattuordecuples a value
	
	public static final int HIDDEN1_X = 1400; // X position for HIDDEN1 nodes
	public static final int LAYER_SPACING = 170; // Space between layers
	public static final int HIDDEN1_START_Y = 40; // Starting Y value for the HIDDEN1 nodes
	public static final int HIDDEN1_END_Y = Main.SCREEN_HEIGHT - HIDDEN1_START_Y - DOUBLE * NODE_SIZE; // Ending Y value for the HIDDEN1 nodes
	public static final int HIDDEN_SPACING_Y = (int) Math.ceil((float) (HIDDEN1_START_Y + HIDDEN1_END_Y) / (Main.HIDDEN_LAYER_NODES)); // Space between HIDDEN1 nodes
	public static final int OUTPUT_START_Y = 205; // Starting Y value for the HIDDEN1 nodes
	public static final int OUTPUT_END_Y = Main.SCREEN_HEIGHT - HIDDEN1_START_Y - DOUBLE * NODE_SIZE; // Ending Y value for the HIDDEN1 nodes
	public static final int OUTPUT_SPACING = (int) Math.ceil((float) (HIDDEN1_START_Y + HIDDEN1_END_Y) / (Main.HIDDEN_LAYER_NODES)); // Space between HIDDEN1 nodes

	public static final int OUTPUT_WEIGHT_START_X = 1085; // Starting x position of the output weight numbers
	public static final int OUTPUT_WEIGHT_START_Y = 40; // Starting y position of the output weight numbers
	public static final int INPUT_WEIGHT_START_X = OUTPUT_WEIGHT_START_X - WEIGHT_LABEL_SPACE; // Starting x position of the input weight numbers
	public static final int INPUT_WEIGHT_START_Y = 40; // Starting y position of the input weight numbers
	public static final int GRADIENT_OUTPUT_WEIGHT_START_X = OUTPUT_WEIGHT_START_X - DOUBLE * WEIGHT_LABEL_SPACE; // Starting x position of the gradient output weight numbers
	public static final int GRADIENT_OUTPUT_WEIGHT_START_Y = 40; // Starting y position of the gradient output weight numbers
	public static final int GRADIENT_INPUT_WEIGHT_START_X = OUTPUT_WEIGHT_START_X - TRIPLE * WEIGHT_LABEL_SPACE; // Starting x position of the gradient input weight numbers
	public static final int GRADIENT_INPUT_WEIGHT_START_Y = 40; // Starting y position of the gradient input weight numbers
	
	public static final int MIN_INPUT_NODES = 0; // Minimum amount of first selection nodes that the user can go through
	public static final int MAX_INPUT_NODES = Main.rows * Main.cols; // Maximum amount of input selection nodes that the user can go through
	public static final int MIN_HIDDEN_NODES = 0; // Minimum amount of hidden layer selection nodes that the user can go through
	public static final int MAX_HIDDEN_NODES = Main.HIDDEN_LAYER_NODES; // Maximum amount of hidden layer selection nodes that the user can go through
	public static final int MIN_OUTPUT_NODES = 0; // Minimum amount of output layer selection nodes that the user can go through
	public static final int MAX_OUTPUT_NODES = Main.OUTPUT_LAYER_NODES; // Maximum amount of output layer selection nodes that the user can go through
	
	public static final int SECOND_HIDDEN_LAYER_NODES = Main.HIDDEN_LAYER_NODES; // First node number of the second hidden layer
	public static final int OUTPUT_HIDDEN_LAYER_NODES = Main.HIDDEN_LAYER_NODES * DOUBLE; // First node number of the output hidden layer
	public static final int NODES_BEFORE_SELECTED_NODE = 7; // Draws 7 nodes before the selected input node
	public static final int NODE_INDEX_FIXED_LOCATION = 7; // Not the first selected node, but where the selected node should be drawn out of the 16 input nodes on screen
	public static final int DEFAULT_HIDDEN_LAYER_NODE_SELECTED = 7; // Default second and third selected nodes
	public static final int DEFAULT_OUTPUT_LAYER_NODE_SELECTED = 4; // Default fourth selected node
	public static final int DRAW_INPUT_NODES = 16; // Total amount of input nodes that should be drawn (Can be used for the hidden layer nodes since they also only display 16 nodes)
	
	public static final int COST_FUNCTION_X = 0; // X position of the cost function image
	public static final int COST_FUNCTION_Y = 0; // Y position of the cost function image
	public static final int COST_FUNCTION_WIDTH = 276; // Width of the cost function image
	public static final int COST_FUNCTION_HEIGHT = 126; // Height of the cost function image
	public static final int AVERAGE_COST_FUNCTION_X = COST_FUNCTION_X + COST_FUNCTION_WIDTH + COST_FUNCTION_SPACE; // X position of the average cost function image
	public static final int AVERAGE_COST_FUNCTION_Y = 0; // Y position of the average cost function image
	public static final int AVERAGE_COST_FUNCTION_WIDTH = 219; // Width of the average cost function image
	public static final int AVERAGE_COST_FUNCTION_HEIGHT = 115; // Height of the average cost function image
	
	public static final int CURRENT_IMAGE_PIXEL_SIZE = 6;
	public static final int LAST_NINE_IMAGES_PIXEL_SIZE = 3;
	public static final int CUTOFF = 3; // Columns that are cut off on the right side of the image
	public static final int IMAGE_START_X = Main.SCREEN_WIDTH - ((Main.cols + CUTOFF) * CURRENT_IMAGE_PIXEL_SIZE); // Where the image's x location should start
	public static final int IMAGE_START_Y = 0; // Where the image's y location should start
	public static final int LAST_NINE_IMAGES_START_X = 0; // Where the first of the last nine images should start (x position)
	public static final int LAST_NINE_IMAGES_START_Y = COST_FUNCTION_Y + COST_FUNCTION_HEIGHT; // Where the first of the last nine images should start (y position)
	public static final int SPACE_BETWEEN_IMAGES = Main.rows * LAST_NINE_IMAGES_PIXEL_SIZE; // Space between images
	
	public static final int OUTPUT_WEIGHT_LABEL_START_X = OUTPUT_WEIGHT_START_X; // Starting x position of the output weight numbers
	public static final int OUTPUT_WEIGHT_LABEL_START_Y = 28; // Starting y position of the output weight numbers (Rest of the labels shift based off this label)
	public static final int INPUT_WEIGHT_LABEL_START_X = INPUT_WEIGHT_START_X; // Starting x position of the input weight numbers
	public static final int INPUT_WEIGHT_LABEL_START_Y = 28; // Starting y position of the input weight numbers
	public static final int GRADIENT_OUTPUT_WEIGHT_LABEL_START_X = GRADIENT_OUTPUT_WEIGHT_START_X; // Starting x position of the output weight numbers
	public static final int GRADIENT_OUTPUT_WEIGHT_LABEL_START_Y = 28; // Starting y position of the output weight numbers
	public static final int GRADIENT_INPUT_WEIGHT_LABEL_START_X = GRADIENT_INPUT_WEIGHT_START_X; // Starting x position of the input weight numbers
	public static final int GRADIENT_INPUT_WEIGHT_LABEL_START_Y = 28; // Starting y position of the input weight numbers
	public static final int IMAGES_TRAVERSED_LABEL_X = LAST_NINE_IMAGES_START_X + LAST_NINE_IMAGES_PIXEL_SIZE * Main.cols + TEXT_SPACE; // Starting x position of the amount of images traversed label
	public static final int IMAGES_TRAVERSED_LABEL_Y = COST_FUNCTION_Y + COST_FUNCTION_HEIGHT + TRAVERSED_TEXT_SPACE; // Starting y position of the amount of images traversed label
	
	public static final int FIRST_CONTROL_LABEL_X = IMAGES_TRAVERSED_LABEL_X; // Starting x position of the control labels
	public static final int FIRST_CONTROL_LABEL_Y = Main.SCREEN_HEIGHT - 50; // Starting x position of the control labels

	public static int firstSelectedNode = 0; // Node in the input layer that the user is currently selecting
	public static int secondSelectedNode = DEFAULT_HIDDEN_LAYER_NODE_SELECTED; // Node in the first hidden layer that the user is currently selecting
	public static int thirdSelectedNode = DEFAULT_HIDDEN_LAYER_NODE_SELECTED; // Node in the second hidden layer that the user is currently selecting
	public static int fourthSelectedNode = DEFAULT_OUTPUT_LAYER_NODE_SELECTED; // Node in the output layer that the user is currently selecting
	
	// Draws all of the objects on the panel (which is added to the JFrame)
	public void paint(Graphics g) {
		if(Main.loaded) {
			// Call the super class
			super.paintComponent(g);
			
			// Determine background setting
			if(Main.darkMode) {
				this.setBackground(Color.BLACK);
			}
			else {
				this.setBackground(Color.WHITE);
			}
			
			// The thickness of the line doesn't exist in Graphics 1D
			Graphics2D g2D = (Graphics2D)g;
			
			// Draw the current training image on the top right corner of the screen
			for(int i = 0; i < Main.rows; i ++) {
				for(int j = 0; j < Main.cols; j ++) {
					int color = Main.currentImage[i][j];
					g.setColor(new Color(color, color, color));
					g.fillRect(IMAGE_START_X + i * CURRENT_IMAGE_PIXEL_SIZE, IMAGE_START_Y + j * CURRENT_IMAGE_PIXEL_SIZE, CURRENT_IMAGE_PIXEL_SIZE, CURRENT_IMAGE_PIXEL_SIZE);
				}
			}		
			
			// If we're in dark mode, box the current image
			if(Main.darkMode) {
				g.setColor(Color.WHITE);
				g.drawRect(IMAGE_START_X , IMAGE_START_Y, Main.rows * CURRENT_IMAGE_PIXEL_SIZE, Main.cols * CURRENT_IMAGE_PIXEL_SIZE);
			}
			
			// Highlight the current image if it's the tenth image
			if(Main.count % 10 == 0 && Main.count >= 10) {
				g.setColor(Color.GREEN);
				g.drawRect(IMAGE_START_X, IMAGE_START_Y, Main.rows * CURRENT_IMAGE_PIXEL_SIZE, Main.cols * CURRENT_IMAGE_PIXEL_SIZE);
			}
			
			// Highlight the selected pixel (selected input node) on the current image
			g.setColor(Color.RED);
			g.drawRect(IMAGE_START_X + (firstSelectedNode % Main.rows) * CURRENT_IMAGE_PIXEL_SIZE, (firstSelectedNode / Main.cols) * CURRENT_IMAGE_PIXEL_SIZE, CURRENT_IMAGE_PIXEL_SIZE, CURRENT_IMAGE_PIXEL_SIZE);
			
			// Draw the last 9 images on the top left corner of the screen
			for(int k = 0; k < Main.lastNineImages.size(); k ++) {
				// Run if k is less than LAST_NINE_IMAGES
				if(k < Main.LAST_NINE_IMAGES) {
					int [][] imageToDraw = Main.lastNineImages.get(k);
					for(int i = 0; i < imageToDraw.length; i ++) {
						for(int j = 0; j < imageToDraw[i].length; j ++) {
							int color = imageToDraw[i][j];
							g.setColor(new Color(color, color, color));
							g.fillRect(LAST_NINE_IMAGES_START_X + i * LAST_NINE_IMAGES_PIXEL_SIZE, LAST_NINE_IMAGES_START_Y + j * LAST_NINE_IMAGES_PIXEL_SIZE + k * SPACE_BETWEEN_IMAGES, LAST_NINE_IMAGES_PIXEL_SIZE, LAST_NINE_IMAGES_PIXEL_SIZE);
						}
					}
				}
				
				// If we're in dark mode, box the current image
				if(Main.darkMode) {
					g.setColor(Color.WHITE);
					g.drawRect(LAST_NINE_IMAGES_START_X , LAST_NINE_IMAGES_START_Y + k * SPACE_BETWEEN_IMAGES, Main.rows * LAST_NINE_IMAGES_PIXEL_SIZE, Main.cols * LAST_NINE_IMAGES_PIXEL_SIZE);
				}
			}
			
			// Draw the tenth image (we have Main.count != 10 because we don't want to highlight the current image rectangle)
			if(Main.count >= 10 && Main.count % 10 != 0) {
				g.setColor(Color.GREEN);
				g.drawRect(LAST_NINE_IMAGES_START_X, LAST_NINE_IMAGES_START_Y + (Main.count % 10 - 1) * SPACE_BETWEEN_IMAGES, Main.rows * LAST_NINE_IMAGES_PIXEL_SIZE , Main.cols * LAST_NINE_IMAGES_PIXEL_SIZE);
			}
			
			// Draw the labels
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
			if(Main.darkMode) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.BLACK);
			}
		    g.drawString("Outputs", OUTPUT_WEIGHT_LABEL_START_X, OUTPUT_WEIGHT_LABEL_START_Y);
		    g.drawString("Inputs", INPUT_WEIGHT_LABEL_START_X, INPUT_WEIGHT_LABEL_START_Y);
		    g.drawString("∂C/∂w(o)", GRADIENT_OUTPUT_WEIGHT_LABEL_START_X, GRADIENT_OUTPUT_WEIGHT_LABEL_START_Y);
		    g.drawString("∂C/∂w(i)", GRADIENT_INPUT_WEIGHT_LABEL_START_X, GRADIENT_INPUT_WEIGHT_LABEL_START_Y);
		    g.drawString("Traversed images (10 per group for", IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y); // Starting from here, we stack these labels
		    g.drawString("   SGD): n(group) = " + ((Main.count - 1) % 10 + 1), IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE);
		    g.drawString("Total training data traversed n(total) = " + Main.count, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * DOUBLE);
		    g.drawString("Total training images = " + Main.numImages, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * TRIPLE);
		    g.drawString("Loaded image rows = " + Main.rows, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * QUADRUPLE);
		    g.drawString("Loaded image columns = " + Main.cols, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * QUINTUPLE);
		    g.drawString("Image label (brightest output node): y = " + Main.currentLabel, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * SEXTUPLE);
		    if(Main.inTraining) {
		    	g.drawString("Backpropogation count: " + Main.count / 10, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * SEPTUPLE);
		    }
		    else {
		    	g.drawString("Backpropogation: Disabled", IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * SEPTUPLE);
		    }
		    
		    // Before drawing the bias label, figure out which layer we are selecting (while doing this, lets also grab the partial derivatives of that node)
		    String bias = "None";
		    String partialDerivativeActivation = "None";
		    String partialDerivativeBias = "None";
		    String lastTenPartialDerivativeBias = "None";
		    String unsquishedActivation = "None";
		    if(InputFrame.layerSelected == Main.INPUT_LAYER) {
		    	unsquishedActivation = Math.round(Main.inputLayer[firstSelectedNode].unsquishedActivation * ROUNDING) / ROUNDING + "";	
		    }
		    else if(InputFrame.layerSelected == Main.HIDDEN_LAYER1) {
		    	bias = Math.round(Main.biasVector1[secondSelectedNode] * ROUNDING) / ROUNDING + "";
		    	partialDerivativeActivation = Math.round(Main.hiddenLayer1[secondSelectedNode].partialDerivative * ROUNDING) / ROUNDING + "";
		    	partialDerivativeBias = Math.round(Main.partialDerivativeBiasVector1[secondSelectedNode] * ROUNDING) / ROUNDING + "";
		    	lastTenPartialDerivativeBias = Math.round((Main.stochasticPartialDerivativeBiasVector1[secondSelectedNode] / ((Main.count - 1) % 10 + 1)) * ROUNDING) / ROUNDING + "";
		    	unsquishedActivation = Math.round(Main.hiddenLayer1[secondSelectedNode].unsquishedActivation * ROUNDING) / ROUNDING + "";
		    }
		    else if(InputFrame.layerSelected == Main.HIDDEN_LAYER2) {
		    	bias = Math.round(Main.biasVector2[thirdSelectedNode] * ROUNDING) / ROUNDING + "";
		    	partialDerivativeActivation = Math.round(Main.hiddenLayer2[thirdSelectedNode].partialDerivative * ROUNDING) / ROUNDING + "";
		    	partialDerivativeBias = Math.round(Main.partialDerivativeBiasVector2[thirdSelectedNode] * ROUNDING) / ROUNDING + "";
		    	lastTenPartialDerivativeBias = Math.round((Main.stochasticPartialDerivativeBiasVector2[thirdSelectedNode] / ((Main.count - 1) % 10 + 1)) * ROUNDING) / ROUNDING + "";
		    	unsquishedActivation = Math.round(Main.hiddenLayer2[thirdSelectedNode].unsquishedActivation * ROUNDING) / ROUNDING + "";
		    }
		    else if(InputFrame.layerSelected == Main.OUTPUT_LAYER) {
		    	bias = Math.round(Main.biasVector3[fourthSelectedNode] * ROUNDING) / ROUNDING + "";
		    	partialDerivativeBias = Math.round(Main.partialDerivativeBiasVector3[fourthSelectedNode] * ROUNDING) / ROUNDING + "";
		    	lastTenPartialDerivativeBias = Math.round((Main.stochasticPartialDerivativeBiasVector3[fourthSelectedNode] / ((Main.count - 1) % 10 + 1)) * ROUNDING) / ROUNDING + "";
		    	unsquishedActivation = Math.round(Main.outputLayer[fourthSelectedNode].unsquishedActivation * ROUNDING) / ROUNDING + "";
		    }
		    
		    g.drawString("Bias of selected node: " + bias, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * OCTUPLE);
		    // Display partial derivatives of bias when training, but change to accuracy stats after
		    if(Main.inTraining) {
			    g.drawString("(Selected node) ∂C/∂b = " + partialDerivativeBias, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * NONUPLE);
			    g.drawString("(Last 10 data average) ∂C/∂b = " + lastTenPartialDerivativeBias, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * DECUPLE);
		    }
		    else {
			    g.drawString("Images guessed correctly: " + Main.numCorrect, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * NONUPLE);
			    // Change the color temporarily to highlight the accuracy value when we finished testing
			    if(Main.checkCompleted()) {
			    	g.setColor(Color.MAGENTA);
			    }
			    g.drawString("Accuracy: " + (Math.round((CONVERT_TO_PERCENTAGE * Main.numCorrect / Main.count) * ROUNDING) / ROUNDING) + "%", IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * DECUPLE);
			    
				if(Main.darkMode) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.BLACK);
				}
		    }
		    g.drawString("Network's guess: a = " + Main.guessedNumber, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * UNDECUPLE);
		    g.drawString("∂C/∂a = " + partialDerivativeActivation, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * DUODECUPLE);
		    g.drawString("Unsquished activation: " + unsquishedActivation, IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * TREDECUPLE);
		    // Tell whether weight restriction is on
		    if(Main.disableWeightRestriction) {
		    	g.drawString("Weight restriction: Disabled", IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * QUATTUORDECUPLE);
		    }
		    else {
		    	g.drawString("Weight restriction: Enabled", IMAGES_TRAVERSED_LABEL_X, IMAGES_TRAVERSED_LABEL_Y + LABEL_SPACE * QUATTUORDECUPLE);
		    }
		    
		    // Draw the controls (from bottom to top)
		    g.drawString("Toggle dark mode: D", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y);
		    g.drawString("Toggle automatic image advancement: A", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - LABEL_SPACE);
		    g.drawString("Load next image manually: Space or Enter", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - DOUBLE * LABEL_SPACE);
		    g.drawString("   arrow keys", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - TRIPLE * LABEL_SPACE);
		    g.drawString("Change nodes within layer: Up and Down", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - QUADRUPLE * LABEL_SPACE);
		    g.drawString("Change layers: Left and Right arrow keys", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - QUINTUPLE * LABEL_SPACE);
		    g.drawString("   constant throughout training session): B", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - SEXTUPLE * LABEL_SPACE);
		    g.drawString("   (recommended to keep this option", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - SEPTUPLE * LABEL_SPACE);
		    g.drawString("Toggle weight restriction (limit to [-1, 1]) ", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - OCTUPLE * LABEL_SPACE);
		    g.drawString("Controls", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - NONUPLE * LABEL_SPACE);
		    if(Main.checkCompleted()) {
			    if(Main.inTraining) {
			    	// Print this text if we finished training
				    g.setColor(Color.GREEN);
				    g.drawString("                an accuracy test.", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - NONUPLE * LABEL_SPACE);
				    g.drawString("Training Completed! Press T to perform", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - DECUPLE * LABEL_SPACE);
		    	}
			    else {
			    	// Print this text if we finished testing
			    	// Change the text according to whether a file was printed
				    g.setColor(Color.GREEN);
			    	if(!Main.fileWritten) {
					    g.drawString("                the network file.", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - NONUPLE * LABEL_SPACE);
					    g.drawString("Testing Completed! Press W to write", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - DECUPLE * LABEL_SPACE);
			    	}
			    	else {
					    g.drawString("                this network trainer.", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - NONUPLE * LABEL_SPACE);
					    g.drawString("File written! Thank you for using", FIRST_CONTROL_LABEL_X, FIRST_CONTROL_LABEL_Y - DECUPLE * LABEL_SPACE);
			    	}
			    }
		    }
			
			// Draw the cost function image and write the cost
			g.drawImage(Main.costFunction, COST_FUNCTION_X, COST_FUNCTION_Y, null);
			// High cost is red while low cost is green
			if(Main.cost > 0.75) {
				g.setColor(Color.RED);
			}
			else if(Main.cost > 0.1) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 23)); 
			g.drawString(Math.round(Main.cost * ROUNDING) / ROUNDING + "", COST_FUNCTION_X + COST_FUNCTION_WIDTH - TEXT_SPACE, COST_FUNCTION_Y + COST_FUNCTION_HEIGHT / HALF + COST_TEXT_SPACE);		
			
			// Draw the average cost function image and write the average cost
			g.drawImage(Main.avgCostFunction, AVERAGE_COST_FUNCTION_X, AVERAGE_COST_FUNCTION_Y, null);
			// High cost is red while low cost is green
			if(Main.totalCost / ((Main.count - 1) % 10 + 1) > 0.75) {
				g.setColor(Color.RED);
			}
			else if(Main.totalCost / ((Main.count - 1) % 10 + 1) > 0.1) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.GREEN);
			}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 23)); 
			g.drawString(Math.round((Main.totalCost / ((Main.count - 1) % 10 + 1)) * ROUNDING) / ROUNDING + "", AVERAGE_COST_FUNCTION_X + AVERAGE_COST_FUNCTION_WIDTH - DOUBLE * TEXT_SPACE, AVERAGE_COST_FUNCTION_Y + AVERAGE_COST_FUNCTION_HEIGHT / HALF + AVERAGE_COST_TEXT_SPACE);
			
			// Draw weights and biases connected to the selected nodes
			// Draw the weights connected to the first selected node (input layer) to the first hidden layer
			int connectionStartX = HIDDEN1_X - LAYER_SPACING + (NODE_SIZE / HALF);
			int connectionStartY = HIDDEN1_START_Y + NODE_INDEX_FIXED_LOCATION * HIDDEN_SPACING_Y + (NODE_SIZE / HALF);
			int connectionEndX;
			int connectionEndY;
			
			// Start drawing the connections
			int firstNodeToDraw = firstSelectedNode - NODES_BEFORE_SELECTED_NODE; // First node in the input node array to draw
			
			if(InputFrame.layerSelected == Main.INPUT_LAYER) {
				for(int i = 0; i < Main.weightMatrix1.length; i ++) {
					// Determine which weight we are drawing
					float weight = Main.weightMatrix1[i][firstSelectedNode];
				    g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
				    
				    // Set the color based on the sign of the weight
				    if(weight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the output weight
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
				    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", OUTPUT_WEIGHT_START_X, OUTPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				    
				    // Get the end destination of the weight connection
				    connectionEndX = HIDDEN1_X + (NODE_SIZE / HALF);
				    connectionEndY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
				    
				    // Now to draw the line
				    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
				    
				    // Set the color based on the sign of the gradient weight
					float gradientWeight = Main.partialDerivativeWeightMatrix1[i][firstSelectedNode];
				    
				    if(gradientWeight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the gradient weight value
				    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_OUTPUT_WEIGHT_START_X, GRADIENT_OUTPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				}
				
				// Reset the stroke size and highlight the selected node
				g2D.setStroke(new BasicStroke(8));
				g.setColor(Color.GREEN);
				g.drawOval(HIDDEN1_X - LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * NODE_INDEX_FIXED_LOCATION, NODE_SIZE, NODE_SIZE);
			}
			else if(InputFrame.layerSelected == Main.HIDDEN_LAYER1) {   
				// Draw the connections from the input layer to the first hidden layer
				// Get the end destination of the weight connection
			    connectionEndX = HIDDEN1_X + (NODE_SIZE / HALF);
			    connectionEndY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * secondSelectedNode + (NODE_SIZE/ HALF);
			    
				for(int i = 0; i < DRAW_INPUT_NODES; i ++) {
					// Don't draw if the node is out of index bounds
					if(!(firstNodeToDraw + i < 0 || firstNodeToDraw + i >= Main.rows * Main.cols)) {
						// Determine which weight we are drawing
						float weight = Main.weightMatrix1[secondSelectedNode][i + firstNodeToDraw];
					    g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
					    
					    // Set the color based on the sign of the weight
					    if(weight >= 0) {
					    	// Blue for positive weight
					    	g.setColor(Color.BLUE);
					    }
					    else {
					    	// Red for negative weight
					    	g.setColor(Color.RED);
					    }
					    
					    // Draw the input weight
						g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
					    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", INPUT_WEIGHT_START_X, INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
					    
					    // Get the start destination of the weight connection
					    connectionStartX = HIDDEN1_X - LAYER_SPACING + (NODE_SIZE / HALF);
					    connectionStartY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
					    
					    // Now to draw the line
					    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
					    
					    // Set the color based on the sign of the gradient weight
						float gradientWeight = Main.partialDerivativeWeightMatrix1[secondSelectedNode][i + firstNodeToDraw];
					    
					    if(gradientWeight >= 0) {
					    	// Blue for positive weight
					    	g.setColor(Color.BLUE);
					    }
					    else {
					    	// Red for negative weight
					    	g.setColor(Color.RED);
					    }
					    
					    // Draw the gradient weight value
					    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_INPUT_WEIGHT_START_X, GRADIENT_INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
					}
				}
				
				// Draw the connections from the first hidden layer to the second hidden layer
				connectionStartX = HIDDEN1_X + (NODE_SIZE / HALF);
				connectionStartY = HIDDEN1_START_Y + secondSelectedNode * HIDDEN_SPACING_Y + (NODE_SIZE / HALF);
				
				for(int i = 0; i < Main.weightMatrix2.length; i ++) {
					// Determine which weight we are drawing
					float weight = Main.weightMatrix2[i][secondSelectedNode];
				    g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
				    
				    // Set the color based on the sign of the weight
				    if(weight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the output weight
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
				    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", OUTPUT_WEIGHT_START_X, OUTPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				    
				    // Get the end destination of the weight connection
				    connectionEndX = HIDDEN1_X + LAYER_SPACING + (NODE_SIZE / HALF);
				    connectionEndY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
				    
				    // Now to draw the line
				    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
				    
				    // Set the color based on the sign of the gradient weight
					float gradientWeight = Main.partialDerivativeWeightMatrix2[i][secondSelectedNode];
				    
				    if(gradientWeight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the gradient weight value
				    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_OUTPUT_WEIGHT_START_X, GRADIENT_OUTPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				}
				
				// Reset the stroke size and highlight the selected node
				g2D.setStroke(new BasicStroke(8));
				g.setColor(Color.GREEN);
				g.drawOval(HIDDEN1_X, HIDDEN1_START_Y + HIDDEN_SPACING_Y * secondSelectedNode, NODE_SIZE, NODE_SIZE);	
			}
			else if(InputFrame.layerSelected == Main.HIDDEN_LAYER2) {   
				// Draw the connections from the first hidden layer to the second hidden layer
			    // Get the end destination of the weight connection
			    connectionEndX = HIDDEN1_X + LAYER_SPACING + (NODE_SIZE / HALF);
			    connectionEndY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * thirdSelectedNode + (NODE_SIZE/ HALF);
				    
				for(int i = 0; i < Main.HIDDEN_LAYER_NODES; i ++) {
					// Determine which weight we are drawing
					float weight = Main.weightMatrix2[thirdSelectedNode][i];
					g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
					   
					// Set the color based on the sign of the weight
					if(weight >= 0) {
					// Blue for positive weight
				    	g.setColor(Color.BLUE);
					}
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the input weight
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
				    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", INPUT_WEIGHT_START_X, INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				    
				    // Get the start destination of the weight connection
				    connectionStartX = HIDDEN1_X + (NODE_SIZE / HALF);
				    connectionStartY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
			    
				    // Now to draw the line
				    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
				    
				    // Set the color based on the sign of the gradient weight
					float gradientWeight = Main.partialDerivativeWeightMatrix2[thirdSelectedNode][i];
				    
				    if(gradientWeight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the gradient weight value
				    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_INPUT_WEIGHT_START_X, GRADIENT_INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				}
				
				// Draw the connections from the second hidden layer to the output layer
				connectionStartX = HIDDEN1_X + LAYER_SPACING + (NODE_SIZE / HALF);
				connectionStartY = HIDDEN1_START_Y + thirdSelectedNode * HIDDEN_SPACING_Y + (NODE_SIZE / HALF);
				
				for(int i = 0; i < Main.weightMatrix3.length; i ++) {
					// Determine which weight we are drawing
					float weight = Main.weightMatrix3[i][thirdSelectedNode];
				    g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
				    
				    // Set the color based on the sign of the weight
				    if(weight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the output weight
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
				    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", OUTPUT_WEIGHT_START_X, OUTPUT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i); // Use OUTPUT_START_Y instead of OUTPUT_WIEHGT_START_Y because the node position is different
				    
				    // Get the end destination of the weight connection
				    connectionEndX = HIDDEN1_X + LAYER_SPACING * DOUBLE + (NODE_SIZE / HALF);
				    connectionEndY = OUTPUT_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
					    
				    // Now to draw the line
				    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
				    
				    // Set the color based on the sign of the gradient weight
					float gradientWeight = Main.partialDerivativeWeightMatrix3[i][thirdSelectedNode];
				    
				    if(gradientWeight >= 0) {
				    	// Blue for positive weight
				    	g.setColor(Color.BLUE);
				    }
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the gradient weight value
				    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_OUTPUT_WEIGHT_START_X, OUTPUT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				}
				
				// Reset the stroke size and highlight the selected node
				g2D.setStroke(new BasicStroke(8));
				g.setColor(Color.GREEN);
				g.drawOval(HIDDEN1_X + LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * thirdSelectedNode, NODE_SIZE, NODE_SIZE);
			}
			else if(InputFrame.layerSelected == Main.OUTPUT_LAYER) {   
				// Draw the connections from the second hidden layer to the output layer
			    // Get the end destination of the weight connection
			    connectionEndX = HIDDEN1_X + LAYER_SPACING * DOUBLE + (NODE_SIZE / HALF);
			    connectionEndY = OUTPUT_START_Y + HIDDEN_SPACING_Y * fourthSelectedNode + (NODE_SIZE/ HALF);
				    
				for(int i = 0; i < Main.HIDDEN_LAYER_NODES; i ++) {
					// Determine which weight we are drawing
					float weight = Main.weightMatrix3[fourthSelectedNode][i];
					g2D.setStroke(new BasicStroke(determineThickness(Math.abs(weight))));
					   
					// Set the color based on the sign of the weight
					if(weight >= 0) {
					// Blue for positive weight
				    	g.setColor(Color.BLUE);
					}
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
				    
				    // Draw the input weight
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
				    g.drawString(Math.round(weight * ROUNDING) / ROUNDING + "", INPUT_WEIGHT_START_X, INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				    
				    // Get the start destination of the weight connection
				    connectionStartX = HIDDEN1_X + LAYER_SPACING + (NODE_SIZE / HALF);
				    connectionStartY = HIDDEN1_START_Y + HIDDEN_SPACING_Y * i + (NODE_SIZE/ HALF);
			    
				    // Now to draw the line
				    g2D.drawLine(connectionStartX, connectionStartY, connectionEndX, connectionEndY);
				    
				    // Draw the gradient weight
					float gradientWeight = Main.partialDerivativeWeightMatrix3[fourthSelectedNode][i];
					   
					// Set the color based on the sign of the gradient weight
					if(gradientWeight >= 0) {
					// Blue for positive weight
				    	g.setColor(Color.BLUE);
					}
				    else {
				    	// Red for negative weight
				    	g.setColor(Color.RED);
				    }
					
					// Draw the gradient weight value
				    g.drawString(Math.round(gradientWeight * ROUNDING) / ROUNDING + "", GRADIENT_INPUT_WEIGHT_START_X, GRADIENT_INPUT_WEIGHT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);
				}
			
				// Reset the stroke size and highlight the selected node
				g2D.setStroke(new BasicStroke(8));
				g.setColor(Color.GREEN);
				g.drawOval(HIDDEN1_X + LAYER_SPACING * DOUBLE, OUTPUT_START_Y + OUTPUT_SPACING * fourthSelectedNode, NODE_SIZE, NODE_SIZE);
			}
			
			// Start drawing the nodes
			// Draw 16 of the total input nodes
			// First set the font size
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g2D.setStroke(new BasicStroke(1)); 
			firstNodeToDraw = firstSelectedNode - NODES_BEFORE_SELECTED_NODE;
			for(int i = 0; i < DRAW_INPUT_NODES; i ++) {
				// Don't draw nodes that are out of index bounds
				if(!(firstNodeToDraw < 0 || firstNodeToDraw >= Main.rows * Main.cols)) {
					int grayscale = (int) (Main.inputLayer[firstNodeToDraw].unsquishedActivation);
					g.setColor(new Color(grayscale, grayscale, grayscale));
					g.fillOval(HIDDEN1_X - LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
					if(Main.darkMode) {
						g.setColor(Color.WHITE);
						g.drawOval(HIDDEN1_X - LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
					}
					g.setColor(Color.GRAY);
					g.drawString(Math.round(Main.inputLayer[firstNodeToDraw].activation * ROUNDING) / ROUNDING + "", HIDDEN1_X + NODE_SIZE + TEXT_SPACE - LAYER_SPACING, HIDDEN1_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);			
				}
				
				firstNodeToDraw ++;
			}
			
			for(int i = 0; i < Main.hiddenLayer1.length; i ++) {
				int grayscale = (int) (Main.hiddenLayer1[i].activation * 255);
				// Because of the way doubles work, it's possible to get a value very slightly above 255 or below 0
				if(grayscale > 255) {
					grayscale = 255;
				}
				else if(grayscale < 0) {
					grayscale = 0;
				}
				g.setColor(new Color(grayscale, grayscale, grayscale));
				g.fillOval(HIDDEN1_X, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
				if(Main.darkMode) {
					g.setColor(Color.WHITE);
					g.drawOval(HIDDEN1_X, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
				}
				g.setColor(Color.GRAY);
				g.drawString(Math.round(Main.hiddenLayer1[i].activation * ROUNDING) / ROUNDING + "", HIDDEN1_X + NODE_SIZE + TEXT_SPACE, HIDDEN1_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);	
			}
			
			// Draw second hidden layer
			for(int i = 0; i < Main.hiddenLayer2.length; i ++) {
				int grayscale = (int) (Main.hiddenLayer2[i].activation * 255);
				// Because of the way doubles work, it's possible to get a value very slightly above 255 or below 0
				if(grayscale > 255) {
					grayscale = 255;
				}
				else if(grayscale < 0) {
					grayscale = 0;
				}
				g.setColor(new Color(grayscale, grayscale, grayscale));
				g.fillOval(HIDDEN1_X + LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
				if(Main.darkMode) {
					g.setColor(Color.WHITE);
					g.drawOval(HIDDEN1_X + LAYER_SPACING, HIDDEN1_START_Y + HIDDEN_SPACING_Y * i, NODE_SIZE, NODE_SIZE);
				}
				g.setColor(Color.GRAY);
				g.drawString(Math.round(Main.hiddenLayer2[i].activation * ROUNDING) / ROUNDING + "", HIDDEN1_X + NODE_SIZE + TEXT_SPACE + LAYER_SPACING, HIDDEN1_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + HIDDEN_SPACING_Y * i);	
			}
			
			// Draw output layer
			for(int i = 0; i < Main.outputLayer.length; i ++) {
				int grayscale = (int) (Main.outputLayer[i].activation * 255);
				// Because of the way doubles work, it's possible to get a value very slightly above 255 or below 0
				if(grayscale > 255) {
					grayscale = 255;
				}
				else if(grayscale < 0) {
					grayscale = 0;
				}
				g.setColor(new Color(grayscale, grayscale, grayscale));
				g.fillOval(HIDDEN1_X + LAYER_SPACING * DOUBLE, OUTPUT_START_Y + OUTPUT_SPACING * i, NODE_SIZE, NODE_SIZE);
				if(Main.darkMode) {
					g.setColor(Color.WHITE);
					g.drawOval(HIDDEN1_X + LAYER_SPACING * DOUBLE, OUTPUT_START_Y + OUTPUT_SPACING * i, NODE_SIZE, NODE_SIZE);
				}
				g.setColor(Color.GRAY);
				g.drawString(Math.round(Main.outputLayer[i].activation * ROUNDING) / ROUNDING + "", HIDDEN1_X + NODE_SIZE + TEXT_SPACE + LAYER_SPACING * DOUBLE, OUTPUT_START_Y + (NODE_SIZE / HALF) + TEXT_SPACE + OUTPUT_SPACING * i);	
			}
		}
	}

	public int determineThickness(float weight) {
		// Returns a thickness value for the connection drawing based on the weight
		if(weight >= 0.9) {
			return 20;
		}
		else if(weight >= 0.8) {
			return 18;
		}
		else if(weight >= 0.7) {
			return 16;
		}
		else if(weight >= 0.6) {
			return 14;
		}
		else if(weight >= 0.5) {
			return 12;
		}
		else if(weight >= 0.4) {
			return 10;
		}
		else if(weight >= 0.3) {
			return 8;
		}
		else if(weight >= 0.2) {
			return 6;
		}
		else if(weight >= 0.1) {
			return 4;
		}
		else {
			return 2;
		}
	}
}
