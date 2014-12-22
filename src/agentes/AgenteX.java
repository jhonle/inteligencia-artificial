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
  
  
  
  protected ArrayList<Actividad> agenda = new ArrayList<Actividad>();
  private ArrayList<Persona> p = new ArrayList<Persona>();// por que?
 
  public VentanaCalendario ventanaCalendario = new VentanaCalendario();// ventana pricipar del agente
  private VentanaOrganizar organizar = new VentanaOrganizar(p);  // por que le manda p si esta vasia en un principio
  private VentanaMatriz vMatriz; //lo llenamos solo si es el organizador;
  
  protected BaseDatos baseDedatos;// Talvez ya no sea nesesario;

  String [][] matrizmarce;// segun la base de datos
  
  

  private int i=0;// numero de columna vasia en la  la matrizjhon 
  private String matrizjhon[][];// lo llenamos solo si es el organizador
  
  
  private boolean esOrganizador = false; // true si es el organizador. False si no lo es nadie es organizador al empezar
                                            // ojo con esto. en  ningun lado le buelvo a poner en false
    protected void setup()
    {  
	    // iniciando ventanas
  	    ventanaCalendario.setVisible(true);
  	    ventanaCalendario.ColocarNombre(getLocalName());
 	    ventanaCalendario.btnAtras.addActionListener(this);
 	   
 	    //add controles de las ventanas
 	    ventanaCalendario.btnOrganizar.addActionListener(this); 	 
 	    organizar.btnEnviar.addActionListener(this);
 	    
 	    
 	     //llenamos la agenda de cada Agente
 	    ComLlenarAgenda a = new ComLlenarAgenda(agenda);
	    addBehaviour(a);
	    
	    //una referencia que tenemos en los archivos
	    baseDedatos = new BaseDatos();
	   

 	  
 	    
 	
        //comportamientoRecibirMsg
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
				    // si mensaje es Obtener horario
					if(msg.getContent().equals("obtener horario"))
					{						
						AID emisor = new AID();
				        emisor.setLocalName(getLocalName());
						
				        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
				        mensaje.setSender(emisor);
				        mensaje.setLanguage("Espaniol");
				        String respuesta="";
				        
				        // diseñando mensaje de respuesta
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
						//si el mensaje es una respuesta a obtener horario
						if(msg.getContent().length()>10)// si el mensage tiene un tamaño grande. solo los de respuesta a "obtener horario deberia ser grande"
						{
							String a[]=msg.getContent().split(" ");
					        for(int fila=0;fila<24;fila++)
						    {
						 	  
					            matrizjhon[fila][i]= a[fila];
					    	}
						    i++;//nueva columna vasia Disponible  
						    }
					     }
				  }
				    else 
				    {
				      
				      block();// si ya no hay otro mensage esperamos a que haya(supongo)
				    }		
				
			  }
			});
	  
    }// fin setUp

    protected void takeDown() 
    {
        //baseDedatos.addAgenda(this.getLocalName(), agenda);	
    	//System.out.println("Desde algun el agente :"+getLocalName()+" -> modifico baseDeDatos");
        //baseDedatos.guardarlistaDeAgendas();
        doDelete();   
 	}
  
    
    
    
  //Eventos de las ventanas  
  @Override
  public void actionPerformed(ActionEvent e) 
  {	
	 //si se presiona el boton organizar 
	if(e.getSource().equals(ventanaCalendario.btnOrganizar))
	{
	     esOrganizador = true;
         
	     // mejor decirle a AgentePricipal que nos de la lista de Personas
		 Serializador ser = new Serializador();
	     ArrayList<Persona> listaPersonas = baseDedatos.getListaDePersonas();
    	
	      organizar = new VentanaOrganizar(listaPersonas);//	 
	      organizar.btnEnviar.addActionListener(this);
    	  organizar.setVisible(true);
          ventanaCalendario.setVisible(false);	
          
          
	
	}
	
	// si se presiona el boton enviar
	if(e.getSource().equals(organizar.btnEnviar))
	{
        ArrayList<String> lista = new ArrayList<String>();
		lista = organizar.ComprobrarLista();
		matrizjhon = new String[24][lista.size()]; // inicializo solo si es organizador
		
		// envio de mensaje de "obtener horario"
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
	     
	        System.out.println( "Se envio mensaje de 'obtener horario ' a: "+i);
	     }
        send(mensaje);//Por que afuera del for?
		
        organizar.setVisible(false);
 		ventanaCalendario.setVisible(true);
 		
 		 try {
 			Thread.sleep(3000);
 		} catch (InterruptedException e1) {
 			// TODO Auto-generated catch block
 		 	e1.printStackTrace();
 		    System.out.println("no se pudo hacer esperar al agente");
 		}
        
 		if(esOrganizador==true){
 	    //para ponerlo en la VMatriz
 		
 			
 	    String listAux[]= new  String[lista.size()];
 		
 		
 		for(int i = 0; i<lista.size();i++){
 			listAux[i]=lista.get(i);
 		   System.out.println("en listAux pos :"+i+"->"+listAux[i]);
 		}
 		
 		
 		System.out.println("###MATRIZ DE JHON ");
 		imprimirMatriz();
 		
 		
 		
 		 //mostramos la matriz
 		vMatriz= new VentanaMatriz();
 		vMatriz.llenarTabla(matrizjhon, listAux);
 		vMatriz.setVisible(true);
 		}
 		
 		
 		
 		
	/*
 		System.out.println("Enviando a receptor");
        System.out.println(mensaje.toString());
  		   
  		 generarMatriz(lista);
	*/
	 }	
	
	
	
	if(e.getSource().equals(ventanaCalendario.btnAtras))
	{  
	   System.out.println("agente "+getLocalName()+" enviando mensage a agentePrincipal");
	   String smg = "habiltarVprincipal";
       enviarMensage(smg,"agentePrincipal");
       ventanaCalendario.setVisible(false);
      // this.takeDown();// se deberia terminar la ejecucion del agente?
    }
	
	
   }//fin metodo performed	
		

		
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
	
  private void imprimirMatriz() 
  {
	
		for(int y = 0; y<24; y++)
	    {
			System.out.print(y+":00 ");
		  for(int x = 0; x<matrizjhon[0].length; x++)
		   {
	          System.out.print(matrizjhon[y][x]+" ");
		   }
		   System.out.println(" ");
		    
	    }	
	  
 } 
	
}//fin de la clase
	    
	    
	
	
/*

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

*/

 /* imprime  la matriz por consola*/ 

   
  
  
  
  
  
  
  
  
  
  
  
  
 
