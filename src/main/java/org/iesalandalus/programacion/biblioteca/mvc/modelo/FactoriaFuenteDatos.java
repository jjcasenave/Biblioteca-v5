
package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;


public enum FactoriaFuenteDatos {
	
	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};

	
	FactoriaFuenteDatos() {
		
	}
	
	public abstract IFuenteDatos crear();
	
	
}