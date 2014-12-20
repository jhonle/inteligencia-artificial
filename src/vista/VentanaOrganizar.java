package vista;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class VentanaOrganizar extends JDialog  
{

	private JPanel principal;
	public JTextField txtNombre;
	public JButton btnMensaje;
	public JLabel lblnombres;
	
	public VentanaOrganizar() 
	{
	   setBounds(50, 50,400,300);
	   getContentPane().setLayout(null);
	   setVisible(true);
	   
	   principal = new JPanel();
	   principal.setBounds(0,0,384,262);
	   principal.setBackground(getBackground().WHITE);
	   principal.setLayout(null);	   
	   getContentPane().add(principal);
	   
	    lblnombres= new JLabel();
	    lblnombres.setBounds(241, 47, 118, 159);
	    principal.add(lblnombres);
	   
	   btnMensaje = new JButton("enviar");
	   btnMensaje.setBounds(57, 174, 89, 23);
	   principal.add(btnMensaje);
	   
	   txtNombre = new JTextField();
	   txtNombre.setBounds(57, 90, 86, 20);
	   principal.add(txtNombre);	   
	}
	public void CargarMensaje(String mensaje)
	{
		lblnombres.setText(mensaje);
	}
}
