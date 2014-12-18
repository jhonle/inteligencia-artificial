package agentes;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;

public class VentanaPrincipal extends JFrame implements ActionListener 
{
	private JButton btnRegistro,btnLogin,btnSalir;
	public JButton btnAceptar;
	private JLabel lblMensaje,lblNombre,lblContrasena;
	private JPanel principal;
	public JTextField txtNombre,txtContrasena;//para el 
	//private 

	public VentanaPrincipal() 
	{
       setBounds(50,100,400,400);
       setVisible(true);
       getContentPane().setLayout(null);
       
       principal = new JPanel();
       principal.setBounds(0,0,384,362);
       principal.setLayout(null);
       principal.setBackground(getBackground().WHITE);
       getContentPane().add(principal);
       
       
       btnLogin = new JButton("LOGIN");
       btnLogin.setBounds(109,93,124,38);
       principal.add(btnLogin);
       
       btnRegistro = new JButton("REGISTRARSE");
       btnRegistro.setBounds(109,189,124,30);
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
	   lblNombre.setBounds(36,101,130,30);
	   principal.add(lblNombre);
	   lblNombre.setVisible(false);
	   
	   lblContrasena = new JLabel("Ingrese la contrase�a");
	   lblContrasena.setBounds(36,189,130,30);
	   principal.add(lblContrasena);
	   lblContrasena.setVisible(false);
       
	   txtNombre = new JTextField();
	   txtNombre.setBounds(176,97,130,30);
	   principal.add(txtNombre);
	   txtNombre.setVisible(false);
	   
	   txtContrasena = new JTextField();
	   txtContrasena.setBounds(176,189,130,30);
	   principal.add(txtContrasena);
	   txtContrasena.setVisible(false);
	   
       btnAceptar = new JButton("ACEPTAR");
       btnAceptar.setBounds(154,278,93,30);
       principal.add(btnAceptar);
       btnAceptar.setVisible(false);
	   
       btnLogin.addActionListener(this);
       btnRegistro.addActionListener(this);
       btnSalir.addActionListener(this);

	}	
	
	//eventos
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource().equals(btnRegistro))
    	{
    		lblMensaje.setVisible(true);
    		lblNombre.setVisible(true);
    		lblContrasena.setVisible(true);
    		txtNombre.setVisible(true);
    		txtContrasena.setVisible(true);
    		btnAceptar.setVisible(true);
    		
    		btnLogin.setVisible(false);
    		btnRegistro.setVisible(false);
    		btnSalir.setVisible(false);  		
    		
    	 }else
    	  { if(e.getSource().equals(btnLogin)){
    		  //Parte de login 
    		     
    		 } 
    	    if (e.getSource().equals(btnSalir)){
    	    	System.exit(0);
    	    	
    	    }
    	  }
    	
    	if(e.getSource().equals(btnAceptar)){
    		
    	  if(llenadoCompleto()){
    		  
    		  
    		  
    	  }
    	}
    	
    	
    	
    	
    	
       }
	
	
	
	
	/**
	 * verifica si se lleno correctamente los campos
	 * */
   public boolean llenadoCompleto() {
		
		return true;
	}

//solo para prubas
	public static void main (String []args){
	
		JFrame v = new VentanaPrincipal();
		v.setVisible(true);
		
    }
}
