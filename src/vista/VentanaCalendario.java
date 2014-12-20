package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class VentanaCalendario extends JDialog 
{
	private JLabel lblFecha;
	private JTable tbTabla;
	private JScrollPane scrooltabla;
	private DefaultTableModel modelo; 
    private JPanel principal;
    public JButton btnAtras,btnOrganizar;
    
    
	public VentanaCalendario() 
	{
		setBounds(100,200,500,700);
		setVisible(true);
		
		 principal = new JPanel();
	     principal.setBounds(50,0,500,650);
	     principal.setLayout(null);
	     principal.setBackground(getBackground().WHITE);
	     getContentPane().add(principal);
	       
		String titulo[]={"HORA", "DETALLE"};
        String datos[][] = {};
     		  
     	modelo = new DefaultTableModel(datos,titulo);
     	tbTabla= new JTable(modelo);

     	scrooltabla = new JScrollPane(tbTabla); 
     	scrooltabla.setBounds(20,23,394,494);
     	principal.add(scrooltabla);
     	
     	btnAtras = new JButton("ATRAS");
     	btnAtras.setBounds(325, 586, 89, 23);
     	principal.add(btnAtras);
     	
     	btnOrganizar = new JButton("organizar");
     	btnOrganizar.setBounds(91, 586, 89, 23);
     	principal.add(btnOrganizar);
     
     	
     	LlenarTabla();
     	
	}
	private void LlenarTabla()
	{
		String datos[]={"","LIBRE"};
		int hora=0;
		for(int i=0;i<=23;i++)
		{
			datos[0] = ""+hora;			
		    datos[1] = "LIBRE";		    
			modelo.addRow(datos);
			hora ++;
		}
		
	}
}
