package main.file.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import main.file.basics.HuffmanBean;

/**
 * Classe permettant de lire un fichier et de retourner le nombre d'occurences de chaque lettre, ordonn� selon ce nombre (ordre croissant)
 * @version 0.4.0
 */

public class FileReader {
	private String url;
	
	public HuffmanBean proccessOctetFrequencyFrom(String url) {
		this.url = url;
		BufferedInputStream file = this.openFile(url);
		int tab[] = initializeArray(256);
		processEntireFileOctetCountingFrequency(tab, file);
		return new HuffmanBean(tab);
	}
	
	/**
	 * M�thode optimis�e permettant de r�cuperer les fr�quences de chaque caract�re � partir de l'URL d'un fichier
	 * @param url URL du fichier d�sir�
	 * @return HuffmannBean : Ensemble tri� de noeuds associant : une String correspondant � une lettre, et un Integer (sa fr�quence)
	 * @version 2.0 AKA "Methode Daix"
	 */
	public HuffmanBean processLetterFrequencyFrom(String url) {
		this.url = url;
		BufferedReader reader = this.openFileAt(url);
		int tab[] = initializeArray(65536);
		processEntireFileCountingFrequency(tab,reader);
		return new HuffmanBean(tab);
	}

	/**
	 * M�thode permettant de cr�er un tableau de n cases, toutes initialis�es � z�ro
	 * @param size Taille du tableau
	 * @return Le tableau complet, avec toutes les cases initialis�es
	 */
	private int[] initializeArray(int size) {
		int tab[] = new int[size];
		for(int i = 0; i < size; i++) {
			tab[i] = 0;
		}
		return tab;
	}
	
	/**
	 * M�thode permettant de r�cuperer les fr�quences de chaque caract�re � partir du flux de donn�es dans un tableau associ�
	 * @param reader BufferedReader correspondant au flux de donn�es provenant du fichier pr�c�dement ouvert
	 * @return Un tableau, chaque String correspond � une lettre, et chaque Integer associ� � sa fr�quence
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
			throw new FileReaderException("[ERROR] Something went wrong when trying to read file",e);
		}
	}
	
	/**
	 * Methode permettant de r�cup�rer la fr�quence de chaque octet dans le fichier � partir d'un flux d'octets.
	 * @param tab Tableau, chaque case correspond � un octet
	 * @param reader :
	 */
	private void processEntireFileOctetCountingFrequency(int[] tab, BufferedInputStream reader) {
		int octet;
		byte buffer[] = new byte[1024];
		try {
			while((octet=reader.read(buffer, 0, 1024))!=-1) {
				for(int j = 0; j < octet; j++) {
				tab[buffer[j] + 128]++;
				}
			}
		} catch (IOException e) {
			throw new FileReaderException("[ERROR] Something went wrong when trying to read file",e);
		}
	}

	/**
	 * M�thode permettant de r�cup�rer un flux lisible � partir de l'URL d'un fichier (gestion des erreurs)
	 * @param url String contenant l'URL du fichier souhait�
	 * @return BufferedReader : Flux lisible ligne � ligne
	 */
	private BufferedReader openFileAt(String url) {
		try {
			InputStream ips = new FileInputStream(url);
			InputStreamReader ipsr = new InputStreamReader(ips);
			return new BufferedReader(ipsr);
		} catch (FileNotFoundException e) {
			throw new FileReaderException("Error while loading file at '" + this.url + "'", e);
		}
	}
	
	/**
	 * Methode permettant d'ouvrir un fichier � partir d'un URL.
	 * @param url
	 * @return
	 */
	private BufferedInputStream openFile(String url) {
		try{
			return new BufferedInputStream(new FileInputStream(url));
		} catch (FileNotFoundException e) {
			throw new FileReaderException("Error while loading file at '" + this.url + "'", e);
		}
	}
	
	/**
	 * M�thode DEPRECATED (utilisant des TreeMap) permettant de r�cuperer les fr�quences de chaque caract�re � partir de l'URL d'un fichier
	 * @param url URL du fichier d�sir�
	 * @return HuffmannBean : Ensemble tri� de noeuds associant : une String correspondant � une lettre, et un Integer (sa fr�quence)
	 * @version 1.0  AKA "Methode Romain"
	 */
	@Deprecated
	public HuffmanBean oldProcessLetterFrequencyFrom(String url) {
		this.url = url;
		BufferedReader reader = openFileAt(url);
		//Methode Romain, tr�s bien �crite mais pas tr�s rapide :
		Map<String,Integer> frequencyByLetter = processEntireFileCountingFrequency(reader);
		return new HuffmanBean(frequencyByLetter);
	}

	/**
	 * M�thode permettant de r�cuperer les fr�quences de chaque caract�re � partir du flux de donn�es
	 * @param reader BufferedReader correspondant au flux de donn�es provenant du fichier pr�c�dement ouvert
	 * @return Une MAP<String,Integer>, chaque String correspond � une lettre, et chaque Integer associ� � sa fr�quence
	 * @version 1.0 AKA "Methode Romain"
	 */
	private Map<String,Integer> processEntireFileCountingFrequency(BufferedReader reader) {
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
	 * M�thode permettant de normaliser la String re�ue (enl�ve tous les espaces et met toutes les lettres en minuscule)
	 * @param lineToProcess String d'origine contenant �ventuellement des caract�res � normaliser
	 * @return renvoie une String sans espaces et sans majuscules
	 */
	private String normalizeLine(String lineToProcess) {
		/* Je l'ai gard�, mais je trouve �a inutile !
		 * Je t'en parle en cours pourquoi... Mais c'est toujours l'histoire des octets.
		 * Et m�me pire, si tu dis que A = a, Ok, tu gagne en place, mais tu corrompts le fichier.
		 * Tu perds des informations, tu gagne en compression, mais avec pertes.
		 * Moi je serais pour, ne plus travailler avec des caract�res mais des octets.
		 * Moins de gal�re, le fichier reste le m�me, et on peut traiter des fichiers txt comme des mp3
		 * Sans probl�me !
		 */
		lineToProcess = lineToProcess.replaceAll("\\s+", ""); 
		return lineToProcess.toLowerCase();
	}


	/**
	 * M�thode priv�e permettant de r�cuperer la fr�quence de toutes les lettres d'une ligne
	 * @param lineToProcess String contenant la ligne � analyser
	 * @param frequencyByLetter Map<String,Integer> � remplir ou dont les fr�quences sont � incr�menter
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
	 * M�thode de debug pour avoir un rendu visuel sur le contenu du fichier lu
	 * /!\ M�thode qui sera donc supprim�e /!\
	 * @param url : String contenant l'url (chemin relatif) du fichier � ouvrir
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
