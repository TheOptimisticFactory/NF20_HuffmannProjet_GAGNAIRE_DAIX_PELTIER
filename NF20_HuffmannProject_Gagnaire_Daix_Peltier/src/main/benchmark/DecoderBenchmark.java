package main.benchmark;

import main.file.FileProcessor;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;

public class DecoderBenchmark extends Benchmark {
	private FileProcessor fileProcessor;
	@Param ({
		"src/main/ressources/emptyFile.bin",
		"src/main/ressources/simpleFile.bin",
		"src/main/ressources/mediumFile.bin",
		"src/main/ressources/shitLoadOfTextFile.bin"
	}) String url;

	@Override
	protected void setUp() throws Exception{
		this.fileProcessor = new FileProcessor(false);
	}

	public int timeEncodeFileWithoutSaving(int reps) {
		String url = this.url;
		int dummyValue = 0;
		for (int i = 0; i < reps; i++) {
			dummyValue |= this.fileProcessor.decodeFileAt(url);
		}
		return dummyValue;
	}
}
