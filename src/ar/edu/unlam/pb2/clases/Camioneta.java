package ar.edu.unlam.pb2.clases;

import java.time.Duration;
import java.time.LocalDateTime;

import ar.edu.unlam.pb2.enums.MarcaEnum;

public class Camioneta extends Vehiculo {

	public Camioneta(String patente, MarcaEnum marca, LocalDateTime horaIngreso) {
		super(patente, marca, horaIngreso);
	}

	@Override
	public Double calcularCosto() {
		Double recargo = 50.0;
		double minutosExactos = Duration.between(getHoraIngreso(), getHoraSalida()).toSeconds();
		Double costo = (minutosExactos / 60.0) * 1000.0 * (1 + recargo / 100);
		return costo;
	}
}