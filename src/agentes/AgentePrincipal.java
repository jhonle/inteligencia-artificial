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
	   
	   Runtime rt = Runtime.instance();

       Profile p = new ProfileImpl();
 
       ContainerController mainContainer = rt.createMainContainer(p);

		try 
		{
		   AgenteX a;
	       AgentController ac = mainContainer.createNewAgent("Agente1", a,new Object[0);
  	       ac.start();
	    }
		catch (jade.wrapper.StaleProxyException e) 
		{
	        System.err.println("Error launching agent...");
	    }
   }
   protected void takeDown() 
	{
	   doDelete();
	}
}
