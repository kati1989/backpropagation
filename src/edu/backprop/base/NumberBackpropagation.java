package edu.backprop.base;

import java.io.File;
import java.io.IOException;

import edu.backprop.network.Backpropagation;

import java.io.FileNotFoundException;


/**
 * Szamfelismero
 */
public class NumberBackpropagation extends Backpropagation {
    public static final String LEARN_FILE = "pattern.src";       
    public static final String FILE_PATH = "src/edu/backprop/base/";
    public static final String NETWORK_FILE = "trainedNetwork.src";
    
    public static final int PATTERN_WIDTH=5;
    public static final int PATTERN_HEIGHT=7;
    public static final int PATTERN_COUNT=10;
  
    /**
     * Konstruktor egy Backpropagation halozatnak
     */
    public NumberBackpropagation(int inputSize, int middleSize, int outputSize, double learningRate, double momentum) {
    	super(inputSize, middleSize, outputSize, learningRate, momentum);
    }

    /**
     * Konstruktor egy meglevo Backpropagation halozatnak
     */
    public NumberBackpropagation(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
    	super(file);
    }
}
