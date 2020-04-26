package edu.backprop.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Absztrakt osztaly a neuronoknak
 */
public abstract class Neuron implements Serializable{

	final int id = UniqueIdGenerator.getId(); // egyedi azonosito
	double error; // a neuron hibaja
	double value; // a neuron erteke
	List<Connection> inConnections = new ArrayList<Connection>(); // bementi kapcsolatok
	List<Connection> outConnections = new ArrayList<Connection>(); // kimeneti kapcsolatok

	private static final long serialVersionUID = -8437051058370670157L;

	public int getId() {
		return id;
	}

	public double getError() {
		return this.error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double getValue() {
		return (value);
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void connect(Neuron output, Connection con) {
		outConnections.add(con);
		output.inConnections.add(con);

		con.setInputNeuron(this);
		con.setOutputNeuron(output);
	}

	/**
	 * Kiiratjuk, hogy az aktualis Neuron mely Neuronokhoz kapcsolodik.
	 */
	public String printConnections() {
		String tmp = "id:" + id;

		tmp += ":in:";
		Iterator<Connection> inConIterator = inConnections.iterator();

		while (inConIterator.hasNext()) {
			tmp += inConIterator.next().toString() + ":";
		}

		tmp += "out:";
		inConIterator = outConnections.iterator();

		while (inConIterator.hasNext()) {
			tmp += inConIterator.next().toString() + ":";
		}

		return (tmp);
	}

	public String toString() {
		return (id + " error:" + error + " value:" + value + " input:" + inConnections.size() + " output:"
				+ outConnections.size());
	}

}
