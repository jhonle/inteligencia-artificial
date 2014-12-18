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
  private ArrayList<Actividad> agenda;
  private static int INTERVALO_DE_TIEMPO=10; //INTERVALO DEL TIEMPO PARA LA AGENDA, EN MINUTOS
 
  protected void setup()
  {
     llenarAgenda();
     
     // espero antes de imprimir para que no se solape con los mensajes de jade
     try {
		Thread.sleep(4000);
	 } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
     
     addBehaviour(new comportaminentoImprimir(agenda));

     
  }
 
  public void llenarAgenda(){
	  agenda= new ArrayList<Actividad>();
	  llenarHorarios();
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
	     	 agenda.add(new Actividad(auxMinutos,true));
	      }
	   }
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


