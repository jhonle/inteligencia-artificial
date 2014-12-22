package datos;

import java.util.ArrayList;

import agentes.Persona;
import datos.Serializador;

public class PrueballenarDatos {
	 public PrueballenarDatos() {
		Serializador ser = new Serializador();
		//ArrayList<Persona> listaPersonas = (ArrayList<Persona>)ser.leerObjeto("Datos.a");
		ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
		listaPersonas.add(new Persona("marcelo", "marcelo123"));
		listaPersonas.add(new Persona("juan", "juan123"));
		//listaPersonas.add(new Persona("jhon", "jhon123"));
		//listaPersonas.add(new Persona("mario", "mario123"));
		//listaPersonas.add(new Persona("rodrigo", "rodrigo123"));
		//listaPersonas.add(new Persona("katy", "katy123"));
		ser.escribirObjeto(listaPersonas, "Datos.a");
		
	}
	
	

}
	   	

