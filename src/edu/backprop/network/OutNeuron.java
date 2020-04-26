package edu.backprop.network;

import java.util.Iterator;

public class OutNeuron extends Neuron {
	
    double learnRate; //a hiba szamolasnal felhasznalt ertek 
    double momentum; // sulyzok szamitasahoz szukseges a kapcsolatoknal
    
 	private static final long serialVersionUID = -8313299157918441811L;

	/**
	 * Konstruktor
	 * 
	 * @param learningRate
	 * @param momentum
	 */
	public OutNeuron(double learningRate, double momentum) {
		this.learnRate = learningRate;
		this.momentum = momentum;
	}
    
    public double getLearningRate() {
    	return learnRate;
    }
    
    public double getMomentum() {
    	return momentum;
    }

	/**
	 * Neuron ertekenek ujraszamolasa a sulyzott bement alapjan
	 */
	public void runNode() {
		double total = 0.0;

		Iterator<Connection> iterator = inConnections.iterator();
		while (iterator.hasNext()) {
			Connection arc = iterator.next();
			total += arc.getWeightedInputValue();
		}

		value = sigmoidTransfer(total);
	}

	/**
	 * Ujraszamolja a sulyzokat a hiba alapjan
	 */
	public void learnNode() {
		error = calculateError();

		Iterator<Connection> iterator = inConnections.iterator();
		while (iterator.hasNext()) {
			Connection connection = iterator.next();
			double delta = learnRate * error * connection.getInputValue();
			connection.updateWeight(delta);
		}
	}
    
    /**
     * Visszateriti a sigmoid tovabbitas erteket  0.0 < ertek < 1.0
     * 
     */
    private double sigmoidTransfer(double value) {
    	return (1.0 / (1.0 + Math.exp(-value)));
    }
    
    /**
     * Kiszamitja a kimeneti neuron hibat a sigmoid fuggveny derivaltja alapjan
     */
    private double calculateError() {
    	return (value * (1.0 - value) * (error - value));
    }
    
    public String toString() {
    	String result = "Kimenti Neuron:" + super.toString() + " learning rate:" + learnRate + " momentum:" + momentum;
    	return result;
    }

}