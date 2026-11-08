package ar.edu.unlam.pb2.exceptions;

public class VehiculoNoSeEncuentraEnCochera extends Exception {
	public VehiculoNoSeEncuentraEnCochera() {
		super("El vehiculo ingresado no se encuentra en la cochera");
	}
}
