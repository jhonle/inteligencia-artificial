package agentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	  private int operacion = 0;
      
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
   	
	   if(e.getSource().equals(ventanaPricipal.btnRegistro))
   	    { 
		   ventanaPricipal.lblMensaje.setVisible(true);
		   ventanaPricipal.lblNombre.setVisible(true);
		   ventanaPricipal.lblContrasena.setVisible(true);
		   ventanaPricipal.txtNombre.setVisible(true);
		   ventanaPricipal.txtContrasena.setVisible(true);
		   ventanaPricipal.btnAceptar.setVisible(true);
   		
		   ventanaPricipal.btnLogin.setVisible(false);
		   ventanaPricipal.btnRegistro.setVisible(false);
		   ventanaPricipal.btnSalir.setVisible(false);  
		   ventanaPricipal.btnAtras.setVisible(true); 
		   operacion = 2;	  
		   	   			   
         }
	   if(e.getSource().equals(ventanaPricipal.btnLogin))
  	   {
		   ventanaPricipal.lblMensaje.setVisible(true);
		   ventanaPricipal.lblNombre.setVisible(true);
		   ventanaPricipal.lblContrasena.setVisible(true);
		   ventanaPricipal.txtNombre.setVisible(true);
		   ventanaPricipal.txtContrasena.setVisible(true);		   
		   ventanaPricipal.btnAceptar.setVisible(true);
		   ventanaPricipal.btnAtras.setVisible(true);
		   ventanaPricipal.btnLogin.setVisible(false);
		   ventanaPricipal.btnRegistro.setVisible(false);
		   ventanaPricipal.btnSalir.setVisible(false);  
		   operacion = 1;		   
  	   }
	   if (e.getSource().equals(ventanaPricipal.btnSalir))
	   {
	    	System.exit(0);
	   }
	   if(e.getSource().equals(ventanaPricipal.btnAceptar))
	   {
		   System.out.println("entro al boton aceptar");
		   if(operacion == 1)
		   {
			   String nombre;
			   String contrasena;
			   nombre = ventanaPricipal.txtNombre.getText();
			   contrasena =ventanaPricipal.txtContrasena.getText();
			   for(Persona p : listaPersonas)
			   {
				 if(p.getNombre().equals(nombre))//&& p.getContrasenia().equals(contrasena))
				 {
					 enviarMensage("login",nombre);	 
					 break;
				 }  
			   }
		   }
		   else if(operacion == 2)
		   {
			   String nombre;
			   String contrasena;
			   if(llenadoCompleto())
			   {				   
				   nombre = ventanaPricipal.txtNombre.getText();
				   contrasena = ventanaPricipal.txtContrasena.getText();
				   
				   ComportamientoCrearAgentes c = new ComportamientoCrearAgentes(getContainerController(),nombre);
	               addBehaviour(c);		
	               
				   System.out.println("se creo al agente(pupuestamente) con nombre : " + nombre+" y contaseña: "+contrasena); 			 
			       listaPersonas.add(new Persona(nombre, contrasena));	
			       try
			        {
				  	    Thread.sleep(500);		
				    } 
			        catch (InterruptedException p) 
				    {		 			
			  		   System.out.println("Error al dormir al agente X ");
				    }
			       enviarMensage("login",nombre);	   		     
			   }  
		   }		    
	   }
	   if(e.getSource().equals(ventanaPricipal.btnAtras))
	   {
		   ventanaPricipal.lblMensaje.setVisible(false);
		   ventanaPricipal.lblNombre.setVisible(false);
		   ventanaPricipal.lblContrasena.setVisible(false);
		   ventanaPricipal.txtNombre.setVisible(false);
		   ventanaPricipal.txtContrasena.setVisible(false);		   
		   ventanaPricipal.btnAceptar.setVisible(false);
		   ventanaPricipal.btnAtras.setVisible(false);
		   ventanaPricipal.btnLogin.setVisible(true);
		   ventanaPricipal.btnRegistro.setVisible(true);
		   ventanaPricipal.btnSalir.setVisible(true);  
	   }
	   
    }
   private void addControlesDeVentanas() 
   {
	   
	 ventanaPricipal.btnAceptar.addActionListener(this);
	 ventanaPricipal.btnRegistro.addActionListener(this);
	 ventanaPricipal.btnLogin.addActionListener(this);
	 ventanaPricipal.btnSalir.addActionListener(this);
	 ventanaPricipal.btnAtras.addActionListener(this);
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
   private void enviarMensage(String smg,String destino) 
   {
 		
 		AID emisor = new AID();
 	    emisor.setLocalName(getLocalName());
 	   
 	    AID receptor = new AID();
 	    receptor.setLocalName(destino);
 	    
 	    ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST); 
 	    mensaje.setSender(emisor);
 	    mensaje.setLanguage("Espaniol");
 	    mensaje.addReceiver(receptor);
 	    mensaje.setContent(smg);    
 	    send(mensaje);
 	    
 	    System.out.println( getLocalName()+"(AgenteX) enviado '"+smg+"'agentePrincipal");
 	    System.out.println("mensage: "+ mensaje.toString());
 	    
 	    //ventanaPricipal.setVisible(false);
   }	    
   private boolean llenadoCompleto() 
   {
       boolean res= true;	
	   if(ventanaPricipal.txtNombre.getText().equals("") || ventanaPricipal.txtContrasena.getText().equals("")){
		  res = false; 
	      JOptionPane.showMessageDialog(null," Debe llenar  todos los campos" );
	}
	return res;
   }
}
