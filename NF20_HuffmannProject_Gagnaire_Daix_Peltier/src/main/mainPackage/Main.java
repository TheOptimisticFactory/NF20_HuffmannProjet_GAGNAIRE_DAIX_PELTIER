package main.mainPackage;

import main.fileProcessing.FileReader;

/**
 * Classe contenant le point d'entrée du programme
 * @author JoeTheFuckingFrypan
 * @version 0.1.2
 */

public class Main {

	public static void main (String[] args) {
		FileReader reader = new FileReader();
		String url1 = "src/main/ressources/file.txt";
		String url2 = "src/main/ressources/ex1.txt";
		String url3 = "src/main/ressources/ex2.txt";
	
		//reader.displayTest(url1); //Décommenter pour afficher le contenu du fichier dans la console
		reader.displayFrequencyResults(url1);
		
		//reader.displayTest(url2); //Décommenter pour afficher le contenu du fichier dans la console
		reader.displayFrequencyResults(url2);
		
		//reader.displayTest(url3); //Décommenter pour afficher le contenu du fichier dans la console
		reader.displayFrequencyResults(url3);
	}
}
