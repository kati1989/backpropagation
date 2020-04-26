package edu.backprop.network;


import java.util.Iterator;

/**
 * Kozepso (rejtett) neuron
 */
public class MiddleNeuron extends OutNeuron {
    
	private static final long serialVersionUID = -1713752591254222978L;
    
    public MiddleNeuron(double learningRate, double momentum) {
    	super(learningRate, momentum);
    }

	/**
	 * Hiba szamitasa
	 * 
	 */
	private double computeError() {
		double total = 0.0;

		Iterator<Connection> i = outConnections.iterator();
		while (i.hasNext()) {
			Connection arc = i.next();
			total += arc.getWeightedOutputError();
		}

		double result = value * (1.0 - value) * total;

		return result;
	}

	/**
	 * Frissiti a sulyzokat a hibak alapjan
	 */
	public void learnNode() {
		error = computeError();

		Iterator<Connection> i = inConnections.iterator();
		while (i.hasNext()) {
			Connection connection = i.next();
			double delta = learnRate * error * connection.getInputValue();
			connection.updateWeight(delta);
		}
	}
    
    public String toString() {
    	String result = "Kozepso Neuron:" + super.toString() + " tanulasi ráta:" + learnRate + " momentum:" + momentum;
    	return result;
    }
    
}


