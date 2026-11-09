package ar.edu.unlam.pb2.exceptions;

public class PagoNoEncontradoException extends Exception {

	public PagoNoEncontradoException() {
		super("Pago no ha sido encontrado");
	}

}
