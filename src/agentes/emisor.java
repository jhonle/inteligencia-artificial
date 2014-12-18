package agentes;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
 
public class emisor extends Agent
{
    class EmisorComportaminento extends SimpleBehaviour
    {
        boolean fin = false;
        public void action()
        {
            System.out.println(getLocalName() +": Preparandose para enviar un mensaje a receptor");
            AID id = new AID();
            id.setLocalName("Receptor");
 
        // Creaci�n del objeto ACLMessage
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST); 
        //Rellenar los campos necesarios del mensaje
            mensaje.setSender(getAID());
            mensaje.setLanguage("Espa�ol");
            mensaje.addReceiver(id);
            mensaje.setContent("Hola, que tal receptor"); 
        //Envia el mensaje a los destinatarios
            send(mensaje);
            System.out.println("Enviando hola a receptor");
            System.out.println(mensaje.toString());
            fin = true;
        }
 
        public boolean done()
        {
            return fin;
        }
    }
    protected void setup()
    {
        addBehaviour(new EmisorComportaminento());
    }
}