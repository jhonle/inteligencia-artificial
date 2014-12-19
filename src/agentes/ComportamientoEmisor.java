package agentes;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class ComportamientoEmisor extends SimpleBehaviour
{
    boolean fin = false;
    private String receptor,mensaje2;
    private AID emisor;
    public ComportamientoEmisor(String receptor,String mensaje2,AID emisor)
    {
    	this.receptor = receptor;
    	this.mensaje2 = mensaje2;
    	this.emisor = emisor;
    }
    public void action()
    {
        //System.out.println(getLocalName() +": Preparandose para enviar un mensaje a receptor");
        AID id = new AID();
        id.setLocalName(receptor);

        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST); 

        mensaje.setSender(emisor);
        mensaje.setLanguage("Espaniol");
        mensaje.addReceiver(id);
        mensaje.setContent(mensaje2);    
        //send(mensaje);
        
        System.out.println("Enviando hola a receptor");
        System.out.println(mensaje.toString());
        fin = true;
    }
    public boolean done()
    {
        return fin;
    }
}