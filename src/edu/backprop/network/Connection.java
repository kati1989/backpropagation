package edu.backprop.network;

import java.io.Serializable;

/**
 * Modellezi a kapcsolatot ket csomopont kozott
 */
public class Connection  implements Serializable{
	
    final int id = UniqueIdGenerator.getId();
 
    private double weight = MathUtil.getBoundedRandom(-1.0, 1.0); // a sulyzokat random ertekekkel latjuk el -1 es 1 kozott
    private double delta;
    private Neuron inNeuron;
    private Neuron outNeuron;
    
    private static final long serialVersionUID = -8884064153744639354L;

    public int getId() {
    	return(id);
    }
    
    public void setInputNeuron(Neuron arg) {
        inNeuron = arg;
    }
    
    public void setOutputNeuron(Neuron arg) {
        outNeuron = arg;
    }
    
    public double getInputValue() {
        return(inNeuron.getValue());
    }
      
    /**
     * A hiba es a sulyzok szorzata a bemeneti neuronnak
     */
    public double getWeightedInputValue() {
        return(inNeuron.getValue() * weight);
    }
    
    /**
     * A hiba es a sulyzok szorzata a kimeno neuronnak
     */
    public double getWeightedOutputError() {
        return(outNeuron.getError() * weight);
    }
    
    public void updateWeight(double weight) {
        OutNeuron on = (OutNeuron) outNeuron;
        this.weight += weight + on.getMomentum() * delta;
        delta = weight;
    }

	public String toString() {
		String tmp = "Connection:" + id + " weight:" + weight + " delta:" + delta;

		if (inNeuron == null) {
			tmp += " in:null";
		} else {
			tmp += " in:" + inNeuron.getId();
		}

		if (outNeuron == null) {
			tmp += " out:null";
		} else {
			tmp += " out:" + outNeuron.getId();
		}

		return (tmp);
	}
    
}