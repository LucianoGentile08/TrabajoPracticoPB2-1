package ar.edu.unlam.pb2.exceptions;

public class DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException extends Exception {
	public DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException() {
		super("El vehiculo debe de tener un casco asignado para poder calcular su costo de salida");
	}
}
