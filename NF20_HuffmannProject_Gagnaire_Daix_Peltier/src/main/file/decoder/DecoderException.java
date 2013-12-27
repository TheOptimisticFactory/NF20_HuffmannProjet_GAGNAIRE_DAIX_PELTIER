package main.file.decoder;

public class DecoderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la cr�ation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 */
	public DecoderException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception � propager
	 */
	public DecoderException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 * @param e Exception � propager
	 */
	public DecoderException(String message, Exception e) {
		super(message, e);
	}
}
