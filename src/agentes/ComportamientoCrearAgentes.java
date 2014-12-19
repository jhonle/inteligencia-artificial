package agentes;

import jade.core.behaviours.SimpleBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class ComportamientoCrearAgentes extends SimpleBehaviour
{
	ContainerController c;
	String nombre;
	
	public ComportamientoCrearAgentes(ContainerController c,String nombre)
	{
		this.c=c;
		this.nombre=nombre;
			
	}

	@Override
	public void action() 
	{
		try 
		{		 
		   Object reference = new Object();
		   Object agentObject[] = new Object[1];
		   agentObject[0] = reference;
	       AgentController ac = c.createNewAgent(nombre,"agentes.AgenteX", agentObject);	             
  	       ac.start();  	         	       
	    }
		catch (jade.wrapper.StaleProxyException e) 
		{
	        System.err.println("Error al ejecutar el agente...");
	    }
		
	}

	@Override
	public boolean done() 
	{
	
		return true;
	}

}
