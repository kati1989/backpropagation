package edu.backprop.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Az osszes minta csoportositva
 *
 */
public class PatternGroup {

    private List<Pattern> patterns = new ArrayList<Pattern>();

    public PatternGroup() {
    }
    
    /**
     * Beolvassa a minta adatot az allomanybol
     * 
     * @param file - minta allomany
     */
    public PatternGroup(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
    	readPatternGroupFromFile(file);
    }
    
    public void add(Pattern pattern) {
    	patterns.add(pattern);
    }
    
    /**
     * Hozzaad egy uj mintat a megadott bement es kimenet alapjan
     * @param input
     * @param output
     */
    public void add(double[] in, double[] out) {
    	patterns.add(new Pattern(in, out));
    }
    
    public Pattern get(int i) {
    	return patterns.get(i);
    }
    
    public int size() {
    	return patterns.size();
    }

	/**
	 * Kiirja a mintakat serializalva egy allomanyba
	 * 
	 * @param file kiementi allomany
	 */
	public void writePatternGroupToFile(File file) throws IOException, FileNotFoundException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject((ArrayList<Pattern>)patterns);
		outputStream.close();
	}

	/**
	 * Beolvassa a mintakat egy allomanybol
	 * 
	 * @param file bemeneti alloman
	 */
	@SuppressWarnings("unchecked")
	public void readPatternGroupFromFile(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
		patterns = (ArrayList<Pattern>) inputStream.readObject();
		inputStream.close();
	}

}

