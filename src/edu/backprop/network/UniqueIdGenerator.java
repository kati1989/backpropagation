package edu.backprop.network;

public class UniqueIdGenerator {

    private static int id = 1;
    
    public static synchronized int getId() {
    	return(id++);
    }
}

