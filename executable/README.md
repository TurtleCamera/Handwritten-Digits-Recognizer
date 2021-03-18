# Executable for this project
Allen Zou (TurtleCamera)
July 14, 2019

### Purpose  
The purpose of this program is to provide a relatively simple framework with debug to the user to interact with the neurons, weights, and biases of the neural network in training. The network's purpose is to learn how to correctly read handwriting digits (supervised). After training, the neural network will then enter an accuracy testing phase in which it will be tested on the percentage of images it can read correctly.

### Usage  
Upon opening, the neural network will be initialized with random weights and biases. Essentially, this network is going to perform poorly. In order to train it, the under will need to either automatically or manaully load images into the network. For every image loaded, the neural network will feed forward the image to each layer until it reaches the output layer in which it will decide on which number it thinks the image shows. More on the specifics will be mentioned later. 

### Controls
-------------------------------------|--------------------------
Toggle weight restriction (limits the weights of the neural network to the range [-1, 1], but it is recommended that this option be kept constant throughout the training session).                            | B
Change layers                        | Left and right arrow keys
Change nodes within a layer          | Up and down arrow keys
Load next image manually             | Space or Enter
Toggle automatic image advancement   | A
Toggle dark mode                     | D
Transition to testing phase (can only be pressed after training is completed) | T
Write the network with statistics file (can only be pressed after the testing phase is completed) | W
-------------------------------------|--------------------------

The program will display debug info, but the main debug feature implemented is the neural network display. The user can select different nodes within layers and between layers. When a node is selected, the debugger will display information corresponding to that selected node. This includes the unsquished activation on the left of the screen and all weight connections going into and out of the node. Additionally, for all of the weight connections visible, the weight values will be displayed in columns at the center of the screen along with the partial derivatives of the cost function with respect to those weights. Finally, the debugger will also display the bias and the partial derivative of the cost function with respect to the bias for the selected node as well as the partial derivative of the cost function with respect to the activation of the selected node (if it is needed for another calculation). Only the hidden layers have the partial derivative calculation. Additionally, in terms of selection, it is not feasible to display all input nodes as training data could contain hundreds of input nodes (784 if the user decides to use the training data provided), so a scrolling function has been added for that specific layer. To read the output nodes, they are internally numbered 0-9 from top to bottom. The final component of reading the network display on the right of the screen is the gray values on the sides of the activation. These gray values are the squished activations of each node. For the rest of the debug text on the left of the screen, they provide information such as traversed images in group (limited to 10 images per group for the purpose of stochastic gradient descent), all traversed images, total training images to be traversed, image rows and columns, image label (solution to image), backpropogation count, network's guess, and whether the weight restriction is enabled or disabled. On the top left corner of the screen, the cost function will be displayed as well as the average cost function of the current image group. For reference, the cost will display red if the value is over 0.75, yellow if the value is over 0.1, and green if the value is equal to or below 0.1. The current image as well as the last 9 processed images will be displayed on the top right corner and the left of the screen respectively. To tell where the image group is, the 10th image of the group will be highlighted with a green box. One small thing to note is that the partial derivatives of the cost function with respect to the bias values get replaced with images guessed correctly and accuracy during the testing phase. To enter the testing phase, the user must first complete the network's training session (load all training data). Then the user may press T to enter testing phase in which backpropogation and partial derivatives are no longer calculated. At the end of the testing session, the user may also press W to print a file with the following format:  

Total testing images (integer)  
Number of images guessed correctly (integer)  
Accuracy percentage (float)  
Number of input nodes (integer)  
Number of hidden layer 1 nodes (integer)  
Number of hidden layer 2 nodes (integer)  
Number of output layer nodes (integer)  
Weight matrix 1 values (floats)  
Weight matrix 2 values (floats)  
Weight matrix 3 values (floats)  
Bias vector 1 values (floats)  
Bias vector 2 values (floats)  
Bias vector 3 values (floats)  

### How to run  
Make sure the following files are in the same directory (there are image files because some symbols can't be written in java):  
- Supervised_Handwriting_Reader.jar   
- avgCostFuntion.png  
- costFunction.png 
- train-images.idx3-ubyte  
- train-labels.idx1-ubyte  
- t10k-images.idx3-ubyte  
- t10k-labels.idx1-ubyte   
  
Then simply run the Supervised_Handwriting_Reader.jar file or run the start.bat script if the user wants the console open. The console only displays which files are missing (if there are any) and what gets loaded.
