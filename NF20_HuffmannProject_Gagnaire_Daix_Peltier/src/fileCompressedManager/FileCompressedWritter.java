package fileCompressedManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import main.file.reader.FileReaderException;

public class FileCompressedWritter {
	/**
	 * Class pour créer un fichier compressé
	 */
	Dictionnary dico;
	BufferedInputStream fileReader;
	BufferedOutputStream fileWriter;
	int currentByte;
	int dec;
	long bitWritted;
	public FileCompressedWritter(Dictionnary dico, String url)
	{
		this.bitWritted = 0;
		this.dec = 0;
		this.currentByte = 0;
		this.dico = dico;
		try {
			this.fileReader = new BufferedInputStream(new FileInputStream(url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode pour compresser un fichier.
	 * @param url
	 * @throws FileNotFoundException
	 */
	public void saveCompressedFile(String url) throws FileNotFoundException
	{
		try {
		this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
		//Creation de l'en tête (contient l'arbre de Huffman) :
		this.createHeader();
		//Creation du contenus du fichier :
		this.saveContent();
		this.closeWritter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Ecrit un bit, car impossible d'écrire un bit en java par défaut.
	 * @param bit
	 * @throws IOException
	 */
	private void WriteBit(int bit) throws IOException
	{
		this.currentByte = bit | (this.currentByte << 1);
		this.dec++;
		this.bitWritted++;
		if(this.dec == 8){
			this.fileWriter.write(this.currentByte);
			this.dec = 0;
			this.currentByte = 0;
		}
	}
	/**
	 * Ecriture d'un octet mais bit à bit.
	 * @param octet
	 * @throws IOException
	 */
	private void WriteByte(int octet) throws IOException{
		for(int j = 0; j < 8;j++)
		{
			this.WriteBit(octet >> (7-j) & 1);
		}
	}
	
	/**
	 * Fermeture de l'ecriture, ecriture des bits encore contenus dans le buffer.
	 * @throws IOException
	 */
	private void closeWritter() throws IOException{
		if(this.dec > 0)
		{
			this.currentByte <<= 8 - this.dec;
			this.fileWriter.write(this.currentByte);
			this.fileWriter.close();
		}
	}
	
	/**
	 * Stockage de l'arbre de huffman actuel:
	 */
	private void createHeader()
	{
		int size;
		try {
			//Pour chaque "octet" :
			for(int octet = 0; octet < 256; octet++)
			{
				//Si l'octet est présent dans le fichier :
				if(this.dico.getSize(octet)>0)
				{
					//On ecrit la valeur de l'octet :
					this.WriteByte(octet-128);
					size = this.dico.getSize(octet);
					//On ecrit le nombre de bit pour l'encoder :
					this.WriteByte(size);
					//Puis on ecrit la sequence de bit correspondante à cet octet :
					for(int bit = 0; bit < size; bit++){
							this.WriteBit((this.dico.getBit(octet, bit)?1:0));
					}
							// TODO Auto-generated catch block
				}
			}
			//L'octet NULL représente la fin du fichier :
			this.WriteByte(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sauvegarde du contenus du fichier :
	 */
	private void saveContent()
	{
		int octet;
		int num;
		byte buffer[] = new byte[1024];
		try {
			//Tant qu'on a du contenus à lire :
			while((num=fileReader.read(buffer, 0, 1024))!=-1) {
				// Pour chaque octet lus, on ecrit sa sequence de bit :
				for(int j = 0; j < num; j++)
				{
					octet = buffer[j] + 128;
					//Ecriture des bits utilisés pour stocker cet octet :
					for(int k = 0; k < this.dico.getSize(octet); k++)
						this.WriteBit((this.dico.getBit(octet, k)? 1 : 0));
				}
			}
		} catch (IOException e) {
			throw new FileReaderException("[ERROR] Something went wrong when trying to read file");
		}
	}
}
