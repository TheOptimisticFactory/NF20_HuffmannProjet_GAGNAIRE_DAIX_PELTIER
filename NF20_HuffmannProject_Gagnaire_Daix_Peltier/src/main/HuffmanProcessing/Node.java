package main.HuffmanProcessing;

import java.io.PrintStream;

/**
 * Classe correspondant à un noeud dans l'arbre de Huffman (chaque noeud peut contenir une lettre, une fréquence et un poids, ou simplement 1 à 2 sous-noeuds)
 * @version 0.2.0
 */

public class Node implements Comparable<Node>{
	private Node leftNode;
	private Node rightNode;
	private Integer frequency;
	private final Boolean isFinalLeaf;
	private int value;
	private String weight;
	private boolean encoding[];
	private int encodingLength;


	/**
	 * Constructeur d'un noeud correspondant à une lettre (avec fréquence, avec lettre, et sans sous-noeuds)
	 * @param letter
	 * @param frequency
	 */
	public Node(int value, int frequency) {
		this.value = value;
		this.frequency = frequency;
		this.leftNode = null;
		this.rightNode = null;
		this.isFinalLeaf = true;
		this.encoding = new boolean[24];
		this.encodingLength = 0;
	}

	public Node(int mergedvalue, Integer mergedFrequency, Node lowestEntry, Node nextLowestEntry) {
		this.value = mergedvalue;
		this.frequency = mergedFrequency;
		this.leftNode = lowestEntry;
		this.rightNode = nextLowestEntry;
		this.isFinalLeaf = false;
		this.encoding = new boolean[24];
		this.encodingLength = 0;
	}

	/**
	 * Méthode permettant d'incrémenter la fréquence de la lettre associée
	 */
	public void increaseFrequency() {
		++this.frequency;
	}

	/**
	 * Méthode permettant d'assigner à chaque lettre son poids selon l'encodage de Huffman
	 * @param weight String contenant le poids à assigner
	 */
	public void assignWeightToLetter(String weight) {
		if(isFinalLeaf) {
			this.weight = weight;
		}
		if(hasLeftChild()) {
			leftNode.assignWeightToLetter(weight+"0");
		}
		if(hasRightChild()) {
			rightNode.assignWeightToLetter(weight+"1");
		}
	}
	
	/**
	 * Methode permettant d'assigner chaque lettre avec son encodage dans Huffman
	 * @param father : Tableau de l'element parent.
	 * @param fatherLength : Taille du tableau de l'element parent.
	 * @param current : Nouvelle valeur à ajouter au tableau
	 */
	public void assignEncodage(boolean father[], int fatherLength, boolean current){
		if(isFinalLeaf){
			for(int i = 0; i < fatherLength; i++)
				this.encoding[i] = father[i];
			this.encoding[fatherLength] = current;
			this.encodingLength = fatherLength + 1;
		}
		if(hasLeftChild()){
			leftNode.assignEncodage(this.encoding, this.encodingLength, false);
		}
		if(hasRightChild()){
			rightNode.assignEncodage(this.encoding, this.encodingLength, true);
		}
	}

	/**
	 * Méthode permettant d'afficher les données d'un noeud, et ceux des sous-noeuds associés (s'ils existent)
	 * @param stream Flux de sortie sur lequel afficher le message
	 * @param depth Numéro correspondant à la profondeur dans l'arbre (0 étant la racine)
	 */
	public void displayNodeInfoWithDepth(PrintStream stream, int depth) {
		stream.print("[" + getValue() + "] : " + getFrequency());
		if(hasLeftChild()) {
			stream.println();
			for(int i=0; i<=depth; i++) {
				stream.print("--");
			}
			stream.print("[LEFT] ");
			this.leftNode.displayNodeInfoWithDepth(stream,depth+1);
		}
		if(hasRightChild()) {
			stream.println();
			for(int i=0; i<=depth; i++) {
				stream.print("--");
			}
			stream.print("[RIGHT] ");
			this.rightNode.displayNodeInfoWithDepth(stream,depth+1);
		}
	}

	/**
	 * Méhode permettant d'afficher le poids de chaque noeud
	 * @param stream
	 */
	public void displayWeight(PrintStream stream) {
		if(isFinalLeaf) {
			stream.println("Letter [" + getValue() + "] weights : " + getWeight());
		}
		if(hasLeftChild()) {
			this.leftNode.displayWeight(stream);
		}
		if(hasRightChild()) {
			this.rightNode.displayWeight(stream);
		}
	}

	/**
	 * Méthode permettant de récuperer la lettre associée au noeud
	 * @return La lettre associée au noeud
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Méthode permettant de récuperer la fréquence de la lettre associée au noeud
	 * @return La fréquence de la lettre associée au noeud
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * Méthode permettant de récuperer l'encodage de Huffman pour cette lettre
	 * @return String contenant l'encodage de Huffmann associé
	 */
	public String getWeight() {
		return this.weight;
	}
	
	/**
	 * Méthode permettant de savoir si le noeud a un fils à gauche
	 * @return true si le noeud a un fils à gauche, false sinon
	 */
	public Boolean hasLeftChild() {
		return this.leftNode != null;
	}

	/**
	 * Méthode permettant de savoir si le noeud a un fils à droite
	 * @return true si le noeud a un fils à droite, false sinon
	 */
	public Boolean hasRightChild() {
		return this.rightNode != null;
	}

	/**
	 * Méthode redéfinissant la façon dont sont comparés les noeuds
	 * Trie d'abord sur la fréquence, et en cas d'égalité trie sur la lettre
	 */
	@Override
	public int compareTo(Node other) {
		if(this.getFrequency().equals(other.getFrequency())) {
			return this.getValue() - other.getValue();
		} else {
			return this.getFrequency().compareTo(other.getFrequency());
		}
	}
}

