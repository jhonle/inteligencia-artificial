package agentes;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.Vector;

import agentes.emisor.EmisorComportaminento;

public class AgenteX extends Agent {
  
  /**
   * representa la agenda de una persona 
   * por ahora solo la de 1 dia(00:00 hrs a 23:30 hrs)
   * */
  private ArrayList<Actividad> agenda;
  private static int INTERVALO_DE_TIEMPO=30; //INTERVALO DEL TIEMPO PARA LA AGENDA, EN MINUTOS
 
  protected void setup()
  {
	  llenarAgenda();
     
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
 
 
 


/*
 * Este metodo solo para probar 
 */
 public AgenteX()
 {
	llenarAgenda();
	
 } 

/*
 * metodo solo para probar.
 */
 public static void main(String[]args )
 {
	   AgenteX agent= new AgenteX();
	  /*
	  for (int i=0;i<agent.agenda.size();i++)
	  {
		  System.out.println("hrs "+agent.agenda.get(i).getHora());
	  } */
	   agent.imprimirAgenda();
  }
   
}
