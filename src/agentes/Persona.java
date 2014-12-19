package agentes;

import jade.util.leap.Serializable;

public class Persona implements Serializable
{
	String nombre;
	String contrasenia;

	public Persona(String nombre, String contrasenia) 
	{
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
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
	public String getContrasenia() 
	{
		return contrasenia;
	}
	
	

}
