package edu.backprop.network;

import java.util.Random;

/**
 * Utility fuggvenyek, statikus ertekek.
 */
public class MathUtil {

	public static final double ONE = 0.9999999999;
	public static final double ZERO = 0.0000000001;

	private final static Random random = new Random();

	/**
	 * Visszaterit egy random valos erteket ket hatar kozott.
	 * 
	 * @param also hatar
	 * @param felso hatar
	 * @return egy random ertek a ket hatar kozott.
	 */
	public static synchronized double getBoundedRandom(double rangeLow, double rangeUp) {
		double range = rangeUp - rangeLow;
		double result = random.nextDouble() * range + rangeLow;
		return result;
	}

	/**
	 * Egy random valos szam.
	 * 
	 * @return random ertek
	 */
	public static synchronized double getRandomDouble() {
		return random.nextDouble();
	}

	/**
	 * Atalakit ertekeket egy hatar ertek segitsegevel 1 ill. 0. ha nem sikeres
	 * -1
	 * 
	 * @param limit
	 *            hatarertek
	 * @param values
	 *            atalakitando ertekek
	 * @return 0, 1 vagy -1 
	 */
	public static synchronized int[] convertToLimitValues(double limit, double[] values) {
		double upperLimit = 1.0 - limit;
		double lowerLimit = limit;

		int[] converted = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			if (values[i] > upperLimit) {
				converted[i] = 1;
			} else if (values[i] < lowerLimit) {
				converted[i] = 0;
			} else {
				converted[i] = -1;
			}
		}

		return converted;
	}

}