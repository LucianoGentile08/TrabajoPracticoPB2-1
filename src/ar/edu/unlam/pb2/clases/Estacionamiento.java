package ar.edu.unlam.pb2.clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;
import ar.edu.unlam.pb2.exceptions.PatenteDuplicadaException;

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

	public Boolean cambiarVehiculoDeCochera(Integer idCocheraOrigen, Vehiculo vehiculo, Integer idCocheraDestino)
			throws CocheraOcupadaException {
		if (cocheras.containsKey(idCocheraOrigen) && cocheras.containsValue(vehiculo)) {
			validarCocheraOcupada(idCocheraDestino);
			cocheras.replace(idCocheraOrigen, null);
			cocheras.put(idCocheraDestino, vehiculo);
			return true;
		}
		return false;
	}
}