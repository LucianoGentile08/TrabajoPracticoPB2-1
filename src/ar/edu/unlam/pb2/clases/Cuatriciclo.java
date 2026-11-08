package ar.edu.unlam.pb2.clases;

import java.time.Duration;
import java.time.LocalDateTime;

import ar.edu.unlam.pb2.enums.MarcaEnum;

public class Cuatriciclo extends Vehiculo {

	public Cuatriciclo(String patente, MarcaEnum marca, LocalDateTime horaIngreso) {
		super(patente, marca, horaIngreso);
	}

	@Override
	public Double calcularCosto() {
		double minutosExactos = Duration.between(getHoraIngreso(), getHoraSalida()).toSeconds();
		Double costo = (minutosExactos / 60.0) * 800.0;
		return costo;
	}
}