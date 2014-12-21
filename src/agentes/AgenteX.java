
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
import vista.VentanaMatriz;
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

  String [][] matrizmarce;
  
  

  private int i=0;
  private String matrizjhon[][] = new String [10][24];
  private VentanaMatriz vMatriz = new VentanaMatriz();

    protected void setup()
    {  
	 
  	    ventanaCalendario.setVisible(true);
  	    ventanaCalendario.ColocarNombre(getLocalName());
 	    ventanaCalendario.btnAtras.addActionListener(this);
 	    ventanaCalendario.btnOrganizar.addActionListener(this); 	 
 	    organizar.btnEnviar.addActionListener(this);
 	    baseDedatos = new BaseDatos();

 	    vMatriz.anadirColumna(getLocalName());
 	    
 	    int p=0;
 	    for(Actividad o : agenda)
		{
			matrizjhon[i][p]= ""+o.estaDisponible();
			p++;
		}
 	    i++;
		System.out.println("la matriz a�adio una columna");
        i++;  
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
        			System.out.println("agente : "+getLocalName()+" recivio el mensaje : "+ msg.getContent() + "  del agente : "+msg.getSender().getLocalName());
					if(msg.getContent().equals("obtener horario"))
					{						
						AID emisor = new AID();
				        emisor.setLocalName(getLocalName());
						
				        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
				        mensaje.setSender(emisor);
				        mensaje.setLanguage("Espaniol");
				        String respuesta="";
				        for(Actividad a : agenda)
				        {
				        	respuesta = respuesta +a.estaDisponible()+" ";
				        	
				        }
				        System.out.println("horario de :"+ getLocalName()+ "es el siguiente : " +respuesta);
				        mensaje.setContent(respuesta);

						AID receptor = new AID();
				        receptor.setLocalName(msg.getSender().getLocalName());
				        mensaje.addReceiver(receptor);					                      
						send(mensaje);
	
					}
					else
					{		
						String a[]=msg.getContent().split(" ");
						vMatriz.anadirColumna(msg.getSender().getLocalName());
						for(int o=0;o<24;o++)
						{
							matrizjhon[i][o]= a[o];
							
						}
						System.out.println("la matriz a�adio una columna");
                         i++;  
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
		send(mensaje);        	  
		 try
	     {
		  	    Thread.sleep(2000);		
		 } 
		 catch (InterruptedException f) 
		 {		 			
	  		   System.out.println("Error al dormir al agente X ");
		 }
		 
 		organizar.setVisible(false);
 		vMatriz.setVisible(true);
 		//vMatriz.anadirfilas(matrizjhon,i);
	
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
	  matrizmarce = new String[24][lista.size()];
	  System.out.println("se imprime la matriz de 24 filas y "+lista.size() );
	  imprimirMatriz();//matriz antes de  modificar
	 
	  for(String nombre: lista)
	  {
	    //ArryList<Actividad> agendaX=baseDedatos.getAgenda(nombre);
	   
		  for(int x = 0; x<lista.size(); x++)
		   {
			  for(int y = 0; y<24; y++)
			    {
	         
	      	      matrizmarce[y][x]=""+ agenda.get(x).estaDisponible(); 
		        }
		     
		   } 
	    }	
    System.out.println("====================================");
    imprimirMatriz();//matriz despues de modificar
 }



   /* imprime  la matriz por consola*/ 

   private void imprimirMatriz() 
   {
	for(int y = 0; y<24; y++)
    {
		System.out.print(y+":00 ");
	  for(int x = 0; x<matrizmarce[0].length; x++)
	   {
        System.out.print(matrizmarce[y][x]+" ");
	   }
	   System.out.println(" ");
	    
    }	
  } 
	
}//fin de la clase
  
  
  
  
  
  
  
  
  
  
  
  
 
