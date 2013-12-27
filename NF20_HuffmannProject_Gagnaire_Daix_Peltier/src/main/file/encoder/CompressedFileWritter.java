package main.file.encoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import main.file.basics.Dictionnary;

/**
 * Encodage
 */
public class CompressedFileWritter {
	Dictionnary dico;
	BufferedInputStream fileReader;
	BufferedOutputStream fileWriter;
	int currentByte;
	int dec;
	long bitWritted;

	public CompressedFileWritter(Dictionnary dico, String url) {
		this.bitWritted = 0;
		this.dec = 0;
		this.currentByte = 0;
		this.dico = dico;
		try {
			this.fileReader = new BufferedInputStream(new FileInputStream(url));
		} catch (FileNotFoundException e) {
			throw new EncoderException("[ERROR] Impossible to create file writer : file cannot be found",e);
		}
	}

	public void saveCompressedFile(String url) {
		try {
			this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
			this.createHeader();
			this.saveContent();
			this.closeWritter();
		} catch (IOException e) {
			throw new EncoderException("[ERROR] Impossible to save compressed file",e);
		}
	}
	
	private void WriteBit(int bit) {
		this.currentByte = bit | (this.currentByte << 1);
		this.dec++;
		this.bitWritted++;
		if(this.dec == 8){
			try {
				this.fileWriter.write(this.currentByte);
				this.dec = 0;
				this.currentByte = 0;
			} catch (IOException e) {
				throw new EncoderException("[ERROR] Something went wrong when trying to write bit",e);
			}
		}
	}
	
	private void WriteByte(int octet) {
		for(int j = 0; j < 8;j++) {
			this.WriteBit(octet >> (7-j) & 1);
		}
	}
	
	private void closeWritter(){
		if(this.dec > 0) {
			try {
				this.currentByte <<= 8 - this.dec;
				this.fileWriter.write(this.currentByte);
				this.fileWriter.close();
			} catch (IOException e) {
				throw new EncoderException("[ERROR] Something went wrong when trying to close writer",e);
			}
		}
	}
	
	private void createHeader() {
		int size;
		for(int octet = 0; octet < 256; octet++) {
			if(this.dico.getSize(octet)>0) {
				this.WriteByte(octet-128);
				size = this.dico.getSize(octet);
				this.WriteByte(size);
				for(int bit = 0; bit < size; bit++) {
					this.WriteBit((this.dico.getBit(octet, bit)?1:0));
				}
			}
		}
		this.WriteByte(0);
	}
	
	private void saveContent() {
		int octet;
		int num;
		byte buffer[] = new byte[1024];
		try {
			while((num=fileReader.read(buffer, 0, 1024))!=-1) {
				for(int j = 0; j < num; j++) {
					octet = buffer[j] + 128;
					for(int k = 0; k < this.dico.getSize(octet); k++) {
						this.WriteBit((this.dico.getBit(octet, k)? 1 : 0));
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException("[ERROR] Something went wrong when trying to read file",e);
		}
	}
}
