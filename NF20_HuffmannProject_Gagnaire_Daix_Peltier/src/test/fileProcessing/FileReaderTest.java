package test.fileProcessing;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.fileProcessing.FileReader;
import main.fileProcessing.FileReaderException;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileReader.class)
public class FileReaderTest {
	private FileReader reader;
	private String validURL;
	private String invalidURL;
	private Map<String,Integer> expectedResultsFromTestFile;

	@Before
	public void setup() throws Exception {
		this.reader = new FileReader();
		this.validURL = "src/test/ressources/testFile.txt";
		this.invalidURL = "randomFolder/randomFileName.txt";
		
		this.expectedResultsFromTestFile = new HashMap<String,Integer>();
		this.expectedResultsFromTestFile.put("a", 1);
		this.expectedResultsFromTestFile.put("b", 1);
		this.expectedResultsFromTestFile.put("c", 1);
		this.expectedResultsFromTestFile.put("d", 2);
		this.expectedResultsFromTestFile.put("e", 1);
		this.expectedResultsFromTestFile.put("f", 3);
	}
	
	@Test(expected=FileReaderException.class)
	public void testFailProcessLetterFrequencyFromInvalidUrl() {
		this.reader.processLetterFrequencyFrom(this.invalidURL);
	}
	
	@Test(expected=FileReaderException.class)
	public void testFailWhileOpeningile() throws Exception {
		FileReader mockFileReader = PowerMockito.spy(new FileReader());
		BufferedReader bufferedReader = mock(BufferedReader.class);
		PowerMockito.doReturn(bufferedReader).when(mockFileReader,"openFileAt", anyString());
		when(bufferedReader.readLine()).thenThrow(new IOException());
		mockFileReader.processLetterFrequencyFrom("anyIncorrectFileAtAnyValidLocation");
		
		
		System.out.println("Should be an error ^");
	}
	
	@Test 
	public void testSucceedProcessLetterFrequencyFromExistingUrl() {
		this.reader.processLetterFrequencyFrom(this.validURL);
	}
	
	@Test 
	public void testResultsFromProcessLetterFrequency() {
		Map<String, Integer> results = this.reader.processLetterFrequencyFrom(this.validURL);
		String key = "a";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
		key = "b";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
		key = "c";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
		key = "d";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
		key = "e";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
		key = "f";
		assertEquals(this.expectedResultsFromTestFile.get(key), results.get(key));
		displayValuesForKeyFrom(key,results);
	}
	
	private void displayValuesForKeyFrom(String key, Map<String,Integer> currentResuls) {
		System.out.println("From processed results, for the key [" + key + "], frequency is : " + currentResuls.get(key));
	}
}
