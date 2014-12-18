package agentes;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

import agentes.Receptor.ReceptorComportaminento;
import agentes.emisor.EmisorComportaminento;

public class AgenteX extends Agent {
  
  /**
   * representa la agenda de una persona 
   * por ahora solo la de 1 dia(00:00 hrs a 23:30 hrs)
   * */
  protected ArrayList<Actividad> agenda;
  
 
  protected void setup()
  {
	 ComLlenarAgenda a = new ComLlenarAgenda(agenda);
     addBehaviour(a);
     
     // espero antes de imprimir para que no se solape con los mensajes de jade
     try
     {
		Thread.sleep(4000);
	 } catch (InterruptedException e) 
	 {		 	
		e.printStackTrace();
	 }
     
     addBehaviour(new comportaminentoImprimir(agenda));

     
  }
 
 
}//fin clase AgenteX

/**
 * Comportamiento para  imprimir la agenda de una agente
 * 
 * */
class comportaminentoImprimir extends SimpleBehaviour
{     ArrayList<Actividad>agenda;
      public comportaminentoImprimir(ArrayList<Actividad>agenda) {
		this.agenda=agenda;
	  }
	
	   private boolean fin = false;
        public void action()
        {
          imprimirAgenda();
          fin = true;
        }
        public boolean done()
        {
            return fin;
        }
        
        
        /*
         *este metodo muestra por consola la agenda de un dia 
         *talvez pueda serbir para mostrar por interfaz grafica
         * */
        public void imprimirAgenda()
        {		  
       	 System.out.println("HRS         | DETALLE      | DISPONIBLE");
       	 System.out.println("------------|--------------|-----------");

       	 for (int i=0;i<agenda.size();i++)
       	  {
       		  int hora = (int) agenda.get(i).getHora();
       	      int minutos;
       	      if(hora<10) 
       	      {
       	    	  minutos = (int)((agenda.get(i).getHora()*10)%10);
       	    	  System.out.println("hrs 0"+hora+":"+minutos+"0   |");
       	    	  
       	      }else
       	       {
       	    	    minutos =(int)((agenda.get(i).getHora()*100)%100);
       	    	    if(minutos==0)System.out.println("hrs "+hora+":"+minutos+"0   |" );
       	    	    else System.out.println("hrs "+hora+":"+minutos+"   |");
       	       }
       	    } 
       	 
        }
}


