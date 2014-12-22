package vista;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jvnet.substance.SubstanceLookAndFeel;

import java.awt.Font;

public class VentanaPrincipal extends JFrame 
{	
	public JButton btnAceptar,btnRegistro,btnLogin,btnSalir,btnAtras;
	public JLabel lblMensaje,lblNombre,lblContrasena;
	private JPanel principal;
	public JTextField txtNombre,txtContrasena;//para el 
	//private 

	public VentanaPrincipal() 
	{
       setBounds(50,100,400,400);
       setVisible(true);
       getContentPane().setLayout(null);
       
       SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.SaharaSkin");
		
       principal = new JPanel();
       principal.setBounds(0,0,384,362);
       principal.setLayout(null);
       principal.setBackground(getBackground().WHITE);
       getContentPane().add(principal);
       
       
       btnLogin = new JButton("LOGIN");
       btnLogin.setBounds(109,93,124,38);
       principal.add(btnLogin);
       
       btnRegistro = new JButton("REGISTRARSE");
       btnRegistro.setBounds(109,189,124,38);
       principal.add(btnRegistro);
       
       btnSalir = new JButton("SALIR");
       btnSalir.setBounds(281,321,93,30);
       principal.add(btnSalir);
       
       lblMensaje = new JLabel(" Ingrese los siguientes datos");
       lblMensaje.setFont(new Font("Arial Black", Font.PLAIN, 14));
	   lblMensaje.setBounds(79,29,229,30);
	   principal.add(lblMensaje);
	   lblMensaje.setVisible(false);
	   
	   lblNombre = new JLabel("Ingrese nombre de usuario");
	   lblNombre.setBounds(21,97,157,30);
	   principal.add(lblNombre);
	   lblNombre.setVisible(false);
	   
	   lblContrasena = new JLabel("Ingrese la contrasenia");
	   lblContrasena.setBounds(21,189,157,30);
	   principal.add(lblContrasena);
	   lblContrasena.setVisible(false);
       
	   txtNombre = new JTextField();
	   txtNombre.setBounds(202,97,130,30);
	   principal.add(txtNombre);
	   txtNombre.setVisible(false);
	   
	   txtContrasena = new JTextField();
	   txtContrasena.setBounds(202,189,130,30);
	   principal.add(txtContrasena);
	   txtContrasena.setVisible(false);
	   
       btnAceptar = new JButton("ACEPTAR");
       btnAceptar.setBounds(154,278,93,30);
       principal.add(btnAceptar);
       
       btnAtras = new JButton("ATRAS");
       btnAtras.setBounds(281, 278, 93, 30);
       btnAtras.setVisible(false);
       principal.add(btnAtras);
       
       btnAceptar.setVisible(false);

	}	
}
