package agentes;

import java.util.ArrayList;

import jade.core.behaviours.OneShotBehaviour;

public class ComLlenarAgenda extends OneShotBehaviour
{
	private ArrayList<Actividad> agenda;
	private static int INTERVALO_DE_TIEMPO=60; //INTERVALO DEL TIEMPO PARA LA AGENDA, EN MINUTOS
	
	public ComLlenarAgenda(ArrayList<Actividad> agenda)
	{
		this.agenda=agenda;
	}	
	@Override
	public void action() 
	{
   	 llenarAgenda();
		 
	}
	public void llenarAgenda()
	  {		 
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
}
