package neuralNetwork;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class InputFrame extends JFrame implements KeyListener {
	
	public static int layerSelected = 0; // Tells which layer the user is currently on
	
	@Override
	public void keyPressed(KeyEvent e) {
		// User selects nodes depending on which layer they are currently selecting
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(layerSelected == Main.INPUT_LAYER) {
				if(PaintPanel.firstSelectedNode < PaintPanel.MAX_INPUT_NODES - 1) {
					PaintPanel.firstSelectedNode ++;
				}
			}
			else if(layerSelected == Main.HIDDEN_LAYER1) {	
				if(PaintPanel.secondSelectedNode < PaintPanel.MAX_HIDDEN_NODES - 1) {
					PaintPanel.secondSelectedNode ++;
				}
			}
			else if(layerSelected == Main.HIDDEN_LAYER2) {	
				if(PaintPanel.thirdSelectedNode < PaintPanel.MAX_HIDDEN_NODES - 1) {
					PaintPanel.thirdSelectedNode ++;
				}
			}
			else if(layerSelected == Main.OUTPUT_LAYER) {	
				if(PaintPanel.fourthSelectedNode < PaintPanel.MAX_OUTPUT_NODES - 1) {
					PaintPanel.fourthSelectedNode ++;
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(layerSelected == Main.INPUT_LAYER) {
				if(PaintPanel.firstSelectedNode > PaintPanel.MIN_INPUT_NODES) {
					PaintPanel.firstSelectedNode --;
				}
			}
			else if(layerSelected == Main.HIDDEN_LAYER1) {		
				if(PaintPanel.secondSelectedNode > PaintPanel.MIN_HIDDEN_NODES) {
					PaintPanel.secondSelectedNode --;
				}
			}
			else if(layerSelected == Main.HIDDEN_LAYER2) {		
				if(PaintPanel.thirdSelectedNode > PaintPanel.MIN_HIDDEN_NODES) {
					PaintPanel.thirdSelectedNode --;
				}
			}
			else if(layerSelected == Main.OUTPUT_LAYER) {		
				if(PaintPanel.fourthSelectedNode > PaintPanel.MIN_OUTPUT_NODES) {
					PaintPanel.fourthSelectedNode --;
				}
			}
		}	// Change layer
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(layerSelected < Main.OUTPUT_LAYER) {
				layerSelected ++;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(layerSelected > Main.INPUT_LAYER) {
				layerSelected --;
			}
		}	// Advances training data
		else if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			Main.next = true;
		}	// When toggled, training data advances automatically
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			Main.autoAdvance = !Main.autoAdvance;
		}	// When toggled, weights are no longer restricted to the range [-1, 1]
		else if(e.getKeyCode() == KeyEvent.VK_B) {
			Main.disableWeightRestriction = !Main.disableWeightRestriction;
		}	// Can only be pressed after training completes-enters accuracy test mode
		else if(e.getKeyCode() == KeyEvent.VK_T && Main.checkCompleted() && Main.inTraining) {
			Main.inTraining = false;
			Main.inTesting = true;
		}	// Write a file (once again, can only be pressed after testing has completed
		else if(e.getKeyCode() == KeyEvent.VK_W && Main.checkCompleted() && Main.inTesting) {
			Main.fileWritten = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Toggles between light and dark mode
		if(e.getKeyCode() == KeyEvent.VK_D) {
			Main.darkMode = !Main.darkMode;
			
			// Invert images
			Main.invertImages();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing needed here
	}
}
