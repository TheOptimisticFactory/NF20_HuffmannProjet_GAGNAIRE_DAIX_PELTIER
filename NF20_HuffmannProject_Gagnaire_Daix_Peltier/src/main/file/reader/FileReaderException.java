package main.file.reader;

public class FileReaderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la cr�ation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 */
	public FileReaderException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception � propager
	 */
	public FileReaderException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 * @param e Exception � propager
	 */
	public FileReaderException(String message, Exception e) {
		super(message, e);
	}
}
