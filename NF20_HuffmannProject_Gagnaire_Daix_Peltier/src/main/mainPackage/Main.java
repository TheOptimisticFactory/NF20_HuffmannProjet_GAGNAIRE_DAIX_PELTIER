package main.mainPackage;

import java.util.Map;

import main.HuffmanProcessing.Displayer;
import main.HuffmanProcessing.HuffmannBean;
import main.HuffmanProcessing.HuffmannEncoder;
import main.fileProcessing.FileReader;

/**
 * Classe contenant le point d'entrée du programme
 * @author JoeTheFuckingFrypan
 * @version 0.1.3
 */

public class Main {

	public static void main (String[] args) {
		FileReader reader = new FileReader();
		Displayer display = new Displayer();
		String url1 = "src/main/ressources/file.txt";
		String url2 = "src/main/ressources/ex1.txt";
		String url3 = "src/main/ressources/ex2.txt";
		
		
		reader.displayTest(url2); //Décommenter pour afficher le contenu du fichier dans la console
		display.displayFrequencyResultsFrom(reader.processLetterFrequencyFrom(url2));
		display.displayFrequencyResultsAsListFrom(reader.processLetterFrequencyFrom(url2));
	
		Map<String, Integer> res = reader.processLetterFrequencyFrom(url2);
		HuffmannEncoder encoder = new HuffmannEncoder(res);
		
		encoder.encodeWithDebugInfo();
		
	}
}
