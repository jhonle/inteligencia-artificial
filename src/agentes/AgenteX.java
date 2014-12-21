
package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA.OMGVMCID;

import vista.VentanaCalendario;
import vista.VentanaOrganizar;
import agentes.Receptor.ReceptorComportaminento;
import agentes.emisor.EmisorComportaminento;
import datos.BaseDatos;
import datos.Serializador;

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
  protected BaseDatos baseDedatos;
  String [][] matriz;
  
  
    protected void setup()
    {  
	 
  	    ventanaCalendario.setVisible(true);
  	    ventanaCalendario.ColocarNombre(getLocalName());
 	    ventanaCalendario.btnAtras.addActionListener(this);
 	    ventanaCalendario.btnOrganizar.addActionListener(this); 	 
 	    organizar.btnEnviar.addActionListener(this);
 	    baseDedatos = new BaseDatos();
       
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
                     if(msg.getContent().equals("obtener horario")){
                    	 
                      String emisor= msg.getSender().getLocalName();
                       System.out.println(" El agente "+ getLocalName()+"recivio un mensage:-> "+msg.getContent());	 
                    
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
        baseDedatos.addAgenda(this.getLocalName(), agenda);	
    	System.out.println("Desde algun el agente :"+getLocalName());
        baseDedatos.guardarlistaDeAgendas();
        doDelete();   
 	}
  @Override
  public void actionPerformed(ActionEvent e) 
  {	
	if(e.getSource().equals(ventanaCalendario.btnOrganizar))
	{
	      Serializador ser = new Serializador();
	      ArrayList<Persona> listaPersonas = baseDedatos.getListaDePersonas();
    	
	      organizar = new VentanaOrganizar(listaPersonas);//	 
	      organizar.btnEnviar.addActionListener(this);
    	  organizar.setVisible(true);

	}
	
	if(e.getSource().equals(organizar.btnEnviar))
	{
  		 
		ArrayList<String> lista = new ArrayList<String>();
		lista = organizar.ComprobrarLista();
		
		AID emisor = new AID();
        emisor.setLocalName(getLocalName());
		
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.setSender(emisor);
        mensaje.setLanguage("Espaniol");
        mensaje.setContent("obtener horario");
		for(String i : lista)
		{
			System.out.println(i);
			AID receptor = new AID();
	        receptor.setLocalName(i);
	        mensaje.addReceiver(receptor);
	        send(mensaje);
	        System.out.println( "Se envio mensaje a:"+i);
		}		                                 
		
        System.out.println("Enviando a receptor");
        System.out.println(mensaje.toString());
  		  
  		 organizar.setVisible(false);		  		  
	
  
  		  
  		 generarMatriz(lista);
	
	 }	
  
	if(e.getSource().equals(ventanaCalendario.btnAtras))
	{  
		System.out.println("agente "+getLocalName()+" enviando mensage a agentePrincipal");
	   String smg = "habiltarVprincipal";
       enviarMensage(smg,"agentePrincipal");
      
    }
  
  }
  
  private void enviarMensage(String smg,String destino) {
		
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
	    
	    ventanaCalendario.setVisible(false);
  }
  
  
  private void generarMatriz(ArrayList<String> lista) 
  { 
	  matriz = new String[24][lista.size()];
	  System.out.println("se imprime la matriz de 24 filas y "+lista.size() );
	  imprimirMatriz();//matriz antes de  modificar
	 
	  for(String nombre: lista)
	  {
	    //ArryList<Actividad> agendaX=baseDedatos.getAgenda(nombre);
	   
		  for(int x = 0; x<lista.size(); x++)
		   {
			  for(int y = 0; y<24; y++)
			    {
	         
	      	      matriz[y][x]=""+ agenda.get(x).estaDisponible(); 
		        }
		     
		   } 
	    }	
  System.out.println("====================================");
  imprimirMatriz();//matriz despues de modificar
  }

/*
 * devuelve la siguiente columna vacia en la matriz
 * */
private int obtenerSiguienteColumna(int i) 
{
	if(matriz[0][i]==null){
		return i;
		
	}else{
		return obtenerSiguienteColumna(i+1);
	}
	
 }

   /* imprime  la matriz por consola*/ 
  private void imprimirMatriz() 
  {
	for(int y = 0; y<24; y++)
    {
		System.out.print(y+":00 ");
	  for(int x = 0; x<matriz[0].length; x++)
	   {
          System.out.print(matriz[y][x]+" ");
	   }
	   System.out.println(" ");
	    
    }	
  } 
	


}//fin de la clase

  
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 