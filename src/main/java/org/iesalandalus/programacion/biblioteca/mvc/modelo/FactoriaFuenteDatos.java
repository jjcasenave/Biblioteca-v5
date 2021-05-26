
package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb.FactoriaFuenteDatosMongoDB;


public enum FactoriaFuenteDatos {

	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	},
	
	MONGODB {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMongoDB();
		}
	};
	
	FactoriaFuenteDatos() {

	}

	public abstract IFuenteDatos crear();
	
}