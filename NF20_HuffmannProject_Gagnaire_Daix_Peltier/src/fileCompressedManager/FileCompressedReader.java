package fileCompressedManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCompressedReader {
	/**
	 * Class utilisé pour lire un fichier compressé.
	 */
	
	//Arbre "dictionnaire" utilisé pour trouvé les sequences de bits :
	TreeDictionnary dic;
	
	//Fichier compressé :
	BufferedInputStream fileReader;
	
	//Fichier décompressé :
	BufferedOutputStream fileWriter;
	int currentInt;
	int dec;
	
	/**
	 * Initialize la class :
	 * @param url
	 */
	public FileCompressedReader(String url)
	{
		this.dec = 0;
		this.currentInt = 0;
		try {
			this.fileReader = new BufferedInputStream(new FileInputStream(url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode pour lire un bit
	 * de base, java peut lire uniquement des entier, char, ou tout autre combinaison d'octet
	 * il faut donc une class pour lire des "bits".
	 * @return renvoie 0 ou 1 (ou -1 en fin de fichier).
	 * @throws IOException
	 */
	public int readBit() throws IOException{
		if(dec == 0){
			currentInt = this.fileReader.read();
			dec = 8;
			if(currentInt == -1)
				return -1;
		}
		int val = this.currentInt>>7 & 1;
		this.currentInt = this.currentInt << 1;
		dec--;
		return val;
	}
	
	/**
	 * Lecture d'un octet mais bit à bit.
	 * @return
	 * @throws IOException
	 */
	public int readByte() throws IOException{
		int val = 0;
		for(int j = 0; j < 8; j++)
			val = (val << 1) | readBit();
		return val;
	}
	
	/**
	 * Operation de decompression.
	 * @param url
	 */
	public void unCompress(String url){
		try {
			this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
			buildDictionnary();
			readContent();
			this.fileWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creation d'un dictionnaire avec le fichier courrant
	 * Lit l'en tête du fichier (contient le dictionnaire)
	 * et créer un TreeDictionnary avec les données contenus dans ce dernier.
	 */
	private void buildDictionnary(){
		this.dic = new TreeDictionnary();
		try {
			//octet : octet = valeur stocké
			//length : nombre de bit pour encoder cet octet.
			int octet = readByte();
			do{
				int length = readByte();
				boolean bit[] = new boolean[length];
				for(int i = 0; i < length; i++){
					bit[i] = readBit()==1;
				}
				this.dic.addValue(octet, bit, length);
				octet = readByte();
			}while(octet != 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lis le flux de bit et cherche des "serie" correspondant à un octet
	 */
	private void readContent(){
		int value;
		int bit;
		try {
			while((bit = readBit())!=-1)
			{
				value = this.dic.getValue(bit==1);
				if(value != -1){
					this.fileWriter.write(value);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
