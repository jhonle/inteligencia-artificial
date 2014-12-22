package agentes;

import jade.core.behaviours.SimpleBehaviour;

import java.util.ArrayList;

class comportaminentoImprimir extends SimpleBehaviour
{     ArrayList<Actividad>agenda;
      public comportaminentoImprimir(ArrayList<Actividad> agenda)
      {
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
       	    	  System.out.println("hrs 0"+hora+":"+minutos+"0   |"+"               |"+agenda.get(i).estaDisponible());
       	    	  
       	      }else
       	       {
       	    	    minutos =(int)((agenda.get(i).getHora()*100)%100);
       	    	    if(minutos==0)System.out.println("hrs "+hora+":"+minutos+"0   |"+agenda.get(i).estaDisponible());
       	    	    else System.out.println("hrs "+hora+":"+minutos+"   |"+"               |"+agenda.get(i).estaDisponible());
       	       }
       	    } 
       	 
        }}
