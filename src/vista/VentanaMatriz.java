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
	
	
	public void llenarTabla(String [][] tabla, String [] titulosColumnas){
		if(tabla!=null)
		{
			
			// poniendo las cabeseras de la tabla o titulos de las columnas
			for(int i = 0; i<titulosColumnas.length; i++)
			{
				this.modelo.addColumn(titulosColumnas[i]);//adiciona las cabeseras de la tabla;
			    System.out.println("SE puso la columna: "+titulosColumnas[i]);
			}
			
			//llenando la matriz fila por fila
		    for(int y = 0; y<24; y++)
			{
				  String [] fila = obtenerFila(tabla,y); 
				  System.out.println("se obtubo la fila N°: "+y);
				  for(int x = 0; x<tabla[0].length; x++)
				   {
					  modelo.addRow ( fila );
				      System.out.println("Se introdujo:  un elemente en ("+y+","+x+")" );	  
				   }
				  System.out.println("");
		    
			}	
			
			
			this.tbTabla.setModel(modelo);
			
			
			
			
		
		}else{
			
			System.out.println("##Tabla vacia");
		}
		
		
	}


/**
 * reetorna una fila de la tabla[][] ingresada
 * */
   private String[] obtenerFila(String[][] tabla, int fila) {
		String res[]= new String[tabla[0].length];
	    System.out.println("Cantidad de columnas= "+ res.length);
		for(int i =0; i <tabla[0].length;i++){
	    	 res[i]=tabla[fila][i]; 
	     }
	   
	   return res;
	
	}
public static void main(String [] args){
	
	VentanaMatriz vm = new VentanaMatriz();
	
	String cabeseras [] = {"Marcelo	","Juan","Jhon","katy"};
	String [][] tabla= new String[24][4];
	
	for(int y = 0; y<24; y++)
    {
		
	  for(int x = 0; x<tabla[0].length; x++)
	   {
         tabla[y][x]="True";
        
        }
	}	
	
	
	vm.llenarTabla(tabla, cabeseras);
		
	vm.setVisible(true);

	
	
	
	
	
	
}






}
