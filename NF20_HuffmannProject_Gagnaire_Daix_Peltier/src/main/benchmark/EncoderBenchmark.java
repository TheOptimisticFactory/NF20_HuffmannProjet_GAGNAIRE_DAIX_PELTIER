package main.benchmark;

import main.file.FileProcessor;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;

public final class EncoderBenchmark extends Benchmark {
	private FileProcessor fileProcessor;
	private String urlToCompressedFile = "src/main/ressources/compressed.bin";
	private final String alpha = "";
	@Param ({
		alpha,
		"src/main/ressources/simpleFile.txt",
		"src/main/ressources/mediumFile.txt",
		"src/main/ressources/shitLoadOfTextFile.txt"
	}) String url;

	@Override
	protected void setUp() throws Exception{
		//FIXME
		this.fileProcessor = new FileProcessor(false);
		this.fileProcessor.encodeFileAt(url, urlToCompressedFile);
	}

	public int timeEncodeFileWithoutSaving(int reps) {
		String url = this.url;
		int dummyValue = 0;
		for (int i = 0; i < reps; i++) {
			dummyValue |= this.fileProcessor.encodeFileAtWitoutSaving(url);
		}
		return dummyValue;
	}

	public int timeEncodeFile(int reps) {
		String url = this.url;
		int dummyValue = 0;
		for (int i = 0; i < reps; i++) {
			dummyValue |= this.fileProcessor.encodeFileAt(url,this.urlToCompressedFile);
		}
		return dummyValue;
	}

	public int timeEncodeAndThenDecode(int reps) {
		String url = this.url;
		int dummyValue = 0;
		for (int i = 0; i < reps; i++) {
			dummyValue |= this.fileProcessor.encodeAndThenDecondeFileAt(url, this.urlToCompressedFile);
		}
		return dummyValue;
	}
}
