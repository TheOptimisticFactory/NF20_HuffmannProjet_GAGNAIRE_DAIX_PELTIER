package main.HuffmanProcessing;

/**
 * Classe permettant d'obtenir l'arbre de Huffman final (avec attribution des poids correspondant à chaque lettre) à partir d'une arborescence initiale (HuffmanBean)
 * @version 0.2.0
 */
public class HuffmanEncoder {
	public HuffmanEncoder() {
	}
	
	/**
	 * Méthode permettant d'encoder selon la méthode de Huffmann
	 */
	public void encode(HuffmanBean res) {
		while(res.stillHasNodesToProcess()) {
			res.processingOneMoreStep();
		}
		res.assignWeightToAllLetters();
	}
	
	/**
	 * Méthode permettant d'encoder selon la méthode de Huffmann (avec affichage de chaque étape)
	 * @param res 
	 */
	@Deprecated
	public void encodeWithDebugInfo(HuffmanBean res) {
		while(res.stillHasNodesToProcess()) {
			res.processingOneMoreStep();
			res.displayTree(System.out);
		}
		res.assignWeightToAllLetters();
	}
}
