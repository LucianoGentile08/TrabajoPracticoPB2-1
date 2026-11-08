package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.exceptions.CocheraInexistenteException;
import ar.edu.unlam.pb2.exceptions.CocheraOcupadaException;

public class GestionEstacionamientoTest {
	

	@Before
	public void setUp() throws Exception {
	
	}

    @Test
    public void deberiaEstacionarUnAutoEnUnaCocheraLibre() throws CocheraOcupadaException {
        fail("Not yet implemented");
    }
 
    @Test
    public void noDeberiaPermitirEstacionarDosVehiculosConLaMismaPatente() throws CocheraOcupadaException {
        fail("Not yet implemented");
    }
 
    @Test(expected = CocheraOcupadaException.class)
    public void deberiaLanzarCocheraOcupadaExceptionAlEstacionarEnUnaCocheraOcupada() throws CocheraOcupadaException {
        fail("Not yet implemented");
    }
 
    @Test(expected = CocheraInexistenteException.class)
    public void deberiaLanzarCocheraInexistenteExceptionAlEstacionarEnUnaCocheraQueNoExiste() throws CocheraOcupadaException {
        fail("Not yet implemented");
    }
 
    @Test
    public void deberiaCambiarUnVehiculoDeCocheraLiberandoLaCocheraDeOrigen() throws CocheraOcupadaException {
        fail("Not yet implemented");
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
