package agentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgentePrincipal extends Agent implements ActionListener
{
	   VentanaPrincipal ventanaPricipal= new VentanaPrincipal();;
	   VentanaCalendario ventanaCalendario;
	   ArrayList<Persona> listaPersonas = new ArrayList<Persona>(); // por cada persona en esta lista debera haber un ajeteX verdad?
	    
   protected void setup()
   {
	   
	   addControlesDeVentanas();
	   
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
	   //Antes De elimirar al agente se debe guardar listaDePersonas
	   
	   doDelete();
	   
	}
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
   	
	   if(e.getSource().equals(ventanaPricipal.btnAceptar))
   	    { 
		   String nombre;
		   String contraseña;
		   boolean aux= ventanaPricipal.llenadoCompleto();
			 System.out.println("### "+aux); 

		   if(ventanaPricipal.llenadoCompleto()){
			   
			   nombre = ventanaPricipal.txtNombre.getText();
			   contraseña = ventanaPricipal.txtContrasena.getText();
			 
			 System.out.println("se creo al agente(pupuestamente) con nombre : " + nombre+" y contaseña: "+contraseña); 
		     listaPersonas.add(new Persona(nombre, contraseña));// esto llevar al comportamoento comportamientoCrearAgente(sugerencia)
			 
		     //aqui se deberia crear al agente y guadar en la listadepersonas jhon
		    // comportamientoCrearAgente(nombre,contraseña,listaDePersonas);//sugerencia
		   ventanaPricipal.setVisible(false);
		   ventanaCalendario = new VentanaCalendario();
		   ventanaCalendario.setVisible(true);
		     
		   }
         }
    }


   private void addControlesDeVentanas() {
	   
	 ventanaPricipal.btnAceptar.addActionListener(this);
     
	 //tal vez se necesite para cerrar correctamente la ejecucion del agente
	 //ventanaPricipal.btnSalir.addActionListener(this);//falta hace publico ese boton e implementar el evento
   }
   

}
