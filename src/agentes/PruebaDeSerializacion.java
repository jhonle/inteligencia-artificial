package agentes;

import java.util.ArrayList;

public class PruebaDeSerializacion {
   
	public static void main (String args[]){
    
		Serializador ser = new Serializador();
		ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
		listaPersonas = (ArrayList<Persona>)ser.leerObjeto("Datos.a");
		
		for(Persona p : listaPersonas){
			System.out.println("nombre :"+p.getNombre());
			System.out.println("contraseña : "+p.getContrasenia());
			System.out.println("  ");
		}
		
		
    }
	
	
	
}
