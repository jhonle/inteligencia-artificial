package agentes;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaCalendario extends JDialog 
{
	private JLabel lblFecha;
	private JTable tbTabla;
	private JScrollPane scrooltabla;
	private DefaultTableModel modelo; 
    private JPanel principal;
	public VentanaCalendario() 
	{
		setBounds(100,200,500,700);
		setVisible(true);
		
		 principal = new JPanel();
	     principal.setBounds(0,0,500,700);
	     principal.setLayout(null);
	     principal.setBackground(getBackground().WHITE);
	     getContentPane().add(principal);
	       
		String titulo[]={"HORA", "DETALLE"};
        String datos[][] = {};
     		  
     	modelo = new DefaultTableModel(datos,titulo);
     	tbTabla= new JTable(modelo);

     	scrooltabla = new JScrollPane(tbTabla); 
     	scrooltabla.setBounds(20,23,396,614);
     	principal.add(scrooltabla);
     	
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
