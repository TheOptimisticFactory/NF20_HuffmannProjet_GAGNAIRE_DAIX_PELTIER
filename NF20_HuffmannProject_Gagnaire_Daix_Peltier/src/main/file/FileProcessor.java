package main.file;

import main.file.basics.Dictionnary;
import main.file.basics.HuffmanBean;
import main.file.decoder.CompressedFileReader;
import main.file.encoder.CompressedFileWritter;
import main.file.encoder.HuffmanEncoder;
import main.file.reader.FileReader;

public class FileProcessor {
	private long startTime;
	private boolean timeDisplayOn;
	private FileReader reader;
	private HuffmanEncoder encoder;

	/* ========================================= CONSTRUCTEUR ========================================= */

	public FileProcessor(boolean timeDisplayOn) {
		this.timeDisplayOn = timeDisplayOn;
		this.reader = new FileReader();
		this.encoder = new HuffmanEncoder();
	}

	/* ========================================= COMPRESSION ========================================= */

	public int encodeFileAtWitoutSaving(String url) {
		if(this.timeDisplayOn) {
			this.startTime = System.nanoTime();
		}
		HuffmanBean res = this.reader.proccessOctetFrequencyFrom(url);
		this.encoder.encode(res);
		if(this.timeDisplayOn) {
			double elapsedTime = (System.nanoTime() - startTime)/1000000000.0;
			System.out.println("[ENCODING WITHOUT SAVING] Duration = " + elapsedTime + " secondes");
		}
		return this.hashCode(); //Caliper workaround
	}

	public int encodeFileAt(String url,String urlToCompressedFile) {
		if(this.timeDisplayOn) {
			this.startTime = System.nanoTime();
			HuffmanBean res = this.reader.proccessOctetFrequencyFrom(url);
			double elapsedTime = (System.nanoTime() - startTime)/1000000000.0;
			double relativeTime = System.nanoTime();
			System.out.println("Lecture du fichier = " + elapsedTime + " secondes");
			this.encoder.encode(res);
			elapsedTime = (System.nanoTime() - relativeTime)/1000000000.0;
			relativeTime = System.nanoTime();
			System.out.println("Arbre de huffman = " + elapsedTime + " secondes");
			Dictionnary dico = res.getDictionnary();
			CompressedFileWritter w = new CompressedFileWritter(dico, url);
			w.saveCompressedFile(urlToCompressedFile);
			elapsedTime = (System.nanoTime() - relativeTime)/1000000000.0;
			System.out.println("Sauvegarde du fichier compressé = " + elapsedTime + " secondes");
			if(this.timeDisplayOn) {
				elapsedTime = (System.nanoTime() - startTime)/1000000000.0;
				System.out.println("[ENCODING WITH SAVING] Duration = " + elapsedTime + " secondes");
			}
		} else {
			HuffmanBean res = this.reader.proccessOctetFrequencyFrom(url);
			this.encoder.encode(res);
			Dictionnary dico = res.getDictionnary();
			CompressedFileWritter w = new CompressedFileWritter(dico, url);
			w.saveCompressedFile(urlToCompressedFile);
		}
		return this.hashCode(); //Caliper workaround
	}

	/* ========================================= DECOMPRESSION ========================================= */

	public int decodeFileAt(String urlToCompressedFile) {
		if(this.timeDisplayOn) {
			this.startTime = System.nanoTime();
			CompressedFileReader reader = new CompressedFileReader(urlToCompressedFile);
			reader.decondeToFile("src/main/ressources/result.txt");
			double elapsedTime = (System.nanoTime() - startTime)/1000000000.0;
			System.out.println("[DECODING] Duration = " + elapsedTime + " secondes");
		} else {
			CompressedFileReader reader = new CompressedFileReader(urlToCompressedFile);
			reader.decondeToFile("src/main/ressources/result.txt");
		}
		return this.hashCode(); //Caliper workaround
	}

	/* ========================================= ALL IN ONE ========================================= */

	public int encodeAndThenDecondeFileAt(String url,String urlToEncodedFile) {
		if(this.timeDisplayOn) {
			long globalTime = System.nanoTime();
			this.encodeFileAt(url,urlToEncodedFile);
			this.decodeFileAt(urlToEncodedFile);
			double elapsedTime = (System.nanoTime() - globalTime)/1000000000.0;
			System.out.println("[TOTAL TIME : ENCODING + DECODING] Duration = " + elapsedTime + " secondes");
		} else {
			this.encodeFileAt(url,urlToEncodedFile);
			this.decodeFileAt(urlToEncodedFile);
		}
		return this.hashCode(); //Caliper workaround
	}
}
