package edu.backprop.network;

/**
 * Bemeneti neuron 
 */
public class InputNeuron extends Neuron {
    
	private static final long serialVersionUID = -7434630887769481379L;
    
	public void setValue(double value) {
		if ((value < 0.0) || (value > 1.0)) {
			throw new IllegalArgumentException("hibas neuron ertek");
		}

		this.value = value;
	}

	public String toString() {
		return ("Bemeneti Neuron:" + super.toString());
	}

}