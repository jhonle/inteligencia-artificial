package vista;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaMatriz extends JDialog
{
  private JPanel principal = new JPanel();
  private JTable tbTabla;
  private JScrollPane scrooltabla;
  private DefaultTableModel modelo; 
  
  public void anadirColumna(String columna)
  {
		modelo.addColumn(columna);
		
  }
  public void anadirfilas(String matriz[][],int tamano)
  {
	  
	  String datos[]= new String[tamano];
	  for(int i=0;i<=23;i++)
		{
		  for(int y =0;y<tamano;tamano++)
	      {
			datos[y]= matriz[y][i];	
		  }   
		}
	  modelo.addRow(datos);
		
  }
	public VentanaMatriz() 
	{
       setBounds(100, 40,425,439);
       getContentPane().setLayout(null);
       setVisible(false);
       
       principal.setBounds(0,0,409,401);
       principal.setLayout(null);
       principal.setBackground(getBackground().WHITE);
       getContentPane().add(principal);
       
       String titulo[]={};
       String datos[][] = {};
    		  
       modelo = new DefaultTableModel(datos,titulo);
       tbTabla= new JTable(modelo);

       scrooltabla = new JScrollPane(tbTabla); 
       scrooltabla.setBounds(10,38,389,298);
       principal.add(scrooltabla);       
	}
	

}
