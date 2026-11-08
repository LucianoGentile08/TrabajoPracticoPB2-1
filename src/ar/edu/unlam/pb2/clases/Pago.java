package ar.edu.unlam.pb2.clases;

import java.time.LocalDateTime;

import ar.edu.unlam.pb2.enums.MedioDePagoEnum;

public class Pago {
	private Vehiculo vehiculo;
	private Double monto;
	private MedioDePagoEnum medioDePago;
	private LocalDateTime fecha;
	public Pago(Vehiculo vehiculo, Double monto, MedioDePagoEnum medioDePago, LocalDateTime fecha) {
		this.vehiculo = vehiculo;
		this.monto = monto;
		this.medioDePago = medioDePago;
		this.fecha = fecha;
	}

	public Double getMonto() {
		return monto;
	}
	public MedioDePagoEnum getMedioDePago() {
		return medioDePago;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	
	
}
