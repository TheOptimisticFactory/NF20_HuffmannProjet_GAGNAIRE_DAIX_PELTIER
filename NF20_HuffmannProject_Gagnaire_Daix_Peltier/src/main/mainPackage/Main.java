package main.mainPackage;

import java.util.HashMap;
import java.util.Map;

import main.fileProcessing.FileReader;

/**
 * Classe contenant le point d'entrée du programme
 * @author JoeTheFuckingFrypan
 * @version 0.1.1
 */

public class Main {
	public static void main (String[] args) {
		FileReader reader = new FileReader();
		String url = "src/main/ressources/file.txt";
		Map<String,Integer> m = new HashMap<String,Integer>();
	
		reader.displayTest(url);
		m = reader.processLetterFrequencyFrom(url);
	}
}
