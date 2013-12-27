package main.file.decoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import main.file.basics.TreeDictionnary;

public class CompressedFileReader {
	TreeDictionnary dic;
	BufferedInputStream fileReader;
	BufferedOutputStream fileWriter;
	int currentInt;
	int dec;
	
	public CompressedFileReader(String url) {
		this.dec = 0;
		this.currentInt = 0;
		try {
			this.fileReader = new BufferedInputStream(new FileInputStream(url));
		} catch (FileNotFoundException e) {
			throw new DecoderException("[ERROR] Impossible to create reader : cannot find file",e);
		}
	}
	
	public int readBit(){
		if(dec == 0){
			try { 
				currentInt = this.fileReader.read();
				dec = 8;
				if(currentInt == -1) {
					return -1;
				}
			} catch (IOException e) {
				throw new DecoderException("[ERROR] Something went wrong while reading bit",e);
			}
		}
		int val = this.currentInt>>7 & 1;
		this.currentInt = this.currentInt << 1;
		dec--;
		return val;
	}
	
	public int readByte() {
		int val = 0;
		for(int j = 0; j < 8; j++)
			val = (val << 1) | readBit();
		return val;
	}
	
	public void decondeToFile(String url) {
		try {
			this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
			buildDictionnary();
			readContent();
			this.fileWriter.close();
		} catch (FileNotFoundException e) {
			throw new DecoderException("[ERROR] Impossible to uncompress file : file not found",e);
		} catch (IOException e) {
			throw new DecoderException("[ERROR] Impossible to uncompress file",e);
		}
	}
	
	private void buildDictionnary() {
		this.dic = new TreeDictionnary();
			int octet = readByte();
			do {
				int length = readByte();
				boolean bit[] = new boolean[length];
				for(int i = 0; i < length; i++){
					bit[i] = readBit()==1;
				}
				this.dic.addValue(octet, bit, length);
				octet = readByte();
			} while(octet != 0);
	}
	
	private void readContent() {
		int value;
		int bit;
		try {
			while((bit = readBit())!=-1) {
				value = this.dic.getValue(bit==1);
				if(value != -1) {
					this.fileWriter.write(value);
				}
			}
		} catch (IOException e) {
			throw new DecoderException("[ERROR] Impossible to read content",e);
		}
	}
}
