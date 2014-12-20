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
	private JLabel lblFecha,lblPersona;
	private JTable tbTabla;
	private JScrollPane scrooltabla;
	private DefaultTableModel modelo; 
    private JPanel principal;
    public JButton btnAtras,btnOrganizar;       
    
	public VentanaCalendario() 
	{
		setBounds(100,20,488,576);
		setVisible(true);
		
		 principal = new JPanel();
	     principal.setBounds(50,0,500,550);
	     principal.setLayout(null);
	     principal.setBackground(getBackground().WHITE);
	     getContentPane().add(principal);
	       
		String titulo[]={"HORA", "DETALLE"};
        String datos[][] = {};
     		  
     	modelo = new DefaultTableModel(datos,titulo);
     	tbTabla= new JTable(modelo);

     	scrooltabla = new JScrollPane(tbTabla); 
     	scrooltabla.setBounds(20,53,394,446);
     	principal.add(scrooltabla);
     	
     	btnAtras = new JButton("ATRAS");
     	btnAtras.setBounds(325, 510, 89, 23);
     	principal.add(btnAtras);
     	
     	btnOrganizar = new JButton("organizar");
     	btnOrganizar.setBounds(88, 510, 89, 23);
     	principal.add(btnOrganizar);
     	
     	lblPersona = new JLabel();
     	lblPersona.setBounds(20, 11, 188, 23);
     	principal.add(lblPersona);
     
     	
     	LlenarTabla();
     	
	}
	public void ColocarNombre(String nombre)
	{
		lblPersona.setText("AGENDA DE : "+nombre);
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
