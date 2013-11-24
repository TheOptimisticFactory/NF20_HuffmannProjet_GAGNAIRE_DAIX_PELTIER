package main.fileProcessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import main.HuffmanProcessing.HuffmanBean;

/**
 * Classe permettant de lire un fichier et de retourner le nombre d'occurences de chaque lettre, ordonné selon ce nombre (ordre croissant)
 * @version 0.4.0
 */

public class FileReader {
	private String url;

	/**
	 * Méthode optimisée permettant de récuperer les fréquences de chaque caractère à partir de l'URL d'un fichier
	 * @param url URL du fichier désiré
	 * @return HuffmannBean : Ensemble trié de noeuds associant : une String correspondant à une lettre, et un Integer (sa fréquence)
	 * @version 2.0 AKA "Methode Daix"
	 */
	public HuffmanBean processLetterFrequencyFrom(String url) {
		this.url = url;
		BufferedReader reader = openFileAt(url);
		int tab[] = initializeArray(65536);
		processEntireFileCountingFrequency(tab,reader);
		return new HuffmanBean(tab);
	}

	/**
	 * Méthode permettant de créer un tableau de n cases, toutes initialisées à zéro
	 * @param size Taille du tableau
	 * @return Le tableau complet, avec toutes les cases initialisées
	 */
	private int[] initializeArray(int size) {
		int tab[] = new int[size];
		for(int i = 0; i < size; i++) {
			tab[i] = 0;
		}
		return tab;
	}
	
	/**
	 * Méthode permettant de récuperer les fréquences de chaque caractère à partir du flux de données dans un tableau associé
	 * @param reader BufferedReader correspondant au flux de données provenant du fichier précédement ouvert
	 * @return Un tableau, chaque String correspond à une lettre, et chaque Integer associé à sa fréquence
	 * @throws FileReaderException Exception avec message indiquant qu'une erreur s'est produite lors de la lecture du fichier
	 * @version 2.0 AKA "Methode Daix"
	 */
	private void processEntireFileCountingFrequency(int[] tab, BufferedReader reader) {
		try {
			String lineToProcess;
			while((lineToProcess=reader.readLine())!=null) {
				for(char letter: lineToProcess.toCharArray()) {
					tab[(int)letter]++;
				}
			}
		} catch (IOException e) {
			throw new FileReaderException("[ERROR] Something went wrong when trying to read file");
		}
	}

	/**
	 * Méthode permettant de récupérer un flux lisible à partir de l'URL d'un fichier (gestion des erreurs)
	 * @param url String contenant l'URL du fichier souhaité
	 * @return BufferedReader : Flux lisible ligne à ligne
	 * @throws FileReaderException : Exception indiquant avec un message qu'une erreur s'est produite lors de l'ouverture du fichier
	 */
	private BufferedReader openFileAt(String url) throws FileReaderException {
		try {
			InputStream ips = new FileInputStream(url);
			InputStreamReader ipsr = new InputStreamReader(ips);
			return new BufferedReader(ipsr);
		} catch (FileNotFoundException e) {
			throw new FileReaderException("Error while loading file at '" + this.url + "'", e);
		}
	}
	
	/**
	 * Méthode DEPRECATED (utilisant des TreeMap) permettant de récuperer les fréquences de chaque caractère à partir de l'URL d'un fichier
	 * @param url URL du fichier désiré
	 * @return HuffmannBean : Ensemble trié de noeuds associant : une String correspondant à une lettre, et un Integer (sa fréquence)
	 * @version 1.0  AKA "Methode Romain"
	 */
	@Deprecated
	public HuffmanBean oldProcessLetterFrequencyFrom(String url) {
		this.url = url;
		BufferedReader reader = openFileAt(url);
		//Methode Romain, très bien écrite mais pas très rapide :
		Map<String,Integer> frequencyByLetter = processEntireFileCountingFrequency(reader);
		return new HuffmanBean(frequencyByLetter);
	}

	/**
	 * Méthode permettant de récuperer les fréquences de chaque caractère à partir du flux de données
	 * @param reader BufferedReader correspondant au flux de données provenant du fichier précédement ouvert
	 * @return Une MAP<String,Integer>, chaque String correspond à une lettre, et chaque Integer associé à sa fréquence
	 * @throws FileReaderException Exception avec message indiquant qu'une erreur s'est produite lors de la lecture du fichier
	 * @version 1.0 AKA "Methode Romain"
	 */
	private Map<String,Integer> processEntireFileCountingFrequency(BufferedReader reader) throws FileReaderException {
		Map<String,Integer> frequencyByLetter = new TreeMap<String,Integer>();
		try {
			String lineToProcess;
			while((lineToProcess=reader.readLine())!=null) {
				lineToProcess = normalizeLine(lineToProcess);
				processAllLettersFromLine(lineToProcess,frequencyByLetter);
			}
		} catch (IOException e) {
			throw new FileReaderException("Error while reading file at '" + this.url + "'", e);
		}
		return frequencyByLetter;
	}

	/**
	 * Méthode permettant de normaliser la String reçue (enlève tous les espaces et met toutes les lettres en minuscule)
	 * @param lineToProcess String d'origine contenant éventuellement des caractères à normaliser
	 * @return renvoie une String sans espaces et sans majuscules
	 */
	private String normalizeLine(String lineToProcess) {
		/* Je l'ai gardé, mais je trouve ça inutile !
		 * Je t'en parle en cours pourquoi... Mais c'est toujours l'histoire des octets.
		 * Et même pire, si tu dis que A = a, Ok, tu gagne en place, mais tu corrompts le fichier.
		 * Tu perds des informations, tu gagne en compression, mais avec pertes.
		 * Moi je serais pour, ne plus travailler avec des caractères mais des octets.
		 * Moins de galère, le fichier reste le même, et on peut traiter des fichiers txt comme des mp3
		 * Sans problème !
		 */
		lineToProcess = lineToProcess.replaceAll("\\s+", ""); 
		return lineToProcess.toLowerCase();
	}


	/**
	 * Méthode privée permettant de récuperer la fréquence de toutes les lettres d'une ligne
	 * @param lineToProcess String contenant la ligne à analyser
	 * @param frequencyByLetter Map<String,Integer> à remplir ou dont les fréquences sont à incrémenter
	 */
	private void processAllLettersFromLine(String lineToProcess, Map<String, Integer> frequencyByLetter) {	
		for(char letter: lineToProcess.toCharArray()) {
			String expectedKey = String.valueOf(letter);
			if(frequencyByLetter.containsKey(expectedKey)) {
				Integer updatedValue = frequencyByLetter.get(expectedKey) + 1;
				frequencyByLetter.put(expectedKey, updatedValue);
			} else {
				frequencyByLetter.put(expectedKey, 1);
			}
		}
	}

	/**
	 * Méthode de debug pour avoir un rendu visuel sur le contenu du fichier lu
	 * /!\ Méthode qui sera donc supprimée /!\
	 * @param url : String contenant l'url (chemin relatif) du fichier à ouvrir
	 * @param stream Flux de sortie sur lequel afficher les informations 
	 */
	public void displayTest(PrintStream stream, String url) {
		try {
			BufferedReader reader = openFileAt(url);
			String ligne;
			while((ligne=reader.readLine())!=null) {
				stream.println(ligne);
			}
		} catch (IOException e) {
			throw new FileReaderException("Error while reading file at '" + url + "'", e);
		}
	}
}
