/*Crea el enumerado FactoriaVista que tendrá un método abstracto llamado crear y que tendrá una única instancia, por ahora, etiquetada como TEXTO y que nos devolverá la implementación de la interfaz IVista que anteriormente hemos creado (VistaTexto). Realiza un commit.
 *extrayendo su interfaz y colocando las clases y la interfaz en los paquetes adecuados. Realiza un commit.*/

package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {
	
	TEXTO{
		
		@Override
		public IVista crear () {
			return new VistaTexto();
		}
	};
	
	FactoriaVista() {
	
	}
	
	public abstract IVista crear ();
}