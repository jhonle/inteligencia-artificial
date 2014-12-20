package agentes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class PruebaDeSerializacion {
   
	public static void main (String args[]){
    
		Serializador ser  = new Serializador();
		if(ser.leerObjeto("Datos.a")==null){
		
	   /*
	    * Aqui se crea el archivo para guardar los nombres de las personas o los agentes
	    *
	    * */
		ArrayList<Persona> datos= new ArrayList<Persona>();
        ser.escribirObjeto(datos,"Datos.a");
		JOptionPane.showMessageDialog(null,"Se creo un nuevo Archivo de Datos 'Datos.a'. Esto solo se realiza en la primera Ejecucion del Programa" );
		}
		listarPersonaGuardadas();
		
		/*
    	 * se utiliza para guuardar la agenda de cada agente 
    	 * clave(K)= String = nombre del agente= nombre de persona
    	 * valor(V)=Arraylist<Actividad> = agenda de cada agente/pesona
    	 * Este se guarda en un archivo llamado Datos.b 
    	 */
		if(ser.leerObjeto("Datos.b")==null){
	   
		
		HashMap<String, ArrayList<Actividad>> listaDeagendas = new HashMap<String, ArrayList<Actividad>>();
        ser.escribirObjeto(listaDeagendas, "Datos.b");
        
		JOptionPane.showMessageDialog(null,"Se creo un nuevo Archivo de Datos 'Datos.b'. Esto solo se realiza en la primera Ejecucion del Programa" );
		}
		
	
    	
	}
		
	private static void listarPersonaGuardadas() {
		Serializador ser = new Serializador();
		ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
		listaPersonas = (ArrayList<Persona>)ser.leerObjeto("Datos.a");
		
		for(Persona p : listaPersonas)
		{
			System.out.println("nombre :"+p.getNombre());
			System.out.println("contraseña : "+p.getContrasenia());
			System.out.println("  ");
		
	    }
	
	}
	
}
