
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
import vista.VentanaModificar;
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
  private VentanaModificar b = new VentanaModificar(agenda,0);
  private static int INTERVALO_DE_TIEMPO=60; //INTERVALO DEL TIEMPO PARA LA AGENDA, EN MINUTO  
    protected void setup()
    {  
        try
        {
	  	    Thread.sleep(500);		
	    } 
        catch (InterruptedException e) 
	    {		 			
  		   System.out.println("Error al dormir al agente X ");
	    }
        
        //ComLlenarAgenda a = new ComLlenarAgenda(agenda);
	    //addBehaviour(a);
        llenarHorarios();
        funcionalidadTabla();
	    b.btnAceptar.addActionListener(this);    	    	  	    
  	    ventanaCalendario.ColocarNombre(getLocalName());
	    ventanaCalendario.LlenarTabla(agenda);	   
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
		System.out.println("la matriz aï¿½adio una columna");
        i++;  
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
					else if(msg.getContent().equals("login"))
					{
						System.out.println("recivio el mensaje");
						ventanaCalendario.setVisible(true);
					}
					else
					{		
						String a[]=msg.getContent().split(" ");
						vMatriz.anadirColumna(msg.getSender().getLocalName());
						for(int o=0;o<24;o++)
						{
							matrizjhon[i][o]= a[o];
							
						}
						System.out.println("la matriz aï¿½adio una columna");
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
	if(e.getSource().equals(b.btnAceptar))
	{
		b.modificarActividad();
		b.setVisible(false);
		ventanaCalendario.vaciarTabla();
	    ventanaCalendario.LlenarTabla(agenda);
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
	private void funcionalidadTabla()
	{
		ventanaCalendario.tbTabla.addMouseListener(new java.awt.event.MouseAdapter()
     	{
     		public void mouseClicked(java.awt.event.MouseEvent e) 
     		{ 
     			int fila=0,columna=0; 
     			if( e.getButton() == java.awt.event.MouseEvent.BUTTON3 )
     			{
     				fila = ventanaCalendario.tbTabla.rowAtPoint(e.getPoint());
     				
     				b = new VentanaModificar(agenda,fila);
     				b.sethora(fila);
                 	columna = ventanaCalendario.tbTabla.columnAtPoint(e.getPoint());     				     					            
                 	if ((fila > -1) && (columna > -1))
                 	{
                 		ventanaCalendario.popMenu.show(e.getComponent(), e.getX(), e.getY());
                 		ventanaCalendario.popMenu.setVisible(true);   	               
    	                ventanaCalendario.JModificar.addActionListener(new ActionListener() 
    	             	{			
    	        			@Override
    	        			public void actionPerformed(ActionEvent arg) 
    	        			{							

    	        					System.out.println("presiono moficiar");
    	        					b.setVisible(true);
    	        					
    	        					b.btnAceptar.addActionListener(new ActionListener()
    	        					{										
										@Override
										public void actionPerformed(ActionEvent argo) 
										{
											if(argo.getSource().equals(b.btnAceptar))
											{
												b.modificarActividad();
												ventanaCalendario.vaciarTabla();
			    	        					ventanaCalendario.LlenarTabla(agenda);
			    	        					b.setVisible(false);
											}											
										}
									});    	        					
    	        			}
    	        		});
                 	}
 	                
	            } 
     	}
     	});
	}
	 private void llenarHorarios() 
	 {
		  for(int hora=0; hora<24;hora++)
		  {
		 	 for(int minutos=0; minutos<60;minutos=minutos+INTERVALO_DE_TIEMPO)
		 	 {
		     	 double  horaAux = (double)hora;
		     	 double  minAux = (double)(minutos/(double)100);//todos deven ser doubles para que de como resultado un double
		         double auxMinutos=(double)horaAux+minAux;
		         Actividad a = new Actividad(auxMinutos,true);
		     	 agenda.add(a);
		     	 System.out.println("se añadio correctamente");
		      }
		   }
	  }	  
}//fin de la clase  