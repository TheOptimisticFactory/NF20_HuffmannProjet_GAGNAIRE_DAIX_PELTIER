package test.fileProcessing;

import main.file.reader.FileReaderException;

import org.junit.Test;

public class FileReaderExceptionTest {
	@Test(expected=FileReaderException.class)
	public void testCreateException() {
		throw new FileReaderException("Error message");
	}

	@Test(expected=FileReaderException.class)
	public void propagateExceptionWithoutMessage() {
		try {
			throw new Exception();
		} catch (Exception e) {
			throw new FileReaderException(e);
		}
	}

	@Test(expected=FileReaderException.class)
	public void propagateExceptionWithMessage() {
		try {
			throw new Exception();
		} catch (Exception e) {
			throw new FileReaderException("Error message", e);
		}
	}
}
