package main.HuffmanProcessing;

public class Node implements Comparable<Node>{
	private String letter;
	private Integer frequency;
	private Node leftNode;
	private Node rightNode;
	
	/**
	 * Constructeur d'un noeud correspondant à une lettre (avec fréquence, avec lettre, et sans sous-noeuds)
	 * @param letter
	 * @param frequency
	 */
	public Node(String letter, Integer frequency) {
		this.letter = letter;
		this.frequency = frequency;
		this.leftNode = null;
		this.rightNode = null;
	}
	
	/**
	 * Constructeur d'un noeud père correspondant à une lettre (avec fréquence, avec lettre, et sans sous-noeuds)
	 * @param letter
	 * @param frequency
	 * @param leftNode
	 * @param rigthNode
	 */
	public Node(String letter, Integer frequency, Node leftNode, Node rigthNode) {
		this.letter = letter;
		this.frequency = frequency;
		this.leftNode = leftNode;
		this.rightNode = rigthNode;
	}
	
	/**
	 * Méthode permettant d'afficher les données d'un noeud, et ceux des sous-noeuds associés (s'ils existent)
	 * @param depth Numéro correspondant à la profondeur dans l'arbre (0 étant la racine)
	 */
	public void displayNodeInfoWithDepth(int depth) {
		System.out.print("[" + getLetter() + "] : " + getFrequency());
		if(hasLeftChild()) {
			System.out.println();
			for(int i=0; i<=depth; i++) {
				System.out.print("--");
			}
			System.out.print("[LEFT] ");
			this.leftNode.displayNodeInfoWithDepth(depth+1);
		}
		if(hasRightChild()) {
			System.out.println();
			for(int i=0; i<=depth; i++) {
				System.out.print("--");
			}
			System.out.print("[RIGHT] ");
			this.rightNode.displayNodeInfoWithDepth(depth+1);
		}
	}
	
	/**
	 * Méthode permettant de récuperer la lettre associée au noeud
	 * @return La lettre associée au noeud
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * Méthode permettant de récuperer la fréquence de la lettre associée au noeud
	 * @return La fréquence de la lettre associée au noeud
	 */
	public Integer getFrequency() {
		return frequency;
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
			return this.getLetter().compareTo(other.getLetter());
		} else {
			return this.getFrequency().compareTo(other.getFrequency());
		}
	}
}

