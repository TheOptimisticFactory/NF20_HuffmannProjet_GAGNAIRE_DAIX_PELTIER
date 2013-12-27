package main.file.basics;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe correspondant � l'arborescence de l'arbre de Huffman (ensemble ordonn�e de noeuds)
 * @version 0.2.0
 */
public class HuffmanBean {
	private SortedSet<Node> tree;
	
	/* ========================================= CONSTRUCTEUR ========================================= */
	
	/**
	 * Constructeur, prend une MAP<String, Integer> et la transforme en une arborescence de noeuds 
	 * @param originalValues : Map<String, Integer> contenant toutes les lettres et leurs fr�quences associ�es
	 */
	public HuffmanBean(Map<String, Integer> originalValues) {
		this.tree = new TreeSet<Node>();
		for(Entry<String, Integer> entry : originalValues.entrySet()) {
			this.tree.add(new Node(0,entry.getValue()));
		}
	}
	
	/**
	 * Constructeur, prend un tableau d'entier, et la transforme en une arborescence de noeuds
	 * @param originalValues : La cl� => Valeur de l'octet, la taille, sa fr�quence. Le tableau fait forcement 256 de taille.
	 */
	public HuffmanBean(int[] originalValues){
		this.tree = new TreeSet<Node>();
		for(int i =0; i < 256; i++)
		{
			if(originalValues[i]!=0)
			this.tree.add(new Node(i, originalValues[i]));
		}
	}
	
	/* ========================================= LOGIQUE METIER ========================================= */
	
	/**
	 * M�thode permettant de progression d'une �tape dans la r�alisation du codage de huffmann
	 * Principe de fonctionnement :
	 * - R�cupere la lettre ayant la fr�quence la plus basse
	 * - V�rifie s'il existe s'il existe une lettre ayant une fr�quence plus �lev�e (lettre suivante dans la collection)
	 * - S'il en existe une alors, les deux sont mix�es en une (en faisant la somme de leurs fr�quences respectives)
	 */
	public void processingOneMoreStep() {
		Iterator<Node> it = this.tree.iterator();
		Node lowestEntry;

		lowestEntry = it.next(); //R�cup�ration de la lettre ayant la plus petite fr�quence

		if(it.hasNext()) {
			mergeLowestTwoValues(it,lowestEntry); //addition des fr�quences s'il reste encore une lettre
		}
	}

	/**
	 * M�thode priv�e permettant de mixer deux lettres en une (en additionnant leurs fr�quences respectives)
	 * @param it Iterateur permettant de parcourir la collection
	 * @param lowestEntry Entry r�cup�r�e par la m�thode pr�c�dente (correspond � la lettre ayant la fr�quence minimale)
	 */
	private void mergeLowestTwoValues(Iterator<Node> it, Node lowestEntry) {
		Node nextLowestEntry;
		Integer frequencyOne, frequencyTwo;

		frequencyOne = lowestEntry.getFrequency();

		if(it.hasNext()) {
			nextLowestEntry = it.next();
			frequencyTwo = nextLowestEntry.getFrequency();
			Integer mergedFrequency = frequencyOne + frequencyTwo;
			insertNewNodeWithLeaves(-1, mergedFrequency, lowestEntry, nextLowestEntry);
		}
	}

	/**
	 * M�thode permettant d'inserer un nouveau noeud dans l'arbre (en prenant ajoutant les deux pr�c�dents comme fils)
	 * @param mergedLetters String contenant la (ou les) lettre(s)
	 * @param mergedFrequency Integer contenant la fr�quence (ou somme de fr�quences) associ�e(s)
	 * @param lowestEntry Noeud correpondant � la fr�quence la plus faible
	 * @param nextLowestEntry Noeud correspondant � la fr�quence (suivante) la plus faible
	 */
	private void insertNewNodeWithLeaves(int Mergedvalue, Integer mergedFrequency, Node lowestEntry, Node nextLowestEntry) {
		this.tree.add(new Node(Mergedvalue,mergedFrequency,lowestEntry,nextLowestEntry));
		this.tree.remove(lowestEntry);
		this.tree.remove(nextLowestEntry);
	}
	
	/**
	 * M�thode permettant d'attribuer � chacune des lettres de l'arbre de huffman un poids
	 */
	public void assignWeightToAllLetters() {
		for(Node node : this.tree) {
			String initialWeight = "";
			boolean father[] = new boolean[0];
			int fatherLength = 0;
			boolean current = false;
			node.assignWeightToLetter(initialWeight);
			node.assignEncodage(father, fatherLength, current);
		}
	}
	
	/* ========================================= AFFICHAGE ========================================= */
	
	/**
	 * M�thode permettant d'avoir une repr�sentation visuelle de l'arbre de Huffman
	 * @param stream Flux de sortie o� afficher l'arbre
	 */
	public void displayTree(PrintStream stream) {
		for(Node node : this.tree) {
			node.displayNodeInfoWithDepth(stream,0);
			stream.println("");
		}
	}
	
	/**
	 * M�thode permettant d'afficher le poids (l'encodage) de chacune des lettres de l'arbre
	 * @param stream Flux de sortie o� afficher les r�sultats
	 */
	public void displayNodeWeight(PrintStream stream) {
		for(Node node : this.tree) {
			node.displayWeight(stream);
			stream.println("");
		}
	}
	
	/* ========================================= GETTERS ========================================= */
	
	public Dictionnary getDictionnary(){
		Dictionnary dec = new Dictionnary();
		for(Node node : this.tree)
		{
			node.addDictionnary(dec);
		}
		return dec;
	}
	

	/**
	 * M�thode permettant de v�rifier si l'algorithme est termin�
	 * @return true s'il reste plus de 2 noeuds ou false, s'il n'en reste plus qu'un
	 */
	public boolean stillHasNodesToProcess() {
		if(this.tree.size() >= 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * M�thode permettant de r�cuperer le nombre de noeuds de l'arbre
	 * @return int correspondant au nombre de noeuds
	 */
	public int size() {
		return this.tree.size();
	}
	
	/**
	 * M�thode permettant de r�cup�rer un iterateur sur l'ensemble de noeuds
	 * @return iterateur permettant d'iterer sur la collection
	 */
	public Iterator<Node> getIterator() {
		return this.tree.iterator();
	}
}
