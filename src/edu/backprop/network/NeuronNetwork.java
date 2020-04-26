package edu.backprop.network;

import java.io.Serializable;

/**
 * Backpropagation neuron halo.
 * 
 */
public class NeuronNetwork implements Serializable {

	private final Connection[] connections;
	private final InputNeuron[] inputNeurons;
	private final MiddleNeuron[] middleNeurons;
	private final OutNeuron[] outputNeurons;

	private static final long serialVersionUID = 5668812853290831632L;

	/**
	 * Konstruktor egy 3 szintes Backpropagation-hoz Neuronok es kapcsolatok
	 * letrehozasa es osszekotese
	 * 
	 * @param inputSize
	 *            bemeneti csomopontok szama
	 * @param middleSize
	 *            kozepso csomopontok szama
	 * @param outputSize
	 *            kimeneti csomopontok szama
	 * @param learningRate
	 *            tanulasi rata
	 * @param momentum
	 */
	public NeuronNetwork(int inputSize, int middleSize, int outputSize, double learningRate, double momentum) {
		inputNeurons = new InputNeuron[inputSize];
		for (int i = 0; i < inputNeurons.length; i++) {
			inputNeurons[i] = new InputNeuron();
		}

		middleNeurons = new MiddleNeuron[middleSize];
		for (int i = 0; i < middleNeurons.length; i++) {
			middleNeurons[i] = new MiddleNeuron(learningRate, momentum);
		}

		outputNeurons = new OutNeuron[outputSize];
		for (int i = 0; i < outputNeurons.length; i++) {
			outputNeurons[i] = new OutNeuron(learningRate, momentum);
		}

		connections = new Connection[(inputSize * middleSize) + (middleSize * outputSize)];
		for (int i = 0; i < connections.length; i++) {
			connections[i] = new Connection();
		}

		int i = 0;
		for (int j = 0; j < inputNeurons.length; j++) {
			for (int k = 0; k < middleNeurons.length; k++) {
				inputNeurons[j].connect(middleNeurons[k], connections[i++]);
			}
		}

		for (int j = 0; j < middleNeurons.length; j++) {
			for (int k = 0; k < outputNeurons.length; k++) {
				middleNeurons[j].connect(outputNeurons[k], connections[i++]);
			}
		}
	}

	/**
	 * Halozat futtatasa az osztalyozo mintara
	 * 
	 * @param bementei
	 *            csomopont ertekek
	 * @return kimeneti csomopont eredmeny
	 */
	public double[] runNeuronNetwork(double[] input) {
		for (int i = 0; i < input.length; i++) {
			inputNeurons[i].setValue(input[i]);
		}

		for (int i = 0; i < middleNeurons.length; i++) {
			middleNeurons[i].runNode();
		}

		for (int i = 0; i < outputNeurons.length; i++) {
			outputNeurons[i].runNode();
		}

		double[] result = new double[outputNeurons.length];
		for (int i = 0; i < outputNeurons.length; i++) {
			result[i] = outputNeurons[i].getValue();
		}

		return result;
	}

	/**
	 * Tanitas Backpropagationnel (mozog hatrafele csomopontok kozott es
	 * modositja a sulyzokat/hibakat).
	 * 
	 * @param realPattern igazi minta
	 */
	public double[] trainNeuronNetwork(double[] realPattern) {
		for (int i = 0; i < realPattern.length; i++) {
			outputNeurons[i].setError(realPattern[i]);
		}

		for (int i = outputNeurons.length - 1; i >= 0; i--) {
			outputNeurons[i].learnNode();
		}

		for (int i = middleNeurons.length - 1; i >= 0; i--) {
			middleNeurons[i].learnNode();
		}

		double[] result = new double[outputNeurons.length];
		for (int i = 0; i < outputNeurons.length; i++) {
			result[i] = outputNeurons[i].getValue();
		}

		return result;
	}

}
