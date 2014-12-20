
package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import agentes.Receptor.ReceptorComportaminento;
import agentes.emisor.EmisorComportaminento;

public class AgenteX extends Agent implements ActionListener
{
  
  /**
   * representa la agenda de una persona 
   * por ahora solo la de 1 dia(00:00 hrs a 23:30 hrs)
   * */
  public VentanaCalendario ventanaCalendario = new VentanaCalendario();
  protected ArrayList<Actividad> agenda = new ArrayList<Actividad>();
  private ArrayList<Persona> p = new ArrayList<Persona>();
  private VentanaOrganizar organizar = new VentanaOrganizar(p);  
    protected void setup()
    {  
	 // espero antes de imprimir para que no se solape con los mensajes de jade    	
  	    ventanaCalendario.setVisible(true);
  	    ventanaCalendario.ColocarNombre(getLocalName());
 	    ventanaCalendario.btnAtras.addActionListener(this);
 	    ventanaCalendario.btnOrganizar.addActionListener(this); 	 
 	    organizar.btnEnviar.addActionListener(this);
 	    
        try
        {
	  	    Thread.sleep(4000);		
	    } 
        catch (InterruptedException e) 
	    {		 			
  		   System.out.println("Erro al dormir al agente X ");
	    }
     
	    ComLlenarAgenda a = new ComLlenarAgenda(agenda);
	    addBehaviour(a);
   	    //comportaminentoImprimir b = new comportaminentoImprimir(agenda);
        //addBehaviour(b);
  
  /*  addBehaviour(new SimpleBehaviour()
     {
				
		@Override
		public void action() 
		{
				
		}		
		@Override
		public boolean done() 
		{		
			return true;
		}
	});*/
     
   /* addBehaviour(new SimpleBehaviour() 
    {
        private boolean fin = false;
        public void action()
        {
            System.out.println(" Preparandose para recibir");
            ACLMessage mensaje = receive();
            if (mensaje!= null)
            {
                System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                System.out.println(mensaje.getContent());//toString());
                fin = true;
            }            
        }
        public boolean done()
        {
            return fin;
        }
    });
    */
   }

    protected void takeDown() 
    {
     	//guarda sus datos antes de destruirce
    	HashMap<String, ArrayList<Actividad>> listaDeagendas = new HashMap<String, ArrayList<Actividad>>();
        listaDeagendas.put(this.getName(), agenda);
        Serializador ser = new Serializador();
        ser.escribirObjeto(listaDeagendas, "Datos.b");
        
        doDelete();   
 	}
  @Override
  public void actionPerformed(ActionEvent e) 
  {	
	if(e.getSource().equals(ventanaCalendario.btnOrganizar))
	{
	       Serializador ser = new Serializador();
	       ArrayList<Persona> listaPersonas = (ArrayList<Persona>) ser.leerObjeto("datos.a");
    	
	      organizar = new VentanaOrganizar(listaPersonas);	 
	      organizar.btnEnviar.addActionListener(this);
    	  organizar.setVisible(true);

	}
	
	if(e.getSource().equals(organizar.btnEnviar))
	{
  		 
		ArrayList<String> lista = new ArrayList<String>();
		lista = organizar.ComprobrarLista();
		for(String i : lista)
		{
			System.out.println(i);	
		}
		 
  		  
  		/*AID emisor = new AID();
        emisor.setLocalName(getLocalName());
        
        AID receptor = new AID();
        receptor.setLocalName("prueba");
        
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST); 

        mensaje.setSender(emisor);
        mensaje.setLanguage("Espaniol");
        mensaje.addReceiver(receptor);
        mensaje.setContent("hola");    
        send(mensaje);
        
        System.out.println("Enviando a receptor");
        System.out.println(mensaje.toString());*/
  		  
  		  organizar.setVisible(false);		  		  
	}	
  }
}


