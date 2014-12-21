package datos;

import java.util.ArrayList;

import agentes.Persona;

public class PrueballenarDatos {
	public static void main (String []args){
		Serializador ser = new Serializador();
		ArrayList<Persona> listaPersonas = (ArrayList<Persona>)ser.leerObjeto("Datos.a");
		listaPersonas.add(new Persona("marcelo", "marcelo123"));
		listaPersonas.add(new Persona("juan", "juan123"));
		listaPersonas.add(new Persona("jhon", "jhon123"));
		listaPersonas.add(new Persona("mario", "mario123"));
		listaPersonas.add(new Persona("rodrigo", "rodrigo123"));
		listaPersonas.add(new Persona("katy", "katy123"));
		ser.escribirObjeto(listaPersonas, "Datos.a");
	   	
		
		
	}
	
	

}
