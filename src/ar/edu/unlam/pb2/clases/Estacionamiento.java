package ar.edu.unlam.pb2.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.unlam.pb2.enums.MedioDePagoEnum;
import ar.edu.unlam.pb2.exceptions.CascoAsignadoException;
import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;
import ar.edu.unlam.pb2.exceptions.DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException;
import ar.edu.unlam.pb2.exceptions.PatenteDuplicadaException;
import ar.edu.unlam.pb2.exceptions.VehiculoInvalidoParaAsignarCasco;
import ar.edu.unlam.pb2.exceptions.VehiculoNoSeEncuentraEnCochera;
import ar.edu.unlam.pb2.interfaces.Asignable;

public class Estacionamiento {

	private Map<Integer, Vehiculo> cocheras;
	private Set<Vehiculo> vehiculos;
	private List<Pago> pagos;

	public Estacionamiento() {
		this.cocheras = new HashMap<Integer, Vehiculo>();
		this.vehiculos = new HashSet<>();
		this.pagos = new ArrayList<>();
	}

	public Boolean estacionarVehiculo(Integer idCochera, Vehiculo vehiculo)
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		validarPatenteDuplicada(vehiculo);
		validarCocheraOcupada(idCochera);
		validarCocheraInexistente(idCochera);
		this.cocheras.put(idCochera, vehiculo);
		return true;
	}

	public void validarCocheraOcupada(Integer idCochera) throws CocheraOcupadaException {
		if (cocheras.containsKey(idCochera)) {
			throw new CocheraOcupadaException("Cochera ocupada");
		}
	}

	public void validarPatenteDuplicada(Vehiculo vehiculo) throws PatenteDuplicadaException {
		for (Vehiculo vehiculoEncontrado : cocheras.values()) {
			if (vehiculoEncontrado.getPatente().equals(vehiculo.getPatente())) {
				throw new PatenteDuplicadaException("Patente duplicada");
			}
		}
	}

	public void validarCocheraInexistente(Integer idCochera) throws CocheraInexistenteException {
		if (idCochera <= 0 || idCochera > 100) {
			throw new CocheraInexistenteException("Cochera inexistente");
		}
	}
	
	public Boolean asignarCascoAUnTipoDeVehiculo(Vehiculo vehiculo, Casco casco) throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		if(vehiculo instanceof Asignable) {
			return ((Asignable) vehiculo).asignarCasco(casco);
		}
		throw new VehiculoInvalidoParaAsignarCasco();
	}

	public Boolean cambiarVehiculoDeCochera(Integer idCocheraOrigen, Vehiculo vehiculo, Integer idCocheraDestino)
			throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera {
		
		validarCocheraInexistente(idCocheraOrigen);
		
		validarCocheraInexistente(idCocheraDestino);
			
		validarVehiculoEstacionadoEnCochera(idCocheraOrigen, vehiculo);
		
		validarCocheraOcupada(idCocheraDestino); 
		
		if(vehiculo != null) {
			cocheras.replace(idCocheraOrigen, null);
			cocheras.put(idCocheraDestino, vehiculo);
			return true;
		}
		return false;
	}

	private void validarVehiculoEstacionadoEnCochera(Integer idCocheraOrigen, Vehiculo vehiculo) throws VehiculoNoSeEncuentraEnCochera {
		
		if(!cocheras.containsKey(idCocheraOrigen) || !cocheras.containsValue(vehiculo)) {
			throw new VehiculoNoSeEncuentraEnCochera();
		}
	}

	public Map<String, Double> generarReporteDePagos(LocalDateTime fecha) throws DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException {
		HashMap<String, Double> reporte = new HashMap<String, Double>();
		Double costoAuto = 0.0;
		Double costoMoto = 0.0;
		Double costoCamioneta = 0.0;
		Double costoCuatri = 0.0;
		
		for (Pago pago : pagos) {
			if (pago.getVehiculo() instanceof Moto) {
				costoMoto += pago.getMonto();
			}
			if (pago.getVehiculo() instanceof Cuatriciclo) {
				costoCuatri += pago.getMonto();
			}
			if (pago.getVehiculo() instanceof Auto) {
				costoAuto += pago.getMonto();
			}
			if (pago.getVehiculo() instanceof Camioneta) {
				costoCamioneta += pago.getMonto();
			}
		}
		
		reporte.put("Auto", costoAuto);
		reporte.put("Moto", costoMoto);
		reporte.put("Camioneta", costoCamioneta);
		reporte.put("Cuatriciclo", costoCuatri);
		
		return reporte;
		
	}
	
	public Boolean pagarEnSalida(Integer idCochera,LocalDateTime fechaSalida, Vehiculo vehiculo, MedioDePagoEnum medioDePago) throws DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, VehiculoNoSeEncuentraEnCochera {

		validarVehiculoEstacionadoEnCochera(idCochera, vehiculo);
		
		Double costo = vehiculo.calcularCosto(fechaSalida);
		
		Pago pago = new Pago(vehiculo, costo , medioDePago, fechaSalida );
		
		cocheras.remove(idCochera);
		
		return this.pagos.add(pago);
		
	}

	
	
	
	
	
	
}