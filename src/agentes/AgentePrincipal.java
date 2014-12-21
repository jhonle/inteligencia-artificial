package agentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import vista.VentanaPrincipal;
import datos.BaseDatos;
import datos.Serializador;
import jade.core.*;
import jade.core.Runtime;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgentePrincipal extends Agent implements ActionListener
{
	  BaseDatos baseDeDatos;
	  VentanaPrincipal ventanaPricipal= new VentanaPrincipal();;
	  ArrayList<Persona> listaPersonas; // por cada persona en esta lista debera haber un ajeteX verdad?
      private static final long serialVersionUID = 1L;
	  
      
   protected void setup()
   {	   

	   baseDeDatos = new BaseDatos();
	   listaPersonas = baseDeDatos.getListaDePersonas();
	   CargarAgentes();// no deveria mostrar todas las ventanas
	   addControlesDeVentanas();
	
	   
	   addBehaviour(new SimpleBehaviour() 
	    {
	    	private boolean fin = false;
			@Override
			public boolean done() 
			{
					  
				    
				return fin;
			}
			
			@Override
			public void action() 
			{
				 ACLMessage msg = receive();
				 if (msg != null) 
				 {
					System.out.println("##agente Principar : "+getLocalName()+" recivio un mensaje : "+msg.getContent() + "  del agente : "+msg.getSender().getLocalName()); 
                    System.out.println("eñ mesage fue:"+msg.getContent());
					if(msg.getContent().equals("habiltarVprincipal")){
                	  ventanaPricipal.setVisible(true);
                	   
                   }				    
				 
				 }
				 else 
				 {				    
				      block();
				 }		
			}
		});
	   
	   
	   
   }
   protected void takeDown() 
   {

	   Serializador ser = new Serializador();
       ser.escribirObjeto(listaPersonas,"Datos.a");//Guarda la clase listaPersonas al Archivo Datos.a         
       baseDeDatos.setListaDePersonas(listaPersonas);
       System.out.println("Desde Agente Principar :"+getName());
       baseDeDatos.guardarListaPersonas();
	   doDelete();   
	}
   @Override
   public void actionPerformed(ActionEvent e)
   {
   	
	   if(e.getSource().equals(ventanaPricipal.btnAceptar))
   	    { 
		   String nombre;
		   String contrasena;
		   boolean aux= ventanaPricipal.llenadoCompleto();
		   System.out.println("### "+aux); 

		   if(ventanaPricipal.llenadoCompleto())
		   {
			   
			   nombre = ventanaPricipal.txtNombre.getText();
			   contrasena = ventanaPricipal.txtContrasena.getText();
			   ComportamientoCrearAgentes c = new ComportamientoCrearAgentes(getContainerController(),nombre);
               addBehaviour(c);			 
			   System.out.println("se creo al agente(pupuestamente) con nombre : " + nombre+" y contaseña: "+contrasena); 			 
		       listaPersonas.add(new Persona(nombre, contrasena));// esto llevar al comportamoento comportamientoCrearAgente(sugerencia)		     
			 
		      //aqui se deberia crear al agente y guadar en la listadepersonas jhon
		      // comportamientoCrearAgente(nombre,contraseña,listaDePersonas);//sugerencia
		      ventanaPricipal.setVisible(false);
		   		     
		   }		   
         }	 
    }
   private void addControlesDeVentanas() 
   {
	   
	 ventanaPricipal.btnAceptar.addActionListener(this);
     
	 //tal vez se necesite para cerrar correctamente la ejecucion del agente
	 //ventanaPricipal.btnSalir.addActionListener(this);//falta hace publico ese boton e implementar el evento
   }
   private void CargarAgentes()
   {

	   Serializador ser = new Serializador();
       ArrayList<Persona> listaPersonas = (ArrayList<Persona>) ser.leerObjeto("Datos.a");       

       for(Persona p : listaPersonas)
       {
    	   ComportamientoCrearAgentes c = new ComportamientoCrearAgentes(getContainerController(),p.getNombre());
           addBehaviour(c);   
       }
	   
   }

}
