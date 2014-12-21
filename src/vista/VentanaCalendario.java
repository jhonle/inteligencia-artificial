package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import agentes.Actividad;

import java.awt.Component;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.util.ArrayList;

public class VentanaCalendario extends JDialog 
{
	private JLabel lblFecha,lblPersona;
	public JTable tbTabla;
	private JScrollPane scrooltabla;
	private DefaultTableModel modelo; 
    private JPanel principal;
    public JButton btnAtras,btnOrganizar;       
    public JPopupMenu popMenu;
    public JMenuItem JModificar;
    
	public VentanaCalendario() 
	{
		setBounds(100,20,488,576);
		setVisible(false);
		
		 principal = new JPanel();
	     principal.setBounds(50,0,500,550);
	     principal.setLayout(null);
	     principal.setBackground(getBackground().WHITE);
	     getContentPane().add(principal);
	       
		String titulo[]={"HORA", "DETALLE"};
        String datos[][] = {};
     		  
     	modelo = new DefaultTableModel(datos,titulo);     	
     	
     	popMenu = new JPopupMenu();
     	JModificar = new JMenuItem("modificar");
     	

     	popMenu.add(JModificar);
     	     	
     	tbTabla= new JTable(modelo);        
     	scrooltabla = new JScrollPane(tbTabla); 
     	scrooltabla.setBounds(20,53,394,446);
     	principal.add(scrooltabla);
     	principal.add(popMenu);
     	
     	
     	btnAtras = new JButton("ATRAS");
     	btnAtras.setBounds(325, 510, 89, 23);
     	principal.add(btnAtras);
     	
     	btnOrganizar = new JButton("organizar");
     	btnOrganizar.setBounds(88, 510, 89, 23);
     	principal.add(btnOrganizar);
     	
     	lblPersona = new JLabel();
     	lblPersona.setBounds(20, 11, 188, 23);
     	principal.add(lblPersona);
     	
	}
	public void ColocarNombre(String nombre)
	{
		lblPersona.setText("AGENDA DE : "+nombre);
	}
	public void LlenarTabla(ArrayList<Actividad>b)
	{
		String datos[]={"",""};
		int hora=0;
		System.out.println("empezando a leer de la lista");
		for(Actividad o : b)
		{
			System.out.println("leyendo de la lista");
			datos[0] = ""+hora;			
		    datos[1] = o.getDetalle();		    
			modelo.addRow(datos);
			hora ++;
		}
	}
    public void vaciarTabla()
    {
        int filas = modelo.getRowCount();
        if (filas > 0) 
        {
            for (int i = 0; i < filas; i++) 
            {                
              	modelo.removeRow(0);            	
            }
        }
    }
}
