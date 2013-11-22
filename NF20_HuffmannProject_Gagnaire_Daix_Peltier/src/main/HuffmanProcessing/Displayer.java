package main.HuffmanProcessing;

import java.io.PrintStream;

/**
 * Classe permettant de faire différents types d'affichage
 * @version 0.2.0
 */

public class Displayer {
	/**
	 * Méthode affichant une ligne vide dans la console
	
	 * @param stream Flux de sortie à utiliser
	 */
	public void insertBlankLine(PrintStream stream) {
		stream.println("");
	}
	
	/**
	 * Méthode affichant un header avec texte et emphase dans la console
	 * @param stream Flux de sortie à utiliser
	 * @param header String à afficher comme header
	 */
	public void insertHeader(PrintStream stream, String header) {
		stream.println("======= " + header + " =======");
	}
}
