package agentes;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgentePrincipal extends Agent 
{
   protected void setup()
   {
	   
	   VentanaPrincipal v = new VentanaPrincipal();
	   
	   //Runtime rt = Runtime.instance();

       //Profile p = new ProfileImpl();
 
      // ContainerController mainContainer = rt.createMainContainer(p);

		try 
		{		 
		   Object reference = new Object();
		   Object agentObject[] = new Object[1];
		   agentObject[0] = reference;
	       AgentController ac = getContainerController().createNewAgent("Prueba2","agentes.AgenteX", agentObject);
	       AgentController ab = getContainerController().createNewAgent("prueba","agentes.AgenteX", agentObject);	      
  	       ac.start();  	       
  	       ab.start();
  	       ac.suspend();
	    }
		catch (jade.wrapper.StaleProxyException e) 
		{
	        System.err.println("Error al ejecutar el agente...");
	    }
   }
   protected void takeDown() 
	{
	   doDelete();
	}
}
