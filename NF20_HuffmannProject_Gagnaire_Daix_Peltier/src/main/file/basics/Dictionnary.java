package main.file.basics;

public class Dictionnary {
	boolean values[][];
	int valuesSize[];
	
	/* ========================================= CONSTRUCTEUR ========================================= */
	
	public Dictionnary(){
		this.values = new boolean[256][24];
		this.valuesSize = new int[256];
	}
	
	/* ========================================= GETTERS ========================================= */
	
	public int getSize(int octet){
		return this.valuesSize[octet];
	}
	
	public boolean getBit(int octet, int bitIndex) {
		return this.values[octet][bitIndex];
	}
	
	/* ========================================= SETTERS ========================================= */
	
	public void setValue(int octet, int size, boolean encodage[]) {
		size--;
		for(int i = 0; i < size; i++)
		this.values[octet][i] = encodage[i + 1];
		this.valuesSize[octet] = size;
		
	}
}
