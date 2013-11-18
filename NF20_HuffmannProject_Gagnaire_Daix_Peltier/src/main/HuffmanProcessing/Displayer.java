package main.HuffmanProcessing;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Classe permettant de faire différents types d'affichage
 * @author JoeTheFuckingFrypan
 * @version 0.0.1
 */

public class Displayer {
	/**
	 * Méthode permettant obtenir un rendu visuel de la fréquence de chaque lettre composant le fichier analysé
	 * @param url : String contenant l'url (chemin relatif) du fichier à ouvrir
	 * @author JoeTheFuckingFrypan
	 */
	public void displayFrequencyResultsFrom(Map<String,Integer> results) {
		insertBlankLine();
		insertHeader("Displaying frenquency from file");
		for(Entry<String, Integer> entry : Sorter.entriesSortedByValues(results)) {
			System.out.println("[" + entry.getKey() + "] has a frequency of : " + entry.getValue());
		}
		insertBlankLine();
	}
	
	/**
	 * Méthode permettant obtenir un rendu visuel de la fréquence de chaque lettre composant le fichier analysé sous la forme d'une liste
	 * @param url : String contenant l'url (chemin relatif) du fichier à ouvrir
	 * @author JoeTheFuckingFrypan
	 */
	public void displayFrequencyResultsAsListFrom(Map<String,Integer> results) {
		insertBlankLine();
		insertHeader("Displaying frenquency from file");
		System.out.println(Sorter.entriesSortedByValues(results));
		insertBlankLine();
	}	
	
	public void displayTree(HuffmannBean bean) {
		insertBlankLine();
		insertHeader("Displaying frenquency from current tree");
		bean.displayContent();
		insertBlankLine();
	}
	
	
	/**
	 * Méthode privée affichant une ligne vide dans la console
	 * @author JoeTheFuckingFrypan
	 */
	private void insertBlankLine() {
		System.out.println("");
	}
	
	/**
	 * Méthode privée affichant un header avec texte et emphase dans la console
	 * @param header String à afficher comme header
	 */
	private void insertHeader(String header) {
		System.out.println("=======" + header + "=======");
	}
}
