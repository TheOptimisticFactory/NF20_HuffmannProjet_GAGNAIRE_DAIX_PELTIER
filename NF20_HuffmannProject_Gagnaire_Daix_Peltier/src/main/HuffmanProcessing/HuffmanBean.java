package main.HuffmanProcessing;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe correspondant à l'arborescence de l'arbre de Huffman (ensemble ordonnée de noeuds)
 * @version 0.2.0
 */
public class HuffmanBean {
	private SortedSet<Node> tree;
	
	/**
	 * Constructeur, prend une MAP<String, Integer> et la transforme en une arborescence de noeuds 
	 * @param originalValues : Map<String, Integer> contenant toutes les lettres et leurs fréquences associées
	 */
	public HuffmanBean(Map<String, Integer> originalValues) {
		this.tree = new TreeSet<Node>();
		for(Entry<String, Integer> entry : originalValues.entrySet()) {
			this.tree.add(new Node(entry.getKey(),entry.getValue()));
		}
	}
	
	/**
	 * Constructeur, prend un tableau d'entier, et la transforme en une arborescence de noeuds
	 * @param originalValues : La clé => Valeur de l'octet, la taille, sa fréquence. Le tableau fait forcement 256 de taille.
	 */
	public HuffmanBean(int[] originalValues){
		this.tree = new TreeSet<Node>();
		for(int i =0; i < 65536; i++)
		{
			if(originalValues[i]!=0)
			this.tree.add(new Node(""+((char)i), originalValues[i]));
		}
	}
	
	
	/**
	 * Méthode permettant de vérifier si l'algorithme est terminé
	 * @return true s'il reste plus de 2 noeuds ou false, s'il n'en reste plus qu'un
	 */
	public boolean stillHasNodesToProcess() {
		if(this.tree.size() >= 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode permettant de progression d'une étape dans la réalisation du codage de huffmann
	 * Principe de fonctionnement :
	 * - Récupere la lettre ayant la fréquence la plus basse
	 * - Vérifie s'il existe s'il existe une lettre ayant une fréquence plus élevée (lettre suivante dans la collection)
	 * - S'il en existe une alors, les deux sont mixées en une (en faisant la somme de leurs fréquences respectives)
	 */
	public void processingOneMoreStep() {
		Iterator<Node> it = this.tree.iterator();
		Node lowestEntry;

		lowestEntry = it.next(); //Récupération de la lettre ayant la plus petite fréquence

		if(it.hasNext()) {
			mergeLowestTwoValues(it,lowestEntry); //addition des fréquences s'il reste encore une lettre
		}
	}

	/**
	 * Méthode privée permettant de mixer deux lettres en une (en additionnant leurs fréquences respectives)
	 * @param it Iterateur permettant de parcourir la collection
	 * @param lowestEntry Entry récupérée par la méthode précédente (correspond à la lettre ayant la fréquence minimale)
	 */
	private void mergeLowestTwoValues(Iterator<Node> it, Node lowestEntry) {
		Node nextLowestEntry;
		String letterOne, letterTwo;
		Integer frequencyOne, frequencyTwo;

		letterOne = lowestEntry.getLetter();
		frequencyOne = lowestEntry.getFrequency();

		if(it.hasNext()) {
			nextLowestEntry = it.next();
			letterTwo = nextLowestEntry.getLetter();
			frequencyTwo = nextLowestEntry.getFrequency();

			String mergedLetters = letterOne + "." + letterTwo;
			Integer mergedFrequency = frequencyOne + frequencyTwo;

			insertNewNodeWithLeaves(mergedLetters, mergedFrequency, lowestEntry, nextLowestEntry);
			//System.out.println("");
			//System.out.println("[MERGING] " + letterOne + "(" + frequencyOne + ")" + " AND " + letterTwo + "(" + frequencyTwo + ")" + " => " + mergedLetters + "(" + mergedFrequency + ")"); 
		}
	}

	/**
	 * Méthode permettant d'inserer un nouveau noeud dans l'arbre (en prenant ajoutant les deux précédents comme fils)
	 * @param mergedLetters String contenant la (ou les) lettre(s)
	 * @param mergedFrequency Integer contenant la fréquence (ou somme de fréquences) associée(s)
	 * @param lowestEntry Noeud correpondant à la fréquence la plus faible
	 * @param nextLowestEntry Noeud correspondant à la fréquence (suivante) la plus faible
	 */
	private void insertNewNodeWithLeaves(String mergedLetters, Integer mergedFrequency, Node lowestEntry, Node nextLowestEntry) {
		this.tree.add(new Node(mergedLetters,mergedFrequency,lowestEntry,nextLowestEntry));
		this.tree.remove(lowestEntry);
		this.tree.remove(nextLowestEntry);
	}
	
	/**
	 * Méthode permettant d'attribuer à chacune des lettres de l'arbre de huffman un poids
	 */
	public void assignWeightToAllLetters() {
		for(Node node : this.tree) {
			String initialWeight = "";
			node.assignWeightToLetter(initialWeight);
		}
	}
	
	/**
	 * Méthode permettant d'avoir une représentation visuelle de l'arbre de Huffman
	 * @param stream Flux de sortie où afficher l'arbre
	 */
	public void displayTree(PrintStream stream) {
		for(Node node : this.tree) {
			node.displayNodeInfoWithDepth(stream,0);
			stream.println("");
		}
	}
	
	/**
	 * Méthode permettant d'afficher le poids (l'encodage) de chacune des lettres de l'arbre
	 * @param stream Flux de sortie où afficher les résultats
	 */
	public void displayNodeWeight(PrintStream stream) {
		for(Node node : this.tree) {
			node.displayWeight(stream);
			stream.println("");
		}
	}
	
	/**
	 * Méthode permettant de récuperer le nombre de noeuds de l'arbre
	 * @return int correspondant au nombre de noeuds
	 */
	public int size() {
		return this.tree.size();
	}
	
	/**
	 * Méthode permettant de récupérer un iterateur sur l'ensemble de noeuds
	 * @return iterateur permettant d'iterer sur la collection
	 */
	public Iterator<Node> getIterator() {
		return this.tree.iterator();
	}
}
