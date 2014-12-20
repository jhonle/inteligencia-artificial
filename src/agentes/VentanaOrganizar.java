package agentes;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class VentanaOrganizar extends JDialog  
{

	private JPanel principal;
	public JButton btnEnviar;
	private ArrayList<Persona> listaPersona;
	private JLabel lblMensaje;
	private JCheckBox lista[] = new JCheckBox[20];
	private int i=0;
	public VentanaOrganizar(ArrayList<Persona> listaPersona) 
	{
	   this.listaPersona=listaPersona;
	   setBounds(50, 50,427,379);
	   getContentPane().setLayout(null);
	   setVisible(false);
	   
	   principal = new JPanel();
	   principal.setBounds(0,0,411,341);
	   principal.setBackground(getBackground().WHITE);
	   principal.setLayout(null);	   
	   getContentPane().add(principal);
	   
	   btnEnviar = new JButton("enviar");
	   btnEnviar.setBounds(312, 153, 89, 23);
	   principal.add(btnEnviar);
	   
	   lblMensaje = new JLabel("        Lista de personas");
	   lblMensaje.setBounds(116, 34, 147, 23);
	   principal.add(lblMensaje);
	   
	   Listar();
	}
	private void Listar()
	{		
		String nombres = "";
		
		int posY = 60;
    	for(Persona p : listaPersona)
    	{
    		nombres = p.getNombre();   		 		 		
    		
    		lista[i] = new JCheckBox(nombres);
    		lista[i].setBounds(30,posY,140,40);
    		principal.add(lista[i]);
    		i++;
    		posY = posY + 30;
    	}    	
	}	
	public ArrayList<String> ComprobrarLista()
	{
		
		ArrayList<String> respuesta = new ArrayList<String>();
		System.out.println("numero de personas : "+ i);	
		int p=0;
		for(int y=0;y<i;y++)
		{
			if(lista[p].isSelected()==true)
			{
				respuesta.add(lista[p].getText());
				p++;
			}
		}		
		return respuesta;
	}
}
