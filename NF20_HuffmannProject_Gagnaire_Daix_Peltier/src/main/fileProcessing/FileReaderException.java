package main.fileProcessing;

public class FileReaderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la création d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 */
	public FileReaderException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception à propager
	 */
	public FileReaderException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 * @param e Exception à propager
	 */
	public FileReaderException(String message, Exception e) {
		super(message, e);
	}
}
