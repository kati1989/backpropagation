package edu.backprop.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Backpropagation Algoritmus a neuron halonak.
 */
public class Backpropagation {
	
	private NeuronNetwork network;

	/**
	 * Konstruktor
	 *
	 * @param inputNeurons
	 *            bemeneti csomopontok szama
	 * @param middleNeurons
	 *            kozepso szinten levo neuronok szama
	 * @param outputNeurons
	 *            kimeneti szinten levo neuronok szama
	 * @param learningRate
	 *            tanulasi rata hiba szamitaskor
	 * @param momentum
	 *            sulyzok szamitasakor hasynalt ertek
	 */
	public Backpropagation(int inputNeurons, int middleNeurons, int outputNeurons, double learningRate,
			double momentum) {
		network = new NeuronNetwork(inputNeurons, middleNeurons, outputNeurons, learningRate, momentum);
	}

	/**
	 * Konstruktor perzisztalt neuron haloknak
	 * 
	 * @param file
	 *            allomanyban perzisztalt neuron halo.
	 */
	public Backpropagation(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
		network = (NeuronNetwork) inputStream.readObject();
		inputStream.close();
	}

	/**
	 * Halozat futtatasa a megadott minta segitsegevel
	 * 
	 * @param bemeneti
	 *            minta
	 * @return osztalyozasi kimenet
	 */
	public double[] runNetwork(double[] pattern) {
		return network.runNeuronNetwork(pattern);
	}

	/**
	 * Halozat tanitasa a megadott minta csoport alapjan
	 * 
	 * @param patternGroup
	 *            minta lista tanitasra
	 * @param match
	 *            mintak szama melyeknel megegyezes kell legyen, hogy a tanitast
	 *            sikeresnek tekintsuk, ha -1 akkor az osszes kell.
	 * @param cycles
	 *            maximum tanitasi ciklusok szama, ha -1 akkor nincs korlat
	 * @param limit
	 *            koszobertek mely segitsegevel a kozel zero vagy egyes
	 *            ertekeket eleg kozelinek tekintunk.
	 * @param verbose
	 *            kiirja a fejlodes lepeseit
	 * @return megtanitott mintak mennyisege
	 */
	public int learnNetwork(PatternGroup patternGroup, int match, int cycles, double limit, boolean verbose) {
		int size = patternGroup.size();
		int counter = 0;
		int success;
		int maxSuccess = 0;

		if (match < 0) {
			match = size;
		}

		boolean exit;

		do {
			success = 0;

			for (int i = 0; i < size; i++) {
				Pattern pattern = patternGroup.get(i);
				network.runNeuronNetwork(pattern.getInput());

				double[] rawResults = network.trainNeuronNetwork(pattern.getOutput());
				int[] realValues = MathUtil.convertToLimitValues(limit, pattern.getOutput());
				int[] results = MathUtil.convertToLimitValues(limit, rawResults);

				pattern.setLearned(true);
				for (int j = 0; j < results.length; j++) {
					if (results[j] != realValues[j]) {
						pattern.setLearned(false);
					}
				}

				if (pattern.isLearned()) {
					success++;
				}
			}

			if (maxSuccess < success) {
				maxSuccess = success;
			}

			if ((++counter % 10000) == 0) {
				if (verbose) {
					System.out.println(counter + " Sikeres:" + success + " hatra van:" + patternGroup.size() + " legjobb futas:"
							+ maxSuccess);
				}
			}

			if (success < size) {
				exit = true;
			} else {
				exit = false;
			}

			if (cycles > -1) {
				if (counter >= cycles) {
					exit = false;
				}
			}

			if (success >= match) {
				exit = false;
			}
		} while (exit);

		if (verbose) {
			System.out.println("Tanitas kesz lett: " + counter + " ciklus alatt");
		}

		return success;
	}

	/**
	 * A backpropagat halozat kimenetse allomanyba
	 * 
	 * @param file
	 *           allomany
	 */
	public void saveNetwork(File file) throws IOException, FileNotFoundException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(network);
		outputStream.close();
	}
}