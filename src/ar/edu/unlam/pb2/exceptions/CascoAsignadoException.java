package ar.edu.unlam.pb2.exceptions;

public class CascoAsignadoException extends Exception {
	public CascoAsignadoException() {
		super("El vehiculo ya tiene un casco asignado!");
	}
}
