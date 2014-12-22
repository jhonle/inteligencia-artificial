package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

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

  protected ArrayList<Actividad> agenda = new ArrayList<Actividad>();

  private ArrayList<Persona> p = new ArrayList<Persona>();

  public VentanaCalendario ventanaCalendario = new VentanaCalendario();// ventana pricipar del agente  
  private VentanaOrganizar organizar = new VentanaOrganizar(p);  // por que le manda p si esta vasia en un principio
  private VentanaMatriz vMatriz; //lo llenamos solo si es el organizador;
  private VentanaModificar b = new VentanaModificar(agenda,0);
  private static int INTERVALO_DE_TIEMPO=60; //INTERVALO DEL TIEMPO PARA LA AGENDA, EN MINUTO  
  
  protected BaseDatos baseDedatos;// Talvez ya no sea nesesario;

  String [][] matrizmarce;// segun la base de datos
    
  private int i=0;// numero de columna vasia en la  la matrizjhon 
  private String matrizjhon[][];// lo llenamos solo si es el organizador 
  
  private boolean esOrganizador = false; // true si es el organizador. False si no lo es nadie es organizador al empezar
                                            // ojo con esto. en  ningun lado le buelvo a poner en false

  private String[] matrizReducida;
  
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

            llenarHorarios();
            funcionalidadTabla();          


        // iniciando ventanas  	    
  	    ventanaCalendario.ColocarNombre(getLocalName());
 	    ventanaCalendario.btnAtras.addActionListener(this);
 	    ventanaCalendario.LlenarTabla(agenda);
 	    
 	    //add controles de las ventanas
 	    ventanaCalendario.btnOrganizar.addActionListener(this); 	 
 	    b.btnAceptar.addActionListener(this);
 	    ventanaCalendario.btnAtras.addActionListener(this);
        organizar.btnEnviar.addActionListener(this);

 	    
 	    
 	     //llenamos la agenda de cada Agente
 	 //   ComLlenarAgenda a = new ComLlenarAgenda(agenda);
	   // addBehaviour(a);
	    
	    //una referencia que tenemos en los archivos
	    baseDedatos = new BaseDatos();
	   

 	  
 	    
 	
        //comportamientoRecibirMsg
        addBehaviour(new CyclicBehaviour() 
	    {
        	
        	//Devuelve una plantilla de mensaje que coincida con algún mensaje con una perfomativa dada.
            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            MessageTemplate filtroInform2 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

          
            MessageTemplate plantilla = MessageTemplate.and(filtroInform,filtroInform2);
        	
    
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
					else{	
						 //si el mensaje es una respuesta a obtener horario
						if(msg.getContent().length()>10 && msg.getContent().length()<125)// si el mensage tiene un tamaño grande. solo los de respuesta a "obtener horario deberia ser grande"
						{
							String a[]=msg.getContent().split(" ");
					        for(int fila=0;fila<24;fila++)
						    {	try{
						    	//  matrizjhon[fila][i]= null;
					              matrizjhon[fila][i]= a[fila];
						        }catch(java.lang.ArrayIndexOutOfBoundsException e){
						        
						        	matrizjhon= null;
						        	esOrganizador= false;
						        // Poner esto en un boton: VMtriz
						        //  matrizjhon=null;
								//JOptionPane.showMessageDialog(null,"Intente de nuevo" );
                                //esOrganizador= true;
                                //vMatriz.setVisible(false);
                                //organizar.setVisible(true);
						        }
						    
						    }
						    i++;//nueva columna vasia Disponible  
						}else {
							if(msg.getContent().equals("login"))
							{
								System.out.println("recivio el mensaje");
								ventanaCalendario.setVisible(true);
							}
							
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
		 String listAux[]= new  String[lista.size()];
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
 		
 			
 	  
 		
 		
 		for(int i = 0; i<lista.size();i++){
 			listAux[i]=lista.get(i);
 		   System.out.println("en listAux pos :"+i+"->"+listAux[i]);
 		}
 		
 		
 		System.out.println("###MATRIZ CONFIGURADA ");
 		imprimirMatriz();
 		//System.out.println("###MATRIZ PROPAGACION ");
 		//String [][] matrizReultado = aplicarPropagacion(matrizjhon);
 	//	imprimirMatriz(matrizReultado);
 		
 		
 		//mostramos la matriz
 		vMatriz= new VentanaMatriz();
 		vMatriz.llenarTabla(matrizjhon, listAux);
 		vMatriz.setVisible(true);
 		
 		this.esOrganizador = false;
 		}
 		
 		
 		
 		 organizar.setVisible(false);
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
	if(e.getSource().equals(b.btnAceptar))
	{
		b.modificarActividad();
		b.setVisible(false);
		ventanaCalendario.vaciarTabla();
	    ventanaCalendario.LlenarTabla(agenda);
	}
	

	
   }//fin metodo performed	

		

  private String[][] aplicarPropagacion(String[][] matrizjhon2) {
	
	
	
	ArrayList<String []> aux= new ArrayList<String[]>();//nuevasFilas 
	for( int y=0;y<24;y++){
	    String[] fila= obtenerFila(matrizjhon2, y);
	    if(!hayFalse(fila))
		  {
			  
		  	   aux.add(fila);   
		   
		  }
	  }
	
	String[][] res = new String[aux.size()][matrizjhon2.length];
	matrizReducida= new String[aux.size()];//falta implementar
	
	  
	
		for(int i=0;i<aux.size();i++){
		  	 insertaFila(i,matrizjhon2,aux.get(i));
			
	      	
	    }
  
  
	
	return res;
}

  

private void insertaFila(int numeroFila, String[][] matrizjhon2, String[] fila) {
	
	for(int i=0;i<fila.length;i++){
		matrizjhon2[numeroFila][i]=fila[i];
	}
	
}

private boolean hayFalse(String[] fila) {
	boolean res = false;
	for(int i=0; i<fila.length; i++){
		if(fila[i].equals("false")){
			System.out.println(i+": es falso?="+fila[i]);
			res=true;
		}
		
	}

    return res;
}

/**
   * reetorna una fila de la tabla[][] ingresada
   * */
     private String[] obtenerFila(String[][] tabla, int fila) {
  		String res[]= new String[tabla[0].length];
  	    System.out.println("Cantidad de columnas= "+ res.length);
  		for(int i =0; i <tabla[0].length;i++){
  	    	 res[i]=tabla[fila][i]; 
  	     }
  	   
  	   return res;
  	
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
	
  private void imprimirMatriz() 
  {
	
		for(int y = 0; y<matrizjhon.length; y++)
	    {
			System.out.print(y+":00 ");
		  for(int x = 0; x<matrizjhon[0].length; x++)
		   {
	          System.out.print(matrizjhon[y][x]+" ");
		   }
		   System.out.println(" ");
		    
	    }	
	  
 } 
	
  private void imprimirMatriz(String matriz[][]) 
  {
	
		for(int y = 0; y<matriz.length; y++)
	    {
			System.out.print(y+":00 ");
		  for(int x = 0; x<matriz[0].length; x++)
		   {
	          System.out.print(matriz[y][x]+" ");
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
		     	 System.out.println("se a�adio correctamente");
		      }
		   }
	  }	  
}//fin clase 
