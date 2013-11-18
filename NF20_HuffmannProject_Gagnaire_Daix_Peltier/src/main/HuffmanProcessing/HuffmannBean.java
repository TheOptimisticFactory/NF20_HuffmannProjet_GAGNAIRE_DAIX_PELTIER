package main.HuffmanProcessing;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 * @author JoeTheFuckingFrypan
 * @version 0.1
 */
public class HuffmannBean {
	private SortedSet<Node> tree;
	
	public HuffmannBean(Map<String, Integer> originalValues) {
		this.tree = new TreeSet<Node>();
		for(Entry<String, Integer> entry : originalValues.entrySet()) {
			tree.add(new Node(entry.getKey(),entry.getValue()));
		}
	}
	
	/**
	 * Méthode permettant de vérifier si l'algorithme est terminé
	 * @return true s'il reste plus de 2 noeuds ou false, s'il n'en reste plus qu'un
	 */
	public boolean stillHasNodesToProcess() {
		if(tree.size() >= 2) {
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
		Iterator<Node> it = tree.iterator();
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
			System.out.println("");
			System.out.println("[MERGING] " + letterOne + "(" + frequencyOne + ")" + " AND " + letterTwo + "(" + frequencyTwo + ")" + " => " + mergedLetters + "(" + mergedFrequency + ")"); 
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
		tree.add(new Node(mergedLetters,mergedFrequency,lowestEntry,nextLowestEntry));
		tree.remove(lowestEntry);
		tree.remove(nextLowestEntry);
	}

	/**
	 * Méthode permettant d'afficher le contenu de l'arbre
	 */
	public void displayContent() {
		for(Node node : tree) {
			System.out.println("");
			node.displayNodeInfoWithDepth(0);
		}
	}
}
