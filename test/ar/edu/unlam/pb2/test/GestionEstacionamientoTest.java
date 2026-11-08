package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.clases.Auto;
import ar.edu.unlam.pb2.clases.Camioneta;
import ar.edu.unlam.pb2.clases.Cochera;
import ar.edu.unlam.pb2.clases.Cuatriciclo;
import ar.edu.unlam.pb2.clases.Estacionamiento;
import ar.edu.unlam.pb2.clases.Moto;
import ar.edu.unlam.pb2.clases.Vehiculo;
import ar.edu.unlam.pb2.enums.MarcaEnum;
import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;
import ar.edu.unlam.pb2.exceptions.PatenteDuplicadaException;

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

	@Before
	public void setUp() throws Exception {
		estacionamiento = new Estacionamiento();
		autoA = new Auto("AA123AA", MarcaEnum.FORD, LocalDateTime.of(2026, 11, 1, 16, 0));
		camioneta = new Camioneta("BB123BB", MarcaEnum.CHEVROLET, LocalDateTime.of(2026, 11, 2, 16, 0));
		moto = new Moto("CC123CC", MarcaEnum.HONDA, LocalDateTime.of(2026, 11, 3, 16, 0));
		cuatriciclo = new Cuatriciclo("DD123DD", MarcaEnum.YAMAHA, LocalDateTime.of(2026, 11, 4, 16, 0));
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
			throws CocheraOcupadaException, PatenteDuplicadaException, CocheraInexistenteException {
		estacionamiento.estacionarVehiculo(01, autoA);
		estacionamiento.cambiarVehiculoDeCochera(01, autoA, 02);
	}

	@Test(expected = CocheraOcupadaException.class)
	public void deberiaLanzarCocheraOcupadaExceptionAlCambiarDeCocheraAUnaOcupada() throws CocheraOcupadaException {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaCalcularElCostoDeEstacionamientoSegunElTipoDeVehiculo() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaAsignarUnCascoAUnaMoto() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaAsignarUnCascoAUnCuadriciclo() {
		fail("Not yet implemented");
	}

	@Test
	public void deberiaRegistrarUnPagoEnLaListaDePagosAlPagarLaCochera() {
		fail("Not yet implemented");
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
