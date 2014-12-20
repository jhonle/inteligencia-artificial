package datos;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.ls.LSInput;

import agentes.Actividad;
import agentes.Persona;

/**
 * Clase que representa lo guardado en los Archivos 
 * Asi todos los Agentes tienen acceso a la lista de personas como tambien a sus agendas
 * 
 * Para que un agenteX acceda a la agenda de otro agente X solo deberia buscarlo aca
 * ya no tendria que mandarle mensaje para pedirle sus agenda
 * 
 * Y le mamamos a la lice diciendole que estamos utilizando comunicaion uno a uno jajajjaj..
 * 
 * 
 * Para que un Agente Actualize su Base de Datos tal vez solamente deberia crear una nueva instancia
 * de esta clase
 * 
 * Lo intentare implementar de Esta manera. 
 * 
 * */
public class BaseDatos {

    ArrayList<Persona>listaDePersonas;
	HashMap<String, ArrayList<Actividad>>listaDeAgendas; // ejemplo de uso de HashMap: http://programandoointentandolo.com/2013/02/ejemplo-de-uso-de-hashmap-en-java-2.html
	Serializador ser = new Serializador();	
	public BaseDatos() {
        ser = new Serializador();
	
		this.listaDePersonas=(ArrayList<Persona>)ser.leerObjeto("Datos.a");
		this.listaDeAgendas=(HashMap<String, ArrayList<Actividad>>)ser.leerObjeto("Datos.b");
	 
	}

	/**
	 * 
	 * @return the listaDePersonas
	 */
	public ArrayList<Persona> getListaDePersonas() {
		return listaDePersonas;
	}

	/**
	 * @param listaDePersonas the listaDePersonas to set
	 */
	public void setListaDePersonas(ArrayList<Persona> listaDePersonas) {
	
		this.listaDePersonas = listaDePersonas;
	   
		
	
	}

	/**
	 * @return the listaDeAgendas
	 */
	public HashMap<String, ArrayList<Actividad>> getListaDeAgendas() {
		return listaDeAgendas;
	}

	/**
	 * @param listaDeAgendas the listaDeAgendas to set
	 */
	public void setListaDeAgendas(
			HashMap<String, ArrayList<Actividad>> listaDeAgendas) {
		this.listaDeAgendas = listaDeAgendas;
	}
	
	public void addPersona(Persona nomPersona){
		listaDePersonas.add(nomPersona);
		
	}
	
	public void addAgenda(String nomPersona ,ArrayList<Actividad> agenda){
		
		// si no existe la clave addiciona nuevo elemento 
		// si existe la clave solo cambia su valor
		listaDeAgendas.put(nomPersona, agenda);
		
	}
	
	public ArrayList<Actividad> getAgenda(String nomPersona){
		return listaDeAgendas.get(nomPersona);
	}
	
	public void guardarListaPersonas(){
		
		for(Persona p : this.listaDePersonas){
			System.out.println(" Guardando: "+ p.getNombre());
		}
	ser.escribirObjeto(this.listaDePersonas, "Datos.a");
	}
	
    public void guardarlistaDeAgendas(){
		
	for(Persona p : this.listaDePersonas){
	
		System.out.println(" Guardando: "+ p.getNombre());
	}
	  ser.escribirObjeto(this.listaDeAgendas, "Datos.b");
	}
	
	
	
	public static void main(String []args){
		
	    BaseDatos temporal = new BaseDatos();
	    ArrayList<Persona> listaPersonas = temporal.getListaDePersonas();
	    for(Persona per : listaPersonas){
	    	System.out.println(per.getNombre());
	    }
    }
	
	
	
	
}
