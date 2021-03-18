package neuralNetwork;

public class Node {
	public float activation; // Activation value of this node (or input value if it's an input node)
	public float unsquishedActivation; // Value of the activation before it was put into the sigmoid function
	public float partialDerivative; // Stores ∂C/∂a for this node (0 for input layer)
	public int layer; // Layer of this node
	  
	//constructor
	public Node(int l) {
		layer = l; // 0 for input layer, 1 for hidden layer 1, 2 for hidden layer 2, and 3 for output layer
		unsquishedActivation = 0;
		activation = 0;
		partialDerivative = 0;
	}
}