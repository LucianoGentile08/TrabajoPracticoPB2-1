package ar.edu.unlam.pb2.clases;

import java.time.LocalDateTime;

import ar.edu.unlam.pb2.enums.MedioDePagoEnum;

public class Pago {
	private String patente;
	private Double monto;
	private MedioDePagoEnum medioDePago;
	private LocalDateTime fecha;
}
