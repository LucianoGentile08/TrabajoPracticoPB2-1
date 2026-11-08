package ar.edu.unlam.pb2.interfaces;

import ar.edu.unlam.pb2.clases.Casco;
import ar.edu.unlam.pb2.exceptions.CascoAsignadoException;

public interface Asignable {
	public Boolean asignarCasco(Casco casco) throws CascoAsignadoException;
	public Casco getCasco();
}
