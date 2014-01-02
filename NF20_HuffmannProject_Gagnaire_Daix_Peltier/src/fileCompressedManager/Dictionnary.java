package fileCompressedManager;

public class Dictionnary {
	/*
	 * Class représentant un dictionnaire de valeur
	 * Clé : une lettre
	 * Valeur : la serie de bit équivalent.
	 */
	
	//values[octet][numero_de_bit] => 0 ou 1 (valeur du bit)
	boolean values[][];
	//valuesSize[octet] => nombre de bits pour ecrire cet octet en compression
	int valuesSize[];
	
	/**
	 * Constructeur
	 */
	public Dictionnary(){
		this.values = new boolean[256][24];
		this.valuesSize = new int[256];
	}
	/**
	 * Place un octet dans le dictionnaire.
	 * @param octet a quel octet correspond cet encodage
	 * @param size nombre de bit pour encoder cet octet
	 * @param encodage tableau de bit correspondant
	 */
	public void setValue(int octet, int size, boolean encodage[]) {
		size--;
		for(int i = 0; i < size; i++)
		this.values[octet][i] = encodage[i + 1];
		this.valuesSize[octet] = size;
		
	}
	/**
	 * Renvoie le nombre de bits pour ecrire l'octet
	 * @param octet dont on cherche la taille
	 * @return nombre de bit pour l'encoder
	 */
	public int getSize(int octet){
		return this.valuesSize[octet];
	}
	
	/**
	 * Renvoie un bit
	 * @param octet : octet dont on cherche un bit
	 * @param bitIndex index du bit qu'on cherche
	 * @return la valeur du bit 1 (true) ou 0 (false)
	 */
	public boolean getBit(int octet, int bitIndex) {
		return this.values[octet][bitIndex];
	}
}
