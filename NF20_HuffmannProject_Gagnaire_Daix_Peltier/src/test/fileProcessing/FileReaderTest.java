package test.fileProcessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.HuffmanProcessing.HuffmanBean;
import main.HuffmanProcessing.Node;
import main.fileProcessing.FileReader;
import main.fileProcessing.FileReaderException;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
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
	private HuffmanBean expectedResultBeanFromTestFile;

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
		this.expectedResultBeanFromTestFile = new HuffmanBean(expectedResultsFromTestFile);
	}
	
	@Test(expected=FileReaderException.class)
	public void testFailProcessLetterFrequencyFromInvalidUrl() {
		this.reader.processLetterFrequencyFrom(this.invalidURL);
	}
	
	@Test(expected=FileReaderException.class)
	public void testFailWhileOpeningFile() throws Exception {
		FileReader mockFileReader = PowerMockito.spy(new FileReader());
		BufferedReader bufferedReader = mock(BufferedReader.class);
		PowerMockito.doReturn(bufferedReader).when(mockFileReader,"openFileAt", anyString());
		when(bufferedReader.readLine()).thenThrow(new IOException());
		mockFileReader.processLetterFrequencyFrom("anyIncorrectFileAtAnyValidLocation");
	}
	
	@Test 
	public void testSucceedProcessLetterFrequencyFromExistingUrl() {
		this.reader.processLetterFrequencyFrom(this.validURL);
	}
	
	@Test 
	public void testResultsFromProcessLetterFrequency() {
		HuffmanBean results = this.reader.processLetterFrequencyFrom(this.validURL);
		assertEquals(this.expectedResultBeanFromTestFile.size(), results.size());
		Iterator<Node> iterator = results.getIterator();
		Iterator<Node> iteratorFromExpectedResults = expectedResultBeanFromTestFile.getIterator();
		while(iteratorFromExpectedResults.hasNext()) {
			assertTrue(iterator.hasNext());
			Node currentNode = iterator.next();
			Node expectedNode = iteratorFromExpectedResults.next();	
			assertEquals(expectedNode.getValue(),currentNode.getValue());
			assertEquals(expectedNode.getFrequency(),currentNode.getFrequency());
			displayValuesForKeyFrom(""+(char)currentNode.getValue(),currentNode.getFrequency());
		}
	}
	
	private void displayValuesForKeyFrom(String key,Integer frequency) {
		System.out.println("From processed results, for the key [" + key + "], frequency is : " + frequency);
	}
}
