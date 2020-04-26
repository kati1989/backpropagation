package edu.backprop.network;

import java.io.Serializable;

/**
 * Minta adatstruktura taroloja
 * 
 */
public class Pattern implements Serializable {

	private double[] in;
	private double[] out;
	private boolean learned = false; // igaz hogyha ez a minta mar tanitva volt

	private static final long serialVersionUID = 9154436050951080903L;

	/**
	 * Konstruktor
	 * @param in 
	 * 		bemeneti minta
	 * @param out 
	 * 		kimeneti minta
	 */
	public Pattern(double[] in, double[] out) {
		this.in = (double[]) in.clone();
		this.out = (double[]) out.clone();
	}

	public double[] getInput() {
		return (in);
	}

	public double[] getOutput() {
		return (out);
	}

	public boolean isLearned() {
		return learned;
	}

	public void setLearned(boolean l) {
		learned = l;
	}

	public String toString() {
		String tmp = "trained:" + learned;

		tmp += " input ";
		if (in == null) {
			tmp += " null ";
		} else {
			for (int i = 0; i < in.length; i++) {
				tmp += in[i] + " ";
			}
		}

		tmp += " output ";
		if (out == null) {
			tmp += " null ";
		} else {
			for (int i = 0; i < out.length; i++) {
				tmp += out[i] + " ";
			}
		}

		return (tmp);
	}

	public void printPattern() {
		System.out.println("Kimeneti Minta");
		for (int i = 0; i < out.length; i++) {
			System.out.println(i + " " + out[i]);
		}

		System.out.println("Bemeneti Minta");
		for (int i = 0; i < in.length; i++) {
			System.out.println(i + " " + in[i]);
		}
	}

}