package agentes;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
 
public class Receptor extends Agent
{
    class ReceptorComportaminento extends SimpleBehaviour
    {
            private boolean fin = false;
            public void action()
            {
                System.out.println(" Preparandose para recibir");
 
            //Obtiene el primer mensaje de la cola de mensajes
                ACLMessage mensaje = receive();
 
                if (mensaje!= null)
                {
                    System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                    System.out.println(mensaje.getContent());//toString());
                    fin = true;
                }
            }
            public boolean done()
            {
                return fin;
            }
    }
    protected void setup()
    {
        addBehaviour(new ReceptorComportaminento());
    }
}