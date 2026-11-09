package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.clases.Auto;
import ar.edu.unlam.pb2.clases.Camioneta;
import ar.edu.unlam.pb2.clases.Casco;
import ar.edu.unlam.pb2.clases.Cuatriciclo;
import ar.edu.unlam.pb2.clases.Estacionamiento;
import ar.edu.unlam.pb2.clases.Moto;
import ar.edu.unlam.pb2.clases.Pago;
import ar.edu.unlam.pb2.clases.Vehiculo;
import ar.edu.unlam.pb2.enums.MarcaEnum;
import ar.edu.unlam.pb2.enums.MedioDePagoEnum;
import ar.edu.unlam.pb2.enums.TipoColor;
import ar.edu.unlam.pb2.exceptions.CascoAsignadoException;
import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraNoEncontradaException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;
import ar.edu.unlam.pb2.exceptions.DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException;
import ar.edu.unlam.pb2.exceptions.PatenteDuplicadaException;
import ar.edu.unlam.pb2.exceptions.VehiculoInvalidoParaAsignarCasco;
import ar.edu.unlam.pb2.exceptions.VehiculoNoSeEncuentraEnCochera;
import ar.edu.unlam.pb2.exceptions.VehiculoNuloException;

public class GestionEstacionamientoTest {

	private Estacionamiento estacionamiento;
	private Vehiculo autoA;
	private Vehiculo autoB;
	private Vehiculo camionetaA;
	private Vehiculo motoA;
	private Vehiculo cuatricicloA;
	private Casco cascoA;
	private Casco cascoB;

	@Before
	public void setUp() {
		estacionamiento = new Estacionamiento();
		autoA = new Auto("AA123AA", MarcaEnum.FORD, LocalDateTime.of(2026, 11, 1, 16, 0));
		camionetaA = new Camioneta("BB123BB", MarcaEnum.CHEVROLET, LocalDateTime.of(2026, 11, 2, 16, 0));
		motoA = new Moto("CC123CC", MarcaEnum.HONDA, LocalDateTime.of(2026, 11, 3, 16, 0));
		cuatricicloA = new Cuatriciclo("DD123DD", MarcaEnum.YAMAHA, LocalDateTime.of(2026, 11, 4, 16, 0));
		cascoA = new Casco(1, TipoColor.BLANCO, "M");
		cascoB = new Casco(2, TipoColor.AMARILLO, "L");
	}

	@Test
	public void deberiaEstacionarUnAutoEnUnaCocheraLibre()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		Boolean seEstaciono = estacionamiento.estacionarVehiculo(01, autoA);
		assertTrue(seEstaciono);
	}

	@Test(expected = PatenteDuplicadaException.class)
	public void noDeberiaPermitirEstacionarDosVehiculosConLaMismaPatente()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		autoB = new Auto("AA123AA", MarcaEnum.FORD, LocalDateTime.of(2026, 11, 1, 16, 0));
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(02, autoB);
	}

	@Test(expected = CocheraOcupadaException.class)
	public void noDeberiaPermitirEstacionarEnUnaCocheraOcupada()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(01, camionetaA);
	}

	@Test(expected = CocheraInexistenteException.class)
	public void noDeberiaPermitirEstacionarEnUnaCocheraQueNoExiste()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(-10, autoA);
	}

	@Test
	public void deberiaCambiarUnVehiculoDeCocheraLiberandoLaCocheraDeOrigen()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			VehiculoNoSeEncuentraEnCochera, VehiculoNuloException {
		estacionamiento.estacionarVehiculo(01, autoA);
		Map<Integer, Vehiculo> cocheraDeOrigen = estacionamiento.cambiarVehiculoDeCochera(01, autoA, 02);
		assertEquals(cocheraDeOrigen.get(01), null);
	}

	@Test(expected = CocheraOcupadaException.class)
	public void noDeberiaPermitirCambiarDeCocheraAUnaOcupada()
			throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera,
			PatenteDuplicadaException, VehiculoNuloException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(02, camionetaA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, 02);
	}

	@Test(expected = CocheraInexistenteException.class)
	public void noDeberiaPermitirCambiarVehiculoAUnaCocheraInexistente()
			throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera,
			PatenteDuplicadaException, VehiculoNuloException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, -1);
	}

	@Test(expected = VehiculoNoSeEncuentraEnCochera.class)
	public void noDeberiaPermitirMoverUnvehiculoQueNoSeEncuentraEnLaCocheraOrigen()
			throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera,
			PatenteDuplicadaException, VehiculoNuloException {
		estacionamiento.estacionarVehiculo(03, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, 03);
	}

	@Test
	public void deberiaAsignarUnCascoAUnaMoto() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		Boolean seAsigno = estacionamiento.asignarCascoAUnTipoDeVehiculo(motoA, cascoA);
		assertTrue(seAsigno);
	}

	@Test
	public void deberiaAsignarUnCascoAUnCuadriciclo() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		Boolean seAsigno = estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatricicloA, cascoA);
		assertTrue(seAsigno);
	}

	@Test(expected = CascoAsignadoException.class)
	public void noDeberiaPermitirAsignarUnCascoAUnaMotoQueYaTieneUnCascoAsignado()
			throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatricicloA, cascoA);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatricicloA, cascoA);
	}

	@Test(expected = VehiculoInvalidoParaAsignarCasco.class)
	public void noDeberiaPermitirAsignarUnCascoAUnAuto()
			throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		estacionamiento.asignarCascoAUnTipoDeVehiculo(autoA, cascoA);
	}

	@Test
	public void deberiaCalcularseCorrectamenteElCostoDeEstacionamientoSegunElTipoDeVehiculo()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, CascoAsignadoException,
			VehiculoInvalidoParaAsignarCasco {
		// AUTO
		estacionamiento.estacionarVehiculo(01, autoA);
		Double costoAuto = autoA.calcularCosto(LocalDateTime.of(2026, 11, 1, 18, 0));
		assertEquals(2000, costoAuto, 0.0);
		// CAMIONETA
		estacionamiento.estacionarVehiculo(02, camionetaA);
		Double costoCamioneta = camionetaA.calcularCosto(LocalDateTime.of(2026, 11, 2, 18, 0));
		assertEquals(3000, costoCamioneta, 0.0);
		// MOTO
		estacionamiento.estacionarVehiculo(03, motoA);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(motoA, cascoA);
		Double costoMoto = motoA.calcularCosto(LocalDateTime.of(2026, 11, 3, 18, 0));
		assertEquals(1000, costoMoto, 0.0);
		// CUATRICICLO
		estacionamiento.estacionarVehiculo(04, cuatricicloA);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatricicloA, cascoB);
		Double costoCuatriciclo = cuatricicloA.calcularCosto(LocalDateTime.of(2026, 11, 4, 18, 0));
		assertEquals(1600, costoCuatriciclo, 0.0);
	}

	@Test(expected = DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException.class)
	public void noDeberiaPoderCalcularCostoDeVehiculoSiNoTieneUnCascoAsignado()
			throws DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, CocheraOcupadaException,
			PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(04, cuatricicloA);
		LocalDateTime salidaCuatriciclo = LocalDateTime.of(2026, 11, 4, 18, 0);
		cuatricicloA.calcularCosto(salidaCuatriciclo);
	}

	@Test
	public void deberiaAplicarDescuentoAlPagarEnEfectivo()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, VehiculoNoSeEncuentraEnCochera {
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		estacionamiento.estacionarVehiculo(01, autoA);
		Pago pago = estacionamiento.calcularPago(01, salidaAuto, autoA, MedioDePagoEnum.EFECTIVO);
		estacionamiento.realizarPago(pago);
		assertEquals(pago.getMonto(), 1800, 0.0);
	}

	@Test
	public void deberiaRegistrarUnPagoEnLaListaDePagosAlPagarUnaCochera()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, VehiculoNoSeEncuentraEnCochera {
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		estacionamiento.estacionarVehiculo(01, autoA);
		Pago pago = estacionamiento.calcularPago(01, salidaAuto, autoA, MedioDePagoEnum.TARJETA);
		Boolean sePago = estacionamiento.realizarPago(pago);
		assertTrue(sePago);
	}

	@Test
	public void deberiaLiberarLaCocheraAlPagarElVehiculo() throws CocheraOcupadaException, PatenteDuplicadaException,
			CocheraInexistenteException, DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException,
			VehiculoNoSeEncuentraEnCochera, CocheraNoEncontradaException {
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		estacionamiento.estacionarVehiculo(01, autoA);
		Pago pago = estacionamiento.calcularPago(01, salidaAuto, autoA, MedioDePagoEnum.EFECTIVO);
		estacionamiento.realizarPago(pago);
		Map<Integer, Vehiculo> cocheraLiberada = estacionamiento.buscarCochera(01);
		assertEquals(cocheraLiberada.get(01), null);
	}

	@Test(expected = DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException.class)
	public void noDeberiaPermitirPagarUnaMotoSinCascoAsignado()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			VehiculoNoSeEncuentraEnCochera, DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException {
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		estacionamiento.estacionarVehiculo(01, motoA);
		Pago pago = estacionamiento.calcularPago(01, salidaAuto, motoA, MedioDePagoEnum.EFECTIVO);
		estacionamiento.realizarPago(pago);
	}

	@Test
	public void deberaGenerarReporteDeLaRecaudacionDeLosPagosRealizadosPorVehiculo()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException,
			DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, CascoAsignadoException,
			VehiculoInvalidoParaAsignarCasco, VehiculoNoSeEncuentraEnCochera {
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		LocalDateTime salidaCamioneta = LocalDateTime.of(2026, 11, 2, 18, 0);
		LocalDateTime salidaMoto = LocalDateTime.of(2026, 11, 3, 18, 0);
		LocalDateTime salidaCuatriciclo = LocalDateTime.of(2026, 11, 4, 18, 0);
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(02, camionetaA);
		estacionamiento.estacionarVehiculo(03, motoA);
		estacionamiento.estacionarVehiculo(04, cuatricicloA);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(motoA, cascoA);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatricicloA, cascoB);
		Pago pagoAuto = estacionamiento.calcularPago(01, salidaAuto, autoA, MedioDePagoEnum.TARJETA);
		Pago pagoCamioneta = estacionamiento.calcularPago(02, salidaCamioneta, camionetaA, MedioDePagoEnum.TARJETA);
		Pago pagoMoto = estacionamiento.calcularPago(03, salidaMoto, motoA, MedioDePagoEnum.TARJETA);
		Pago pagoCuatriciclo = estacionamiento.calcularPago(04, salidaCuatriciclo, cuatricicloA,
				MedioDePagoEnum.TARJETA);
		estacionamiento.realizarPago(pagoAuto);
		estacionamiento.realizarPago(pagoCamioneta);
		estacionamiento.realizarPago(pagoMoto);
		estacionamiento.realizarPago(pagoCuatriciclo);
		Map<String, Double> reporteDeLaRecaudacion = estacionamiento.generarReporteDeLaRecaudacion();
		for (Map.Entry<String, Double> entry : reporteDeLaRecaudacion.entrySet()) {
			String key = entry.getKey();
			Double val = entry.getValue();
			switch (key) {
			case "Auto": {
				assertEquals(2000.0, val, 0.0);
				break;
			}
			case "Moto": {
				assertEquals(1000.0, val, 0.0);
				break;
			}
			case "Camioneta": {
				assertEquals(3000.0, val, 0.0);
				break;
			}
			case "Cuatriciclo": {
				assertEquals(1600.0, val, 0.0);
				break;
			}
			}
		}
	}

	@Test
	public void deberiaObtenerLosVehiculosEstacionadosEntreDosFechas() throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(02, camionetaA);
		estacionamiento.estacionarVehiculo(03, motoA);
		estacionamiento.estacionarVehiculo(04, cuatricicloA);
		LocalDateTime fechaDeInicio = LocalDateTime.of(2026, 11, 1, 0, 0);
		LocalDateTime fechaDeFin = LocalDateTime.of(2026, 11, 4, 0, 0);
		List<Vehiculo> vehiculos = estacionamiento.obtenerVehiculosEstacionadosEntreDosFechas(fechaDeInicio, fechaDeFin);
		assertEquals(vehiculos.getFirst(), autoA);
		assertEquals(vehiculos.getLast(), motoA);
	}

	@Test
	public void deberiaObtenerLosVehiculosOrdenadosPorPatenteUsandoTreeSetYComparable() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaObtenerLosVehiculosQueSuperanLaMediaHoraOrdenadosPorTiempoDeMayorAMenorUsandoTreeSetYComparator() {
		fail("Not yet implemented");
	}

}
