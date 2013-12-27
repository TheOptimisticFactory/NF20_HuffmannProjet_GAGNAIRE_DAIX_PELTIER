package fileCompressedManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import main.fileProcessing.FileReaderException;

public class FileCompressedWritter {
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
	
	public void saveCompressedFile(String url) throws FileNotFoundException
	{
		try {
		this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
		this.createHeader();
		this.saveContent();
		this.closeWritter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	private void WriteByte(int octet) throws IOException{
		for(int j = 0; j < 8;j++)
		{
			this.WriteBit(octet >> (7-j) & 1);
		}
	}
	private void closeWritter() throws IOException{
		if(this.dec > 0)
		{
			this.currentByte <<= 8 - this.dec;
			this.fileWriter.write(this.currentByte);
			this.fileWriter.close();
		}
	}
	private void createHeader()
	{
		int size;
		try {
			for(int octet = 0; octet < 256; octet++)
			{
				if(this.dico.getSize(octet)>0)
				{
					this.WriteByte(octet-128);
					size = this.dico.getSize(octet);
					this.WriteByte(size);
					for(int bit = 0; bit < size; bit++){
							this.WriteBit((this.dico.getBit(octet, bit)?1:0));
					}
							// TODO Auto-generated catch block
				}
			}
			this.WriteByte(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void saveContent()
	{
		int octet;
		int num;
		byte buffer[] = new byte[1024];
		try {
			while((num=fileReader.read(buffer, 0, 1024))!=-1) {
				for(int j = 0; j < num; j++)
				{
					octet = buffer[j] + 128;
					for(int k = 0; k < this.dico.getSize(octet); k++)
						this.WriteBit((this.dico.getBit(octet, k)? 1 : 0));
				}
			}
		} catch (IOException e) {
			throw new FileReaderException("[ERROR] Something went wrong when trying to read file");
		}
	}
}
