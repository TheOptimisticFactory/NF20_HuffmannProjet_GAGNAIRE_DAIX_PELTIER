package main.mainPackage;

import java.io.PrintStream;

import main.HuffmanProcessing.Displayer;
import main.HuffmanProcessing.HuffmanBean;
import main.HuffmanProcessing.HuffmanEncoder;
import main.fileProcessing.FileReader;

/**
 * Classe contenant le point d'entrée du programme
 * @version 0.1.3
 */

public class Main {
	
	@SuppressWarnings("unused") //temporaire
	public static void main (String[] args) {
		FileReader reader = new FileReader();
		Displayer display = new Displayer();
		PrintStream output = System.out;
		
		String url1 = "src/main/ressources/file.txt";
		String url2 = "src/main/ressources/ex1.txt";
		String url3 = "src/main/ressources/ex2.txt";
		
		display.insertHeader(output,"Affichage du contenu du fichier");
		reader.displayTest(output,url3);
		display.insertBlankLine(output);
		
		display.insertHeader(output,"Affichage des éléments avant encodage");
		
		long start = System.nanoTime();
		
		HuffmanBean res = reader.processLetterFrequencyFrom(url3);
		//res.displayTree(output);
		//display.insertBlankLine(output);
		
		//display.insertHeader(output,"Encodage des éléments");
		HuffmanEncoder encoder = new HuffmanEncoder();
		//encoder.encodeWithDebugInfo(res);
		encoder.encode(res);
		System.out.println("Duration = " + ((System.nanoTime() - start)/1000000000.0) + "secondes");
		
		//display.insertBlankLine(output);
		//display.insertHeader(output,"Poids de chacune des lettres");
		//res.displayNodeWeight(output);
	}
}
