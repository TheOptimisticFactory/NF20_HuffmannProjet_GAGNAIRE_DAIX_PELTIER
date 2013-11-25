package fileCompressedManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCompressedReader {
	TreeDictionnary dic;
	BufferedInputStream fileReader;
	BufferedOutputStream fileWriter;
	int currentInt;
	int dec;
	
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
	public int readByte() throws IOException{
		int val = 0;
		for(int j = 0; j < 8; j++)
			val = (val << 1) | readBit();
		return val;
	}
	public void unCompress(String url){
		try {
			this.fileWriter = new BufferedOutputStream(new FileOutputStream(url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildDictionnary();
		System.out.println(this.dic);
		readContent();
	}
	private void buildDictionnary(){
		this.dic = new TreeDictionnary();
		try {
			int octet = readByte();
			do{
				int length = readByte();
				boolean bit[] = new boolean[length];
				System.out.print("read_=>"+octet+">"+length+">");
				for(int i = 0; i < length; i++){
					bit[i] = readBit()==1;
					System.out.print(bit[i]?1:0);
				}
				System.out.println();
				this.dic.addValue(octet, bit, length);
				octet = readByte();
			}while(octet != 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readContent(){
		int value;
		int bit;
		try {
			while((bit = readBit())!=-1)
			{
				value = this.dic.getValue(bit==1);
				if(value != -1)
					this.fileWriter.write(value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
