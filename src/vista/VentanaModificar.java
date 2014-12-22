package vista;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import agentes.Actividad;

import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import java.util.ArrayList;

public class VentanaModificar extends JDialog 
{
   private JPanel principal;
   private JLabel lblHora,lblActividad;
   private JTextField txtActividad;
   public JButton btnAceptar;
   ArrayList<Actividad>a = new ArrayList<Actividad>();
   int hora=0;
	public VentanaModificar(ArrayList<Actividad> a,int hora) 
	{
		 this.a=a;
		 this.hora=hora;
         setBounds(150,50,300,200);
         setVisible(false);
         getContentPane().setLayout(null);
         
         principal = new JPanel();
         principal.setBounds(0,0,284,162);
         principal.setBackground(getBackground().WHITE);
         principal.setLayout(null);
         getContentPane().add(principal);
         
         lblHora = new JLabel("HORA : ");
         lblHora.setBounds(10, 10, 169, 22);
         principal.add(lblHora);
         
         txtActividad= new JTextField();
         txtActividad.setBounds(119, 63, 90, 22);
         principal.add(txtActividad);
         
         btnAceptar = new JButton("ACEPTAR");
         btnAceptar.setBounds(109, 107, 100, 22);
         principal.add(btnAceptar);
         
         lblActividad = new JLabel("Actividad");
         lblActividad.setBounds(21, 63, 92, 22);
         principal.add(lblActividad);
	}
	public void modificarActividad()
	{
		Actividad b = new Actividad(hora, false);
		b.setDetalle(txtActividad.getText());
		System.out.println("actividad nro "+ a.get(hora).getHora()+" hora disponible : "+a.get(hora).estaDisponible());
		a.set(hora, b);
	}
	public void sethora(int hora)
	{
		lblHora.setText(lblHora.getText() +""+ hora);
	}
}
