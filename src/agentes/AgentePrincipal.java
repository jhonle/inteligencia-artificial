package agentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgentePrincipal extends Agent implements ActionListener
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
	       AgentController ac = mainContainer.createNewAgent("no se", "no se", null);
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
   
   public void actionPerformed(ActionEvent e)
   {
   	if(e.getSource().equals("tipo de boton pero primero hacer ohir"))
   	{   
   		
   	}
   	   	
   	}
   
   
   
}
