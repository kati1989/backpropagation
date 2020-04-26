package edu.backprop.base;
import java.io.File;

import java.io.IOException;

import edu.backprop.network.PatternGroup;

import java.io.FileNotFoundException;


/**Tanitsa a neuron halot a szamfelismeresre **/
public class Learn {

    private NumberBackpropagation bp;
    private PatternGroup pl;

    /**
     * Letrehoz egy neuron halot.
     */
    public Learn() {
    	int inputSize=NumberBackpropagation.PATTERN_HEIGHT*NumberBackpropagation.PATTERN_WIDTH;
    	int outputSize=NumberBackpropagation.PATTERN_COUNT;
    	bp = new NumberBackpropagation(inputSize, 5, outputSize, 0.45, 0.9);
    }

    /**
     * Betoltjuk a tanitasi adataokat
     * @param tanitasi adataok
     * @throws ClassNotFoundException 
     */
    public int loadTraining(File data) throws IOException, FileNotFoundException, ClassNotFoundException {
    	pl = new PatternGroup();
    	pl.readPatternGroupFromFile(data);
    	return(pl.size());
    }

    /**
     * Tanitsuk a halot a minta alapjan
     */
    public void performTraining() {
		bp.learnNetwork(pl, pl.size(), -1, 0.09, true);
    }

    /**
     * Elmentjuk a halot, a kesobbi hasznalatra
     * @param data amibe mentsuk a halot
     */
    public void saveTraining(File data) throws IOException, FileNotFoundException {
        bp.saveNetwork(data);
    }

	public static void main(String args[]) throws Exception {
		Learn tr = new Learn();
		int population = tr.loadTraining(new File(NumberBackpropagation.LEARN_FILE));
		System.out.println("Mintak betoltve w/" + population + " mintak");
		tr.performTraining();
		tr.saveTraining(new File(NumberBackpropagation.NETWORK_FILE));
	}
}
