package main.fileProcessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de lire un fichier et de retourner le nombre d'occurences de chaque lettre
 * @author JoeTheFuckingFrypan
 * @version 0.2
 */

public class FileReader {
	private String url;

	public FileReader() {
	}

	/**
	 * Méthode permettant de récuperer les fréquences de chaque caractère à partir de l'URL d'un fichier
	 * @param url URL du fichier désiré
	 * @return Une MAP<String,Integer>, chaque String correspond à une lettre, et chaque Integer associé à sa fréquence
	 * @author JoeTheFuckingFrypan
	 */
	public Map<String,Integer> processLetterFrequencyFrom(String url) {
		this.url = url;
		System.out.println(this.url);
		BufferedReader reader = openFileAt(url);
		Map<String,Integer> frequencyByLetter = processEntireFileCountingFrequency(reader);

		return frequencyByLetter;
	}
	
	/**
	 * Méthode permettant de récupérer un flux lisible à partir de l'URL d'un fichier (gestion des erreurs)
	 * @param url String contenant l'URL du fichier souhaité
	 * @return BufferedReader : Flux lisible ligne à ligne
	 * @throws FileReaderException : Exception indiquant avec un message qu'une erreur s'est produite lors de l'ouverture du fichier
	 * @author JoeTheFuckingFrypan
	 */
	private BufferedReader openFileAt(String url) throws FileReaderException {
		System.out.println(this.url);
		try {
			InputStream ips = new FileInputStream(url);
			InputStreamReader ipsr = new InputStreamReader(ips);
			return new BufferedReader(ipsr);
		} catch (FileNotFoundException e) {
			throw new FileReaderException("Error while loading file at '" + this.url + "'", e);
		}
	}

	/**
	 * Méthode permettant de récuperer les fréquences de chaque caractère à partir du flux de données
	 * @param reader BufferedReader correspondant au flux de données provenant du fichier précédement ouvert
	 * @return Une MAP<String,Integer>, chaque String correspond à une lettre, et chaque Integer associé à sa fréquence
	 * @throws FileReaderException Exception avec message indiquant qu'une erreur s'est produite lors de la lecture du fichier
	 * @author JoeTheFuckingFrypan
	 */
	private Map<String, Integer> processEntireFileCountingFrequency(BufferedReader reader) throws FileReaderException {
		Map<String,Integer> frequencyByLetter = new HashMap<String,Integer>();

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
	 * @author JoeTheFuckingFrypan
	 */
	private String normalizeLine(String lineToProcess) {
		lineToProcess = lineToProcess.replaceAll("\\s+", ""); 
		return lineToProcess.toLowerCase();
	}
	
	private void processAllLettersFromLine(String lineToProcess, Map<String, Integer> frequencyByLetter) {
		System.out.println(lineToProcess);
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
	 * Méthode de debug pour tester vérifier que le fichier soit ouvert avec succes
	 * --Méthode qui sera donc supprimée
	 * @param url : String contenant l'url (chemin relatif) du fichier à ouvrir
	 * @author JoeTheFuckingFrypan
	 */
	@Deprecated
	public void displayTest(String url) {
		try {
			BufferedReader reader = openFileAt(url);
			String ligne;
			while((ligne=reader.readLine())!=null) {
				System.out.println(ligne);
			}
		} catch (IOException e) {
			throw new FileReaderException("Error while reading file at '" + url + "'", e);
		}
	}

}
