package main.fileProcessing;

public class FileReaderException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/*============ CREATION d'une exception AVEC message ============*/
	public FileReaderException(String e) {
		super(e);
	}
	
	/*============ PROPAGATION d'une exception SANS message ============*/
    public FileReaderException(Exception e) {
    	super(e);
    }

    
    /*============ PROPAGATION d'une exception AVEC message ============*/
    public FileReaderException(String string, Exception e) {
    	super(string, e);
    }
}
