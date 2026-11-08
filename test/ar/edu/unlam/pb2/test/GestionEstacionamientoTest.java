package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.clases.Auto;
import ar.edu.unlam.pb2.clases.Camioneta;
import ar.edu.unlam.pb2.clases.Casco;
import ar.edu.unlam.pb2.clases.Cochera;
import ar.edu.unlam.pb2.clases.Cuatriciclo;
import ar.edu.unlam.pb2.clases.Estacionamiento;
import ar.edu.unlam.pb2.clases.Moto;
import ar.edu.unlam.pb2.clases.Vehiculo;
import ar.edu.unlam.pb2.enums.MarcaEnum;
import ar.edu.unlam.pb2.enums.MedioDePagoEnum;
import ar.edu.unlam.pb2.enums.TipoColor;
import ar.edu.unlam.pb2.exceptions.CascoAsignadoException;
import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;
import ar.edu.unlam.pb2.exceptions.DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException;
import ar.edu.unlam.pb2.exceptions.PatenteDuplicadaException;
import ar.edu.unlam.pb2.exceptions.VehiculoInvalidoParaAsignarCasco;
import ar.edu.unlam.pb2.exceptions.VehiculoNoSeEncuentraEnCochera;

public class GestionEstacionamientoTest {

	private Estacionamiento estacionamiento;
	private Vehiculo autoA;
	private Vehiculo autoB;
	private Vehiculo camioneta;
	private Vehiculo moto;
	private Vehiculo cuatriciclo;
	private Cochera cochera;
	private Cochera cocheraOrigen;
	private Cochera cocheraDestino;
	private Casco casco1;

	@Before
	public void setUp() throws Exception {
		estacionamiento = new Estacionamiento();
		autoA = new Auto("AA123AA", MarcaEnum.FORD, LocalDateTime.of(2026, 11, 1, 16, 0));
		camioneta = new Camioneta("BB123BB", MarcaEnum.CHEVROLET, LocalDateTime.of(2026, 11, 2, 16, 0));
		moto = new Moto("CC123CC", MarcaEnum.HONDA, LocalDateTime.of(2026, 11, 3, 16, 0));
		cuatriciclo = new Cuatriciclo("DD123DD", MarcaEnum.YAMAHA, LocalDateTime.of(2026, 11, 4, 16, 0));
		casco1 = new Casco(1, TipoColor.BLANCO, "M");
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
	public void deberiaLanzarCocheraOcupadaExceptionAlEstacionarEnUnaCocheraOcupada()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.estacionarVehiculo(01, camioneta);
	}

	@Test(expected = CocheraInexistenteException.class)
	public void deberiaLanzarCocheraInexistenteExceptionAlEstacionarEnUnaCocheraQueNoExiste()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(-10, autoA);
	}

	@Test
	public void deberiaCambiarUnVehiculoDeCocheraLiberandoLaCocheraDeOrigen()
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera {
		estacionamiento.estacionarVehiculo(01, autoA);
		Boolean seCambio = estacionamiento.cambiarVehiculoDeCochera(01, autoA, 02);
		assertTrue(seCambio);
	}

	@Test (expected = CocheraOcupadaException.class)
	public void deberiaLanzarCocheraOcupadaExceptionAlCambiarDeCocheraAUnaOcupada() throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera, PatenteDuplicadaException {
		estacionamiento.estacionarVehiculo(02, camioneta);
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, 02);
	}
	@Test (expected = CocheraInexistenteException.class)
	public void deberiaLanzarCocheraInexistenteDeDestinoExceptionAlCambiarAUnaCocheraInexistente() throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera, PatenteDuplicadaException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, -1);
	}
	@Test (expected = VehiculoNoSeEncuentraEnCochera.class)
	public void deberiaLanzarVehiculoNoSeEncuentraEnCocheraExceptionSiIntentamosMoverUnvehiculoQueNoSeEncuentraEnLaCocheraOrigen() throws CocheraOcupadaException, CocheraInexistenteException, VehiculoNoSeEncuentraEnCochera, PatenteDuplicadaException {
		estacionamiento.estacionarVehiculo(03, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, 03);
	}

	@Test
	public void deberiaAsignarUnCascoAUnaMoto() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		Boolean asignado = estacionamiento.asignarCascoAUnTipoDeVehiculo(moto, casco1);
		assertTrue(asignado);
		
	}
	@Test
	public void deberiaAsignarUnCascoAUnCuadriciclo() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		Boolean asignado = estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatriciclo, casco1);
		assertTrue(asignado);
	}

	@Test (expected = CascoAsignadoException.class)
	public void deberiaLanzarCascoAsignadoExceptionCuandoUnaMotoYaTieneUnCascoAsignado() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatriciclo, casco1);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatriciclo, casco1);
	}
	@Test (expected = VehiculoInvalidoParaAsignarCasco.class)
	public void deberiaLanzarVehiculoInvalidoParaAsignarCascoCuandoAsignamosCascoAUnAuto() throws CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		estacionamiento.asignarCascoAUnTipoDeVehiculo(autoA, casco1);
	}
	@Test
	public void deberiaCalcularseCorrectamenteElCostoDeEstacionamientoSegunElTipoDeVehiculo() throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException, DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, CascoAsignadoException, VehiculoInvalidoParaAsignarCasco {
		//auto
		estacionamiento.estacionarVehiculo(01, autoA);
		//pasó dos horas
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);
		Double costo = autoA.calcularCosto(salidaAuto);
		assertEquals(2000, costo, 0.0);
		
		//camioneta
		estacionamiento.estacionarVehiculo(02, camioneta);
		//pasó dos horas
		LocalDateTime salidaCamioneta = LocalDateTime.of(2026, 11, 2, 18, 0); 
		Double costoCamioneta = camioneta.calcularCosto(salidaCamioneta);
		assertEquals(3000, costoCamioneta, 0.0);
		
		//moto
		estacionamiento.estacionarVehiculo(03, moto);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(moto, casco1);
		//pasó dos horas
		LocalDateTime salidaMoto = LocalDateTime.of(2026, 11, 3, 18, 0);
		Double costoMoto = moto.calcularCosto(salidaMoto);
		assertEquals(1000, costoMoto, 0.0);
		
		//cuatriciclo
		estacionamiento.estacionarVehiculo(04, cuatriciclo);
		estacionamiento.asignarCascoAUnTipoDeVehiculo(cuatriciclo, casco1);
		//pasó dos horas
		LocalDateTime salidaCuatriciclo = LocalDateTime.of(2026,  11, 4, 18, 0);
		//pasó dos horas
		Double costoCuatriciclo = cuatriciclo.calcularCosto(salidaCuatriciclo);
		assertEquals(1600, costoCuatriciclo, 0.0);
	}
	@Test(expected = DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException.class)
	public void deberiaDeLanzarDebeTenerCascoParaPoderCalcularSuCostoDeSalidaExceptionCuandoElTipoDeVehiculoNoTengaUnCascoAsignado() throws DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		//cuatriciclo
		estacionamiento.estacionarVehiculo(04, cuatriciclo);
		//pasó dos horas
		LocalDateTime salidaCuatriciclo = LocalDateTime.of(2026,  11, 4, 18, 0);
		//pasó dos horas
		cuatriciclo.calcularCosto(salidaCuatriciclo);
	}
	@Test
	public void deberiaRegistrarUnPagoEnLaListaDePagosAlPagarLaCochera() throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException, DebeTenerCascoParaPoderCalcularSuCostoDeSalidaException, VehiculoNoSeEncuentraEnCochera {
		estacionamiento.estacionarVehiculo(01, autoA);
		LocalDateTime salidaAuto = LocalDateTime.of(2026, 11, 1, 18, 0);

		assertTrue(estacionamiento.pagarEnSalida(01, salidaAuto, autoA, MedioDePagoEnum.TARJETA));
	}

	@Test
	public void deberiaAplicarDescuentoAlPagarEnEfectivo() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaLiberarLaCocheraAlPagarElVehiculo() {
		fail("Not yet implemented");
	}

	@Test
	public void noDeberiaPermitirPagarUnaMotoSinCascoAsignado() {
		fail("Not yet implemented");
	}
	//REPORTES DE PAGO POR CADA VEHICULO
	@Test
	public void deberiaCalcularLaRecaudacionDeLosPagosRealizadosEnUnDia() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaEncontrarUnVehiculoEstacionadoPorPatente() {
		fail("Not yet implemented");
	}

	@Test
	public void noDeberiaEncontrarUnVehiculoQueNoEstaEstacionado() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaObtenerLosVehiculosEstacionadosEntreDosFechas() {
		fail("Not yet implemented");
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
