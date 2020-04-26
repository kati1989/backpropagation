package edu.backprop.base;

import java.io.File;
import java.io.FileReader;

import edu.backprop.network.MathUtil;
import edu.backprop.network.PatternGroup;

import java.io.BufferedReader;

/**
 * Atalakitja a txt mintakat byte mintakka
 *
 */
public class GeneratePatterns {

	private static final String[] PATTERN_FILENAMES_ = { "0.txt", "1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt",
			"7.txt", "8.txt", "9.txt" };

	/**
	 * Atalakitja a txt mintakat byte- mintakka
	 */
	public void createLearningSet() throws Exception {
		PatternGroup pl = new PatternGroup();

		double input[] = null;
		double output[] = new double[NumberBackpropagation.PATTERN_COUNT];

		for (int i = 0; i < PATTERN_FILENAMES_.length; i++) {
			output[i] = MathUtil.ZERO;
		}

		for (int i = 0; i < PATTERN_FILENAMES_.length; i++) {
			System.out.println(PATTERN_FILENAMES_[i]);
			input = patternReader(NumberBackpropagation.FILE_PATH + PATTERN_FILENAMES_[i]);
			if (i == 0) {
				output[i] = MathUtil.ONE;
			} else {
				output[i] = MathUtil.ONE;
				output[i - 1] = MathUtil.ZERO;
			}

			pl.add(input, output);
		}

		pl.writePatternGroupToFile(new File(NumberBackpropagation.LEARN_FILE));
	}

	/**
	 * Atalakit szoveges mintat byte mintakka a neuron halonak.
	 * 
	 * @param fileName
	 *            a file neve amibol beolvasunk
	 * @return visszateriti byte tomb kent a mintat
	 */
	private double[] patternReader(String fileName) {
		String pattern = null;

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String buffer;
			while ((buffer = br.readLine()) != null) {
				if (pattern == null) {
					pattern = buffer;
				} else {
					pattern += buffer;
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pattern.length() != NumberBackpropagation.PATTERN_HEIGHT * NumberBackpropagation.PATTERN_WIDTH) {
			throw new IllegalArgumentException("rossz minta hossz");
		}

		byte[] tmp = pattern.getBytes();
		double[] result = new double[NumberBackpropagation.PATTERN_HEIGHT * NumberBackpropagation.PATTERN_WIDTH];

		for (int i = 0; i < NumberBackpropagation.PATTERN_HEIGHT * NumberBackpropagation.PATTERN_WIDTH; i++) {
			result[i] = (tmp[i] == 0x20) ? MathUtil.ZERO : MathUtil.ONE;
		}

		return (result);
	}

	public static void main(String args[]) throws Exception {

		GeneratePatterns gp = new GeneratePatterns();
		gp.createLearningSet();

	}
}
