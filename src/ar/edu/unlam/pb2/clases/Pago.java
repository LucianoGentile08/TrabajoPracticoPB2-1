package ar.edu.unlam.pb2.clases;

import java.time.LocalDateTime;

import ar.edu.unlam.pb2.enums.MedioDePagoEnum;

public class Pago {
	private Vehiculo vehiculo;
	private Double monto;
	private MedioDePagoEnum medioDePago;
	private LocalDateTime fechaDePago;
	public Pago(Vehiculo vehiculo, Double monto, MedioDePagoEnum medioDePago, LocalDateTime fechaDePago) {
		this.vehiculo = vehiculo;
		this.monto = monto;
		this.medioDePago = medioDePago;
		this.fechaDePago = fechaDePago;
	}

	public Double getMonto() {
		return monto;
	}
	public MedioDePagoEnum getMedioDePago() {
		return medioDePago;
	}
	public LocalDateTime getFechaDePago() {
		return fechaDePago;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	
}
