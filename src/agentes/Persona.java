package agentes;

import jade.util.leap.Serializable;

public class Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	String contraseña;

	public Persona(String nombre, String contraseña) {
		super();
		this.nombre = nombre;
		this.contraseña = contraseña;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the contraseña
	 */
	public String getContraseña() {
		return contraseña;
	}
	
	

}
