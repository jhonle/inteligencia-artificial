package agentes;
import jade.util.leap.Serializable;

import java.util.ArrayList;

/**
 * clase Actividad, representa una actividad en la ageda
 * de una persona. 
 * */
public class Actividad implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double hora;
	private String detalle;
	private boolean disponible;//true=disponible false=no disponible
	
	/**
	 * metodo contructor. 
	 * ejemplo new Actividad(00.00, true)
	 * @param  double hora: representa la hora\n
	 * 
	 *  * */
	public Actividad(double hora,boolean disponible )
	{
		super();
		this.hora = hora;
		this.detalle = "";
		this.disponible = disponible;

	}
	
	/**
	 * la hola se la maneja como un double HORA.MINUTO
	 * por ejemplo 7.45(7:45)
	 * @return the hora
	 */
	public double getHora() 
	{
		return hora;
	}
	
	/**
	 * la hola se la maneja como un double HORA.MINUTO
	 * por ejemplo 7.45(7:45)
	 * @param hora, 
	 */
	public void setHora(double hora) 
	{
		this.hora = hora;
	}
	
	/**
	 * descricion de la actividad
	 * @return the detalle
	 */
	public String getDetalle() 
	{
		return detalle;
	}
	
	/**descricion de la actividad
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) 
	{
		this.detalle = detalle;
	}
	
	/**
	 * si esta libre la hora de esta actividad devuelve true, caso contrario 
	 * devuelve false
	 * @return the disponible
	 */
	public boolean estaDisponible() 
	{
		return disponible;
	}
	
	/**
	 * modifica la disponibilidad de la hora. 
	 * false= no disponible
	 * true= disponible
	 * @param disponible the disponible to set
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
}   
	
	

	
	

